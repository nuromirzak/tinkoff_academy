package ru.tinkoff.edu.java.bot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.components.BotCommands;
import ru.tinkoff.edu.java.bot.components.TextMessageHandler;
import ru.tinkoff.edu.java.bot.configuration.BotConfig;

@Component
@Log4j2
public class MyTelegramBot extends TelegramLongPollingBot {
    private final TextMessageHandler textMessageHandler;
    private final BotConfig botConfig;

    @Autowired
    public MyTelegramBot(BotConfig botConfig, TextMessageHandler textMessageHandler) {
        super(botConfig.getBotToken());
        this.textMessageHandler = textMessageHandler;
        this.botConfig = botConfig;
        try {
            this.execute(new SetMyCommands(BotCommands.LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        log.info("Bot successfully started");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        String messageText = update.getMessage().hasText() ? update.getMessage().getText() : null;
        String answerText = textMessageHandler.handleTextMessage(update, messageText);
        message.setText(answerText);

        try {
            log.info("Отправляем сообщение: {}", message.getText());
            Message result = execute(message);
            log.info(result.getText());
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    @Override
    public String getBotUsername() {
        return this.botConfig.getBotName();
    }
}