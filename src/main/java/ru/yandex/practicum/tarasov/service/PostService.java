package ru.yandex.practicum.tarasov.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.model.Comment;
import ru.yandex.practicum.tarasov.model.Post;
import ru.yandex.practicum.tarasov.repository.CommentRepository;
import ru.yandex.practicum.tarasov.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Page<Post> findAll(int page, int size) {
        return postRepository.findAll(Pageable.ofSize(size).withPage(page));
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        postRepository.deleteById(id);
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
        //System.out.println(comment.getId());
        //post.getComments().put(comment.getId(), comment);
        //post = postRepository.save(post);

    }

    public Post changeComment(long postId, long commentId, String commentText) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.getComments().get(commentId).setContent(commentText);
        return postRepository.save(post);
    }

    public Post deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.getComments().remove(commentId);
        return postRepository.save(post);
    }

}
