package cn.wolfcode.trip.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StrategyRankFactory {

    @Autowired
    @Qualifier("abroadStrategyRank")
    private IStrategyRank abroadStrategyRank;
    @Autowired
    @Qualifier("chinaStrategyRank")
    private IStrategyRank chinaStrategyRank;

    @Autowired
    @Qualifier("hotStrategyRank")
    private IStrategyRank hotStrategyRank;

    private Map<Integer, IStrategyRank> data = new ConcurrentHashMap<>();

    @PostConstruct  //容器启动之后，初始化数据
    public void initData(){
        data.put(1,abroadStrategyRank);
        data.put(2,chinaStrategyRank);
        data.put(3,hotStrategyRank);
    }

    public IStrategyRank getStrategyRank(int type){
        return data.get(type);
    }
}
