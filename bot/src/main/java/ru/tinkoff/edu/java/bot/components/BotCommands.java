package ru.tinkoff.edu.java.bot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Регистрация пользователя"),
            new BotCommand("/help", "Получить список доступных команд"),
            new BotCommand("/track", "Подписаться на рассылку"),
            new BotCommand("/untrack", "Отписаться от рассылки"),
            new BotCommand("/list", "Вывести список отслеживаемых ссылок")
    );

    String HELP_TEXT = """
            Этот бот поможет подписаться на рассылку изменений Stackoverlow вопросов и Github репозиториев.
            
            Для этого вам нужно ввести команду /track и ссылку на вопрос или репозиторий.
            Например, /track https://stackoverflow.com/questions/123456789
            
            Если хотите отписаться от рассылки, введите команду /untrack и ссылку на вопрос или репозиторий.
            Например, /untrack https://stackoverflow.com/questions/123456789
            
            Для получения списка отслеживаемых ссылок введите команду /list.
            
            Желаем вам приятного использования!
            """;
}