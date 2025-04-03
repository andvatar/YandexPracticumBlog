package ru.yandex.practicum.tarasov.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Post {
    @Id
    private long id;
    private String title;
    private String content;
    private int likes;
    private Set<String> tags;
    @MappedCollection(idColumn = "post_id", keyColumn = "id")
    private Map<Long, Comment> comments;

    public Post() {
    }

    public Post(long id, String title, String content, int likes, Set<String> tags, Map<Long, Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.tags = tags;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikesCount() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Map<Long, Comment> getComments() {
        return comments;
    }

    public List<Comment> getCommentsAsList() {
        return comments.values().stream().toList();
    }

    public void setComments(Map<Long, Comment> comments) {
        this.comments = comments;
    }

    public String getTagsAsText() {
        return String.join(", ", tags);
    }

    public String getTextPreview() {
        return content.substring(0, Math.min(content.length(), 300));
    }

    public void incrementLikes() {
        likes++;
    }

    public List<String> getTextParts() {
        return Arrays.stream(content.split("\n")).toList();
    }
}
