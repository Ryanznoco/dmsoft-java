package cn.com.qjun.dmsoft.model;

import cn.com.qjun.commons.geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OCR结果（带每个字符的坐标）
 *
 * @author RenQiang
 * @date 2024/2/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcrResultEach {
    /**
     * 识别到的字符串
     */
    private String text;
    /**
     * 每个字符的坐标
     */
    private List<Point> charPoints;
}
