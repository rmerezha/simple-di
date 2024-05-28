package org.rmerezha.di.factory;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mockito.Mockito;
import org.rmerezha.di.model.BeanWithMocks;
import org.rmerezha.di.context.ApplicationContext;
import org.rmerezha.di.annotation.Inject;
import org.rmerezha.di.configurator.BeanConfigurator;
import org.rmerezha.di.exception.NoDefaultConstructorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BeanFactory {

    private final BeanConfigurator configurator;
    @Setter
    private ApplicationContext applicationContext;

    public <T> T getBean(Class<T> type) {
        Class<? extends T> implementationClass = type;

        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            implementationClass = configurator.getImplementationClass(type);
        }

        try {
            Constructor<? extends T> beanConstructor = implementationClass.getDeclaredConstructor();
            beanConstructor.setAccessible(true);
            T bean = beanConstructor.newInstance();

            for (var field : implementationClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    field.set(bean, applicationContext.getBean(field.getType()));
                }
            }
            return bean;
        } catch (Exception e) {
            throw new NoDefaultConstructorException("Implementation does not have constructor without parameters");
        }
    }

    public <T> BeanWithMocks<T> getBeanForTest(Class<T> type) {
        Class<? extends T> implementationClass = type;

        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            implementationClass = configurator.getImplementationClass(type);
        }

        try {
            Constructor<? extends T> beanConstructor = implementationClass.getDeclaredConstructor();
            beanConstructor.setAccessible(true);
            T bean = beanConstructor.newInstance();

            Map<Class<?>, Object> mocks = new HashMap<>();
            for (var field : implementationClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    Object mockBean = getMockBean(field.getType());
                    field.set(bean, mockBean);
                    mocks.put(field.getType(), mockBean);
                }
            }
            return new BeanWithMocks<>(bean, mocks);
        } catch (Exception e) {
            throw new NoDefaultConstructorException("Implementation does not have constructor without parameters");
        }
    }

    private <T> T getMockBean(Class<T> type) {
        Class<? extends T> implementationClass = type;

        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            implementationClass = configurator.getImplementationClass(type);
        }

        try {
            Constructor<? extends T> beanConstructor = implementationClass.getDeclaredConstructor();
            beanConstructor.setAccessible(true);
            T bean = beanConstructor.newInstance();

            return (T) Mockito.mock(bean.getClass());
        }  catch (Exception e) {
            throw new NoDefaultConstructorException("Implementation does not have constructor without parameters");
        }
    }


}
