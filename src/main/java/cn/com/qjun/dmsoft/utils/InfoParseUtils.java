package cn.com.qjun.dmsoft.utils;

import cn.com.qjun.commons.geometry.Point;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 信息解析工具
 *
 * @author RenQiang
 * @date 2024/2/14
 */
public class InfoParseUtils {

    /**
     * 将字符串解析成坐标点
     * 字符串格式：x分隔符y，例如 "100,200" 或 "50|75"
     *
     * @param str 要解析的字符串
     * @param separator 分隔符
     * @return 解析后的坐标点
     * @throws NumberFormatException 如果字符串格式不正确
     * @throws ArrayIndexOutOfBoundsException 如果字符串不包含分隔符
     */
    public static Point parsePoint(String str, String separator) {
        String[] parts = str.split(separator);
        return Point.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    /**
     * 分割字符串
     * 如果字符串为 null 或空，返回空列表
     *
     * @param str 要分割的字符串
     * @param regex 分隔符（正则表达式）
     * @return 分割结果列表，如果输入为 null 或空则返回空列表
     */
    public static List<String> splitString(String str, String regex) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(str.split(regex))
                .collect(Collectors.toList());
    }
}
