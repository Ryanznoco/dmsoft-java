package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.enums.DisplayMode;
import cn.com.qjun.dmsoft.enums.KeypadMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author RenQiang
 * @date 2024/2/11
 */
@Slf4j
public class DmSoftTest {
    private static DmSoftWrapper dmSoft;

    @BeforeAll
    public static void init() {
        dmSoft = new DmSoftWrapper();
        dmSoft.basicFunctions().reg("8106259385de4dfb430f2ce4c1375b5e83a11b381","810625938");
    }

    @Test
    public void testVer() {
        log.debug("大漠插件版本: {}", dmSoft.basicFunctions().ver());
    }

    @Test
    public void testCapture() {
        dmSoft.colourFunctions().setPicPwd("12345678");
        dmSoft.backgroundFunctions().bindWindow(9506642L, DisplayMode.DX3, MouseMode.DX, KeypadMode.DX, 0);
        dmSoft.colourFunctions().capture(Rect.of(0, 0, 800, 600), "D:\\temp\\temp.bmp");
        dmSoft.backgroundFunctions().unBindWindow();
    }

    @AfterAll
    public static void close() {
        dmSoft.close();
    }
}
