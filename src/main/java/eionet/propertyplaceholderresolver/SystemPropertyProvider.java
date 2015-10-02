package eionet.propertyplaceholderresolver;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public interface SystemPropertyProvider {
    
    boolean isDefined(String propertyName);
    
    String getPropertyValue(String propertyName);
}
