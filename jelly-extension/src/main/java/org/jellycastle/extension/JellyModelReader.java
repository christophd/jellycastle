package org.jellycastle.extension;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelReader;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;
import java.util.Map;

/**
 * @author Christoph Deppisch
 * @since 2.2
 */
@Component(role = ModelReader.class, hint = "annotate")
public class JellyModelReader implements ModelReader {

    @Override
    public Model read(Reader reader, Map<String, ?> options) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("JellyCastle input reader missing");
        } else {
            try {
                Model model = read(reader, options);

                return model;
            } finally {
                IOUtil.close(reader);
            }
        }
    }

    @Override
    public Model read(File file, Map<String, ?> options) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("JellyCastle input file missing");
        } else {
            Model model = read(new BufferedReader(new FileReader(file)), options);
            model.setPomFile(file);
            return model;
        }
    }

    @Override
    public Model read(InputStream inputStream, Map<String, ?> options) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("JellyCastle input stream missing");
        } else {
            try {
                Model model = read(new InputStreamReader(inputStream), options);
                return model;
            } finally {
                IOUtil.close(inputStream);
            }

        }
    }
}
