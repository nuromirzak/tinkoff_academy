<!-- Improved compatibility of вернуться в начало link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/nuromirzak/tinkoff_academy">
    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Telegram_logo.svg/240px-Telegram_logo.svg.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Телеграм бот оповещатель</h3>

  <p align="center">
    Многомодульный проект для отслеживания обновлений и отправки уведомлений через телеграм бота
    <br />
    <a href="https://github.com/nuromirzak/tinkoff_academy"><strong>Ознакомится с проектом »</strong></a>
    <br />
    <a href="https://github.com/nuromirzak/tinkoff_academy/issues">Сообщить об ошибке</a>
    ·
    <a href="https://github.com/nuromirzak/tinkoff_academy/issues">Запросить фичу</a>
  </p>
</div>

*Читайте на других языках: [English](README.en.md), [Русский](README.md)*

<!-- TABLE OF CONTENTS -->

## Содержание

1. [О Проекте](#о-проекте)
2. [Создано С Помощью](#создано-с-помощью)
3. [Начало Работы](#начало-работы)
    - [Предварительные Требования](#предварительные-требования)
    - [Установка](#установка)
4. [Вклад](#вклад)
5. [Лицензия](#лицензия)

<!-- ABOUT THE PROJECT -->

## О Проекте

Этот проект, разработанный в рамках курса Тинькофф Академия, фокусируется на создании двух отдельных веб-сервисов,
предназначенных для отслеживания обновлений контента через различные ссылки. Сервисы были специально разработаны для
мониторинга вопросов на StackOverflow и обновлений в репозиториях GitHub.

Эти подписки, также известные как ссылки, легко управляются через интерактивный чат с ботом в Telegram. Бот отправляет
уведомления в реальном времени в подписанный чат, как только обнаруживает новые изменения или обновления, обеспечивая
вашу информированность в любое время.

Проект состоит из трех модулей Maven:

- bot: Этот модуль предоставляет бота в Telegram, разработанного для облегчения взаимодействия со службой.
- link-parser: Этот модуль отвечает за разбор содержимого URL.
- scrapper: Этот модуль работает как фоновая задача, которая сканирует отслеживаемые ссылки и сохраняет их в базе
  данных.

Хранение данных обеспечивается надежной системой управления базами данных PostgreSQL, обеспечивающей надежные и
безопасные решения для хранения данных. Система разработана для взаимодействия с базой данных тремя способами для
повышения гибкости:

- JDBC: Этот API подключает приложения Java к базе данных.
- JOOQ: Этот инструмент позволяет строить типобезопасные SQL-запросы.
- Spring Data JPA: Этот модуль упрощает реализацию слоев доступа к данным.

<p align="right">(<a href="#readme-top">вернуться в начало</a>)</p>

### Создано С Помощью

* [![Spring][Spring]][Spring-url]
* [![PostgreSQL][PostgreSQL]][PostgreSQL-url]
* [![IntelliJ IDEA][IntelliJ IDEA]][IntelliJ IDEA-url]

<p align="right">(<a href="#readme-top">вернуться в начало</a>)</p>



<!-- GETTING STARTED -->

## Начало Работы

Следуйте этим шагам для установки и запуска проекта.

### Предварительные Требования

Убедитесь, что следующее установлено на вашем локальном компьютере:

- Java 17
- Maven
- Docker

### Установка

1. Клонируйте этот репозиторий на свой локальный компьютер:
   ```sh
   git clone https://github.com/nuromirzak/tinkoff_academy.git
   ```
2. Перейдите в каталог проекта:
   ```sh
   cd tinkoff_academy
   ```
3. Установите необходимые переменные окружения:

    - `MY_BOT_TOKEN_ENV` должен быть установлен на ваш токен бота. Вы также можете изменить значение `app.bot_token` в
      модуле `bot`.
    - Установите `app.bot_username` на имя вашего бота в Telegram в модуле `bot`.
    - (Необязательно) Остальные настройки не являются обязательными и могут быть настроены согласно вашим требованиям.
      Вы можете просмотреть и изменить их в `application.properties/yaml`.

4. Запустите скрипт для запуска проекта:
   ```sh
   ./start.sh
   ```

<p align="right">(<a href="#readme-top">вернуться в начало</a>)</p>



<!-- CONTRIBUTING -->

## Вклад

Вклады делают сообщество открытого исходного кода таким удивительным местом для обучения, вдохновения и творчества.
Любой вклад, который вы сделаете, **будет очень ценен**.

Если у вас есть предложение, которое сделает это лучше, пожалуйста, сделайте fork репозитория и создайте pull request.
Вы также можете просто открыть вопрос с тегом "enhancement".
Не забудьте поставить проекту звезду! Еще раз спасибо!

1. Сделайте Fork Проекта
2. Создайте Ветку для Новой Фичи (`git checkout -b feature/AmazingFeature`)
3. Совершите Commit Ваших Изменений (`git commit -m 'Add some AmazingFeature'`)
4. Выгрузите в Ветку (`git push origin feature/AmazingFeature`)
5. Откройте Pull Request

<p align="right">(<a href="#readme-top">вернуться в начало</a>)</p>



<!-- LICENSE -->

## Лицензия

Распространяется по лицензии MIT. Смотрите `LICENSE.txt` - для получения дополнительной информации.

<p align="right">(<a href="#readme-top">вернуться в начало</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/nuromirzak/tinkoff_academy.svg?style=for-the-badge

[contributors-url]: https://github.com/nuromirzak/tinkoff_academy/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/nuromirzak/tinkoff_academy.svg?style=for-the-badge

[forks-url]: https://github.com/nuromirzak/tinkoff_academy/network/members

[stars-shield]: https://img.shields.io/github/stars/nuromirzak/tinkoff_academy.svg?style=for-the-badge

[stars-url]: https://github.com/nuromirzak/tinkoff_academy/stargazers

[issues-shield]: https://img.shields.io/github/issues/nuromirzak/tinkoff_academy.svg?style=for-the-badge

[issues-url]: https://github.com/nuromirzak/tinkoff_academy/issues

[license-shield]: https://img.shields.io/github/license/nuromirzak/tinkoff_academy.svg?style=for-the-badge

[license-url]: https://github.com/nuromirzak/tinkoff_academy/blob/master/LICENSE.txt

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://linkedin.com/in/nurmukhammed

[Spring]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white

[Spring-url]: https://spring.io/

[PostgreSQL]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white

[PostgreSQL-url]: https://www.postgresql.org/

[IntelliJ IDEA]: https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white

[IntelliJ IDEA-url]: https://www.jetbrains.com/idea/
