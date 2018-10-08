/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdi2.junit5;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.inject.Qualifier;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

/**
 * Simple CDI extension using {@link SeContainer}.
 * 
 * @author Mark Paluch
 */
public class CdiExtension implements TestInstancePostProcessor, ParameterResolver, AfterAllCallback {

	private static final Predicate<Annotation> IS_QUALIFIER = a -> a.annotationType()
			.isAnnotationPresent(Qualifier.class);

	@Override
	public void afterAll(ExtensionContext context) {

		Store store = getExtensionStore(context);

		SeContainer container = store.get(SeContainer.class, SeContainer.class);
		if (container != null) {
			container.close();
			store.remove(SeContainer.class);
		}
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws IllegalAccessException {

		SeContainer container = getContainer(context);

		for (Field field : getFields(testInstance.getClass())) {

			if (field.getAnnotation(Inject.class) != null) {

				Annotation[] qualifiers = Stream.of(field.getAnnotations()).filter(IS_QUALIFIER).toArray(Annotation[]::new);

				field.setAccessible(true);
				field.set(testInstance, container.select(field.getType(), qualifiers).get());
			}
		}
	}

	private SeContainer getContainer(ExtensionContext context) {

		Store store = getExtensionStore(context);

		SeContainer container = store.getOrComputeIfAbsent(SeContainer.class, it -> SeContainerInitializer.newInstance()
				.addPackages(context.getRequiredTestClass().getPackage()).initialize(), SeContainer.class);
		return container;
	}

	private List<Field> getFields(Class<?> clazzInstance) {

		List<Field> fields = new ArrayList<>();
		if (!clazzInstance.getSuperclass().equals(Object.class)) {
			fields.addAll(getFields(clazzInstance.getSuperclass()));
		} else {
			fields.addAll(Arrays.asList(clazzInstance.getDeclaredFields()));
		}
		return fields;
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return parameterContext.getDeclaringExecutable().isAnnotationPresent(Inject.class);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {

		Parameter parameter = parameterContext.getParameter();
		Annotation[] qualifiers = Stream.of(parameter.getAnnotations()).filter(IS_QUALIFIER).toArray(Annotation[]::new);
		return getContainer(extensionContext).select(parameter.getType(), qualifiers).get();
	}

	private Store getExtensionStore(ExtensionContext context) {
		return context.getStore(Namespace.create("cdi"));
	}
}
