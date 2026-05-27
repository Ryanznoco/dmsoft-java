package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.model.FindResult;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 手动运行的大漠 YOLO 画框回放测试。
 *
 * <p>在 IDE 里直接运行 {@link #saveYoloDetectObjectsToFileFromReplayImage()}，
 * 会使用固定 BMP 作为图色输入，并调用 AiYoloDetectObjectsToFile 输出大漠画框结果。</p>
 */
class DmAiYoloToFileLauncher {
    private static final Path REPLAY_IMAGE = Paths.get(
            "D:\\DevTools\\dmsoft\\【大漠上传】大漠插件绑定测试工具(VIP专用)v81",
            "capture_file.bmp");
    private static final Path AI_MODULE = Paths.get(
            "D:\\CodexProjects\\AutoVisionCC\\dmsoft-java\\src\\main\\resources\\win32-x86\\ai.module");
    private static final Path MODEL_FILE = Paths.get(
            "\\\\HOME-PC\\datasets\\lc\\v5-7.0\\yolov5m_best.dmx");
    private static final String MODEL_PASSWORD = "1111";
    private static final String YOLO_VERSION = "v5-7.0";
    private static final Rect DETECT_REGION = Rect.of(70, 53, 667, 406);
    private static final float PROB = 0.5F;
    private static final float IOU = 0.45F;
    private static final Path OUTPUT_FILE = Paths.get(
            "D:\\DevTools\\dmsoft\\【大漠上传】大漠插件绑定测试工具(VIP专用)v81\\dm-yolo-detect-objects-to-file.bmp");

    @Test
    void saveYoloDetectObjectsToFileFromReplayImage() throws Exception {
        assertTrue(Files.isRegularFile(REPLAY_IMAGE), "固定回放截图不存在: " + REPLAY_IMAGE);
        assertTrue(Files.isRegularFile(AI_MODULE), "大漠 AI 模块不存在: " + AI_MODULE);
        assertTrue(Files.isRegularFile(MODEL_FILE), "请把 MODEL_FILE 改成实际 .dmx 模型路径: " + MODEL_FILE);
        Files.createDirectories(OUTPUT_FILE.getParent());
        Files.deleteIfExists(OUTPUT_FILE);

        DmSoftWrapper dmSoft = new DmSoftWrapper();
        try {
            dmSoft.basicFunctions().reg("8106259385de4dfb430f2ce4c1375b5e83a11b381", "810625938");
            dmSoft.basicFunctions().setDisplayInput("pic:" + REPLAY_IMAGE.toAbsolutePath());
            assertEquals(1, dmSoft.aiFunctions().loadAi(AI_MODULE.toAbsolutePath().toString()));
            dmSoft.aiFunctions().aiYoloSetVersion(YOLO_VERSION);
            dmSoft.aiFunctions().aiYoloSetModel(0, MODEL_FILE.toAbsolutePath().toString(), MODEL_PASSWORD);
            dmSoft.aiFunctions().aiYoloUseModel(0);
            FindResult beforeToFile = dmSoft.aiFunctions().aiYoloDetectObjects(DETECT_REGION, PROB, IOU);
            System.out.println("AiYoloDetectObjects before ToFile: " + beforeToFile);
            dmSoft.aiFunctions().aiYoloDetectObjectsToFile(DETECT_REGION, PROB, IOU,
                    OUTPUT_FILE.toAbsolutePath().toString(), true);
            FindResult findResult = dmSoft.aiFunctions().aiYoloDetectObjects(DETECT_REGION, PROB, IOU);
            System.out.println("AiYoloDetectObjects after ToFile: " + findResult);
        } finally {
            dmSoft.close();
        }

        assertTrue(Files.isRegularFile(OUTPUT_FILE), "大漠未生成画框结果文件: " + OUTPUT_FILE);
        assertTrue(Files.size(OUTPUT_FILE) > 0, "大漠画框结果文件为空: " + OUTPUT_FILE);
        System.out.println("大漠 YOLO 画框结果: " + OUTPUT_FILE.toAbsolutePath());
    }

    @Test
    void compareFileAndMemoryModelLoadingFromReplayImage() throws Exception {
        assertTrue(Files.isRegularFile(REPLAY_IMAGE), "Replay image does not exist: " + REPLAY_IMAGE);
        assertTrue(Files.isRegularFile(AI_MODULE), "AI module does not exist: " + AI_MODULE);
        assertTrue(Files.isRegularFile(MODEL_FILE), "Model file does not exist: " + MODEL_FILE);

        detectWith("LoadAi + AiYoloSetModel", false, false);
        detectWith("LoadAiMemory + AiYoloSetModel", true, false);
        detectWith("LoadAi + AiYoloSetModelMemory", false, true);
        detectWith("LoadAiMemory + AiYoloSetModelMemory", true, true);
        detectAfterPicCacheReset();
    }

    private void detectWith(String name, boolean moduleMemory, boolean modelMemory) throws Exception {
        DmSoftWrapper dmSoft = new DmSoftWrapper();
        try {
            dmSoft.basicFunctions().reg("8106259385de4dfb430f2ce4c1375b5e83a11b381", "810625938");
            dmSoft.basicFunctions().setDisplayInput("pic:" + REPLAY_IMAGE.toAbsolutePath());
            if (moduleMemory) {
                assertEquals(1, dmSoft.aiFunctions().loadAiMemory(Files.readAllBytes(AI_MODULE)));
            } else {
                assertEquals(1, dmSoft.aiFunctions().loadAi(AI_MODULE.toAbsolutePath().toString()));
            }
            dmSoft.aiFunctions().aiYoloSetVersion(YOLO_VERSION);
            if (modelMemory) {
                dmSoft.aiFunctions().aiYoloSetModelMemory(0, Files.readAllBytes(MODEL_FILE), MODEL_PASSWORD);
            } else {
                dmSoft.aiFunctions().aiYoloSetModel(0, MODEL_FILE.toAbsolutePath().toString(), MODEL_PASSWORD);
            }
            dmSoft.aiFunctions().aiYoloUseModel(0);
            FindResult result = dmSoft.aiFunctions().aiYoloDetectObjects(DETECT_REGION, PROB, IOU);
            System.out.println(name + ": " + result);
        } finally {
            dmSoft.close();
        }
    }

    private void detectAfterPicCacheReset() throws Exception {
        DmSoftWrapper dmSoft = new DmSoftWrapper();
        try {
            dmSoft.basicFunctions().reg("8106259385de4dfb430f2ce4c1375b5e83a11b381", "810625938");
            dmSoft.basicFunctions().setDisplayInput("pic:" + REPLAY_IMAGE.toAbsolutePath());
            dmSoft.basicFunctions().enablePicCache(false);
            dmSoft.basicFunctions().enablePicCache(true);
            dmSoft.basicFunctions().setDisplayInput("pic:" + REPLAY_IMAGE.toAbsolutePath());
            assertEquals(1, dmSoft.aiFunctions().loadAiMemory(Files.readAllBytes(AI_MODULE)));
            dmSoft.aiFunctions().aiYoloSetVersion(YOLO_VERSION);
            dmSoft.aiFunctions().aiYoloSetModelMemory(0, Files.readAllBytes(MODEL_FILE), MODEL_PASSWORD);
            dmSoft.aiFunctions().aiYoloUseModel(0);
            FindResult result = dmSoft.aiFunctions().aiYoloDetectObjects(DETECT_REGION, PROB, IOU);
            System.out.println("After EnablePicCache reset + SetDisplayInput: " + result);
        } finally {
            dmSoft.close();
        }
    }
}
