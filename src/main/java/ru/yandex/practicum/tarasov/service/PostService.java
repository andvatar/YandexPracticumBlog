package ru.yandex.practicum.tarasov.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.model.Post;
import ru.yandex.practicum.tarasov.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
}
