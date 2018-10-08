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

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test using {@link CdiExtension}.
 * 
 * @author Mark Paluch
 * @link https://github.com/BrynCooke/cdi-unit/issues/103
 */
@ExtendWith(CdiExtension.class) // only bootstrap types in the same package.
@RequiredArgsConstructor(onConstructor_ = @Inject)
class MyCdiTest {

	private final MyApplicationScopedDependency appScoped;
	private final MyDefaultScopedDependency defaultScoped;

	/* @Deprecated */
	@Inject private MyApplicationScopedDependency myOtherAppScopedDependency;
	@Inject private MyDefaultScopedDependency myOtherDefaultScopedDependency;

	/**
	 * Wiring should work.
	 */
	@Test
	void shouldWireMyDependency() {
		assertThat(appScoped).isNotNull();
	}

	/**
	 * @ApplicationScoped should resolve always to the same dependency.
	 */
	@Test
	void shouldResolveToSameAppScopedDependency() {
		assertThat(appScoped).isSameAs(myOtherAppScopedDependency);
	}

	/**
	 * @Default scoped dependencies are instantiated on each dependency retrieval.
	 */
	@Test
	void shouldResolveToDifferentDefaultScopedDependency() {
		assertThat(defaultScoped).isNotSameAs(myOtherDefaultScopedDependency);
	}
}
