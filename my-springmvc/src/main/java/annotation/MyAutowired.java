package annotation;

import java.lang.annotation.*;

/**
 * @author wangjie
 * @version v1.0
 * @description 自定义Autowired注解$
 * @date 2020/5/11 10:39
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAutowired {
    String value() default "";
}
