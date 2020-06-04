package entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流信息实体类
 *
 * @Author lizzy
 * @Date 2020/5/21 10:39
 * @Version 1.0
 */
@Data
public class Logistics implements Serializable {

    private static final long serialVersionUID = 323187876573640840L;
    public Integer id;

    public Integer orderId;

    public String from;

    public String to;

    public Date date;

    public Boolean state;
}
