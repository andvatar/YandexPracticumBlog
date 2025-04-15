Учебный проект в Yandex practicum - приложение-блог

Стартовая страница - http://localhost:8080/yandex-practicum-blog/posts

Используется БД postgresql для прода и h2 для тестов

Изображения хранятся в файловой системе, путь к директории задается параметром {spring.images.location} в application.properties. Если директории не существует, она будет создана при старте приложения

В текущей версии используется Spring Boot и Gradle.
Для сборки приложения используется команда ./gradle bootJar