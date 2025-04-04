package ru.yandex.practicum.tarasov.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DirectoryCreator {
    private final String location;

    public DirectoryCreator(@Value("${spring.images.location}") String location) {
        this.location = location;
    }

    @PostConstruct
    public void createDirectory()  {

        Path imagePath = Path.of(location);
        if(!Files.exists(imagePath)) {
            try {
                Files.createDirectories(imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
