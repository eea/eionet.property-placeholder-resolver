package eionet.propertyplaceholderresolver;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public class SystemPropertyProviderImpl implements SystemPropertyProvider {

    @Override
    public boolean isDefined(String propertyName) {
        return System.getProperties().containsKey(propertyName);
    }

    @Override
    public String getPropertyValue(String propertyName) {
        return System.getProperty(propertyName);
    }
    
}
