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
package cdi2.requestcontext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.enterprise.context.control.RequestContextController;
import javax.inject.Inject;

import lombok.extern.log4j.Log4j2;

/**
 * @author Mark Paluch
 */
@ApplicationScoped
@Log4j2
class LoginService {

    @Inject
    LoginForm form;

    @Inject
    RequestContextController requestContextController;

    /**
     * Activates the request context for this call and child calls.
     */
    @ActivateRequestContext
    void createLogin() {

        form.setUsername("Username");

        log.info(form.getUsername());
    }

    /**
     * No request context active by default.
     */
    void printLogin() {

        // requestContextController.activate();

        log.info(form.getUsername());

        // requestContextController.deactivate();
    }
}
