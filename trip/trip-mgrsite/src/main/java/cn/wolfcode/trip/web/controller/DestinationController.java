package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.Destination;
import cn.wolfcode.trip.query.DestinationQuery;
import cn.wolfcode.trip.service.IDestinationService;
import cn.wolfcode.trip.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("destination")
public class DestinationController {
    @Autowired
    private IDestinationService destinationService;

    @RequestMapping("list")
    public String list(Model model, @ModelAttribute("qo") DestinationQuery qo){
        Page<Destination> page=destinationService.page(qo);
        model.addAttribute("page",page);
        //需要共享上级所有的目的地集合
      if (qo.getParentId()!=null&&qo.getParentId()>0){
          List<Destination> toasts=destinationService.queryToasts(qo.getParentId());
          model.addAttribute("toasts",toasts);
      }

        return "destination/list";
    }

    //删除方法
    @GetMapping("delete")
    @ResponseBody
    public JsonResult delete(Long id){
        destinationService.removeById(id);
        return JsonResult.success();
    }
    //编辑
    @PostMapping("updateInfo")
    @ResponseBody
    public JsonResult updateInfo(Destination destination){
        destinationService.saveOrUpdate(destination);
        return JsonResult.success();
    }
}
