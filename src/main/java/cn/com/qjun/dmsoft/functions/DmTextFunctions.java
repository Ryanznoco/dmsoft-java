package cn.com.qjun.dmsoft.functions;

import cn.com.qjun.commons.geometry.Point;
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
}
