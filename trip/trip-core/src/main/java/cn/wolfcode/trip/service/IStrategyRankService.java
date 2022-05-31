package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.StrategyRank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IStrategyRankService extends IService<StrategyRank> {
    /**
     * 定义一个数据同步接口，根据同步类型进行数据同步
     * @param type 类型 ： 1 国外， 0 国内， 2热门
     */
    void sysnRankDate(int type);

    /**
     * 根据类型查询对应的查询数据
     * @return
     */
    List<StrategyRank> queryRank(Integer type);
}
