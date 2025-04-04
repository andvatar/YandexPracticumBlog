package unit;

import configuration.UnitTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.tarasov.model.Comment;
import ru.yandex.practicum.tarasov.model.Post;
import ru.yandex.practicum.tarasov.repository.CommentRepository;
import ru.yandex.practicum.tarasov.repository.PostRepository;
import ru.yandex.practicum.tarasov.service.PostService;

import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {UnitTestConfiguration.class})
@ActiveProfiles("Test")
class PostServiceTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;

    @Test
    public void addCommentToExistingPost() {

        Comment expectedComment = new Comment(1L, 1L, "Test comment");
        Post testPost = new Post(1L, "Test title", "Test content", 0, null, null);

        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(postRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.save(any(Comment.class))).thenAnswer(i ->
                                                                    { Comment comment = (Comment)i.getArguments()[0];
                                                                      comment.setId(1L);
                                                                      return comment;
                                                                    });

        postService.addComment(1L, "Test comment");

        verify(commentRepository, times(1)).save(eq(expectedComment));
    }

    @Test
    public void addCommentToNotExistingPost() {
        when(postRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> postService.addComment(1L, "Test comment"));

        verify(commentRepository, times(0)).save(any(Comment.class));

        String expectedMessage = "Post not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void changeExistingComment() {
        Comment testComment = new Comment();
        testComment.setPostId(1L);
        testComment.setContent("Test comment");

        Comment expectedComment = new Comment(1L, 1L, "Test comment");
        Post expectedPost = new Post(1L, "Test title", "Test content", 0, null, new TreeMap<>());
        expectedPost.getComments().put(1L, expectedComment);

        when(postRepository.findById(1L)).thenReturn(Optional.of(expectedPost));
        when(postRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.save(testComment)).thenReturn(expectedComment);

        Post actualPost = postService.addComment(1L, "Test comment");

        assertEquals(expectedPost, actualPost);
        verify(commentRepository, times(1)).save(eq(testComment));
    }

    @Test
    public void changeCommentToNotExistingPost() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> postService.changeComment(1L, 1L, "Test comment"));

        verify(commentRepository, times(0)).save(any(Comment.class));
        verify(postRepository, times(0)).save(any(Post.class));

        String expectedMessage = "Post not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void changeNotExistingComment() {
        Post testPost = new Post(1L, "Test title", "Test content", 0, null, new TreeMap<>());
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));

        Exception exception = assertThrows(RuntimeException.class, () -> postService.changeComment(1L, 1L, "Test comment"));

        verify(commentRepository, times(0)).save(any(Comment.class));
        verify(postRepository, times(0)).save(any(Post.class));

        String expectedMessage = "Comment not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}