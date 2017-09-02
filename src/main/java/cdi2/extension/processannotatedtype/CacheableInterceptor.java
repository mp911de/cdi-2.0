/*
 * Copyright 2017 the original author or authors.
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
package cdi2.extension.processannotatedtype;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import lombok.extern.log4j.Log4j2;

/**
 * Very naive and simple caching interceptor. Does not consider caching keys, only methods.
 *
 * @author Mark Paluch
 */
@Interceptor
@Cacheable
@Log4j2
public class CacheableInterceptor {

    private final Map<Method, Optional<Object>> cache = new ConcurrentHashMap<>();

    @AroundInvoke
    public Object logCall(InvocationContext ic) throws Exception {

        log.info("Interceptor - start");
        try {

            Optional<Object> result = this.cache.get(ic.getMethod());

            if (result == null) {
                result = Optional.ofNullable(ic.proceed());
                this.cache.put(ic.getMethod(), result);
            }

            return result.orElse(null);
        } finally {
            log.info("Interceptor - end");
        }
    }
}
