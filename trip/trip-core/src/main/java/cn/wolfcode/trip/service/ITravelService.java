package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.Travel;
import cn.wolfcode.trip.domain.TravelContent;
import cn.wolfcode.trip.query.TravelQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 游记服务接口
 */
public interface ITravelService extends IService<Travel> {
    /**
     * 分页
     *
     * @param qo
     * @return
     */
    Page<Travel> queryPage(TravelQuery qo);

    /**
     * 查内容
     *
     * @param id
     * @return
     */
    TravelContent getContentById(Long id);

    /**
     * 审核
     *
     * @param id
     * @param state
     */
    void audit(Long id, int state);

    /**
     * 指定目的地下点击量前3
     *
     * @param destId
     * @return
     */
    List<Travel> queryViewnumTop3(Long destId);

    /**
     * 查询目的地下游记
     *
     * @param destId
     * @return
     */
    List<Travel> queryByDestId(Long destId);
}
