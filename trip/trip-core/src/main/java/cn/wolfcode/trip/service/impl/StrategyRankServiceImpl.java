package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.domain.StrategyRank;
import cn.wolfcode.trip.mapper.StrategyRankMapper;
import cn.wolfcode.trip.service.IStrategyRankService;
import cn.wolfcode.trip.service.IStrategyService;
import cn.wolfcode.trip.strategy.IStrategyRank;
import cn.wolfcode.trip.strategy.StrategyRankFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StrategyRankServiceImpl extends ServiceImpl<StrategyRankMapper, StrategyRank> implements IStrategyRankService {
    @Autowired
    private StrategyRankFactory strategyRankFactory;

    @Override
    public void sysnRankDate(int type) {
        IStrategyRank strategyRank=strategyRankFactory.getStrategyRank(type);
        strategyRank.sysnStrategyRankData();
    }

    @Override
    public List<StrategyRank> queryRank(Integer type) {

      /*  SELECT * FROM strategy_rank WHERE type=1 AND statis_time=(SELECT MAX(statis_time) FROM strategy_rank WHERE type =1)
        ORDER BY statisnum DESC*/

        QueryWrapper<StrategyRank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type)
                .inSql("statis_time","SELECT MAX(statis_time) FROM strategy_rank WHERE type="+type)
                .orderByDesc("statisnum");

        return super.list(queryWrapper);
    }
}
