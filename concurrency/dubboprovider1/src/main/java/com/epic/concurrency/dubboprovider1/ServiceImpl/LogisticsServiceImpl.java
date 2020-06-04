package com.epic.concurrency.dubboprovider1.ServiceImpl;

import api.LogisticsServices;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author lizzy
 * @Date 2020/5/21 10:55
 * @Version 1.0
 */
@Service(version = "1.0.0", timeout = 10000)
@Component
@Slf4j
public class LogisticsServiceImpl implements LogisticsServices {
    @Override
    public Boolean submitLogistics(Integer OderId) {
        //模拟物流单提交业务逻辑
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("物流订单操作成功");
        return true;
    }
}
