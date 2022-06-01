package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.Travel;
import cn.wolfcode.trip.domain.TravelContent;
import cn.wolfcode.trip.query.TravelQuery;
import cn.wolfcode.trip.service.ITravelService;
import cn.wolfcode.trip.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("travel")
public class TravelController {
    @Autowired
    private ITravelService travelService;

    @RequestMapping("list")
    public String list(Model model,@ModelAttribute("qo") TravelQuery qo){
        Page<Travel> page=travelService.queryPage(qo);
        model.addAttribute("page",page);
        return "travel/list";
    }

    @RequestMapping("getContentById")
    @ResponseBody
    public JsonResult getContentById(Long id){
        TravelContent content=travelService.getContentById(id);
        return JsonResult.success(content.getContent());
    }
    @RequestMapping("audit")
    @ResponseBody
    public JsonResult audit(Long id,Integer state){
        travelService.audit(id,state);
        return JsonResult.success();
    }
}
