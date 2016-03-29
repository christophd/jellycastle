package org.jellycastle.build;

import org.jellycastle.annotation.Build;
import org.jellycastle.annotation.Property;
import org.jellycastle.annotation.dependency.Dependency;
import org.jellycastle.annotation.jar.JavaBuild;
import org.jellycastle.annotation.plugin.Plugin;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@JavaBuild
@Build(groupId = "org.jellycastle",
        artifactId = "jelly-sample")
@Property(name = "spring.version", value = "4.0.7.RELEASE")
public class BuildConfig {

    @Dependency(groupId = "org.springframework",
                artifactId = "spring-beans",
                version = "${spring.version}")
    public void springBeans() {
    }

    @Dependency(groupId = "org.springframework",
            artifactId = "spring-context",
            version = "${spring.version}")
    public void springContext() {
    }

    @Plugin(groupId = "org.apache.maven.plugins",
            artifactId = "maven-jar-plugin",
            version = "2.5")
    public void mavenJarPlugin() {
    }

}
