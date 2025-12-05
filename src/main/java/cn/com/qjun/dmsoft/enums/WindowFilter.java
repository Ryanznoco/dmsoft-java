package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 枚举窗口时使用的过滤条件，多个条件通常可以组合使用
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@Getter
@RequiredArgsConstructor
public enum WindowFilter implements DmEnum<Integer> {
    /**
     * 匹配窗口标题,参数title有效
     */
    FILTER_TITLE(0b1),
    /**
     * 匹配窗口类名,参数className有效
     */
    FILTER_CLASS_NAME(0b10),
    /**
     * 只匹配指定父窗口的第一层孩子窗口
     */
    FILTER_CHILDREN_ONLY(0b100),
    /**
     * 匹配父窗口为0的窗口,即顶级窗口
     */
    FILTER_TOP_ONLY(0b1000),
    /**
     * 匹配可见的窗口
     */
    FILTER_VISIBLE_ONLY(0b10000),
    /**
     * 匹配出的窗口按照窗口打开顺序依次排列
     */
    ORDER_BY_OPEN_TIME(0b100000);

    /**
     * 计算多个过滤条件的最终值
     * 这些值可以相加,比如4+8+16就是类似于任务管理器中的窗口列表
     *
     * @param filters 过滤条件
     * @return 最终值
     */
    public static int calcValue(WindowFilter[] filters) {
        int result = 0;
        for (WindowFilter filter : filters) {
            result |= filter.value;
        }
        return result;
    }

    private final Integer value;
}
