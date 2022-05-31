package cn.wolfcode.trip.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Region extends BaseDomain {
    //区域名字
    private String name;
    //区域编码
    private String sn;
    //是否热门
    private Integer ishot;
    //序号
    private Integer seq;
    //备注信息
    private String info;
    //关联的ids集合
    private String refIds;

    public String getJsonString(){
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("sn",sn);
        map.put("refIds",getRefIds());
        map.put("ishot",ishot);
        map.put("seq",seq);
        map.put("info",info);
        return JSON.toJSONString(map);
    }
    public List<Long> parseRefIds(){
        List<Long> ids=new ArrayList<>();
        if (StringUtils.hasLength(refIds)) {
            String[] refIdArr=refIds.split(",");//split表示以指定正则表达式规则作为分割条件进行分割，返回分割后的数组
            for (String refId : refIdArr) {
                ids.add((Long.valueOf(refId)));
            }
        }return ids;
    }
}
