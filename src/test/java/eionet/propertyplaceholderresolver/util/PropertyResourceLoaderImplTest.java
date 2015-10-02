package eionet.propertyplaceholderresolver.util;

import eionet.propertyplaceholderresolver.util.PropertyResourceLoaderImpl;
import java.io.IOException;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public class PropertyResourceLoaderImplTest {

    @Test
    public void testLoadFromResource() throws IOException {
        PropertyResourceLoaderImpl p = new PropertyResourceLoaderImpl();
        Properties expected = p.loadFromResource("config.properties");
        assertNotNull(p);
        assertEquals("/home/user", expected.get("app.home"));
        assertEquals("myapp.log", expected.get("log.filename"));
        assertEquals("${app.home}/${log.filename}", expected.get("log.file"));

    }
    @Test(expected = IOException.class)
    public void testLoadFromResourceThrowsIOException() throws IOException {
        PropertyResourceLoaderImpl p = new PropertyResourceLoaderImpl();
        p.loadFromResource("missing.properties");

    }

}
