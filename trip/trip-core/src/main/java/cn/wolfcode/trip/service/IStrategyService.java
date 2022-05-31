package cn.wolfcode.trip.service;


import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.domain.StrategyContent;
import cn.wolfcode.trip.query.StrategyQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略文章服务接口
 */
public interface IStrategyService extends IService<Strategy>{
    /**
     * 攻略的分页查询
     * @param qo
     * @return
     */
        Page<Strategy> page(StrategyQuery qo);

    /**
     * 自定义一个保存数据的方法
     * @param strategy
     * @return
     */
    boolean saveOrUpdate(Strategy strategy);

    /**
     * 根据攻略id查询攻略内容
     * @param id
     * @return
     */
    StrategyContent getContent(Long id);

    /**
     * 根据目的地id查询攻略的点击数量前三
     * @param destId
     * @return
     */
    List<Strategy> queryViewnumTop3(Long destId);

    /**
     * 分页查询数据
     * @param qo 查询条件的封装
     * @return
     */
    Page<Strategy> queryPage(StrategyQuery qo);
}
