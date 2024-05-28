package org.rmerezha.di.configurator;


import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.rmerezha.di.configuration.Configuration;
import org.rmerezha.di.exception.InvalidImplementationCountException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanConfiguratorImpl implements BeanConfigurator {

    private final Reflections reflections;
    private final Map<Class<?>, Class<?>> implemantations;

    public BeanConfiguratorImpl(Configuration configuration) {
        reflections = new Reflections(
                new ConfigurationBuilder()
                        .addScanners(Scanners.SubTypes.filterResultsBy(s->true))
                        .forPackages(configuration.getPath())
        );
        implemantations = new ConcurrentHashMap<>(configuration.getInterfacesToImplementationMap());
    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceType) {
        return (Class<? extends T>) implemantations.computeIfAbsent(interfaceType, clazz -> {
            Set<Class<? extends T>> implementationClasses = reflections.getSubTypesOf(interfaceType);
            if (implementationClasses.size() != 1) {
                throw new InvalidImplementationCountException("interface has 0 or more than 1 implementations," +
                                                              " use configuration.registerImplementation(Class<?> interfaceType, Class<?> implementationClass)");
            }

            return implementationClasses.stream().findFirst().get();
        });
    }
}
