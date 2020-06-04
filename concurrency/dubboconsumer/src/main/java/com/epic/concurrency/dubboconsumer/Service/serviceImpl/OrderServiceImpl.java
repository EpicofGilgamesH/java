package com.epic.concurrency.dubboconsumer.Service.serviceImpl;

import api.LogisticsServices;
import com.alibaba.dubbo.config.annotation.Reference;
import com.epic.concurrency.dubboconsumer.Service.OrderService;
import entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * @Author lizzy
 * @Date 2020/5/21 10:54
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:20880", timeout = 10000)
    private LogisticsServices logisticsServices;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;


    /**
     * 在分布式系统中,要实现事务时
     * 如果在系统并发量不是很大的情况下,可以考虑两阶段提交方式,使用atomikios第三方工具来实现
     * 但是并发量比较大,又需要做事务时,考虑采用tcc柔性事务方式来实现
     *
     * @return
     */
    @Override
    public Boolean generatorOrder() {
        //生成订单
        Order order = new Order();
        //order.setId(1);
        order.setName("My Order");
        //order.setDate(new Date());
        order.setStatus("1");
        order.setVersion(1);
        String sql = "insert into `order` (`name`,`date`,`status`,version) values (?,?,?,?)";
       /* int result = jdbcTemplate.update("insert into `order` (ordername,ordertime,orderstatus,version) values (?,?,?,?)",
                order.getName(), order.getDate(), order.getStatus(), order.getVersion());*/
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getName());
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setString(3, order.getStatus());
            ps.setInt(4, order.getVersion());
            return ps;
        }, keyHolder);

        Integer oderId = keyHolder.getKey().intValue(); //返回自动增加的id号
        //生成物流单
        Boolean state = logisticsServices.submitLogistics(oderId);
        log.debug("订单操作成功");
        return state;
    }

    /**
     * TCC柔性事务分析 T:try  C:confirm C:cancel
     * 分为三个阶段 1.先尝试提交物流信息,更改订单状态 2.提交物流信息成功,更新订单状态  3.提交物流信息失败时,回滚订单状态
     *
     * @return
     */
    @Override
    public Boolean submitOrderTrans(Integer id) {
        //修改订单状态
        //为了防止多次重复点击提交订单,这里使用version字段进行验证
        boolean flag = transactionTemplate.execute(state -> {
            //修改订单状态
            int result = jdbcTemplate.update("update `order` set `status`=? , version=version+1 where  id=? and version=0", 4, id);
            return result == 1;
        });

        //由于在更新数据时,在事务中会通过索引生成行锁,第一个线程进行事务更新时,其他线程进行等待
        // 当然这里可以使用redis的setnx实现锁机制
        Boolean state = false;
        if (flag) {
            //第三方调用不占用事务
            state = logisticsServices.submitLogistics(id);
            //生成物流单
            if (state) {
                state = transactionTemplate.execute(s -> jdbcTemplate.update("update `order` set `status`=? where id=?", 1, id) == 1);
                log.debug("订单最终状态" + state.toString());
            } else {
                log.debug("第三方调用失败,订单表已回滚...");
                //订单状态回滚到初始状态
                jdbcTemplate.update("update `order` set `status`=? and `version`=0 where id=?", 0, id);
            }
        } else {
            log.debug("订单失败,正在订单中...orderId" + id);
            return false;
        }
        return state;
    }

    @Override
    public Order getOrderById(Integer id) {
        String sql = "select * from `order` where id=?";
        RowMapper<Order> mapper = BeanPropertyRowMapper.newInstance(Order.class);
        List<Order> list = jdbcTemplate.query(sql, mapper, id);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
