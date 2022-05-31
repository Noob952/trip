package cn.wolfcode.trip.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DestinationQuery extends  QueryObject {
    private Long parentId;  //父id
    //private Long aaa;
}
