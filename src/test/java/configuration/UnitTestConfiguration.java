package configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import ru.yandex.practicum.tarasov.repository.CommentRepository;
import ru.yandex.practicum.tarasov.repository.PostRepository;
import ru.yandex.practicum.tarasov.service.PostService;

@Configuration
@ComponentScan({"unit"})
@Profile("Test")
public class UnitTestConfiguration {
    @Bean
    public PostRepository mockPostRepository() {
        return Mockito.mock(PostRepository.class);
    }

    @Bean
    public CommentRepository mockedCommentRepository() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    public PostService postService() {
        return new PostService(mockPostRepository(), mockedCommentRepository(), "");
    }

}
