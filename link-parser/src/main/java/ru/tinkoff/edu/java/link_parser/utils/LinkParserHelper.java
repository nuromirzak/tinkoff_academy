package ru.tinkoff.edu.java.link_parser.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public record LinkParserHelper() {
    public static String getPathSegmentByIndex(String link, int index) {
        return getAllPathSegments(link).get(index);
    }

    public static List<String> getAllPathSegments(String link) {
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String path = url.getPath();
        String[] segments = path.split("/");
        return List.of(segments).subList(1, segments.length);
    }

    public static List<String> getSubdomains(String link) {
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String host = url.getHost();
        String[] subdomains = host.split("\\.");
        return List.of(subdomains);
    }

    public static String getTopLevelDomain(String link) {
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String host = url.getHost();
        String[] subdomains = host.split("\\.");
        return subdomains[subdomains.length - 2] + "." + subdomains[subdomains.length - 1];
    }

    public static boolean containsSubdomain(String link) {
        List<String> subdomains = LinkParserHelper.getSubdomains(link);
        return subdomains.size() > 2;
    }
}
