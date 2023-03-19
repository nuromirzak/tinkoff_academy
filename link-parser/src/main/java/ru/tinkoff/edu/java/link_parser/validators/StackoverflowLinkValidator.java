package ru.tinkoff.edu.java.link_parser.validators;

import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;

import java.net.MalformedURLException;
import java.net.URL;

public class StackoverflowLinkValidator implements LinkValidator {
    @Override
    public boolean throwExceptionIfInvalid(String link) {
        try {
            URL url = new URL(link);
        } catch (MalformedURLException e) {
            return false;
        }

        if (LinkParserHelper.containsSubdomain(link)) {
            throw new IllegalArgumentException("Link contains subdomain: " + link);
        }

        if (!LinkParserHelper.getTopLevelDomain(link).equals("stackoverflow.com")) {
            throw new IllegalArgumentException("Link does not contain stackoverflow.com as top level domain: " + link);
        }

        boolean hasAtLeastTwoPathSegments = LinkParserHelper.getAllPathSegments(link).size() >= 2;
        boolean hasValidPathSegment = hasAtLeastTwoPathSegments && LinkParserHelper.getPathSegmentByIndex(link, 0).equals("questions");
        boolean hasValidPathSegment2 = hasAtLeastTwoPathSegments && LinkParserHelper.getPathSegmentByIndex(link, 1).matches("\\d+");

        return hasAtLeastTwoPathSegments && hasValidPathSegment && hasValidPathSegment2;
    }
}
