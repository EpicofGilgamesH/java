package annotation;

import java.lang.annotation.*;

/**
 * @author wangjie
 * @version v1.0
 * @description 自定义requestparam注解
 * @date 2020/5/11 10:42
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyRequsetParam {
    String value() default "";
}
