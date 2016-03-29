package org.jellycastle.extension.lifecycle;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@Component(role = AbstractMavenLifecycleParticipant.class, hint = "jelly")
public class JellyLifecylce extends AbstractMavenLifecycleParticipant {

    @Requirement
    private Logger logger;

    private String separator = "------------------------------------------------------------------------";

    @Override
    public void afterProjectsRead(MavenSession session) throws MavenExecutionException {
        logger.info(separator);
        logger.info("J E L L Y  B U I L D  E X T E N S I O N");
    }
}
