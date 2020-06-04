package entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 订单实体
 *
 * @Author lizzy
 * @Date 2020/5/21 10:33
 * @Version 1.0
 */
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1889365103036029748L;
    public Integer id;

    public String name;

    public Date date;

    public String status;

    public Integer version;

    public String toString() {
        return JSON.toJSONString(this);
    }
}
