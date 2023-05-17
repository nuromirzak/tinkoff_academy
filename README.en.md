<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
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

<h3 align="center">Update Notifier Telegram Bot</h3>

  <p align="center">
    Multi-module project for tracking updates and sending notifications via Telegram bot
    <br />
    <a href="https://github.com/nuromirzak/tinkoff_academy"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/nuromirzak/tinkoff_academy">View Demo</a>
    ·
    <a href="https://github.com/nuromirzak/tinkoff_academy/issues">Report Bug</a>
    ·
    <a href="https://github.com/nuromirzak/tinkoff_academy/issues">Request Feature</a>
  </p>
</div>

*Read this in other languages: [English](README.en.md), [Русский](README.md)*

<!-- TABLE OF CONTENTS -->

## Table of Contents

1. [About The Project](#about-the-project)
2. [Built With](#built-with)
3. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
4. [Contributing](#contributing)
5. [License](#license)

<!-- ABOUT THE PROJECT -->

## About The Project

This project, developed as part of the Tinkoff Academy course, focuses on creating two distinct web services designed to
track content updates through various links. The services have been specifically crafted to monitor issues on
StackOverflow and updates on GitHub repositories.

These subscriptions, also referred to as links, are effortlessly managed via an interactive chat with a bot on Telegram.
The bot sends real-time notifications to the subscribed chat whenever new changes or updates are detected, ensuring you
stay informed at all times.

The project is composed of three Maven modules:

- Bot: This module provides a Telegram bot designed to facilitate interaction with the service.
- Link-parser: This module is responsible for parsing the content of the URL.
- Scrapper: This module operates as a background task that scrapes the tracked links and stores them in the database.

Data storage is handled by the robust PostgreSQL Database Management System (DBMS), ensuring reliable and secure storage
solutions. The system is designed to interact with the database in three ways for increased flexibility:

- JDBC: This API connects the Java applications with the database.
- JOOQ: This tool enables the building of type-safe SQL queries.
- Spring Data JPA: This module simplifies the implementation of data access layers.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* [![Spring][Spring]][Spring-url]
* [![PostgreSQL][PostgreSQL]][PostgreSQL-url]
* [![IntelliJ IDEA][IntelliJ IDEA]][IntelliJ IDEA-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->

## Getting Started

Follow these steps to set up and run the project.

### Prerequisites

Ensure you have the following installed on your local machine:

- Java 17
- Maven
- Docker

### Installation

1. Clone this repository to your local machine:
   ```sh
   git clone https://github.com/nuromirzak/tinkoff_academy.git
   ```
2. Navigate to the project directory:
   ```sh
   cd tinkoff_academy
   ```
3. Set the necessary environment variables:

    - `MY_BOT_TOKEN_ENV` should be set to your bot token. You can alternatively change the `app.bot_token` value in
      the `bot` module.
    - Set `app.bot_username` to your own Telegram bot username in the `bot` module.
    - (Optional) Other configurations are optional and can be customized as per your requirements. You can view and
      modify these in the `application.properties/yaml` files.

4. Run the script to run the project:
   ```sh
   ./start.sh
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also
simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



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
