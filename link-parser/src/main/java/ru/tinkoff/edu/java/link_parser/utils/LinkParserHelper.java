package ru.tinkoff.edu.java.link_parser.utils;

import java.net.URI;
import java.util.List;

public record LinkParserHelper() {
    private static final String DOT_REGEX = "\\.";

    public static String getPathSegmentByIndex(URI uri, int index) {
        return getAllPathSegments(uri).get(index);
    }

    public static List<String> getAllPathSegments(URI uri) {
        String path = uri.getPath();
        String[] segments = path.split("/");
        return List.of(segments).subList(1, segments.length);
    }

    public static List<String> getSubdomains(URI uri) {
        String host = uri.getHost();
        String[] subdomains = host.split(DOT_REGEX);
        return List.of(subdomains);
    }

    public static String getTopLevelDomain(URI uri) {
        String host = uri.getHost();
        String[] subdomains = host.split(DOT_REGEX);
        return subdomains[subdomains.length - 2] + "." + subdomains[subdomains.length - 1];
    }

    public static boolean containsSubdomain(URI uri) {
        List<String> subdomains = LinkParserHelper.getSubdomains(uri);
        return subdomains.size() > 2;
    }
}
