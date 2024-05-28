package org.rmerezha.di.configuration;

import org.junit.jupiter.api.Test;
import org.rmerezha.di.util_for_test.Service;
import org.rmerezha.di.util_for_test.ServiceImpl1;

import static org.junit.jupiter.api.Assertions.*;

class JavaConfigurationTest {


    @Test
    public void registerImplementation() {
        JavaConfiguration configuration = new JavaConfiguration();
        configuration.registerImplementation(Service.class, ServiceImpl1.class);
        assertTrue(configuration.getInterfacesToImplementationMap().containsKey(Service.class));
        assertEquals(ServiceImpl1.class, configuration.getInterfacesToImplementationMap().get(Service.class));
    }

    @Test
    public void setPath() {
        JavaConfiguration configuration = new JavaConfiguration();
        String path = "/path/to/project";
        configuration.setPath(path);
        assertEquals(path, configuration.getPath());
    }

    @Test
    public void configureWithDefaultPath() {
        JavaConfiguration configuration = new JavaConfiguration();
        configuration.configure();
        assertEquals(".", configuration.getPath());
    }

    @Test
    public void configureWithPath() {
        JavaConfiguration configuration = new JavaConfiguration();
        String path = "/path/to/config";
        configuration.setPath(path);
        configuration.configure();
        assertEquals(path, configuration.getPath());
    }
}
