package cn.com.qjun.dmsoft.model;

import cn.com.qjun.commons.geometry.Rect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI检测对象结果
 *
 * @author RenQiang
 * @date 2024/2/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiFindResult {
    /**
     * 类名
     */
    private String className;
    /**
     * 置信度
     */
    private float probability;
    /**
     * 对象所在区域
     */
    private Rect rect;
}
