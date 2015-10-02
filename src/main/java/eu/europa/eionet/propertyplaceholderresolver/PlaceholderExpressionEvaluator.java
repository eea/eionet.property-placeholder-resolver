package eu.europa.eionet.propertyplaceholderresolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public class PlaceholderExpressionEvaluator {
    
    public static interface PropertyResolutionCallback {
        
        String resolvePropertyValue(String propertyName) 
                throws UnresolvedPropertyException, CircularReferenceException;
    }
    
    static final Pattern PLACEHOLDER_PATTERN;
    
    static {
        PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");
    }
    
    public String evaluate(String expression, PropertyResolutionCallback propertyResolutionCallback) 
            throws UnresolvedPropertyException, CircularReferenceException {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(expression);
        StringBuilder sb = new StringBuilder();
        int offset = 0;
        
        while (matcher.find()) {
            sb.append(expression.substring(offset, matcher.start()));
            String propertyName = matcher.group(1);
            String resolvedValue = propertyResolutionCallback.resolvePropertyValue(propertyName);
            sb.append(resolvedValue);
            offset = matcher.end();
        }
        
        sb.append(expression.substring(offset, expression.length()));
        
        return sb.toString();
    }
    
}
