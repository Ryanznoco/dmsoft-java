package cn.com.qjun.dmsoft;

import cn.com.qjun.dmsoft.functions.*;

/**
 * @author 81062
 * @date 2025/11/24
 */
public class DmSoftThreadSafe implements AutoCloseable {
    private final ThreadLocal<DmSoftWrapper> localDmSoft;
    private final DmSoftInitFunction initFunction;

    public DmSoftThreadSafe(DmSoftInitFunction initFunction) {
        this.localDmSoft = new ThreadLocal<>();
        this.initFunction = initFunction;
    }

    public DmAiFunctions aiFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.aiFunctions();
    }

    public DmBackgroundFunctions backgroundFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.backgroundFunctions();
    }

    public DmBasicFunctions basicFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.basicFunctions();
    }

    public DmTextFunctions textFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.textFunctions();
    }

    public DmColourFunctions colourFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.colourFunctions();
    }

    public DmFileFunctions fileFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.fileFunctions();
    }

    public DmInputFunctions inputFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.inputFunctions();
    }

    public DmMemoryFunctions memoryFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.memoryFunctions();
    }

    public DmOtherFunctions otherFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.otherFunctions();
    }

    public DmSystemFunctions systemFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.systemFunctions();
    }

    public DmWindowFunctions windowFunctions() {
        DmSoftWrapper dmSoft = getInstance();
        return dmSoft.windowFunctions();
    }

    private DmSoftWrapper getInstance() {
        DmSoftWrapper dmSoft = localDmSoft.get();
        if (dmSoft == null) {
            dmSoft = initDmSoft();
            localDmSoft.set(dmSoft);
        }
        return dmSoft;
    }

    private DmSoftWrapper initDmSoft() {
        DmSoftWrapper dmSoft = new DmSoftWrapper();
        if (initFunction != null) {
            initFunction.accept(dmSoft);
        }
        return dmSoft;
    }

    @Override
    public void close() {
        DmSoftWrapper dmSoft = localDmSoft.get();
        if (dmSoft != null) {
            dmSoft.close();
        }
    }
}
