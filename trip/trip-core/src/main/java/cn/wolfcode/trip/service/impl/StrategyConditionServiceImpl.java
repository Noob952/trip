package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.domain.StrategyCondition;
import cn.wolfcode.trip.mapper.StrategyConditionMapper;
import cn.wolfcode.trip.service.IStrategyConditionService;
import cn.wolfcode.trip.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StrategyConditionServiceImpl extends ServiceImpl<StrategyConditionMapper, StrategyCondition> implements IStrategyConditionService {

    @Autowired
    private IStrategyService strategyService;

    @Override
    public void syncConditionData(int type) {
        if (type == StrategyCondition.TYPE_ABROAD) {
            QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("dest_id", "dest_name", "count(*) statisNum")
                    .eq("isabroad", 1)
                    .groupBy("dest_id,dest_name")
                    .orderByDesc("statisNum");
            List<Map<String, Object>> mapList = strategyService.listMaps(queryWrapper);
            List<StrategyCondition> strategyConditions = new ArrayList<>();
            Date now = new Date();
            for (Map<String, Object> map : mapList) {
                Long destId = Long.valueOf(map.get("dest_id").toString());
                String destName = map.get("dest_name").toString();
                Integer statisNum = Integer.valueOf(map.get("statisNum").toString());
                StrategyCondition strategyCondition = new StrategyCondition();
                strategyCondition.setRefid(destId);
                strategyCondition.setName(destName);
                strategyCondition.setCount(statisNum);
                strategyCondition.setType(StrategyCondition.TYPE_ABROAD);
                strategyCondition.setStatisTime(now);
                strategyConditions.add(strategyCondition);
            }

            super.saveBatch(strategyConditions);
        }else if (type==StrategyCondition.TYPE_CHINA){
            QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("dest_id", "dest_name", "count(*) statisNum")
                    .eq("isabroad",0)
                    .groupBy("dest_id,dest_name")
                    .orderByDesc("statisNum");
            List<Map<String, Object>> mapList = strategyService.listMaps(queryWrapper);
            List<StrategyCondition> strategyConditions = new ArrayList<>();
            Date now = new Date();
            for (Map<String, Object> map : mapList) {
                Long destId = Long.valueOf(map.get("dest_id").toString());
                String destName = map.get("dest_name").toString();
                Integer statisNum = Integer.valueOf(map.get("statisNum").toString());
                StrategyCondition strategyCondition = new StrategyCondition();
                strategyCondition.setRefid(destId);
                strategyCondition.setName(destName);
                strategyCondition.setCount(statisNum);
                strategyCondition.setType(StrategyCondition.TYPE_CHINA);
                strategyCondition.setStatisTime(now);
                strategyConditions.add(strategyCondition);
            }
            super.saveBatch(strategyConditions);
        }else if(type==StrategyCondition.TYPE_THEME){
            QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("theme_id", "theme_name", "count(*) statisNum")
                    .groupBy("theme_id,theme_name")
                    .orderByDesc("statisNum");
            List<Map<String, Object>> mapList = strategyService.listMaps(queryWrapper);
            List<StrategyCondition> strategyConditions = new ArrayList<>();
            Date now = new Date();
            for (Map<String, Object> map : mapList) {
                Long themeId = Long.valueOf(map.get("theme_id").toString());
                String themeName = map.get("theme_name").toString();
                Integer statisNum = Integer.valueOf(map.get("statisNum").toString());
                StrategyCondition strategyCondition = new StrategyCondition();
                strategyCondition.setRefid(themeId);
                strategyCondition.setName(themeName);
                strategyCondition.setCount(statisNum);
                strategyCondition.setType(StrategyCondition.TYPE_THEME);
                strategyCondition.setStatisTime(now);
                strategyConditions.add(strategyCondition);
            }
            super.saveBatch(strategyConditions);
        }
    }

    @Override
    public List<StrategyCondition> queryCondition(Integer type) {
        QueryWrapper<StrategyCondition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type)
                .inSql("statis_time","select max(statis_time) from strategy_condition where type="+type)
                .orderByDesc("count");
        return super.list(queryWrapper);
    }
}
