package ru.tinkoff.edu.java.link_parser.validators;

import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;

import java.net.MalformedURLException;
import java.net.URL;

public class StackoverflowLinkValidator implements LinkValidator {
    @Override
    public boolean validate(String link) {
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
        if (!hasAtLeastTwoPathSegments) {
            throw new IllegalArgumentException("Link does not contain at least two path segments: " + link);
        }

        boolean hasValidPathSegment = LinkParserHelper.getPathSegmentByIndex(link, 0).equals("questions");
        if (!hasValidPathSegment) {
            throw new IllegalArgumentException("Link does not contain questions as first path segment: " + link);
        }

        boolean hasValidPathSegment2 = LinkParserHelper.getPathSegmentByIndex(link, 1).matches("\\d+");
        if (!hasValidPathSegment2) {
            throw new IllegalArgumentException("Link does not contain number as second path segment: " + link);
        }

        return true;
    }
}
