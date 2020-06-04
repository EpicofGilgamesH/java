package com.epic.concurrency.dubboconsumer.Service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import entity.Order;

/**
 * @Author lizzy
 * @Date 2020/5/21 10:44
 * @Version 1.0
 */
public interface OrderService {
    /**
     * 新增提交订单数据
     *
     * @param id
     * @return
     */
    Boolean generatorOrder();

    Boolean submitOrderTrans(Integer Id);

    Order getOrderById(Integer id);
}
