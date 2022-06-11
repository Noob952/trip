package cn.wolfcode.trip.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrategyStaticsVO {

    private Long strategyId;//攻略id
    private Integer viewnum;
    private Integer replynum;
    private Integer favornum;
    private Integer sharenum;
    private Integer thumbupnum;
}
