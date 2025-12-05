package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 鼠标仿真模式
 *
 * @author RenQiang
 * @date 2024/2/13
 */
@Getter
@RequiredArgsConstructor
public enum MouseMode implements DmEnum<String> {
    /**
     * 正常模式,平常我们用的前台鼠标模式
     */
    NORMAL("normal"),
    /**
     * Windows模式,采取模拟windows消息方式 同按键自带后台插件.
     */
    WINDOWS("windows"),
    /**
     * Windows2 模式,采取模拟windows消息方式(锁定鼠标位置) 此模式等同于BindWindowEx中的mouse为以下组合
     * "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.state.message"
     */
    WINDOWS2("windows2"),
    /**
     * Windows3模式，采取模拟windows消息方式,可以支持有多个子窗口的窗口后台.
     */
    WINDOWS3("windows3"),
    /**
     * dx模式,采用模拟dx后台鼠标模式,这种方式会锁定鼠标输入.有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后激活)，否则可能会出现绑定后鼠标无效的情况.此模式等同于BindWindowEx中的mouse为以下组合
     * "dx.public.active.api|dx.public.active.message|dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.state.api|dx.mouse.state.message|dx.mouse.api|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.cursor"
     */
    DX("dx"),
    /**
     * dx2模式,这种方式类似于dx模式,但是不会锁定外部鼠标输入.
     * 有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后手动激活)，否则可能会出现绑定后鼠标无效的情况. 此模式等同于BindWindowEx中的mouse为以下组合
     * "dx.public.active.api|dx.public.active.message|dx.mouse.position.lock.api|dx.mouse.state.api|dx.mouse.api|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api| dx.mouse.cursor"
     */
    DX2("dx2");

    private final String value;
}
