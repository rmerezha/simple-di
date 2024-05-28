package org.rmerezha.di.configuration;

import java.util.Map;

public interface Configuration {

    <T> Configuration registerImplementation(Class<T> interfaceType, Class<? extends T> implementationClass);

    String getPath();

    Configuration setPath(String path);

    Map<Class<?>, Class<?>> getInterfacesToImplementationMap();

    Configuration configure();
}
