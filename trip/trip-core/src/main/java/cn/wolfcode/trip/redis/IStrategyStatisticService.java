package cn.wolfcode.trip.redis;

import cn.wolfcode.trip.vo.StrategyStaticsVO;

public interface IStrategyStatisticService {
    /**
     * 根据攻略id添加对应的阅读数
     * @param strategyId
     */
    void increViewnum(Long strategyId);

    /**
     * 根据攻略id查询对应的统计数据
     * @param sid
     * @return
     */
    StrategyStaticsVO statisVO(Long sid);

    /**
     * 根据攻略id添加对应的评论数量
     * @param id
     */
    void incrReplynum(Long id);

    /**
     * 根据攻略id获取对应的vo对象
     * @param sid
     * @return
     */
    StrategyStaticsVO getStatisVO(Long sid);

    /**
     * 色泽对应的vo对象
     * @param statisVO
     */
    void setStatisVO(StrategyStaticsVO statisVO);
}
