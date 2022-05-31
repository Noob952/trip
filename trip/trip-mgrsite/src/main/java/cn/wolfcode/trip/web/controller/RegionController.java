package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.Destination;
import cn.wolfcode.trip.domain.Region;
import cn.wolfcode.trip.query.RegionQuery;
import cn.wolfcode.trip.service.IDestinationService;
import cn.wolfcode.trip.service.IRegionService;
import cn.wolfcode.trip.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("region")
public class RegionController {
    @Autowired
    private IRegionService regionService;


    @Autowired
    private IDestinationService destinationService;

    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") RegionQuery qo) {
        Page<Region> page = regionService.page(qo);
        model.addAttribute("page", page);
        //共享所有的目的地
        List<Destination> dests = destinationService.list();
        model.addAttribute("dests", dests);
        return "region/list";
    }

    @GetMapping("/delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        regionService.removeById(id);
        return JsonResult.success();
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Region region) {
        try {
            regionService.saveOrUpdate(region);
            return JsonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.defaultError();
        }
    }

    @GetMapping("/changeHotValue")
    @ResponseBody
    public JsonResult changeHotValue(Integer hot, Long id) {
        regionService.changeHotValue(hot, id);
        return JsonResult.success();
    }
    @GetMapping("getDestByRegionId")
    @ResponseBody
    public List<Destination> getDestByRegionId(Long rid) {
        List<Destination> dests=destinationService.getDestByRegionId(rid);
        return dests;
    }
}
