package eu.europa.eionet.propertyplaceholderresolver.util;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public interface PropertyResourceLoader {

    Properties loadFromResource(String resourceName) throws IOException;
    
}
