package annotation;

import java.lang.annotation.*;

/**
 * @author wangjie
 * @version v1.0
 * @description 自定义Service注解$
 * @date 2020/5/11 10:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyService {
    String value() default "";
}
