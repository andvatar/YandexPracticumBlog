package ru.yandex.practicum.tarasov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.tarasov.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
