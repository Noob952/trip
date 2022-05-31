package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.Destination;
import cn.wolfcode.trip.domain.StrategyCatalog;
import cn.wolfcode.trip.query.DestinationQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IDestinationService extends IService<Destination> {
    /**
     * 根据区域id获取目的地
     * @param id 区域id
     * @return 目的地对象
     */
    List<Destination> getDestByRegionId(Long id);

    /**
     * 目的地分页查询
     * @param qo 分页查询条件
     * @return
     */
     Page<Destination> page(DestinationQuery qo);

    /**
     * 根据parentId查询所有的上级目的地
     * @param parentId
     * @return
     */
    List<Destination> queryToasts(Long parentId);

    /**
     *根据区域id获取目的地,并查询出每个目的地的子目的地
     * @return
     */
    List<Destination> search(Long regionId);

    /**
     * 根据目的地id查询攻略类别，并且每个类别下面需要查出所有的攻略信息
     * @param destId 目的地id
     * @return
     */
    List<StrategyCatalog> queryCatalogs(Long destId);
}
