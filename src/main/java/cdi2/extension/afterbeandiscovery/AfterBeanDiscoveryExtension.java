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
package cdi2.extension.afterbeandiscovery;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

/**
 * @author Mark Paluch
 */
class AfterBeanDiscoveryExtension implements Extension {

    void afterBeanDiscovery(@Observes AfterBeanDiscovery event) {

        event.addBean()
                .types(MyService.class)
                .scope(RequestScoped.class)
                .createWith(ctx -> new MyService("extension"))
                .name("myservice");
    }
}
