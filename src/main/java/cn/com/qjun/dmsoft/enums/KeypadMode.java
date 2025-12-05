package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 键盘仿真模式
 *
 * @author RenQiang
 * @date 2024/2/13
 */
@Getter
@RequiredArgsConstructor
public enum KeypadMode implements DmEnum<String> {
    /**
     * 正常模式,平常我们用的前台键盘模式
     */
    NORMAL("normal"),
    /**
     * Windows模式,采取模拟windows消息方式 同按键的后台插件.
     */
    WINDOWS("windows"),
    /**
     * dx模式,采用模拟dx后台键盘模式。有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后激活)，否则可能会出现绑定后键盘无效的情况. 此模式等同于BindWindowEx中的keypad为以下组合
     * "dx.public.active.api|dx.public.active.message| dx.keypad.state.api|dx.keypad.api|dx.keypad.input.lock.api"
     */
    DX("dx");

    private final String value;
}
