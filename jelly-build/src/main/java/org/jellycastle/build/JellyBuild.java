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

import org.jellycastle.annotation.jar.JavaBuild;
import org.jellycastle.annotation.war.WebBuild;
import org.jellycastle.maven.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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

    /**
     * Default constructor using configuration class with build annotations.
     * @param configuration
     */
    public JellyBuild(Class<?> configuration) {
        this.configuration = configuration;
    }

    private void loadBuildInformation(Maven mvn) {
        Project project = mvn.getProject();

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

    private void loadDependencies(Maven mvn) {
        Project project = mvn.getProject();

        for (Method method : configuration.getDeclaredMethods()) {
            if (method.getAnnotation(org.jellycastle.annotation.dependency.Dependency.class) != null) {
                org.jellycastle.annotation.dependency.Dependency annotation =
                        method.getAnnotation(org.jellycastle.annotation.dependency.Dependency.class);

                if (StringUtils.hasText(annotation.version())) {
                    Dependency dependency = new ObjectFactory().createDependency();
                    dependency.setGroupId(annotation.groupId());
                    dependency.setArtifactId(annotation.artifactId());
                    dependency.setVersion(annotation.version());

                    DependencyManagement dependencyManagement = project.getDependencyManagement();
                    if (dependencyManagement == null) {
                        dependencyManagement = new ObjectFactory().createDependencyManagement();
                        dependencyManagement.setDependencies(new ObjectFactory().createDependencyManagementDependencies());
                        project.setDependencyManagement(dependencyManagement);
                    }

                    dependencyManagement.getDependencies().getDependencies().add(dependency);
                }

                Dependency dependency = new ObjectFactory().createDependency();
                dependency.setGroupId(annotation.groupId());
                dependency.setArtifactId(annotation.artifactId());

                Project.Dependencies dependencies = project.getDependencies();
                if (dependencies == null) {
                    dependencies = new ObjectFactory().createProjectDependencies();
                    project.setDependencies(dependencies);
                }

                dependencies.getDependencies().add(dependency);
            }
        }
    }

    private void loadPlugins(Maven mvn) {
        Project project = mvn.getProject();

        for (Method method : configuration.getDeclaredMethods()) {
            if (method.getAnnotation(org.jellycastle.annotation.plugin.Plugin.class) != null) {
                Build build = project.getBuild();
                if (build == null) {
                    build = new ObjectFactory().createBuild();
                    project.setBuild(build);
                }

                org.jellycastle.annotation.plugin.Plugin annotation =
                        method.getAnnotation(org.jellycastle.annotation.plugin.Plugin.class);

                if (StringUtils.hasText(annotation.version())) {
                    Plugin plugin = new ObjectFactory().createPlugin();
                    plugin.setGroupId(annotation.groupId());
                    plugin.setArtifactId(annotation.artifactId());
                    plugin.setVersion(annotation.version());

                    PluginManagement pluginManagement = build.getPluginManagement();
                    if (pluginManagement == null) {
                        pluginManagement = new ObjectFactory().createPluginManagement();
                        pluginManagement.setPlugins(new ObjectFactory().createPluginManagementPlugins());
                        build.setPluginManagement(pluginManagement);
                    }

                    pluginManagement.getPlugins().getPlugins().add(plugin);
                }

                Plugin plugin = new ObjectFactory().createPlugin();
                plugin.setGroupId(annotation.groupId());
                plugin.setArtifactId(annotation.artifactId());

                Build.Plugins plugins = build.getPlugins();
                if (plugins == null) {
                    plugins = new ObjectFactory().createBuildPlugins();
                    build.setPlugins(plugins);
                }

                plugins.getPlugins().add(plugin);
            }
        }
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
     * Gets the Maven model.
     * @return
     */
    public Maven mvn() {
        Maven mvn = new Maven(workingDirectory);

        loadBuildInformation(mvn);
        loadDependencies(mvn);
        loadPlugins(mvn);

        return mvn;
    }
}
