package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.*;
import cn.wolfcode.trip.mongodb.domain.StrategyComment;
import cn.wolfcode.trip.mongodb.service.IStrategyCommentService;
import cn.wolfcode.trip.query.StrategyQuery;
import cn.wolfcode.trip.redis.IStrategyStatisticService;
import cn.wolfcode.trip.redis.IUserInfoRedisService;
import cn.wolfcode.trip.service.IStrategyConditionService;
import cn.wolfcode.trip.service.IStrategyRankService;
import cn.wolfcode.trip.service.IStrategyService;
import cn.wolfcode.trip.service.IStrategyThemeService;
import cn.wolfcode.trip.util.JsonResult;
import cn.wolfcode.trip.vo.StrategyStaticsVO;
import cn.wolfcode.trip.web.annotation.RequireLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("strategies")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @Autowired
    private IStrategyStatisticService strategyStatisticService;

    @Autowired
    private IStrategyCommentService strategyCommentService;
    @Autowired
    private IStrategyRankService strategyRankService;

    @Autowired
    private IStrategyConditionService strategyConditionService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;

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
        //添加阅读数
        strategyStatisticService.increViewnum(id);

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
    public JsonResult rank(Integer type) {
        List<StrategyRank> strategyRanks = strategyRankService.queryRank(type);
        return JsonResult.success(strategyRanks);
    }

    @GetMapping("condition")
    public JsonResult condition(Integer type) {
        List<StrategyCondition> strategyConditions = strategyConditionService.queryCondition(type);
        return JsonResult.success(strategyConditions);
    }

    @PostMapping("commentAdd")
    @RequireLogin
    public JsonResult commentAdd(StrategyComment strategyComment, HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);
        strategyComment.setCreateTime(new Date());
        BeanUtils.copyProperties(userInfo, strategyComment);
        strategyComment.setUserId(userInfo.getId());
        strategyComment.setThumbupnum(0);
        strategyComment.setId(null);
        strategyCommentService.save(strategyComment);
        return JsonResult.success();
    }

    ///评论的分页显示
    @GetMapping("comments")
    public JsonResult comments(StrategyQuery strategyQuery) {
        //jpa:spring data 中的分页对象
        org.springframework.data.domain.Page<StrategyComment> page = strategyCommentService.queryPage(strategyQuery);
        return JsonResult.success(page);
    }

    ///统计数据回显
    @GetMapping("statisVo")
    public JsonResult statisVo(Long sid) {
        StrategyStaticsVO staticsVO = strategyStatisticService.statisVo(sid);

        return JsonResult.success(staticsVO);
    }

}
