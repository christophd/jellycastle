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

package org.jellycastle.build.dependency;

import org.jellycastle.annotation.Build;
import org.jellycastle.annotation.dependency.Dependency;
import org.jellycastle.annotation.jar.JavaBuild;
import org.jellycastle.annotation.plugin.Plugin;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@JavaBuild
@Build(groupId = "org.sample",
        artifactId = "build-sample")
public class SampleBuild {

    @Dependency(groupId = "org.springframework",
                artifactId = "spring-beans")
    public void springBeans() {
    }

    @Plugin(groupId = "org.apache.maven.plugins",
            artifactId = "maven-jar-plugin",
            version = "2.5")
    public void mavenJarPlugin() {
    }
}
