package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.Travel;
import cn.wolfcode.trip.domain.TravelContent;
import cn.wolfcode.trip.query.TravelQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ITravelService extends IService<Travel> {
    /**
     * 游记的分页查询
     * @param qo
     * @return
     */
    Page<Travel> queryPage(TravelQuery qo);

    /**
     * 根据id查询游记内容
     * @param id
     * @return
     */
    TravelContent getContentById(Long id);

    /**
     * 审核处理操作
     * @param id 游记id
     * @param state 2审核通过，3审核拒绝
     */
    void audit(Long id, Integer state);

    /**
     * 根据目的地查询游记排名前三的数据
     * @param destId
     * @return
     */
    List<Travel> queryViewnumTop3(Long destId);
}
