package cn.wolfcode.trip.service;


import cn.wolfcode.trip.domain.StrategyCatalog;
import cn.wolfcode.trip.query.StrategyCatalogQuery;
import cn.wolfcode.trip.vo.CatalogVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略分类服务接口
 */
public interface IStrategyCatalogService extends IService<StrategyCatalog>{
    /**
     * 分页
     * @param qo
     * @return
     */
    IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo);

    /**
     * 查询所有的攻略分类并且以目的地分组
     * @return
     */
    List<CatalogVo> queryCatalogVos();

}
