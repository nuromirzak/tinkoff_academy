package ru.tinkoff.edu.java.link_parser.validators;

import java.net.URI;

public interface LinkValidator {
    /**
     * @param link ссылка для проверки
     * @return true - если ссылка валидна, кидает исключение - если ссылка не валидна
     */
    public boolean validate(URI link);
}
