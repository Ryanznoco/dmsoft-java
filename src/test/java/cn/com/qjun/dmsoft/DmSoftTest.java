package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.enums.DisplayMode;
import cn.com.qjun.dmsoft.enums.FindDirection;
import cn.com.qjun.dmsoft.enums.KeypadMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import cn.com.qjun.dmsoft.model.FindResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

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
        dmSoft.basicFunctions().reg("8106259385de4dfb430f2ce4c1375b5e83a11b381", "810625938");
    }

    @Test
    public void testVer() {
        log.debug("大漠插件版本: {}", dmSoft.basicFunctions().ver());
    }

    @Test
    public void testFindPic() {
        dmSoft.basicFunctions().setDisplayInput("pic:" + "D:\\DevTools\\dmsoft\\【大漠上传】大漠插件绑定测试工具(VIP专用)v81\\capture_file.bmp");
        FindResult picEx = dmSoft.colourFunctions()
                .findPicSimEx(
                        Rect.of(0, 0, 800, 600),
                        Collections.singletonList("C:\\Users\\81062\\Downloads\\鼠标_箭.bmp"),
                        "050505",
                        90,
                        FindDirection.L_TO_R_AND_T_TO_B
                );
        System.out.println(picEx);
    }

    @Test
    public void testCapture() {
//        dmSoft.colourFunctions().setPicPwd("12345678");
        dmSoft.backgroundFunctions().bindWindow(1380322L, DisplayMode.DX3, MouseMode.DX, KeypadMode.DX, 0);
        FindResult picSim = dmSoft.colourFunctions().findPicSimEx(Rect.of(0, 0, 800, 600), Collections.singletonList("C:\\Users\\81062\\Downloads\\鼠标_箭.bmp"), "202020", 80, FindDirection.L_TO_R_AND_T_TO_B);
        System.out.println(picSim);
//        dmSoft.colourFunctions().capture(Rect.of(0, 0, 800, 600), "D:\\temp\\temp.bmp");
        dmSoft.backgroundFunctions().unBindWindow();
    }

    @AfterAll
    public static void close() {
        dmSoft.close();
    }
}
