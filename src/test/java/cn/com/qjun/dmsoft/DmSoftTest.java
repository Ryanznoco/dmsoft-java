package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.model.FindResult;
import cn.com.qjun.dmsoft.enums.DisplayMode;
import cn.com.qjun.dmsoft.enums.FindDirection;
import cn.com.qjun.dmsoft.enums.KeypadMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author RenQiang
 * @date 2024/2/11
 */
public class DmSoftTest {
    private static DmSoft dmSoft;

    @BeforeAll
    public static void init() {
        dmSoft = new DmSoft(DmOptions.builder("D:\\Workspace\\dmsoft-java", "xx", "xx").build());
    }

    @Test
    public void testBindWindow() {
        dmSoft.opsForBackground().bindWindow(462174L, DisplayMode.DX, MouseMode.DX, KeypadMode.DX, 0);
        Point point = dmSoft.opsForColour().findMultiColor(Rect.of(475, 437, 483, 443), "d4c8b3-101010", "0|2|94886b-101010,2|0|8c846c-101010,2|2|92886e-101010,4|0|988e7c-101010,4|2|6f644a", 0.9, FindDirection.L_TO_R_AND_B_TO_T);
        System.out.println(point);
        FindResult findResult = dmSoft.opsForColour().findPic(Rect.of(879, 430, 903, 454), Collections.singletonList("攻击按钮.bmp"), "101010", 0.9, FindDirection.L_TO_R_AND_B_TO_T);
        System.out.println(findResult);
    }
}
