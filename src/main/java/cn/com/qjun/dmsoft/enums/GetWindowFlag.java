package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 获取给定窗口相关的窗口句柄时使用的标识
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@Getter
@RequiredArgsConstructor
public enum GetWindowFlag implements DmEnum<Integer> {
    /**
     * 获取父窗口
     */
    PARENT(0),
    /**
     * 获取第一个儿子窗口
     */
    FIRST_CHILD(1),
    /**
     * 获取First窗口
     */
    FIRST(2),
    /**
     * 获取Last窗口
     */
    LAST(3),
    /**
     * 获取下一个窗口
     */
    NEXT(4),
    /**
     * 获取上一个窗口
     */
    PREVIOUS(5),
    /**
     * 获取拥有者窗口
     */
    owner(6),
    /**
     * 获取顶层窗口
     */
    TOP(7);

    private final Integer value;
}
