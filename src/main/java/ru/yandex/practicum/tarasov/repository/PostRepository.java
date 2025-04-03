package ru.yandex.practicum.tarasov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.tarasov.model.Post;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>, CrudRepository<Post, Long> {

}
