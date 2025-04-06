package ru.yandex.practicum.tarasov.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.tarasov.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>, CrudRepository<Post, Long> {

    //@Query("select id, title, content, likes, tags from post where concat_ws(',', tags) like concat('%',:tag,'%')")
    @Query("select id, title, content, likes, tags from post p where :tag = ANY(tags)")
    List<Post> findByTag(@Param("tag") String tag);

}
