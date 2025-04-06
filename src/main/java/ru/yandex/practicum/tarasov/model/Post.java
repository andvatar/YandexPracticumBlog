package ru.yandex.practicum.tarasov.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.*;

@Table("post")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && likes == post.likes && Objects.equals(title, post.title) && Objects.equals(content, post.content) && Objects.equals(tags, post.tags) && Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, likes, tags, comments);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("content", content)
                .append("likes", likes)
                .append("tags", tags)
                .append("comments", comments)
                .toString();
    }
}
