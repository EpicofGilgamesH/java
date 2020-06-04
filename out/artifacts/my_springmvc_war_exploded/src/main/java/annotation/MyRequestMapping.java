package annotation;

import java.lang.annotation.*;

/**
 * @author wangjie
 * @version v1.0
 * @description 自定义requestMapping注解$
 * @date 2020/5/11 10:34
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyRequestMapping {
    String value() default "";
}
