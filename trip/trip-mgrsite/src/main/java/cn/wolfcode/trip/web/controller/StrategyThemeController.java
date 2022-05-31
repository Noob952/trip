package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.StrategyTheme;
import cn.wolfcode.trip.query.StrategyThemeQuery;
import cn.wolfcode.trip.service.IStrategyThemeService;
import cn.wolfcode.trip.util.JsonResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("strategyTheme")
public class StrategyThemeController {

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @RequestMapping("list")
    public String list(Model model,@ModelAttribute("qo") StrategyThemeQuery qo){
        IPage<StrategyTheme> page = strategyThemeService.queryPage(qo);
        model.addAttribute("page",page);
        return "strategyTheme/list";
    }

    @RequestMapping("get")
    @ResponseBody
    public JsonResult get(Long id){
        return JsonResult.success(strategyThemeService.getById(id));
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(StrategyTheme strategyTheme){
      strategyThemeService.saveOrUpdate(strategyTheme);
    return JsonResult.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Long id){
      strategyThemeService.removeById(id);
    return JsonResult.success();
    }
}
