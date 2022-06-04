package cn.wolfcode.trip.mongodb.service;

import cn.wolfcode.trip.mongodb.domain.TravelComment;

import java.util.List;

public interface ITravelCommentService {
    /**
     * 保存游记的评论信息
     *
     * @param comment
     */
    void save(TravelComment comment);

    /**
     * 根据评论id获取对应评论
     *
     * @param id
     * @return
     */
    TravelComment get(String id);

    /**
     * 根据游记id查询游记评论列表
     *
     * @param travelId
     * @return
     */
    List<TravelComment> findByTravelId(Long travelId);
}
