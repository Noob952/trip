package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.StrategyCatalog;
import cn.wolfcode.trip.mapper.StrategyCatalogMapper;
import cn.wolfcode.trip.query.StrategyCatalogQuery;
import cn.wolfcode.trip.service.IStrategyCatalogService;
import cn.wolfcode.trip.vo.CatalogVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StrategyCatalogServiceImpl extends ServiceImpl<StrategyCatalogMapper, StrategyCatalog> implements IStrategyCatalogService {

    @Override
    public IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo) {
        QueryWrapper<StrategyCatalog> queryWrapper=new QueryWrapper<>();
        Page<StrategyCatalog> page=new Page<>(qo.getCurrentPage(),qo.getPageSize());
        return super.page(page,queryWrapper);
    }

    @Override
    public List<CatalogVo> queryCatalogVos() {
        List<CatalogVo> catalogVos=new ArrayList<>();
        QueryWrapper<StrategyCatalog> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("dest_name","GROUP_CONCAT(id) ids","GROUP_CONCAT(name) names")
                .groupBy("dest_name");
        List<Map<String, Object>> mapList = super.listMaps(queryWrapper);
        for (Map<String, Object> map : mapList) {
            CatalogVo vo=new CatalogVo();
            String destName=map.get("dest_name").toString();
            vo.setDestName(destName);
            String[] idsArr=map.get("ids").toString().split(",");
            String[] namesArr=map.get("names").toString().split(",");
            ArrayList<StrategyCatalog> catalogList = new ArrayList<>();
            for (int i=0;i<idsArr.length;i++){
                Long id=Long.valueOf(idsArr[i]);
                String name=namesArr[i];
                StrategyCatalog strategyCatalog=new StrategyCatalog();
                strategyCatalog.setId(id);
                strategyCatalog.setName(name);
                catalogList.add(strategyCatalog);
            }
            vo.setCatalogList(catalogList);
            catalogVos.add(vo);
        }
        return catalogVos;
    }
}
