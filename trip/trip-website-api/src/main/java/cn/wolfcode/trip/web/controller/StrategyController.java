package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.domain.StrategyCondition;
import cn.wolfcode.trip.domain.StrategyContent;
import cn.wolfcode.trip.domain.StrategyRank;
import cn.wolfcode.trip.query.StrategyQuery;
import cn.wolfcode.trip.service.IStrategyConditionService;
import cn.wolfcode.trip.service.IStrategyRankService;
import cn.wolfcode.trip.service.IStrategyService;
import cn.wolfcode.trip.service.IStrategyThemeService;
import cn.wolfcode.trip.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("strategies")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @Autowired
    private IStrategyRankService strategyRankService;

    @Autowired
    private IStrategyConditionService strategyConditionService;

    @GetMapping("content")
    public JsonResult content(Long id) {
        StrategyContent content = strategyService.getContent(id);
        return JsonResult.success(content);
    }

    @GetMapping("detail")
    public JsonResult detail(Long id) {
        Strategy strategy = strategyService.getById(id);
        StrategyContent content = strategyService.getContent(id);
        strategy.setContent(content);
        return JsonResult.success(strategy);
    }

    @GetMapping("themes")
    public JsonResult themes() {
        return JsonResult.success(strategyThemeService.list());
    }

    @GetMapping("query")
    public JsonResult query(StrategyQuery qo) {
        Page<Strategy> page = strategyService.queryPage(qo);
        return JsonResult.success(page);
    }

    @GetMapping("rank")
    public JsonResult rank(Integer type){
        List<StrategyRank> strategyRanks=strategyRankService.queryRank(type);
        return JsonResult.success(strategyRanks);
    }
   @GetMapping("condition")
    public JsonResult condition(Integer type){
        List<StrategyCondition> strategyConditions=strategyConditionService.queryCondition(type);
        return JsonResult.success(strategyConditions);
    }

}
