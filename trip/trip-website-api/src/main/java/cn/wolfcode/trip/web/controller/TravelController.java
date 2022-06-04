package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.Travel;
import cn.wolfcode.trip.domain.TravelContent;
import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.mongodb.domain.TravelComment;
import cn.wolfcode.trip.mongodb.service.ITravelCommentService;
import cn.wolfcode.trip.query.TravelQuery;
import cn.wolfcode.trip.redis.IUserInfoRedisService;
import cn.wolfcode.trip.service.ITravelService;
import cn.wolfcode.trip.service.IUserInfoService;
import cn.wolfcode.trip.util.JsonResult;
import cn.wolfcode.trip.web.annotation.RequireLogin;
import cn.wolfcode.trip.web.annotation.UserParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("travels")
public class TravelController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ITravelService travelService;

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Autowired
    private ITravelCommentService travelCommentService;

    @GetMapping("/query")
    public Object query(TravelQuery qo) {
        return JsonResult.success(travelService.queryPage(qo));
    }

    @GetMapping("/detail")
    public Object detail(Long id) {
        Travel travel = travelService.getById(id);
        travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        TravelContent content = travelService.getContentById(id);
        travel.setContent(content);
        return JsonResult.success(travel);
    }

    @RequireLogin
    @PostMapping("/commentAdd")
    public Object commentAdd(TravelComment comment, @UserParam UserInfo userInfo) {

        //用户
    /*    String token = request.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);*/
        BeanUtils.copyProperties(userInfo, comment);
        comment.setCreateTime(new Date());
        comment.setUserId(userInfo.getId());
        comment.setId(null);
        if (comment.getType() == 1) {
            TravelComment refComment = travelCommentService.get(comment.getRefComment().getId());
            comment.setRefComment(refComment);
        }

        travelCommentService.save(comment);
        return JsonResult.success();
    }

    @GetMapping("comments")
    public JsonResult comments(Long travelId) {
        List<TravelComment> data = travelCommentService.findByTravelId(travelId);
        return JsonResult.success(data);
    }

}
