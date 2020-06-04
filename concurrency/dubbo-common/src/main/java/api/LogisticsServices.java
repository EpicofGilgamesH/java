package api;

/**
 * 物流服务接口
 *
 * @Author lizzy
 * @Date 2020/5/21 10:49
 * @Version 1.0
 */
public interface LogisticsServices {

    /**
     * 通过订单编号提交物流单
     *
     * @param OderId
     * @return
     */
    Boolean submitLogistics(Integer OderId);
}
