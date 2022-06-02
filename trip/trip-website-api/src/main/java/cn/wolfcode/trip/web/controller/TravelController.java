package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.Travel;
import cn.wolfcode.trip.domain.TravelContent;
import cn.wolfcode.trip.query.TravelQuery;
import cn.wolfcode.trip.service.ITravelService;
import cn.wolfcode.trip.service.IUserInfoService;
import cn.wolfcode.trip.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("travels")
public class TravelController {
    @Autowired
    private ITravelService travelService;

    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("query")
    public JsonResult query(TravelQuery qo) {
        Page<Travel> page = travelService.queryPage(qo);
        return JsonResult.success(page);
    }

    @RequestMapping("detail")
    public JsonResult detail(Long id) {
        Travel travel = travelService.getById(id);
        TravelContent content = travelService.getContentById(id);
        travel.setContent(content);
        if (travel.getAuthorId() != null) {
            travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        }
        return JsonResult.success(travel);
    }

}
