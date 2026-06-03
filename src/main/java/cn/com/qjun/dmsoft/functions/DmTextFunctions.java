package cn.com.qjun.dmsoft.functions;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.dmsoft.utils.DirectMemoryUtils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import lombok.NonNull;

/**
 * @author 81062
 * @date 2025/12/4
 */
public class DmTextFunctions extends AbstractDmFunctions {
    public DmTextFunctions(@NonNull ActiveXComponent dmSoft) {
        super(dmSoft);
    }

    /**
     * 对插件部分接口的返回值进行解析,并返回ret中的坐标个数
     *
     * @param result 部分接口的返回串
     * @return 返回ret中的坐标个数
     */
    public int getResultCount(String result) {
        return (int) callForLong("GetResultCount", FunctionArgs.of(result));
    }

    /**
     * 对插件部分接口的返回值进行解析,并根据指定的第index个坐标,返回具体的值
     *
     * @param result 部分接口的返回串
     * @param index  第几个坐标
     * @return 坐标点
     */
    public Point getResultPos(String result, int index) {
        FunctionArgs args = FunctionArgs.of(result, index, new Variant(0, true), new Variant(0, true));
        callExpect1("GetResultPos", args);
        return args.getPoint(-2, -1);
    }

    public void setDict(int index, String file) {
        callExpect1("SetDict", FunctionArgs.of(index, file));
    }

    public void setDictMem(int index, byte[] dictBytes) {
        if (dictBytes == null || dictBytes.length == 0) {
            throw new IllegalArgumentException("字库内存数据不能为空");
        }
        DirectMemoryUtils.loadToMemAndConsume(dictBytes,
                memoryInfo -> callExpect1("SetDictMem", FunctionArgs.of(index, memoryInfo.getAddress(), memoryInfo.getSize())));
    }

    public void setDictPwd(String password) {
        callExpect1("SetDictPwd", FunctionArgs.of(password == null ? "" : password));
    }

    public void useDict(int index) {
        callExpect1("UseDict", FunctionArgs.of(index));
    }

    public int getDictCount(int index) {
        return (int) callForLong("GetDictCount", FunctionArgs.of(index));
    }

    public String ocrInFile(int x1, int y1, int x2, int y2, String file, String colorFormat, double sim) {
        return callForString("OcrInFile", FunctionArgs.of(x1, y1, x2, y2, file, colorFormat, sim));
    }

    public String ocr(int x1, int y1, int x2, int y2, String colorFormat, double sim) {
        return callForString("Ocr", FunctionArgs.of(x1, y1, x2, y2, colorFormat, sim));
    }
}
