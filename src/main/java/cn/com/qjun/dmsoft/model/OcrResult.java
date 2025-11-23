package cn.com.qjun.dmsoft.model;

import cn.com.qjun.commons.geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OCR结果
 *
 * @author RenQiang
 * @date 2024/2/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcrResult {
    /**
     * 识别到的文字
     */
    private String text;
    /**
     * 文字所在坐标
     */
    private Point point;
}
