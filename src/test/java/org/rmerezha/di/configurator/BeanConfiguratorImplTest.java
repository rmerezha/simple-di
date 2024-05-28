package org.rmerezha.di.configurator;

import org.junit.jupiter.api.Test;
import org.rmerezha.di.configuration.Configuration;
import org.rmerezha.di.configuration.JavaConfiguration;
import org.rmerezha.di.exception.InvalidImplementationCountException;
import org.rmerezha.di.util_for_test.Service;
import org.rmerezha.di.util_for_test.ServiceImpl2;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeanConfiguratorImplTest {

    @Test
    public void getImplementationClassWithRegisteredImplementation() {
        Configuration configuration = mock(JavaConfiguration.class);
        Map<Class<?>, Class<?>> interfacesToImplementationMap = new HashMap<>();
        interfacesToImplementationMap.put(Service.class, ServiceImpl2.class);
        when(configuration.getInterfacesToImplementationMap()).thenReturn(interfacesToImplementationMap);
        when(configuration.getPath()).thenReturn(".");

        BeanConfiguratorImpl configurator = new BeanConfiguratorImpl(configuration);

        assertEquals(ServiceImpl2.class, configurator.getImplementationClass(Service.class));
    }

    @Test
    public void getImplementationClassWithoutRegisteredImplementation() {
        Configuration configuration = mock(JavaConfiguration.class);
        Map<Class<?>, Class<?>> interfacesToImplementationMap = new HashMap<>();
        when(configuration.getInterfacesToImplementationMap()).thenReturn(interfacesToImplementationMap);
        when(configuration.getPath()).thenReturn(".");

        BeanConfiguratorImpl configurator = new BeanConfiguratorImpl(configuration);

        assertThrows(InvalidImplementationCountException.class, () -> configurator.getImplementationClass(Service.class));
    }

}