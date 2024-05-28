package org.rmerezha.di.configurator;

public interface BeanConfigurator {

    <T> Class<? extends T> getImplementationClass(Class<T> interfaceType);
}
