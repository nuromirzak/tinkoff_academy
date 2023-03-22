# Instructions about this project

## How I created this project

```
mvn archetype:generate \
-DarchetypeGroupId=org.codehaus.mojo.archetypes \
-DarchetypeArtifactId=pom-root \
-DarchetypeVersion=RELEASE
```

I specified the groupId - `com.github.nuromirzak` and artifactId - `tinkoff_academy` and then I created following
modules:

```
mvn archetype:generate \
-DarchetypeGroupId=org.apache.maven.archetypes \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DarchetypeVersion=RELEASE
-DgroupId=ru.tinkoff.edu.java
```

with artifactIds `bot`, `link-parser` and `scraper`.

Source: [How to create an empty multi module Maven project?](https://stackoverflow.com/questions/6328778/how-to-create-an-empty-multi-module-maven-project)