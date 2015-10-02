package eionet.propertyplaceholderresolver;


import eionet.propertyplaceholderresolver.util.ConfigurationLoadException;
import java.util.Map;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public interface ConfigurationDefinitionProvider {

    Map<String, String> getConfigurationDefinition() throws ConfigurationLoadException;
    
}
