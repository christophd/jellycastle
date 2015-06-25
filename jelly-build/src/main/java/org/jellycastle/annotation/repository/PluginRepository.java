package org.jellycastle.annotation.repository;

import java.lang.annotation.*;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginRepository {
}
