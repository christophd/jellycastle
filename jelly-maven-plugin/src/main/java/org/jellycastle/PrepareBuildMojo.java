package org.jellycastle;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.jellycastle.build.JellyBuild;

import java.io.File;
import java.net.*;

/**
 * Goal which touches a timestamp file.
 */
@Component(role = org.apache.maven.plugin.Mojo.class)
@Execute(phase = LifecyclePhase.INITIALIZE,
        goal = "build")
@Mojo( name = "build",
        requiresProject = true,
        defaultPhase = LifecyclePhase.INITIALIZE,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class PrepareBuildMojo extends AbstractMojo {

    @Requirement
    private Logger logger;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "org.jellycastle.build.BuildConfig")
    private String configClass;

    public void execute() throws MojoExecutionException {
        try {
            JellyBuild build = new JellyBuild(getProjectClassLoader().loadClass(configClass))
                    .workingDirectory(project.getBasedir().getAbsolutePath());
            logger.info(build.mvn().print(build.load()));
        } catch (ClassNotFoundException e) {
            logger.error(String.format("Unable to read build configuration class '%s'", configClass));
        }
    }

    /**
     * Construct new class loader from plugin classloader with default project
     * build output folder included.
     *
     * @return
     */
    private ClassLoader getProjectClassLoader() {
        try {
            return new URLClassLoader(new URL[]{new File(project.getBuild().getOutputDirectory()).toURI().toURL()},
                    getClass().getClassLoader());
        } catch (MalformedURLException e) {
            logger.error("Failed!", e);
            return getClass().getClassLoader();
        }
    }
}
