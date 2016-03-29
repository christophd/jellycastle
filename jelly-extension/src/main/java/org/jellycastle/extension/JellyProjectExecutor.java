package org.jellycastle.extension;

import org.apache.maven.execution.ProjectExecutionEvent;
import org.apache.maven.execution.ProjectExecutionListener;
import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.jellycastle.build.JellyBuild;

import java.io.File;
import java.net.*;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@Component( role = ProjectExecutionListener.class )
public class JellyProjectExecutor implements ProjectExecutionListener {

    @Requirement
    private Logger logger;

    @Override
    public void beforeProjectExecution(ProjectExecutionEvent projectExecutionEvent) throws LifecycleExecutionException {
        try {
            MavenProject project = projectExecutionEvent.getProject();
            JellyBuild build = new JellyBuild(getProjectClassLoader(project).loadClass("org.jellycastle.build.BuildConfig"))
                    .workingDirectory(project.getBasedir().getAbsolutePath());
            logger.info(build.mvn().print(build.load()));
        } catch (ClassNotFoundException e) {
            logger.error(String.format("Unable to read build configuration class '%s'", "org.jellycastle.build.BuildConfig"));
        }
    }

    /**
     * Construct new class loader from plugin classloader with default project
     * build output folder included.
     *
     * @return
     */
    private ClassLoader getProjectClassLoader(MavenProject project) {
        try {
            return new URLClassLoader(new URL[]{new File(project.getBuild().getOutputDirectory()).toURI().toURL()},
                    getClass().getClassLoader());
        } catch (MalformedURLException e) {
            logger.error("Failed!", e);
            return getClass().getClassLoader();
        }
    }

    @Override
    public void beforeProjectLifecycleExecution(ProjectExecutionEvent projectExecutionEvent) throws LifecycleExecutionException {
    }

    @Override
    public void afterProjectExecutionSuccess(ProjectExecutionEvent projectExecutionEvent) throws LifecycleExecutionException {
    }

    @Override
    public void afterProjectExecutionFailure(ProjectExecutionEvent projectExecutionEvent) {
    }
}
