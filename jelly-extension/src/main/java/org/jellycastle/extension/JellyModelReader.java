package org.jellycastle.extension;

import org.apache.maven.building.Source;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelProcessor;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelReader;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.ReaderFactory;
import org.jellycastle.build.JellyBuild;
import org.jellycastle.maven.Project;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Properties;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@Component(role = ModelReader.class)
public class JellyModelReader extends DefaultModelReader {

    @Requirement
    private Logger logger;

    @Override
    public Model read(File input, Map<String, ?> options) throws IOException {
        if ( input == null ) {
            throw new IllegalArgumentException( "input file missing" );
        }

        Reader reader = new BufferedReader(new FileReader(input));
        try {
            Model model = read(reader, options);
            model.setPomFile(input);
            return model;
        } finally {
            IOUtil.close(reader);
        }
    }

    @Override
    public Model read(InputStream input, Map<String, ?> options) throws IOException {
        if ( input == null ) {
            throw new IllegalArgumentException( "input stream missing" );
        }

        return read(new InputStreamReader(input), options);
    }

    @Override
    public Model read(Reader reader, Map<String, ?> options) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("input reader missing");
        }

        Source modelSource = (Source) options.get(ModelProcessor.SOURCE);

        if (("" + modelSource).contains("pom.properties")) {
            Properties properties = new Properties();
            properties.load(reader);

            logger.info(JellyExtensionProperties.SEPARATOR);
            logger.info("J E L L Y   B U I L D   A N N O T A T I O N S");

            try {
                JellyBuild build = getJellyBuild(properties);
                Project mavenProject = build.load();
                build.mvn().save(mavenProject);

                String pom = build.mvn().print(mavenProject);
                logger.debug(pom);

                return super.read(ReaderFactory.newXmlReader(new ByteArrayInputStream(pom.getBytes())), options);
            } finally {
                IOUtil.close(reader);
            }
        }

        return super.read(reader, options);
    }

    private JellyBuild getJellyBuild(Properties properties) throws MalformedURLException {
        String configClassName = properties.getProperty(JellyExtensionProperties.CONFIG_CLASS_PROPERTY, JellyExtensionProperties.CONFIG_CLASS_DEFAULT);
        String outputDirectory = properties.getProperty(JellyExtensionProperties.OUTPUT_DIR_PROPERTY, JellyExtensionProperties.OUTPUT_DIR_DEFAULT);

        try {
            return new JellyBuild(new URLClassLoader(new URL[]{new File(outputDirectory).toURI().toURL()},
                    getClass().getClassLoader()).loadClass(configClassName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Unable to read build configuration class '%s'", configClassName), e);
        }
    }
}
