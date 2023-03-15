package ru.tinkoff.edu.java.link_parser.parsers;

import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;
import ru.tinkoff.edu.java.link_parser.validators.StackoverflowLinkValidator;

import java.util.HashMap;
import java.util.Map;

public final class StackoverflowLinkParser implements LinkParser {
    private static final StackoverflowLinkValidator validator = new StackoverflowLinkValidator();

    @Override
    public Map<String, String> parse(String link) {
        if (!canParse(link)) {
            return null;
        }

        Map<String, String> result = new HashMap<>();

        result.put("questionId", LinkParserHelper.getPathSegmentByIndex(link, 1));

        return result;
    }

    @Override
    public boolean canParse(String link) {
        return validator.validate(link);
    }

    @Override
    public String toString() {
        return "StackoverflowLinkParser{}";
    }
}
