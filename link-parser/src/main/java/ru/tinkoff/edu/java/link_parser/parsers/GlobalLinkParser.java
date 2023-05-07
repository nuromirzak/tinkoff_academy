package ru.tinkoff.edu.java.link_parser.parsers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class GlobalLinkParser {
    private final Logger logger = Logger.getLogger(GlobalLinkParser.class.getName());
    private final List<LinkParser> allImplementations;

    public GlobalLinkParser() {
        String currentPackageName = this.getClass().getPackage().getName();
        Set<Class> allClassesInCurrentPackage = findAllClassesUsingClassLoader(currentPackageName);

        allClassesInCurrentPackage = allClassesInCurrentPackage.stream()
            .filter(clazz -> !clazz.isInterface())
            .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
            .filter(LinkParser.class::isAssignableFrom)
            .collect(Collectors.toSet());

        this.allImplementations = allClassesInCurrentPackage.stream()
            .map(clazz -> {
                try {
                    return (LinkParser) clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.warning("Can't instantiate class: " + e.getMessage());
                    return null;
                }
            })
            .collect(Collectors.toList());

        logger.info("These implementations were found: " + allImplementations);
    }

    public Map<String, String> parse(URI link) {
        return allImplementations.stream()
            .map(parser -> parser.parse(link))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    public boolean addParser(LinkParser parser) {
        return allImplementations.add(parser);
    }

    private Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
            .filter(className -> className.endsWith(".class"))
            .map(className -> getClass(className, packageName))
            .collect(Collectors.toSet());
    }

    private Class getClass(String className, String packageName) {
        String classNameWithoutExtension = className.substring(0, className.lastIndexOf('.'));
        try {
            return Class.forName(packageName + "." + classNameWithoutExtension);
        } catch (ClassNotFoundException e) {
            logger.warning("Class not found: " + e.getMessage());
            return null;
        }
    }
}
