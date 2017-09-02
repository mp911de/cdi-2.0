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

import lombok.extern.log4j.Log4j2;

/**
 * @author Mark Paluch
 */
@Log4j2
@UseCase
class MyService {

    int doSomethingUseful() {
        log.info("doSomethingUseful() - start");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 0;
        }


        log.info("doSomethingUseful() - end ");
        return 42;
    }
}
