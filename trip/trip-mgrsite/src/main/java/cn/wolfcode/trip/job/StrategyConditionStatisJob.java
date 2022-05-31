package cn.wolfcode.trip.job;

import cn.wolfcode.trip.service.IStrategyConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class StrategyConditionStatisJob {
    @Autowired
    private IStrategyConditionService strategyConditionService;

    @Scheduled(cron="0/10 * * * * *")
    @Async //使用异步方式执行
    public void doWork(){
        System.out.println("++++++++++++同步攻略条件数据开始++++++++++"+System.currentTimeMillis());
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        strategyConditionService.syncConditionData(1);
        strategyConditionService.syncConditionData(2);
        strategyConditionService.syncConditionData(3);
        System.out.println("++++++++++++同步攻略条件数据结束++++++++++");
    }
}
