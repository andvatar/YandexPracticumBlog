package ru.yandex.practicum.tarasov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.tarasov.model.Comment;
import ru.yandex.practicum.tarasov.model.Post;
import ru.yandex.practicum.tarasov.repository.CommentRepository;
import ru.yandex.practicum.tarasov.repository.PostRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final String imageLocation;

    @Autowired
    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       @Value("${spring.images.location}") String imageLocation) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.imageLocation = imageLocation;
    }

    public void save(Post post, MultipartFile image) {
        post = postRepository.save(post);
        if (image != null) {
            File imageFile = new File(imageLocation + "/" + post.getId()).getAbsoluteFile();
            try {
                image.transferTo(imageFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Page<Post> findAll(String tag, int page, int size) {
        if (tag == null || tag.isEmpty()) {
            return postRepository.findAll(Pageable.ofSize(size).withPage(page));
        }
        else {
            List<Post> posts = postRepository.findByTag(tag);

            PageRequest pageRequest = PageRequest.of(page, size);
            int start = (int)pageRequest.getOffset();
            int end = Math.min(start + pageRequest.getPageSize(), posts.size());

            return new PageImpl<>(posts.subList(start, end), pageRequest, posts.size());
        }
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        postRepository.deleteById(id);
        try {
            Files.delete(Path.of(imageLocation + "/" + id));
        } catch (IOException e) {
            // логирование делать не требовали
            System.out.println("File was not deleted");
        }
    }

    public Post postLike(long id, boolean like) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (like) {
            post.setLikes(post.getLikesCount() + 1);
        }
        else {
            post.setLikes(post.getLikesCount() - 1);
        }
        post = postRepository.save(post);
        return post;
    }

    public Post addComment(Long postId, String commentText) {
        if(postRepository.existsById(postId)) {
            Comment comment = new Comment();
            comment.setContent(commentText);
            comment.setPostId(postId);
            commentRepository.save(comment);
            return postRepository.findById(postId).get();
        }
        else {
            throw new RuntimeException("Post not found");
        }
    }

    public Post changeComment(long postId, long commentId, String commentText) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if(!post.getComments().containsKey(commentId)) {
            throw new RuntimeException("Comment not found");
        }
        post.getComments().get(commentId).setContent(commentText);
        return postRepository.save(post);
    }

    public Post deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.getComments().remove(commentId);
        return postRepository.save(post);
    }
}
