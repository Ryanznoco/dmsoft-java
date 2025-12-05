package cn.com.qjun.dmsoft;

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
    }

    @Test
    public void testVer() {
        log.debug("大漠插件版本: {}", dmSoft.basicFunctions().ver());
    }
    
    @AfterAll
    public static void close() {
        dmSoft.close();
    }
}