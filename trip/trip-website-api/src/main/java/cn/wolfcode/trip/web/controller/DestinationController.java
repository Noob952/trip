package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.*;
import cn.wolfcode.trip.service.IDestinationService;
import cn.wolfcode.trip.service.IRegionService;
import cn.wolfcode.trip.service.IStrategyService;
import cn.wolfcode.trip.service.ITravelService;
import cn.wolfcode.trip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("destinations")
public class DestinationController {

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private ITravelService travelService;

    @GetMapping("hotRegion")
    public JsonResult hotRegion(){
    List<Region> regions=regionService.hotRegion();
   return JsonResult.success(regions);
    }

    @GetMapping("search")
    public JsonResult search(Long regionId){
        List<Destination> dest=destinationService.search(regionId);
        return JsonResult.success(dest);
    }

    @GetMapping("toasts")
    public JsonResult toasts(Long destId){
        List<Destination> dests=destinationService.queryToasts(destId);
        return JsonResult.success(dests);
    }
    @GetMapping("catalogs")
    public JsonResult catalogs(Long destId){
        List<StrategyCatalog> catalogs=destinationService.queryCatalogs(destId);
        return JsonResult.success(catalogs);
    }
    @GetMapping("strategies/viewnumTop3")
    public JsonResult viewnumTop3Strategy(Long destId){
        List<Strategy> strategies=strategyService.queryViewnumTop3(destId);
        return JsonResult.success(strategies);
    }
    @GetMapping("travels/viewnumTop3")
    public JsonResult viewnumTop3Travel(Long destId){

        List<Travel> travels=travelService.queryViewnumTop3(destId);
        return JsonResult.success(travels);
    }
}
