package org.rmerezha.di.model;

import java.util.Map;

public record BeanWithMocks<T>(T bean, Map<Class<?>, Object> mocks) {
}
