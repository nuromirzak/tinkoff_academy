package ru.tinkoff.edu.java.bot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;
import java.util.Map;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Регистрация пользователя"),
            new BotCommand("/help", "Получить список доступных команд"),
            new BotCommand("/track", "Подписаться на рассылку"),
            new BotCommand("/untrack", "Отписаться от рассылки"),
            new BotCommand("/list", "Вывести список отслеживаемых ссылок")
    );

    String COMMAND_START = "/start";
    String COMMAND_LIST = "/list";
    String COMMAND_HELP = "/help";
    String COMMAND_TRACK = "/track";
    String COMMAND_UNTRACK = "/untrack";
    String COMMAND_DELETE = "/delete";
    String COMMAND_TEST = "/test";

    String COMMAND_START_RESPONSE = "Добро пожаловать в бота, %s! Вы успешно зарегистрировались, для подробной информации введите команду /help";
    String COMMAND_LIST_RESPONSE = "Вот список ваших отслеживаемых ссылок:\n%s";
    String COMMAND_HELP_RESPONSE = """
            Этот бот поможет подписаться на рассылку изменений Stackoverlow вопросов и Github репозиториев.
                        
            Для этого вам нужно ввести команду /track и ссылку на вопрос или репозиторий.
            Например, /track https://stackoverflow.com/questions/123456789
                        
            Если хотите отписаться от рассылки, введите команду /untrack и ссылку на вопрос или репозиторий.
            Например, /untrack https://stackoverflow.com/questions/123456789
                        
            Для получения списка отслеживаемых ссылок введите команду /list.
                        
            Желаем вам приятного использования!
            """;
    String COMMAND_TRACK_RESPONSE = "Вы успешно подписались на ссылку %s, теперь вы будете получать уведомления о изменениях";
    String ERROR_MESSAGE_NO_LINKS = "У вас нет отслеживаемых ссылок, для получения списка доступных команд введите команду /help.";
    String ERROR_MESSAGE_LINK_NOT_ADDED = "Не было добавлено ссылок, возможно вы уже отслеживаете эту ссылку.";
    String COMMAND_UNTRACK_RESPONSE = "Вы успешно отписались от ссылки %s, теперь вы не будете получать уведомления о изменениях";
    String ERROR_MESSAGE_LINK_NOT_REMOVED = "Не было удалено ссылок, возможно вы не отслеживаете эту ссылку.";
    String ERROR_MESSAGE_INVALID_COMMAND_ARGUMENTS = "Применение команды: %s <ссылка>";
    String COMMAND_DELETE_RESPONSE = "Вы успешно отписались от всех ссылок и от бота, для получения списка доступных команд введите команду /help.";
    String ERROR_MESSAGE_NOT_REGISTERED = "Вы должны быть зарегистрированы, для получения списка доступных команд введите команду /help.";
    String COMMAND_TEST_RESPONSE = "Вы нашли секретную команду, но у вас нет прав на ее использование))";
    String SECRET_COMMAND_USERNAME = "nrmkhd";
    String ERROR_MESSAGE_COMMAND_NOT_FOUND = "Введите команду для получения списка доступных команд введите команду /help.";
    String ERROR_MESSAGE_UNHANDLED = "Произошла непредвиденная ошибка, обратитесь к администратору бота.";

    Map<String, String> commands = Map.of(
            COMMAND_START, COMMAND_START_RESPONSE,
            COMMAND_HELP, COMMAND_HELP_RESPONSE,
            COMMAND_TRACK, COMMAND_TRACK_RESPONSE,
            COMMAND_UNTRACK, COMMAND_UNTRACK_RESPONSE,
            COMMAND_LIST, COMMAND_LIST_RESPONSE
    );
}