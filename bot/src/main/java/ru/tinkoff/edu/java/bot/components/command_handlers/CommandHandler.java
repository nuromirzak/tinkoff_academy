package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    String handleCommand(Update update, String chatId, String[] commandArguments);
}
