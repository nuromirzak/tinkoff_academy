package ru.tinkoff.edu.java.link_parser.validators;

import java.net.URI;

public interface LinkValidator {
    boolean validate(URI link);
}
