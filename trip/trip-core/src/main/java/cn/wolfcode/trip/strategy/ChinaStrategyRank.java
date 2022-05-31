package cn.wolfcode.trip.strategy;

import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.domain.StrategyRank;
import cn.wolfcode.trip.service.IStrategyRankService;
import cn.wolfcode.trip.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("chinaStrategyRank")
public class ChinaStrategyRank implements IStrategyRank {
    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private IStrategyRankService strategyRankService;

    @Override
    public void sysnStrategyRankData() {
        QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("isabroad", 0).orderByDesc("favornum+thumbsupnum")
                .last("LIMIT 10");
        List<Strategy> strategies = strategyService.list(queryWrapper);
        Date now=new Date();
        ArrayList<StrategyRank> ranks = new ArrayList<StrategyRank>();
        for (Strategy strategy : strategies) {
            StrategyRank strategyRank = new StrategyRank();
            strategyRank.setDestId(strategy.getId());
            strategyRank.setStrategyId(strategy.getId());
            strategyRank.setStrategyTitle(strategy.getTitle());
            strategyRank.setDestName(strategy.getDestName());
            strategyRank.setType(StrategyRank.TYPE_CHINA);
            strategyRank.setStatisnum(strategy.getFavornum()+strategy.getThumbsupnum()+0L);
            strategyRank.setStatisTime(now);
            ranks.add(strategyRank);
        }
        strategyRankService.saveBatch(ranks);
    }
}
