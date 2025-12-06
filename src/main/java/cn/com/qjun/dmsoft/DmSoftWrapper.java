package cn.com.qjun.dmsoft;

import cn.com.qjun.dmsoft.functions.*;
import cn.com.qjun.dmsoft.utils.RuntimeUtils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.LibraryLoader;
import com.sun.jna.Library;
import com.sun.jna.Native;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author RenQiang
 * @date 2025/11/23
 */
@Slf4j
@Accessors(fluent = true)
public class DmSoftWrapper implements AutoCloseable {
    private final ActiveXComponent component;
    private final long id;

    @Getter
    private final DmAiFunctions aiFunctions;
    @Getter
    private final DmBackgroundFunctions backgroundFunctions;
    @Getter
    private final DmBasicFunctions basicFunctions;
    @Getter
    private final DmColourFunctions colourFunctions;
    @Getter
    private final DmFileFunctions fileFunctions;
    @Getter
    private final DmInputFunctions inputFunctions;
    @Getter
    private final DmMemoryFunctions memoryFunctions;
    @Getter
    private final DmTextFunctions textFunctions;
    @Getter
    private final DmOtherFunctions otherFunctions;
    @Getter
    private final DmSystemFunctions systemFunctions;
    @Getter
    private final DmWindowFunctions windowFunctions;

    public DmSoftWrapper() {
        ComThread.InitMTA();
        this.component = new ActiveXComponent("dm.dmsoft");
        this.aiFunctions = new DmAiFunctions(component);
        this.backgroundFunctions = new DmBackgroundFunctions(component);
        this.basicFunctions = new DmBasicFunctions(component);
        this.textFunctions = new DmTextFunctions(component);
        this.colourFunctions = new DmColourFunctions(component, textFunctions);
        this.fileFunctions = new DmFileFunctions(component);
        this.inputFunctions = new DmInputFunctions(component);
        this.memoryFunctions = new DmMemoryFunctions(component);
        this.otherFunctions = new DmOtherFunctions(component);
        this.systemFunctions = new DmSystemFunctions(component);
        this.windowFunctions = new DmWindowFunctions(component);
        this.id = basicFunctions().getId();
        log.info("创建大漠对象成功: ID={}", this.id);
    }

    @Override
    public void close() {
        otherFunctions().releaseRef();
        this.component.safeRelease();
        ComThread.Release();
        log.info("关闭大漠对象成功: ID={}", this.id);
    }

    static {
        try {
            Properties versions = RuntimeUtils.readProperties("/version.properties");
            String jacobDllName = "jacob-" + versions.getProperty("jacob.version") + "-x86";
            Path jacobDll = RuntimeUtils.releaseFileToTempDir("/" + jacobDllName + ".dll", jacobDllName, ".dll");
            System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobDll.toAbsolutePath().toString());
            LibraryLoader.loadJacobLibrary();

            Path dmDll = RuntimeUtils.releaseFileToTempDir("/win32-x86/dm.dll", "dm", ".dll");
            DmReg.INSTANCE.SetDllPathA(dmDll.toAbsolutePath().toString(), 1);
        } catch (IOException e) {
            throw new RuntimeException("Library release fail.", e);
        }
    }

    private interface DmReg extends Library {
        DmReg INSTANCE = Native.load("DmReg", DmReg.class);

        /**
         * 加载大漠插件
         *
         * @param dllPath dm.dll路径
         * @param mode    0表示STA，1表示MTA
         */
        void SetDllPathA(String dllPath, int mode);
    }
}
