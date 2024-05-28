package org.rmerezha.di.context;

import org.rmerezha.di.configuration.JavaConfiguration;
import org.rmerezha.di.model.BeanWithMocks;
import org.rmerezha.di.configuration.Configuration;
import org.rmerezha.di.configurator.BeanConfigurator;
import org.rmerezha.di.configurator.BeanConfiguratorImpl;
import org.rmerezha.di.factory.BeanFactory;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private final BeanFactory factory;
    private final Map<Class<?>, Object> beans;

    public ApplicationContext(Configuration configuration) {
        BeanConfigurator configurator = new BeanConfiguratorImpl(configuration);
        factory = new BeanFactory(configurator);
        factory.setApplicationContext(this);
        beans = new ConcurrentHashMap<>();
    }

    public <T> T getBean(Class<T> interfaceType) {
        if (beans.containsKey(interfaceType)) {
            return (T) beans.get(interfaceType);
        }

        T bean = factory.getBean(interfaceType);

        beans.put(interfaceType, bean);

        return bean;

    }

    public <T> BeanWithMocks<T> getBeanForTest(Class<T> interfaceType) {
        return factory.getBeanForTest(interfaceType);
    }

}
