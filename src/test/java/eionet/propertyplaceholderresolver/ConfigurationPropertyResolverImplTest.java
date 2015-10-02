/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eionet.propertyplaceholderresolver;

import eionet.propertyplaceholderresolver.CircularReferenceException;
import eionet.propertyplaceholderresolver.ConfigurationDefinitionProvider;
import eionet.propertyplaceholderresolver.ConfigurationPropertyResolverImpl;
import eionet.propertyplaceholderresolver.UnresolvedPropertyException;
import eionet.propertyplaceholderresolver.util.ConfigurationLoadException;
import java.util.HashMap;
import java.util.Map;
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public class ConfigurationPropertyResolverImplTest {

    @Test
    public void testResolveValue() throws Exception {
        ConfigurationDefinitionProvider provider = new ConfigurationDefinitionProvider() {

            @Override
            public Map<String, String> getConfigurationDefinition() throws ConfigurationLoadException {
                Map<String, String> map = new HashMap<String, String>();
                map.put("app.home", "/home/user");
                map.put("log.filename", "xmlconv.log");
                map.put("log.file", "${app.home}/${log.filename}");
                map.put("xsl.folder", "${app.home}/xsl");
                map.put("tmp.folder", "${app.home}/tmp");
                return map;
            }
        };

        ConfigurationPropertyResolverImpl c = new ConfigurationPropertyResolverImpl(provider);
        assertEquals("/home/user", c.resolveValue("app.home"));
        assertEquals("/home/user/xsl", c.resolveValue("xsl.folder"));
        assertEquals("/home/user/tmp", c.resolveValue("tmp.folder"));
        assertEquals("/home/user/xmlconv.log", c.resolveValue("log.file"));

    }

    @Test(expected = CircularReferenceException.class)
    public void testResolveValueShouldThrowCircularReferenceException() throws CircularReferenceException, UnresolvedPropertyException, ConfigurationLoadException {
        ConfigurationDefinitionProvider provider = new ConfigurationDefinitionProvider() {

            @Override
            public Map<String, String> getConfigurationDefinition() throws ConfigurationLoadException {
                Map<String, String> map = new HashMap<String, String>();
                map.put("root.home", "${app.home}");
                map.put("app.home", "${root.home}");
                return map;
            }
        };

        ConfigurationPropertyResolverImpl c = new ConfigurationPropertyResolverImpl(provider);
        c.resolveValue("app.home");
    }
    @Test(expected = CircularReferenceException.class)
    public void testResolveValueWithMultiplePlaceholderInValueShouldThrowCircularReferenceException() throws CircularReferenceException, UnresolvedPropertyException, ConfigurationLoadException {
        ConfigurationDefinitionProvider provider = new ConfigurationDefinitionProvider() {

            @Override
            public Map<String, String> getConfigurationDefinition() throws ConfigurationLoadException {
                Map<String, String> map = new HashMap<String, String>();
                map.put("a", "${b}/${c}");
                map.put("b", "${d}");
                map.put("c", "${e}");
                map.put("d", "${e}");
                map.put("d", "${a}");
                return map;
            }
        };

        ConfigurationPropertyResolverImpl c = new ConfigurationPropertyResolverImpl(provider);
        c.resolveValue("a");
    }
    @Test(expected = UnresolvedPropertyException.class)
    public void testResolveValuThrowsUnresolvedPropertyException() throws CircularReferenceException, UnresolvedPropertyException, ConfigurationLoadException {
        ConfigurationDefinitionProvider provider = new ConfigurationDefinitionProvider() {

            @Override
            public Map<String, String> getConfigurationDefinition() throws ConfigurationLoadException {
                Map<String, String> map = new HashMap<String, String>();
                map.put("a", "${b}/${c}");
                map.put("b", "test");
                return map;
            }
        };

        ConfigurationPropertyResolverImpl c = new ConfigurationPropertyResolverImpl(provider);
        c.resolveValue("a");
    }
    @Test(expected = ConfigurationLoadException.class)
    public void testResolveValuThrowsConfigurationLoadException() throws CircularReferenceException, UnresolvedPropertyException, ConfigurationLoadException {
        ConfigurationDefinitionProvider provider = new ConfigurationDefinitionProvider() {

            @Override
            public Map<String, String> getConfigurationDefinition() throws ConfigurationLoadException {
               throw new ConfigurationLoadException(new RuntimeException());
            }
        };

        ConfigurationPropertyResolverImpl c = new ConfigurationPropertyResolverImpl(provider);
        c.resolveValue("a");
    }

}
