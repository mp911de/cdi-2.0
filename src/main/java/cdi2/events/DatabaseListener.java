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
package cdi2.events;

import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;

import lombok.extern.log4j.Log4j2;

/**
 * @author Mark Paluch
 */
@Log4j2
public class DatabaseListener {

    /**
     * lower {@link Priority} comes earlier.
     *
     * @param personCreated
     */
    void onPersonCreated(@Observes @Priority(1) PersonCreated personCreated) {
        log.info("onPersonCreated({})", personCreated);
    }

    /**
     * lower {@link Priority} comes earlier.
     *
     * @param personCreated
     */
    void onPersonCreatedAsync(@ObservesAsync PersonCreated personCreated) {
        log.info("onPersonCreatedAsync({})", personCreated);
    }
}
