package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.domain.StrategyCatalog;
import cn.wolfcode.trip.domain.StrategyContent;
import cn.wolfcode.trip.domain.StrategyTheme;
import cn.wolfcode.trip.query.StrategyQuery;
import cn.wolfcode.trip.service.IStrategyCatalogService;
import cn.wolfcode.trip.service.IStrategyService;
import cn.wolfcode.trip.service.IStrategyThemeService;
import cn.wolfcode.trip.util.JsonResult;
import cn.wolfcode.trip.vo.CatalogVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("strategy")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @RequestMapping("list")
    public String  list(Model model, @ModelAttribute("qo")StrategyQuery qo){
        Page<Strategy> page=strategyService.page(qo);
        model.addAttribute("page",page);
        return "strategy/list";
    }
    @GetMapping("delete")
    @ResponseBody
    public JsonResult  delete(Long id){
        strategyService.removeById(id);
        return JsonResult.success();
    }
    @GetMapping("input")
    public String input(Model model,Long id){
        if (id!=null){
            Strategy strategy=strategyService.getById(id);
            StrategyContent content=strategyService.getContent(id);
            strategy.setContent(content);
            model.addAttribute("strategy",strategy);
        }
        //共享主题
        List<StrategyTheme> themes = strategyThemeService.list();
        model.addAttribute("themes",themes);
        //攻略分类
        List<CatalogVo> catalogs=strategyCatalogService.queryCatalogVos();
        model.addAttribute("catalogs",catalogs);
        return "strategy/input";
    }

    @PostMapping("saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Strategy strategy){
        strategyService.saveOrUpdate(strategy);
        return JsonResult.success();
    }
}
