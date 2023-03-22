package ru.tinkoff.edu.java.link_parser.parsers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public final class GlobalLinkParser {
    private final List<LinkParser> allImplementations;

    public Map<String, String> parse(String link) {
        return allImplementations.stream()
                .map(parser -> parser.parse(link))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public boolean addParser(LinkParser parser) {
        return allImplementations.add(parser);
    }

    /**
     * Этот конструктор ищет все классы, которые имплементируют LinkParser в своем пакете, и кладет их в allImplementations
     */
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
                        System.out.println("Can't instantiate class: " + e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());

        System.out.println("These implementations were found: " + allImplementations);
    }

    /**
     * @param packageName - имя пакета, в котором ищем классы
     * @return Все классы, которые находятся в пакете packageName
     */
    private Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(className -> className.endsWith(".class"))
                .map(className -> getClass(className, packageName))
                .collect(Collectors.toSet());
    }

    /**
     * @param className  - имя класса, которые мы хотим получить
     * @param packageName - имя пакета, в котором ищем класс
     * @return Класс, который находится в пакете packageName и имеет имя className
     */
    private Class getClass(String className, String packageName) {
        className = className.substring(0, className.lastIndexOf('.'));
        try {
            return Class.forName(packageName + "." + className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
            return null;
        }
    }
}
