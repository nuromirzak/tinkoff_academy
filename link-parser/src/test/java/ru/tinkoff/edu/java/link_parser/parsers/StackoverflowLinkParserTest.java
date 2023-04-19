package ru.tinkoff.edu.java.link_parser.parsers;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StackoverflowLinkParserTest {
    private static final LinkParser parser = new StackoverflowLinkParser();

    @Test
    public void parseValidLink() {
        URI uri = URI.create("https://stackoverflow.com/questions/4114095/");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("questionId", "4114095");
        assertEquals(expected, result);
    }

    @Test
    public void parseInvalidLinkWithInvalidFirstSegmentPath() {
        URI uri = URI.create("https://stackoverflow.com/lol/4114095/how-do-i-revert-a-git-repository-to-a-previous-commit");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithMultiplePathSegments() {
        URI uri = URI.create("https://stackoverflow.com/questions/4114095/how-do-i-revert-a-git-repository-to-a-previous-commit/");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("questionId", "4114095");
        assertEquals(expected, result);
    }

    @Test
    public void parseBrokenLink() {
        URI uri = URI.create("http:://stackoverflow.com/questions/4114095/");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithEmptyPathSegments() {
        URI uri = URI.create("https://stackoverflow.com/");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithOnePathSegment() {
        URI uri = URI.create("http:://stackoverflow.com/questions/");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseLinkWithSubdomain() {
        URI link = URI.create("https://ru.stackoverflow.com/questions/4114095/");
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseAnotherDomainLink() {
        URI link = URI.create("https://github.io/nuromirzak/nuromirzak/blob/main/README.md");
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseQuestionIdWithNegativeNumber() {
        URI link = URI.create("https://stackoverflow.com/questions/-4114095/");
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseQuestionIdWithNonNumericCharacters() {
        URI link = URI.create("https://stackoverflow.com/questions/4114095s/");
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithTags() {
        URI link = URI.create("https://stackoverflow.com/questions/tagged/java?sort=newest&pageSize=15");
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithAnswerId() {
        URI uri = URI.create("https://stackoverflow.com/a/123456");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithQueryString() {
        URI uri = URI.create("https://stackoverflow.com/questions/4114095/how-do-i-revert-a-git-repository-to-a-previous-commit/?sort=votes#tab-top");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("questionId", "4114095");
        assertEquals(expected, result);
    }

    @Test
    public void parseValidLinkWithHash() {
        URI uri = URI.create("https://stackoverflow.com/questions/4114095/#comment123456");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("questionId", "4114095");
        assertEquals(expected, result);
    }

    @Test
    public void parseValidLinkWithTrailingSlash() {
        URI uri = URI.create("https://stackoverflow.com/questions/4114095/");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("questionId", "4114095");
        assertEquals(expected, result);
    }
}