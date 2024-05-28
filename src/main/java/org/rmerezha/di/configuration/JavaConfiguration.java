package org.rmerezha.di.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JavaConfiguration implements Configuration {

    private final static String DEFAULT_PATH = ".";
    private final Map<Class<?>, Class<?>> interfacesToImplementationMap;
    private String path = DEFAULT_PATH;

    public JavaConfiguration() {
        interfacesToImplementationMap = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Configuration registerImplementation(Class<T> interfaceType, Class<? extends T> implementationClass) {
        interfacesToImplementationMap.put(interfaceType, implementationClass);
        return this;
    }

    @Override
    public Configuration setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Map<Class<?>, Class<?>> getInterfacesToImplementationMap() {
        return interfacesToImplementationMap;
    }

    @Override
    public Configuration configure() {
        if (path == null) {
            path = DEFAULT_PATH;
        }
        return this;
    }
}
