# 💬 Q&A Forum (Spring Boot)

![Java](https://img.shields.io/badge/Java_17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?logo=springsecurity&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)

**RU** | [EN](#english)

Форум вопросов и ответов в духе Stack Overflow на Spring Boot. Есть веб-интерфейс на Thymeleaf и REST API.

## Возможности
- ❓ Вопросы, ответы, комментарии, теги, голосование
- 🔐 Регистрация и вход на Spring Security, роли пользователей, страница «доступ запрещён»
- 👮 Админ-панель для управления пользователями и ролями
- 🔌 REST API для всех сущностей (`*ApiController`) с документацией OpenAPI / Swagger
- 🧱 Слоистая архитектура: controller → service → repository (Spring Data JPA)
- ✅ Валидация и глобальный обработчик ошибок

## Стек
Java 17 · Spring Boot · Spring MVC · Spring Security · Spring Data JPA · Thymeleaf · PostgreSQL · springdoc-openapi · Maven

## Запуск
1. Создай БД `qa_db` в PostgreSQL.
2. Задай `DB_USER` и `DB_PASSWORD` в переменных окружения (по умолчанию `postgres` / `postgres`).
3. ```bash
   ./mvnw spring-boot:run
   ```
   Сайт: http://localhost:6767, Swagger UI: http://localhost:6767/swagger-ui.html

---

## English

A Stack Overflow-style Q&A forum: questions, answers, comments, tags and votes, with a Thymeleaf web UI and a REST API.

**Features:** Spring Security sign-up/sign-in with roles · admin panel · REST API documented with OpenAPI/Swagger · layered architecture on Spring Data JPA · validation and global error handling.

**Run:** create the `qa_db` PostgreSQL database, set `DB_USER` / `DB_PASSWORD`, run `./mvnw spring-boot:run` and open http://localhost:6767.
