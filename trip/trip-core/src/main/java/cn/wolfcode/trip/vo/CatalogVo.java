package cn.wolfcode.trip.vo;

import cn.wolfcode.trip.domain.StrategyCatalog;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
public class CatalogVo implements Serializable {
    private String destName;
    private List<StrategyCatalog> catalogList;
}
