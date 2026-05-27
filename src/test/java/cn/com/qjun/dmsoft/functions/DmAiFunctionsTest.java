package cn.com.qjun.dmsoft.functions;

import cn.com.qjun.dmsoft.model.FindResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DmAiFunctionsTest {
    @Test
    void parseYoloDetectedItemSupportsDecimalConfidence() {
        FindResult.Item item = DmAiFunctions.parseYoloDetectedItem(new String[]{"monster", "0.643", "80", "25", "560", "390"});

        assertEquals("monster", item.getName());
        assertEquals(0.643D, item.getProbability(), 0.000001D);
        assertEquals(80, item.getRect().getX());
        assertEquals(25, item.getRect().getY());
        assertEquals(560, item.getRect().getWidth());
        assertEquals(390, item.getRect().getHeight());
    }
}
