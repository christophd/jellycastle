/*
 * Copyright 2015-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jellycastle.annotation;

import org.jellycastle.annotation.repository.PluginRepository;
import org.jellycastle.annotation.repository.Repository;

import java.lang.annotation.*;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Build {

    String modelVersion() default "4.0.0";

    String groupId();
    String artifactId();
    String version() default "1.0-SNAPSHOT";

    String name() default "";
    String description() default "";
    String url() default "";

    Class<?>[] modules() default {};

    String organization() default "";
    String inceptionYear() default "";

    String issueManagement() default "";
    String ciManagement() default "";

    String scm() default "";

    String[] mailingLists() default {};
    String[] developers() default {};
    String[] contributors() default {};
    String[] licenses() default {};

    String distributionManagement() default "";

    String reports() default "";
    String reporting() default "";

    Repository[] repositories() default {};
    PluginRepository[] pluginRepositories() default {};
}
