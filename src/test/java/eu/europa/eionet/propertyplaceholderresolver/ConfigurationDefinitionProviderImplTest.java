/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.europa.eionet.propertyplaceholderresolver;

import eu.europa.eionet.propertyplaceholderresolver.util.PropertyResourceLoader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ervis Zyka <ez@eworx.gr>
 */
public class ConfigurationDefinitionProviderImplTest {

    public ConfigurationDefinitionProviderImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConfigurationDefinition method, of class
     * ConfigurationDefinitionProviderImpl.
     */
    @Test
    public void testGetConfigurationDefinition() throws Exception {
        final Properties properties = new Properties();
        properties.put("app.home", "/home/user");
        properties.put("xsl.folder", "${app.home}/xsl");
        properties.put("log.filename", "test.log");
        properties.put("log.file", "${app.home}/${log.filename}");
        PropertyResourceLoader propertyResourceLoader = new PropertyResourceLoader() {

            @Override
            public Properties loadFromResource(String resourceName) throws IOException {
                return properties;
            }
        };
        ConfigurationDefinitionProvider configurationDefinitionProvider = new ConfigurationDefinitionProviderImpl(propertyResourceLoader, new String[]{"dummy.properties"});
        Map<String, String> expected = configurationDefinitionProvider.getConfigurationDefinition();
        assertEquals("/home/user", expected.get("app.home"));
        assertEquals("${app.home}/xsl", expected.get("xsl.folder"));
        assertEquals("${app.home}/${log.filename}", expected.get("log.file"));
    }
}
