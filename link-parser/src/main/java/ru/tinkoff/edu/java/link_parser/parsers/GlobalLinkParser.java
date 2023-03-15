package ru.tinkoff.edu.java.link_parser.parsers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class GlobalLinkParser implements LinkParser {
    private final Set<LinkParser> allImplementations;

    @Override
    public Map<String, String> parse(String link) {
        return allImplementations.stream()
                .filter(parser -> parser.canParse(link))
                .map(parser -> parser.parse(link))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean canParse(String link) {
        return allImplementations.stream()
                .anyMatch(parser -> parser.canParse(link));
    }

    public boolean addParser(LinkParser parser) {
        return allImplementations.add(parser);
    }

    public GlobalLinkParser() {
        String currentPackageName = this.getClass().getPackage().getName();
        Set<Class> allClassesInCurrentPackage = findAllClassesUsingClassLoader(currentPackageName);

        allClassesInCurrentPackage = allClassesInCurrentPackage.stream()
                .filter(clazz -> !clazz.equals(this.getClass()))
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .filter(LinkParser.class::isAssignableFrom)
                .collect(Collectors.toSet());

        this.allImplementations = allClassesInCurrentPackage.stream()
                .map(clazz -> {
                    try {
                        return (LinkParser) clazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        System.out.println("Can't instantiate class: " + e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toSet());

        System.out.println("These implementations were found: " + allImplementations);
    }

    private Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class getClass(String line, String packageName) {
        String className = line.substring(0, line.lastIndexOf('.'));
        try {
            return Class.forName(packageName + "." + className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
            return null;
        }
    }
}
