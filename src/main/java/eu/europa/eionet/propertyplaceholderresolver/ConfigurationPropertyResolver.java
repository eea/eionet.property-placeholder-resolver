package eu.europa.eionet.propertyplaceholderresolver;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public interface ConfigurationPropertyResolver {

    boolean isDefined(String propertyName);
    
    String resolveValue(String propertyName) throws UnresolvedPropertyException, CircularReferenceException;
    
}
