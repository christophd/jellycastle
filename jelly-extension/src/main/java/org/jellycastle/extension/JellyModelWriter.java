package org.jellycastle.extension;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelWriter;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.WriterFactory;

import java.io.*;
import java.util.Map;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@Component(role = ModelWriter.class)
public class JellyModelWriter extends DefaultModelWriter {

    @Override
    public void write(Writer writer, Map<String, Object> options, Model model) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("JellyCastle output writer missing");
        }

        checkModel(model);

        try {
            MavenXpp3Writer w = new MavenXpp3Writer();
            w.write(writer, model);
        } finally {
            IOUtil.close(writer);
        }
    }

    @Override
    public void write(File file, Map<String, Object> options, Model model) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("JellyCastle output file missing");
        }

        checkModel(model);

        file.getParentFile().mkdirs();

        write(WriterFactory.newXmlWriter(file), options, model);
    }

    @Override
    public void write(OutputStream outputStream, Map<String, Object> options, Model model) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("JellyCastle output stream missing");
        }

        checkModel(model);

        try {
            String encoding = model.getModelEncoding();
            if (encoding == null || encoding.length() <= 0) {
                encoding = "UTF-8";
            }
            write(new OutputStreamWriter(outputStream, encoding), options, model);
        } finally {
            IOUtil.close(outputStream);
        }
    }

    private void checkModel(Model model) {
        if (model == null) {
            throw new IllegalArgumentException("Maven model missing");
        }
    }
}
