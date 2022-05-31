package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.Region;
import cn.wolfcode.trip.query.RegionQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IRegionService extends IService<Region> {
    /**
     * 分页查询
     * @param qo
     * @return
     */
    public Page<Region> page(RegionQuery qo);

    /**
     * 根据修改状态
     * @param hot 0 修改为非热门，1 修改为热门
     * @param id 单据id
     */
    void changeHotValue(Integer hot, Long id);

    /**
     * 查询热门区域
     * @return 热门区域列表
     */
    List<Region> hotRegion();

}
