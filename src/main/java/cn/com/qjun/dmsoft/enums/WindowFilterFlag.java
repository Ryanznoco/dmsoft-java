package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 窗口过滤条件匹配方式
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@Getter
@RequiredArgsConstructor
public enum WindowFilterFlag implements DmEnum<Integer> {
    /**
     * 标题
     */
    TITLE(0),
    /**
     * 程序名字. (比如notepad)
     */
    PROCESS_NAME(1),
    /**
     * 类名
     */
    CLASS_NAME(2),
    /**
     * 程序路径.(不包含盘符,比如\windows\system32)
     */
    PROCESS_PATH(3),
    /**
     * 父句柄.(十进制表达的串)
     */
    PARENT_HWND(4),
    /**
     * 父窗口标题
     */
    PARENT_TITLE(5),
    /**
     * 父窗口类名
     */
    PARENT_CLASS_NAME(6),
    /**
     * 顶级窗口句柄.(十进制表达的串)
     */
    TOP_HWND(7),
    /**
     * 顶级窗口标题
     */
    TOP_TITLE(8),
    /**
     * 顶级窗口类名
     */
    TOP_CLASS_NAME(9);

    private final Integer value;
}
