package cn.wolfcode.trip.job;

import cn.wolfcode.trip.service.IStrategyRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class StrategyRankStatisJob {

@Autowired
private IStrategyRankService strategyRankService;
    //传递任务执行表达式
    @Scheduled(cron = " 0/10 * * * * *")
    @Async //使用异步方式执行
    public void doWork(){
        System.out.println("++++++++++++同步攻略排行数据开始++++++++++");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         strategyRankService.sysnRankDate(1);
        strategyRankService.sysnRankDate(2);
        strategyRankService.sysnRankDate(3);
        System.out.println("++++++++++++同步攻略排行数据结束 ++++++++++");
    }
}
