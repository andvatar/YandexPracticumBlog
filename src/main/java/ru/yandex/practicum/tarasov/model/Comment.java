package ru.yandex.practicum.tarasov.model;

import org.springframework.data.annotation.Id;

public class Comment {
    @Id
    private long id;
    private String content;

    public Comment() {

    }

    public Comment(long id, long postId, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
