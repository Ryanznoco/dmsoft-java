package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 屏幕颜色获取方式
 *
 * @author RenQiang
 * @date 2024/2/13
 */
@Getter
@RequiredArgsConstructor
public enum DisplayMode implements DmEnum<String> {
    /**
     * 正常模式,平常我们用的前台截屏模式
     */
    NORMAL("normal"),
    /**
     * gdi模式,用于窗口采用GDI方式刷新时.
     * 此模式占用CPU较大. 参考SetAero  win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试。
     */
    GDI("gdi"),
    /**
     * gdi2模式,此模式兼容性较强,但是速度比gdi模式要慢许多,如果gdi模式发现后台不刷新时,可以考虑用gdi2模式.
     */
    GDI2("gdi2"),
    /**
     * dx2模式,用于窗口采用dx模式刷新,如果dx方式会出现窗口所在进程崩溃的状况,可以考虑采用这种.
     * 采用这种方式要保证窗口有一部分在屏幕外.win7 win8或者vista不需要移动也可后台.
     * 此模式占用CPU较大. 参考SetAero.   win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试。
     */
    DX2("dx2"),
    /**
     * dx3模式,同dx2模式,但是如果发现有些窗口后台不刷新时,可以考虑用dx3模式,此模式比dx2模式慢许多.
     * 此模式占用CPU较大. 参考SetAero. win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试。
     */
    DX3("dx3"),
    /**
     * dx模式,等同于BindWindowEx中，display设置的"dx.graphic.2d|dx.graphic.3d",具体参考BindWindowEx
     */
    DX("dx");

    private final String value;
}
