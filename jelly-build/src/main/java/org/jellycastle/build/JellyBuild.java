/*
 * Copyright 2015-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jellycastle.build;

import org.jellycastle.annotation.Property;
import org.jellycastle.annotation.jar.JavaBuild;
import org.jellycastle.annotation.war.WebBuild;
import org.jellycastle.maven.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
public class JellyBuild {

    /** Logger */
    private static Logger log = LoggerFactory.getLogger(JellyBuild.class);

    /** Class configuration holding annotated build information */
    private final Class<?> configuration;

    /** Working directory for this build, usually the Maven POM file location */
    private String workingDirectory = "";

    /** Maven instance for this build */
    private Maven mvn;

    /**
     * Default constructor using configuration class with build annotations.
     * @param configuration
     */
    public JellyBuild(Class<?> configuration) {
        this.configuration = configuration;
    }

    private void loadBuildInformation(Project project) {
        org.jellycastle.annotation.Build build = configuration.getAnnotation(org.jellycastle.annotation.Build.class);

        project.setModelVersion(build.modelVersion());

        project.setGroupId(build.groupId());
        project.setArtifactId(build.artifactId());
        project.setVersion(build.version());

        if (StringUtils.hasText(build.description())) {
            project.setDescription(build.description());
        }

        if (build.modules().length > 0) {
            project.setPackaging("pom");
        } else if (configuration.getAnnotation(JavaBuild.class) != null) {
            project.setPackaging("jar");
        } else if (configuration.getAnnotation(WebBuild.class) != null) {
            project.setPackaging("web");
        }
    }

    private void loadProperties(Project project) {
        Project.Properties properties = new ObjectFactory().createProjectProperties();

        for (Annotation annotation : configuration.getAnnotations()) {
            if (annotation instanceof Property) {
                Property propertyAnnotation = (Property) annotation;
                properties.getAnies().add(new PropertyElement(propertyAnnotation.name(), propertyAnnotation.value()));
            }
        }

        if (properties.getAnies().size() > 0) {
            project.setProperties(properties);
        }
    }

    private void loadDependencies(Project project) {
        DependencyManagement dependencyManagement = project.getDependencyManagement();
        if (dependencyManagement == null) {
            dependencyManagement = new ObjectFactory().createDependencyManagement();
            dependencyManagement.setDependencies(new ObjectFactory().createDependencyManagementDependencies());
            project.setDependencyManagement(dependencyManagement);
        }

        Project.Dependencies dependencies = project.getDependencies();
        if (dependencies == null) {
            dependencies = new ObjectFactory().createProjectDependencies();
            project.setDependencies(dependencies);
        }

        addJellyBuild(dependencyManagement, dependencies);

        for (Method method : configuration.getDeclaredMethods()) {
            if (method.getAnnotation(org.jellycastle.annotation.dependency.Dependency.class) != null) {
                org.jellycastle.annotation.dependency.Dependency annotation =
                        method.getAnnotation(org.jellycastle.annotation.dependency.Dependency.class);

                if (StringUtils.hasText(annotation.version())) {
                    Dependency dependency = new ObjectFactory().createDependency();
                    dependency.setGroupId(annotation.groupId());
                    dependency.setArtifactId(annotation.artifactId());
                    dependency.setVersion(annotation.version());

                    dependencyManagement.getDependencies().getDependencies().add(dependency);
                }

                Dependency dependency = new ObjectFactory().createDependency();
                dependency.setGroupId(annotation.groupId());
                dependency.setArtifactId(annotation.artifactId());

                dependencies.getDependencies().add(dependency);
            }
        }
    }

    private void addJellyBuild(DependencyManagement dependencyManagement, Project.Dependencies dependencies) {
        Dependency managedDependency = new ObjectFactory().createDependency();
        managedDependency.setGroupId("org.jellycastle");
        managedDependency.setArtifactId("jelly-build");
        managedDependency.setVersion("0.1.0");
        dependencyManagement.getDependencies().getDependencies().add(managedDependency);

        Dependency dependency = new ObjectFactory().createDependency();
        dependency.setGroupId("org.jellycastle");
        dependency.setArtifactId("jelly-build");
        dependency.setScope("provided");
        dependencies.getDependencies().add(dependency);
    }

    private void loadPlugins(Project project) {
        Build build = project.getBuild();
        if (build == null) {
            build = new ObjectFactory().createBuild();
            project.setBuild(build);
        }

        PluginManagement pluginManagement = build.getPluginManagement();
        if (pluginManagement == null) {
            pluginManagement = new ObjectFactory().createPluginManagement();
            pluginManagement.setPlugins(new ObjectFactory().createPluginManagementPlugins());
            build.setPluginManagement(pluginManagement);
        }

        Build.Plugins plugins = build.getPlugins();
        if (plugins == null) {
            plugins = new ObjectFactory().createBuildPlugins();
            build.setPlugins(plugins);
        }

        addJellyBuildPlugin(pluginManagement, plugins);

        for (Method method : configuration.getDeclaredMethods()) {
            if (method.getAnnotation(org.jellycastle.annotation.plugin.Plugin.class) != null) {
                org.jellycastle.annotation.plugin.Plugin annotation =
                        method.getAnnotation(org.jellycastle.annotation.plugin.Plugin.class);

                if (StringUtils.hasText(annotation.version())) {
                    Plugin plugin = new ObjectFactory().createPlugin();
                    plugin.setGroupId(annotation.groupId());
                    plugin.setArtifactId(annotation.artifactId());
                    plugin.setVersion(annotation.version());

                    pluginManagement.getPlugins().getPlugins().add(plugin);
                }

                Plugin plugin = new ObjectFactory().createPlugin();
                plugin.setGroupId(annotation.groupId());
                plugin.setArtifactId(annotation.artifactId());

                plugins.getPlugins().add(plugin);
            }
        }
    }

    private void addJellyBuildPlugin(PluginManagement pluginManagement, Build.Plugins plugins) {
        Plugin managedPlugin = new ObjectFactory().createPlugin();
        managedPlugin.setGroupId("org.jellycastle");
        managedPlugin.setArtifactId("jelly-maven-plugin");
        managedPlugin.setVersion("0.1.0");

        pluginManagement.getPlugins().getPlugins().add(managedPlugin);

        Plugin plugin = new ObjectFactory().createPlugin();
        plugin.setGroupId("org.jellycastle");
        plugin.setArtifactId("jelly-maven-plugin");

        plugins.getPlugins().add(plugin);
    }

    /**
     * Sets the working directory for this build.
     * @param workingDirectory
     * @return
     */
    public JellyBuild workingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
        return this;
    }

    /**
     * Gets new Maven instance for project.
     * @return
     */
    public Maven mvn() {
        if (mvn == null) {
            mvn = new Maven(workingDirectory);
        }

        return mvn;
    }

    /**
     * Load project from configuration.
     * @return
     */
    public Project load() {
        Project project = new ObjectFactory().createProject();

        loadBuildInformation(project);
        loadProperties(project);
        loadDependencies(project);
        loadPlugins(project);

        return project;
    }
}
