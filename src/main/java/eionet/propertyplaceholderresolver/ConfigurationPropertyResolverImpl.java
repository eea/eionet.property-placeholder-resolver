package eionet.propertyplaceholderresolver;

import eionet.propertyplaceholderresolver.util.ConfigurationLoadException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public class ConfigurationPropertyResolverImpl implements ConfigurationPropertyResolver {
    
    private SystemPropertyProvider systemPropertyProvider;
    private PlaceholderExpressionEvaluator expressionEvaluator;

    private Map<String, String> configuration;
    
    public ConfigurationPropertyResolverImpl(ConfigurationDefinitionProvider configDefinitionProvider) 
            throws ConfigurationLoadException {
        this(configDefinitionProvider, new SystemPropertyProviderImpl(), new PlaceholderExpressionEvaluator());
    }

    public ConfigurationPropertyResolverImpl(
            ConfigurationDefinitionProvider configDefinitionProvider,
            SystemPropertyProvider systemPropertyProvider,
            PlaceholderExpressionEvaluator expressionEvaluator) throws ConfigurationLoadException {
        this.systemPropertyProvider = systemPropertyProvider;
        this.expressionEvaluator = expressionEvaluator;
        this.configuration = configDefinitionProvider.getConfigurationDefinition();
    }

    @Override
    public boolean isDefined(String propertyName) {
        if (this.systemPropertyProvider.isDefined(propertyName)) {
            return true;
        }
        
        return this.configuration.containsKey(propertyName);
    }

    @Override
    public String resolveValue(String propertyName)
            throws UnresolvedPropertyException, CircularReferenceException {
        return this.resolveValue(propertyName, new HashSet<String>(), new HashMap<String, String>());
    }

    String resolveValue(String propertyName, final Set<String> pendingToResolve, final Map<String, String> cache)
            throws UnresolvedPropertyException, CircularReferenceException {
        if (cache.containsKey(propertyName)) {
            return cache.get(propertyName);
        }
        
        if (pendingToResolve.contains(propertyName)) {
            throw new CircularReferenceException(propertyName);
        }

        pendingToResolve.add(propertyName);
        String value = this.systemPropertyProvider.getPropertyValue(propertyName);

        if (value != null) {
            return this.normalizeAndReturnValue(propertyName, value, pendingToResolve, cache);
        }

        if (!this.configuration.containsKey(propertyName)) {
            throw new UnresolvedPropertyException(propertyName);
        }

        String expression = configuration.get(propertyName);

        if (!StringUtils.isEmpty(expression)) {
            value = this.expressionEvaluator.evaluate(expression, new PlaceholderExpressionEvaluator.PropertyResolutionCallback() {

                @Override
                public String resolvePropertyValue(String propertyName)
                        throws UnresolvedPropertyException, CircularReferenceException {
                    return resolveValue(propertyName, pendingToResolve, cache);
                }
            });
        }

        return this.normalizeAndReturnValue(propertyName, value, pendingToResolve, cache);
    }

    String normalizeAndReturnValue(String propertyName, String value, Set<String> pendingToResolve, Map<String, String> cache) {
        String normalizedValue = value == null ? "" : value;

        pendingToResolve.remove(propertyName);
        cache.put(propertyName, normalizedValue);

        return normalizedValue;
    }

}
