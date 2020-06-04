package entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 10:32
 */
@Data
public class Person implements Serializable {
    private static final long serialVersionUID = 5055906144950942167L;
    private String name;
    private Integer age;
    private String professional;
    private Boolean gender;
}
