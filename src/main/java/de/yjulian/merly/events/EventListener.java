package de.yjulian.merly.events;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.annotation.*;

/**
 * Annotation to set a method as a listener that should execute when a event is called.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ParametersAreNonnullByDefault
@Documented
public @interface EventListener {
}
