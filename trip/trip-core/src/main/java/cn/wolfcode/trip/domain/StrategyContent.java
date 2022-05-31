package cn.wolfcode.trip.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 攻略内容
 */
@Setter
@Getter
@TableName("strategy_content")
public class StrategyContent implements Serializable {
    private Long id;  //关联攻略表id
    private String content;
}

