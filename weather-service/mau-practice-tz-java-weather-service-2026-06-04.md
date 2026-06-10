---
id: "70.activities.department-internal.internships.mau-practice-tz-java-weather-service-2026-06-04"
type: "activity"
title: "Техническое задание — Java Weather Snapshot Service"
status: "active"
created: "2026-06-04"
updated: "2026-06-04"
tags: ["department-internal", "internships", "mau", "assignment", "java", "backend"]
summary: "Техническое задание: базовый backend-сервис на Java/Spring Boot в стиле GID-сервисов."
kind: "assignment"
phase: "requirements prepared"
owner: "[[40_People/vlad-metelyagin|Влад Метелягин]]"
project: "[[70_Activities/department-internal/department-internal|Department Internal]]"
sources: ["[[70_Activities/department-internal/internships/mau-practice-metelyagin-2026|Практика МАУ — группа Метелягина 2026]]", "[[70_Activities/planning/pm/Planning_tasks/отдать-студентам-требования-к-базовым-се|Отдать студентам требования к базовым сервисам]]"]
confidential: true
contains_pii: false
---

# Техническое задание — Java Weather Snapshot Service

## Задача

Разработать backend-сервис на Java/Spring Boot: REST API, внешний HTTP-клиент,
PostgreSQL, Liquibase, кэш, OpenAPI, метрики и автотесты.

Сервис получает текущую погоду по названию города, сохраняет результат в базу
и отдает нормализованный ответ клиенту.

## Стек проекта

Целевой стек:

- Spring Boot 3.x;
- Java 21;
- Gradle Kotlin DSL;
- PostgreSQL;
- Liquibase;
- OpenFeign или WebClient для внешних HTTP-вызовов;
- springdoc-openapi;
- Actuator;
- Micrometer/Prometheus;
- JUnit 5;
- Testcontainers;
- WireMock.

Кэш:

- обязательный минимум: Caffeine;
- допустимое усложнение: Redis.

Kotlin не использовать в этой задаче.

## Этапы выполнения

### Этап 1. Репозиторий и каркас приложения

- Создать GitHub-репозиторий. +
- Создать Spring Boot проект на Java 21. +
- Настроить Gradle Kotlin DSL. +
- Добавить базовые зависимости. + (добавил только Spring Web при создании)
- Добавить `README.md`, `.env.example`, `docker-compose.yml`. +
- Добавить `application.yml`. +
- Реализовать старт приложения. +
- Проверить `GET /actuator/health`. +

Результат этапа: приложение стартует локально, health endpoint доступен.

### Этап 2. Клиент внешнего weather API

- Добавить слой `client`. +
- Выбрать OpenFeign или WebClient. + (выбрал WebClient)
- Реализовать geocoding-запрос: город -> координаты. +
- Реализовать forecast-запрос: координаты -> текущая погода. +
- Нормализовать внешний ответ во внутренний DTO/model. +
- Настроить timeout внешнего клиента. +
- Добавить обработку ошибок внешнего API. +

Результат этапа: сервисный слой может получить текущую погоду через отдельный
client-слой.

### Этап 3. Работа с PostgreSQL и Liquibase

- Поднять PostgreSQL через Docker Compose. +
- Добавить Liquibase changelog. +
- Создать таблицу `weather_snapshot`. +
- Создать индекс по `city, created_at`. +
- Добавить JPA entity. +
- Добавить repository. +
- Реализовать сохранение snapshot. +
- Реализовать чтение истории по городу. +

Результат этапа: приложение применяет миграции и работает с таблицей
`weather_snapshot`.

### Этап 4. REST API текущей погоды без кэша

- Реализовать `WeatherController`. +
- Реализовать `WeatherService`. +
- Добавить `GET /api/v1/weather/current`. +
- Добавить валидацию `city`. +
- При успешном запросе вызвать внешний API. +
- Сохранить snapshot в PostgreSQL. +
- Вернуть нормализованный response DTO. +
- Добавить единый JSON-формат ошибок. -

Результат этапа: `/current` работает без кэша и сохраняет результат в базу.

### Этап 5. Кэширование

- Добавить Caffeine cache.
- Настроить TTL 5 минут.
- Использовать нормализованный `city` как cache key.
- Проверять кэш до вызова внешнего API.
- При cache hit возвращать `cached: true`.
- При cache miss вызывать внешний API и сохранять результат в кэш.
- Не кэшировать ошибки.

Результат этапа: повторный запрос по тому же городу в пределах TTL не вызывает
внешний API повторно.

### Этап 6. REST API истории

- Добавить `GET /api/v1/weather/history`.
- Поддержать параметры `city` и `limit`.
- Установить `limit` по умолчанию 10.
- Ограничить `limit` максимумом 50.
- При пустой истории возвращать пустой список.

Результат этапа: `/history` возвращает последние сохраненные записи по городу.

### Этап 7. OpenAPI, Actuator и метрики

- Подключить springdoc OpenAPI.
- Проверить Swagger UI.
- Включить Actuator health.
- Включить Prometheus endpoint.
- Добавить простые метрики для запросов и cache hit/cache miss.

Результат этапа: Swagger, health и Prometheus endpoint доступны локально.

### Этап 8. Автотесты

- Добавить unit-тест `WeatherService`.
- Добавить controller test на `400`.
- Добавить WireMock-тест успешного ответа внешнего API.
- Добавить WireMock-тест ошибки внешнего API.
- Добавить Testcontainers-тест сохранения snapshot в PostgreSQL.

Результат этапа: `./gradlew test` проходит локально.

### Этап 9. README и PR

- Описать запуск PostgreSQL.
- Описать запуск приложения.
- Описать env-переменные.
- Добавить примеры curl.
- Добавить ссылку на Swagger.
- Открыть pull request на ревью.

Результат этапа: PR содержит рабочий сервис, тесты и инструкции запуска.

## Функциональные требования

### API

Сервис должен поднять REST API:

```http
GET /api/v1/weather/current?city=Moscow
GET /api/v1/weather/history?city=Moscow&limit=10
GET /api/v1/health
```

Также должны быть доступны:

```http
GET /actuator/health
GET /actuator/prometheus
GET /swagger-ui/index.html
```

`GET /api/v1/weather/current`:

- принимает query-параметр `city`;
- возвращает `400`, если город пустой или короче 2 символов;
- нормализует город для cache key;
- сначала ищет результат в кэше;
- при cache miss вызывает внешний weather API;
- сохраняет snapshot в PostgreSQL;
- возвращает нормализованный DTO.

`GET /api/v1/weather/history`:

- принимает `city` и `limit`;
- возвращает последние сохраненные записи по городу;
- `limit` по умолчанию 10, максимум 50;
- при пустой истории возвращает пустой список.

Пример DTO:

```json
{
  "city": "Moscow",
  "provider": "open-meteo",
  "temperatureCelsius": -2.4,
  "windSpeed": 4.1,
  "observedAt": "2026-06-04T09:00:00Z",
  "cached": false
}
```

## Внешнее API

Рекомендуемый внешний API — Open-Meteo:

- geocoding API: город -> координаты;
- forecast API: координаты -> текущая погода.

Если используется OpenWeatherMap, ключ хранится только в env/config и не
коммитится.

## Зависимости

Обязательные зависимости:

- `org.springframework.boot:spring-boot-starter-web`;
- `org.springframework.boot:spring-boot-starter-validation`;
- `org.springframework.boot:spring-boot-starter-data-jpa`;
- `org.springframework.boot:spring-boot-starter-actuator`;
- `org.postgresql:postgresql`;
- `org.liquibase:liquibase-core`;
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`;
- `com.github.ben-manes.caffeine:caffeine` или Spring Cache + Caffeine;
- `org.springframework.boot:spring-boot-starter-test`;
- `org.testcontainers:junit-jupiter`;
- `org.testcontainers:postgresql`;
- WireMock для внешнего API.

Optional:

- `spring-cloud-starter-openfeign`, если выбран Feign;
- `spring-boot-starter-data-redis`, если выбран Redis;
- `micrometer-registry-prometheus`;
- Sentry/OpenTelemetry только после рабочего MVP.

## База данных

Liquibase changelog должен создать таблицу:

```sql
weather_snapshot(
  id uuid primary key,
  city text not null,
  provider text not null,
  temperature_celsius numeric(5,2),
  wind_speed numeric(6,2),
  raw_payload jsonb,
  observed_at timestamptz not null,
  created_at timestamptz not null
)
```

Нужен индекс:

```sql
create index idx_weather_snapshot_city_created_at
  on weather_snapshot (city, created_at desc);
```

Допустимо описать миграцию через SQL внутри Liquibase changelog. Модель не
нужно усложнять сверх указанной таблицы.

## Кэш

Минимальный вариант:

- Caffeine;
- TTL 5 минут;
- ключ: нормализованный city;
- в ответе показывать `cached: true/false`;
- ошибки внешнего API не кэшировать.

Усложнение:

- Redis вместо Caffeine;
- отдельный тест cache hit/cache miss;
- настройка TTL через `application.yml`.

## Рекомендуемая структура

```text
src/main/java/ru/gid/practice/weather
  WeatherApplication.java
  config/
  controller/
  dto/
  service/
  client/
  repository/
  entity/
  mapper/
src/main/resources
  application.yml
  db/changelog/
src/test/java/ru/gid/practice/weather
docker-compose.yml
.env.example
README.md
```

Главные классы:

- `WeatherController` — REST API;
- `WeatherService` — бизнес-логика cache -> external API -> DB;
- `WeatherProviderClient` — внешний API;
- `WeatherSnapshotRepository` — JPA repository;
- `WeatherSnapshotEntity` — entity;
- DTO для request/response.

## Обработка ошибок

- `400` — пустой/невалидный город;
- `404` — внешний geocoding API не нашел город;
- `502` — внешний weather API недоступен или вернул невалидный ответ;
- `500` — непредвиденная внутренняя ошибка.

Ошибки должны возвращаться единым JSON-форматом:

```json
{
  "code": "WEATHER_PROVIDER_UNAVAILABLE",
  "message": "Weather provider is unavailable"
}
```

## Автотесты

Минимум:

- unit-тест `WeatherService` на cache hit/cache miss;
- controller test на `400` для пустого city;
- WireMock-тест успешного ответа внешнего API;
- WireMock-тест ошибки внешнего API;
- Testcontainers-тест сохранения snapshot в PostgreSQL.

Тесты должны запускаться одной командой:

```bash
./gradlew test
```

## Что должно быть в README

- Назначение сервиса.
- Требования: Java version, Docker.
- Как поднять PostgreSQL.
- Как запустить приложение.
- Переменные окружения.
- Примеры curl.
- Где Swagger.
- Как запустить тесты.
- Known limitations.

## Definition of Done

- Репозиторий создан в GitHub.
- Есть pull request на ревью.
- Сервис стартует локально.
- PostgreSQL поднимается через Docker Compose.
- Liquibase применяет миграции.
- Swagger доступен.
- `/current` возвращает погоду и сохраняет запись.
- Повторный запрос в TTL возвращает `cached: true`.
- `/history` возвращает историю.
- `./gradlew test` проходит.
- В README есть команды запуска и проверки.

## Формат PR на ревью

```md
## Что сделано

## Как запустить

## Как проверить

## Тесты

## Ограничения / вопросы
```
