package org.jellycastle.extension;

import org.apache.maven.model.locator.ModelLocator;
import org.codehaus.plexus.component.annotations.Component;

import java.io.File;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@Component(role = ModelLocator.class)
public class JellyModelLocator implements ModelLocator {
    @Override
    public File locatePom(File projectDirectory) {
        return new File(projectDirectory, "pom.properties");
    }
}
