package ru.tinkoff.edu.java.link_parser.parsers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;
import ru.tinkoff.edu.java.link_parser.validators.StackoverflowLinkValidator;

public final class StackoverflowLinkParser implements LinkParser {
    private static final StackoverflowLinkValidator VALIDATOR = new StackoverflowLinkValidator();

    @Override
    public Map<String, String> parse(URI link) {
        if (!canParse(link)) {
            return null;
        }

        Map<String, String> result = new HashMap<>();

        result.put("questionId", LinkParserHelper.getPathSegmentByIndex(link, 1));

        return result;
    }

    @Override
    public boolean canParse(URI link) {
        try {
            return VALIDATOR.validate(link);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "StackoverflowLinkParser{}";
    }
}
