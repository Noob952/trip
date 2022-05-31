package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.StrategyCatalog;
import cn.wolfcode.trip.query.StrategyCatalogQuery;
import cn.wolfcode.trip.service.IStrategyCatalogService;
import cn.wolfcode.trip.util.JsonResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("strategyCatalog")
public class StrategyCatalogController {
    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @RequestMapping("list")
    public String list(Model model, @ModelAttribute("qo") StrategyCatalogQuery qo){
        IPage<StrategyCatalog> page = strategyCatalogService.queryPage(qo);
        model.addAttribute("page",page);
        return "strategyCatalog/list";
    }

    @RequestMapping("get")
    @ResponseBody
    public JsonResult get(Long id){
        return JsonResult.success(strategyCatalogService.getById(id));
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(StrategyCatalog strategyCatalog){
        strategyCatalogService.saveOrUpdate(strategyCatalog);
        return JsonResult.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Long id){
        strategyCatalogService.removeById(id);
        return JsonResult.success();
    }

}
