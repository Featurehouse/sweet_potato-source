package javax.annotation;

import javax.annotation.meta.When;
import java.lang.annotation.*;

@Documented
@Target( { ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE,
        ElementType.PACKAGE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckReturnValue {
    When when() default When.ALWAYS;
}
