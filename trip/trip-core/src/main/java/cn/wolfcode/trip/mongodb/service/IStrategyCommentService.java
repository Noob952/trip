package cn.wolfcode.trip.mongodb.service;

import cn.wolfcode.trip.mongodb.domain.StrategyComment;
import cn.wolfcode.trip.query.StrategyQuery;
import org.springframework.data.domain.Page;

public interface IStrategyCommentService {
    /**
     * 保存攻略评论对象
     *
     * @param strategyComment
     */
    void save(StrategyComment strategyComment);

    /**
     * 评论内容的分页查询
     *
     * @param strategyQuery
     * @return
     */
    Page<StrategyComment> queryPage(StrategyQuery strategyQuery);
}
