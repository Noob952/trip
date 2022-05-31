package cn.wolfcode.trip.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@TableName("destination")
public class Destination  extends BaseDomain {
    //目的地名称
    private String name;
    //英文名称
    private String english;
    //背景图片地址
    private String coverUrl;
    //备注信息
    private String info;
    //上级地址名称
    private String parentName;
    //上级地址ID
    private Long parentId;
    //所有的子目的地
    @TableField(exist = false)
    private List<Destination> children;

    public String getJsonString(){
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("english",english);
        map.put("coverUrl",coverUrl);
        map.put("parentName",parentName);
        map.put("parentId",parentId);
        map.put("info",info);
        return JSON.toJSONString(map);
    }


}
