package cn.com.qjun.dmsoft.model;

import cn.com.qjun.commons.geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查找结果
 *
 * @author RenQiang
 * @date 2024/2/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindResult {
    /**
     * 找到的特征序号
     */
    private int index;
    /**
     * 特征所在坐标位置
     */
    private Point point;
    /**
     * 相似度百分比，取值为0-100
     */
    private Integer similarity;
}
