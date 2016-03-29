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

package org.jellycastle.maven;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.StringUtils;

import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
public class Maven {

    /** Logger */
    private static Logger log = LoggerFactory.getLogger(Maven.class);

    private static final String BASH = "bash";
    private static final String BASH_OPTION_C = "-c";
    private static final String CMD = "cmd";
    private static final String CMD_OPTION_C = "/c";

    private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    protected static final String MVN = "mvn";
    private List<MavenCommand> commands = new ArrayList<MavenCommand>();

    private String workingDirectory;

    /**
     * Default constructor initializing jaxb marshaller.
     */
    public Maven(String workingDirectory) {
        this.workingDirectory = workingDirectory;
        marshaller.setClassesToBeBound(Project.class);
        try {
            marshaller.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Failed to setup marshaller", e);
        }
    }

    /**
     * Loads project from normal POM location in working directory.
     * @return
     */
    public Project fromPom() {
        try {
            return (Project) marshaller.unmarshal(new StreamSource(getPomFile().getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Maven pom.xml", e);
        }
    }

    /**
     * Run new Maven os process with given arguments and commands.
     */
    public void execute() {
        Process process = null;
        BufferedReader br = null;
        try {
            ProcessBuilder processBuilder = getProcessBuilder();
            processBuilder.redirectErrorStream(true);
            log.info("Starting process: " + processBuilder.command());

            process = processBuilder.start();

            // Read output
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(br);
            destroyProcess(process);
        }
    }

    /**
     * Clean command.
     * @return
     */
    public Maven clean() {
        commands.add(MavenCommand.CLEAN);
        return this;
    }

    /**
     * Compile command.
     * @return
     */
    public Maven compile() {
        commands.add(MavenCommand.COMPILE);
        return this;
    }

    /**
     * Unit test command.
     * @return
     */
    public Maven test() {
        commands.add(MavenCommand.TEST);
        return this;
    }

    /**
     * Package command.
     * @return
     */
    public Maven pack() {
        commands.add(MavenCommand.PACKAGE);
        return this;
    }

    /**
     * Verify command.
     * @return
     */
    public Maven verify() {
        commands.add(MavenCommand.VERIFY);
        return this;
    }

    /**
     * Install command.
     * @return
     */
    public Maven install() {
        commands.add(MavenCommand.INSTALL);
        return this;
    }

    /**
     * Close stream or resource.
     * @param closeable
     */
    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.trace("Error closing stream or resource", e);
            }
        }
    }

    /**
     * Destroy given process.
     * @param process
     */
    private void destroyProcess(Process process) {
        if (process != null) {
            try {
                process.destroy();
            } catch (Exception e) {
                log.trace("Error destroying process", e);
            }
        }
    }

    /**
     * Get Java process builder with basic command line execution based on
     * given os nature.
     * @return
     */
    private ProcessBuilder getProcessBuilder() {
        List<String> commands = new ArrayList<String>();
        if (SystemUtils.IS_OS_UNIX) {
            commands.add(BASH);
            commands.add(BASH_OPTION_C);
        } else {
            commands.add(CMD);
            commands.add(CMD_OPTION_C);
        }

        commands.add(buildCommand());

        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(Paths.get("").toFile().getAbsoluteFile());

        log.trace("Returning ProcessBuilder for command:" + commands);
        return pb;
    }

    /**
     * Build command line string with given Maven commands.
     * @return
     */
    private String buildCommand() {
        StringBuilder command = new StringBuilder();

        command.append(MVN);

        for (MavenCommand mavenCommand : commands) {
            command.append(" ").append(mavenCommand.name().toLowerCase());
        }

        return command.toString();
    }

    /**
     * Gets Maven POM content as printable String.
     */
    public void save(Project project) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(getPomFile().getFile());
            serialize(project, fos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save Maven POM file", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.warn("Failed to close file output stream", e);
                }
            }
        }

    }

    /**
     * Gets Maven POM content as printable String.
     */
    public String print(Project project) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serialize(project, bos);

        return bos.toString();
    }

    /**
     * Serialize Maven POM object tree.
     * @param outputStream
     */
    public void serialize(Project project, OutputStream outputStream) {
        StreamResult stream = new StreamResult(outputStream);

        Map<String, Object> marshallerProperties = new HashMap<String, Object>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshallerProperties.put(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "");

        marshaller.setMarshallerProperties(marshallerProperties);
        marshaller.marshal(project, stream);
    }

    /**
     * Gets the Maven POM file resource.
     * @return
     */
    private Resource getPomFile() {
        if (StringUtils.hasText(workingDirectory)) {
            return new FileSystemResource(workingDirectory + "/pom.xml");
        } else {
            return new FileSystemResource("pom.xml");
        }
    }
}
