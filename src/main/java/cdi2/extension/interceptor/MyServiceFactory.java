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
package cdi2.extension.interceptor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.literal.NamedLiteral;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.transaction.Transactional;

/**
 * @author Mark Paluch
 */
class MyServiceFactory {

    @Produces
    @Default
    MyService myService(InterceptionFactory<MyService> ify) {

        ify.configure().methods().forEach(amc -> amc.add(TransactionalLiteral.of(Transactional.TxType.MANDATORY)));
        ify.configure().add(ApplicationScoped.Literal.INSTANCE).add(NamedLiteral.of("foo"));

        return ify.createInterceptedInstance(new MyService());
    }

}
