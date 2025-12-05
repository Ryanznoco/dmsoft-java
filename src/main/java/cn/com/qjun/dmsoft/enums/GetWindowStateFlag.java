package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 获取窗口状态时使用的标识
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@Getter
@RequiredArgsConstructor
public enum GetWindowStateFlag implements DmEnum<Integer> {
    /**
     * 判断窗口是否存在
     */
    IS_EXIST(0),
    /**
     * 判断窗口是否处于激活
     */
    IS_ACTIVE(1),
    /**
     * 判断窗口是否可见
     */
    IS_VISIBLE(2),
    /**
     * 判断窗口是否最小化
     */
    IS_MINIMIZED(3),
    /**
     * 判断窗口是否最大化
     */
    IS_MAXIMIZED(4),
    /**
     * 判断窗口是否置顶
     */
    IS_TOP(5),
    /**
     * 判断窗口是否无响应
     */
    IS_UNRESPONSIVE(6),
    /**
     * 判断窗口是否可用(灰色为不可用)
     */
    IS_AVAILABLE(7),
    /**
     * 另外的方式判断窗口是否无响应,如果6无效可以尝试这个
     */
    IS_UNRESPONSIVE_2(8),
    /**
     * 判断窗口所在进程是不是64位
     */
    IS_64BIT_PROCESS(9);

    private final Integer value;
}
