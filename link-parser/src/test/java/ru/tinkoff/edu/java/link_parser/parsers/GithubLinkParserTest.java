package ru.tinkoff.edu.java.link_parser.parsers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.link_parser.validators.GithubLinkValidator;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GithubLinkParserTest {
    private LinkParser parser;

    @BeforeEach
    void setUp() {
        parser = new GithubLinkParser();
    }

    @Test
    public void parseValidLink() {
        String link = "https://github.com/Facebook/react/";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "Facebook", "repo", "react");
        assertEquals(expected, result);
    }

    @Test
    public void parseInvalidLinkWithInvalidFirstSegmentPath() {
        String link = "https://github.com/features/copilot/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithMultiplePathSegments() {
        String link = "https://github.com/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "nuromirzak");
        assertEquals(expected, result);
    }

    @Test
    public void parseBrokenLink() {
        String link = "http:://github.com/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithEmptyPathSegments() {
        String link = "https://github.com/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithOnePathSegment() {
        String link = "http:://github.com/nuromirzak/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseLinkWithSubdomain() {
        String link = "https://nuromirzak.github.com/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseAnotherDomainLink() {
        String link = "https://github.io/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }
}