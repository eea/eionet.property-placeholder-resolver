package eu.europa.eionet.propertyplaceholderresolver;


import eu.europa.eionet.propertyplaceholderresolver.util.ConfigurationLoadException;
import java.util.Map;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public interface ConfigurationDefinitionProvider {

    Map<String, String> getConfigurationDefinition() throws ConfigurationLoadException;
    
}
