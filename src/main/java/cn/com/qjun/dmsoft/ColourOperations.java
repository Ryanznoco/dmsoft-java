package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.commons.geometry.Size;
import cn.com.qjun.dmsoft.model.*;
import cn.com.qjun.dmsoft.enums.FindDirection;
import com.jacob.com.Variant;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图色相关操作
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@RequiredArgsConstructor
public class ColourOperations {
    private final DmSoft dmSoft;

    /**
     * 对指定的数据地址和长度，组合成新的参数. FindPicMem FindPicMemE 以及FindPicMemEx专用
     *
     * @param picInfo 老的地址描述串
     * @param addr    数据地址
     * @param size    数据长度
     * @return 新的地址描述串
     */
    @Deprecated
    public String appendPicAddr(String picInfo, long addr, long size) {
        return dmSoft.callForString("AppendPicAddr", picInfo, addr, size);
    }

    /**
     * 把BGR(按键格式)的颜色格式转换为RGB
     *
     * @param bgrColor bgr格式的颜色字符串
     * @return RGB格式的字符串
     */
    public String bgr2Rgb(String bgrColor) {
        return dmSoft.callForString("BGR2RGB", bgrColor);
    }

    /**
     * 抓取指定区域(x1, y1, x2, y2)的图像,保存为file(24位位图)
     *
     * @param rect 指定的区域
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录
     *             当然这里也可以指定全路径名.
     */
    public void capture(Rect rect, String file) {
        dmSoft.callAndCheckResultEq1("Capture", rect.x1(), rect.y1(), rect.x2(), rect.y2(), file);
    }

    /**
     * 抓取指定区域(x1, y1, x2, y2)的动画，保存为gif格式
     *
     * @param rect  截取的区域
     * @param file  保存的文件名,保存的地方一般为SetPath中设置的目录
     *              当然这里也可以指定全路径名.
     * @param delay 动画间隔，单位毫秒。如果为0，表示只截取静态图片
     * @param time  总共截取多久的动画，单位毫秒。
     */
    public void captureGif(Rect rect, String file, long delay, long time) {
        dmSoft.callAndCheckResultEq1("CaptureGif", rect.x1(), rect.y1(), rect.x2(), rect.y2(), file, delay, time);
    }

    /**
     * 抓取指定区域(x1, y1, x2, y2)的图像,保存为file(JPG压缩格式)
     *
     * @param rect    截取的区域
     * @param file    保存的文件名,保存的地方一般为SetPath中设置的目录
     *                当然这里也可以指定全路径名.
     * @param quality jpg压缩比率(1-100) 越大图片质量越好
     */
    public void captureJpg(Rect rect, String file, int quality) {
        dmSoft.callAndCheckResultEq1("CaptureJpg", rect.x1(), rect.y1(), rect.x2(), rect.y2(), file, quality);
    }

    /**
     * 同Capture函数，只是保存的格式为PNG.
     *
     * @param rect 截取的区域
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录
     *             当然这里也可以指定全路径名.
     */
    public void capturePng(Rect rect, String file) {
        dmSoft.callAndCheckResultEq1("CapturePng", rect.x1(), rect.y1(), rect.x2(), rect.y2(), file);
    }

    /**
     * 抓取上次操作的图色区域，保存为file(24位位图)
     * 注意，要开启此函数，必须先调用EnableDisplayDebug
     * 任何图色或者文字识别函数，都可以通过这个来截取. 具体可以查看常见问题中"本机文字识别正常,别的机器为何不正常"这一节.
     *
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录
     *             当然这里也可以指定全路径名.
     */
    public void capturePre(String file) {
        dmSoft.callAndCheckResultEq1("CapturePre", file);
    }

    /**
     * 比较指定坐标点(x,y)的颜色
     *
     * @param point 要比较的坐标点
     * @param color 颜色字符串,可以支持偏色,多色,例如 "ffffff-202020|000000-000000" 这个表示白色偏色为202020,和黑色偏色为000000.颜色最多支持10种颜色组合.
     *              注意，这里只支持RGB颜色.
     * @param sim   相似度(0.1-1.0)
     * @return 颜色是否匹配
     */
    public boolean cmpColor(Point point, String color, double sim) {
        long result = dmSoft.callForLong("CmpColor", point.x(), point.y(), color, sim);
        return result == 1L;
    }

    /**
     * 开启图色调试模式，此模式会稍许降低图色和文字识别的速度.默认不开启.
     *
     * @param enable 是否开启调试
     */
    public void enableDisplayDebug(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableDisplayDebug", enable ? 1 : 0);
    }

    /**
     * 当执行FindPicXXX系列接口时,是否在条件满足下(查找的图片大于等于4,这个值可以根据SetFindPicMultithreadCount来修改),开启多线程查找。 默认打开.
     * 注 : 如果担心开启多线程会引发占用大量CPU资源,那么可以考虑关闭此功能. 在以往版本,这个功能默认都是打开的.
     * 这个只是多线程查找的一个开关,另一个开关是SetFindPicMultithreadCount
     *
     * @param enable 是否开启
     */
    public void enableFindPicMultiThread(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableFindPicMultithread", enable ? 1 : 0);
    }

    /**
     * 允许调用GetColor GetColorBGR GetColorHSV 以及 CmpColor时，以截图的方式来获取颜色。 默认关闭.
     * 注 : 某些窗口上，可能GetColor会获取不到颜色，可以尝试此接口.
     *
     * @param enable 是否开启
     */
    public void enableGetColorByCapture(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableGetColorByCapture", enable ? 1 : 0);
    }

    /**
     * 查找指定区域内的颜色,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * 注: 反色模式是指匹配任意一个指定颜色之外的颜色. 比如"@123456|333333". 在匹配时,会匹配除了123456或者333333之外的颜色.
     *
     * @param rect  查找的区域
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020". 也可以支持反色模式. 前面加@即可. 比如"@123456-000000|aabbcc-202020". 具体可以看下放注释.
     *              注意，这里只支持RGB颜色.
     * @param sim   相似度,取值范围0.1-1.0
     * @param dir   查找方向
     * @return 找到的坐标，如果没有找到返回null
     */
    public Point findColor(Rect rect, String color, double sim, FindDirection dir) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(color), new Variant(sim), new Variant(dir.getValue()), new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindColor", (Object[]) variants);
        if (result == 0L) {
            return null;
        }
        return Point.of(variants[7].getInt(), variants[8].getInt());
    }

    /**
     * 查找指定区域内的颜色块,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * 注: 反色模式是指匹配任意一个指定颜色之外的颜色. 比如"@123456|333333". 在匹配时,会匹配除了123456或者333333之外的颜色.
     *
     * @param rect  查找的区域
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".也可以支持反色模式. 前面加@即可. 比如"@123456-000000|aabbcc-202020". 具体可以看下放注释.
     *              注意，这里只支持RGB颜色.
     * @param sim   相似度,取值范围0.1-1.0
     * @param count 在宽度为width,高度为height的颜色块中，符合color颜色的最小数量.(注意,这个颜色数量可以在综合工具的二值化区域中看到)
     * @param size  颜色块的尺寸
     * @return 找到的坐标，如果没有找到返回null
     */
    public Point findColorBlock(Rect rect, String color, double sim, int count, Size size) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(color), new Variant(sim), new Variant(count), new Variant(size.width()), new Variant(size.height()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindColorBlock", (Object[]) variants);
        if (result == 0L) {
            return null;
        }
        return Point.of(variants[9].getInt(), variants[10].getInt());
    }

    /**
     * 查找指定区域内的所有颜色块,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * 注: 反色模式是指匹配任意一个指定颜色之外的颜色. 比如"@123456|333333". 在匹配时,会匹配除了123456或者333333之外的颜色.
     *
     * @param rect  查找的区域
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".也可以支持反色模式. 前面加@即可. 比如"@123456-000000|aabbcc-202020". 具体可以看下放注释.
     *              注意，这里只支持RGB颜色.
     * @param sim   相似度,取值范围0.1-1.0
     * @param count 在宽度为width,高度为height的颜色块中，符合color颜色的最小数量.(注意,这个颜色数量可以在综合工具的二值化区域中看到)
     * @param size  颜色块的尺寸
     * @return 返回所有颜色块信息的坐标值, 然后通过GetResultCount等接口来解析 (由于内存限制,返回的颜色数量最多为1800个左右)
     */
    public String findColorBlockEx(Rect rect, String color, double sim, int count, Size size) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(color), new Variant(sim), new Variant(count), new Variant(size.width()), new Variant(size.height()),
                new Variant(0, true), new Variant(0, true)};
        return dmSoft.callForString("FindColorBlockEx", (Object[]) variants);
    }

    /**
     * 查找指定区域内的所有颜色,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * 注: 反色模式是指匹配任意一个指定颜色之外的颜色. 比如"@123456|333333". 在匹配时,会匹配除了123456或者333333之外的颜色.
     *
     * @param rect  查找的区域
     * @param color 颜色 格式为"RRGGBB-DRDGDB" 比如"aabbcc-000000|123456-202020".也可以支持反色模式. 前面加@即可. 比如"@123456-000000|aabbcc-202020". 具体可以看下放注释.
     *              注意，这里只支持RGB颜色.
     * @param sim   相似度,取值范围0.1-1.0
     * @param dir   查找方向
     * @return 返回所有颜色信息的坐标值, 然后通过GetResultCount等接口来解析 (由于内存限制,返回的颜色数量最多为1800个左右)
     */
    public String findColorEx(Rect rect, String color, double sim, FindDirection dir) {
        return dmSoft.callForString("FindColorEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(), color, sim, dir.getValue());
    }

    /**
     * 查找指定区域内的所有颜色.
     *
     * @param rect  查找区域
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".也可以支持反色模式. 前面加@即可. 比如"@123456-000000|aabbcc-202020". 具体可以看下放注释.
     *              注意，这里只支持RGB颜色.
     * @param sim   相似度,取值范围0.1-1.0
     * @return 找到的坐标，如果没有找到或部分颜色没有找到，返回null
     */
    public Point findMulColor(Rect rect, String color, double sim) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(color), new Variant(sim), new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindMulColor", (Object[]) variants);
        if (result == 0L) {
            return null;
        }
        return Point.of(variants[6].getInt(), variants[7].getInt());
    }

    /**
     * 根据指定的多点查找颜色坐标
     *
     * @param rect        查找区域
     * @param firstColor  颜色格式为"RRGGBB-DRDGDB|RRGGBB-DRDGDB|…………",比如"123456-000000"
     *                    这里的含义和按键自带Color插件的意义相同，只不过我的可以支持偏色和多种颜色组合
     *                    所有的偏移色坐标都相对于此颜色.注意，这里只支持RGB颜色.
     * @param offsetColor 偏移颜色可以支持任意多个点 格式和按键自带的Color插件意义相同, 只不过我的可以支持偏色和多种颜色组合
     *                    格式为"x1|y1|RRGGBB-DRDGDB|RRGGBB-DRDGDB……,……xn|yn|RRGGBB-DRDGDB|RRGGBB-DRDGDB……"
     *                    比如"1|3|aabbcc|aaffaa-101010,-5|-3|123456-000000|454545-303030|565656"等任意组合都可以，支持偏色
     *                    还可以支持反色模式，比如"1|3|-aabbcc|-334455-101010,-5|-3|-123456-000000|-353535|454545-101010","-"表示除了指定颜色之外的颜色.
     * @param sim         相似度,取值范围0.1-1.0
     * @param dir         查找方向
     * @return 找到的坐标(坐标为first_color所在坐标)，没有找到返回null
     */
    public Point findMultiColor(Rect rect, String firstColor, String offsetColor, double sim, FindDirection dir) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(firstColor), new Variant(offsetColor), new Variant(sim), new Variant(dir.getValue()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindMultiColor", (Object[]) variants);
        if (result == 0L) {
            return null;
        }
        return Point.of(variants[8].getInt(), variants[9].getInt());
    }

    /**
     * 根据指定的多点查找所有颜色坐标
     *
     * @param rect        查找区域
     * @param firstColor  颜色 格式为"RRGGBB-DRDGDB|RRGGBB-DRDGDB|…………",比如"123456-000000"
     *                    这里的含义和按键自带Color插件的意义相同，只不过我的可以支持偏色和多种颜色组合
     *                    所有的偏移色坐标都相对于此颜色.注意，这里只支持RGB颜色.
     * @param offsetColor 偏移颜色 可以支持任意多个点 格式和按键自带的Color插件意义相同, 只不过我的可以支持偏色和多种颜色组合
     *                    格式为"x1|y1|RRGGBB-DRDGDB|RRGGBB-DRDGDB……,……xn|yn|RRGGBB-DRDGDB|RRGGBB-DRDGDB……"
     *                    比如"1|3|aabbcc|aaffaa-101010,-5|-3|123456-000000|454545-303030|565656"等任意组合都可以，支持偏色
     *                    还可以支持反色模式，比如"1|3|-aabbcc|-334455-101010,-5|-3|-123456-000000|-353535|454545-101010","-"表示除了指定颜色之外的颜色.
     * @param sim         似度,取值范围0.1-1.0
     * @param dir         查找方向
     * @return 返回所有颜色信息的坐标值, 然后通过GetResultCount等接口来解析(由于内存限制, 返回的坐标数量最多为1800个左右)，坐标是first_color所在的坐标
     */
    public String findMultiColorEx(Rect rect, String firstColor, String offsetColor, double sim, FindDirection dir) {
        return dmSoft.callForString("FindMultiColorEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                firstColor, offsetColor, sim, dir.getValue());
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,只返回第一个找到的X Y坐标.
     *
     * @param rect       查找区域
     * @param picNames   图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示). 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        相似度,取值范围0.1-1.0
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)和对应坐标(图片左上角坐标)，没有找到返回null
     */
    public FindResult findPic(Rect rect, List<String> picNames, String deltaColor, double sim, FindDirection dir) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(String.join("|", picNames)), new Variant(deltaColor), new Variant(sim), new Variant(dir.getValue()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindPic", (Object[]) variants);
        if (result == -1) {
            return null;
        }
        return new FindResult((int) result, Point.of(variants[8].getInt(), variants[9].getInt()), null);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,并且返回所有找到的图像的坐标.
     *
     * @param rect       查找区域
     * @param picNames   图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 颜色色偏 比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示) . 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        相似度,取值范围0.1-1.0
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)和对应坐标(图片左上角坐标)(由于内存限制, 返回的图片数量最多为1500个左右)
     */
    public List<FindResult> findPicEx(Rect rect, List<String> picNames, String deltaColor, double sim, FindDirection dir) {
        String result = dmSoft.callForString("FindPicEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                String.join("|", picNames), deltaColor, sim, dir.getValue());
        return convertFindResultList(result, false);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,只返回第一个找到的X Y坐标. 这个函数要求图片是数据地址.
     * 注 : 内存中的图片格式必须是24位色，并且不能加密.
     *
     * @param rect       查找区域
     * @param picInfos   图片数据地址集合. 格式为"地址1,长度1|地址2,长度2.....|地址n,长度n". 可以用AppendPicAddr来组合.
     *                   地址表示24位位图资源在内存中的首地址，用十进制的数值表示
     *                   长度表示位图资源在内存中的长度，用十进制数值表示.
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示) . 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        相似度,取值范围0.1-1.0
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)和对应坐标(图片左上角坐标)，没有找到返回null
     */
    public FindResult findPicMem(Rect rect, List<MemoryInfo> picInfos, String deltaColor, double sim, FindDirection dir) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(convertMemoryInfoList(picInfos)), new Variant(deltaColor), new Variant(sim), new Variant(dir.getValue()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindPicMem", (Object[]) variants);
        if (result == -1) {
            return null;
        }
        return new FindResult((int) result, Point.of(variants[8].getInt(), variants[9].getInt()), null);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,并且返回所有找到的图像的坐标. 这个函数要求图片是数据地址.
     * <p>
     * 注 : 内存中的图片格式必须是24位色，并且不能加密
     *
     * @param rect       查找区域
     * @param picInfos   图片数据地址集合. 格式为"地址1,长度1|地址2,长度2.....|地址n,长度n". 可以用AppendPicAddr来组合.
     *                   地址表示24位位图资源在内存中的首地址，用十进制的数值表示
     *                   长度表示位图资源在内存中的长度，用十进制数值表示.
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示) . 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        相似度,取值范围0.1-1.0
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)和对应坐标(图片左上角坐标)(由于内存限制, 返回的图片数量最多为1500个左右)
     */
    public List<FindResult> findPicMemEx(Rect rect, List<MemoryInfo> picInfos, String deltaColor, double sim, FindDirection dir) {
        String result = dmSoft.callForString("FindPicMemEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                convertMemoryInfoList(picInfos), deltaColor, sim, dir.getValue());
        return convertFindResultList(result, false);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,只返回第一个匹配的X Y坐标.
     * <p>
     * 注:此接口和FindPic类似. 只不过FindPicSim是以颜色百分比来进行匹配. 如果待查找区域内有杂色,只要颜色百分比达到要求,也一样可以匹配.
     * 这个接口是FindPic的进阶版本. 当sim为100时,那么FindPicSim就退化为FindPic
     * 此接口速度很慢,因为需要搜索任何一种可能. 所以尽可能把搜索范围要小一些. 以免耗时太长.
     *
     * @param rect       查找区域
     * @param picNames   图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示). 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        最小百分比相似率. 表示匹配的颜色占总颜色数的百分比. 其中透明色也算作匹配色. 取值为0到100. 100表示必须完全匹配. 0表示任意颜色都匹配. 只有大于sim的相似率的才会被匹配
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)和对应坐标(图片左上角坐标)，没有找到返回null
     */
    public FindResult findPicSim(Rect rect, List<String> picNames, String deltaColor, int sim, FindDirection dir) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(String.join("|", picNames)), new Variant(deltaColor), new Variant(sim), new Variant(dir.getValue()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindPicSim", (Object[]) variants);
        if (result == -1) {
            return null;
        }
        return new FindResult((int) result, Point.of(variants[8].getInt(), variants[9].getInt()), null);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,并且返回所有找到的图像的坐标.
     * <p>
     * 注:此接口和FindPicEx类似. 只不过FindPicSimEx是以颜色百分比来进行匹配. 如果待查找区域内有杂色,只要颜色百分比达到要求,也一样可以匹配.
     * 这个接口是FindPicEx的进阶版本. 当sim为100时,那么FindPicSimEx就退化为FindPicEx
     * 此接口速度很慢,因为需要搜索任何一种可能. 所以尽可能把搜索范围要小一些. 以免耗时太长.
     *
     * @param rect       查找区域
     * @param picNames   图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示) . 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        最小百分比相似率. 表示匹配的颜色占总颜色数的百分比. 其中透明色也算作匹配色. 取值为0到100. 100表示必须完全匹配. 0表示任意颜色都匹配. 只有大于sim的相似率的才会被匹配
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)、匹配百分比和对应坐标(图片左上角坐标)(由于内存限制, 返回的图片数量最多为1500个左右)
     */
    public List<FindResult> findPicSimEx(Rect rect, List<String> picNames, String deltaColor, int sim, FindDirection dir) {
        String result = dmSoft.callForString("FindPicSimEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                String.join("|", picNames), deltaColor, sim, dir.getValue());
        return convertFindResultList(result, true);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片, 只返回第一个匹配的X Y坐标. 这个函数要求图片是数据地址.
     * <p>
     * 注 : 内存中的图片格式必须是24位色，并且不能加密.
     * 此接口和FindPicMem类似. 只不过FindPicSimMem是以颜色百分比来进行匹配. 如果待查找区域内有杂色,只要颜色百分比达到要求,也一样可以匹配.
     * 这个接口是FindPicMem的进阶版本. 当sim为100时,那么FindPicSimMem就退化为FindPicMem
     * 此接口速度很慢,因为需要搜索任何一种可能. 所以尽可能把搜索范围要小一些. 以免耗时太长.
     *
     * @param rect       查找区域
     * @param picInfos   图片数据地址集合. 格式为"地址1,长度1|地址2,长度2.....|地址n,长度n". 可以用AppendPicAddr来组合.
     *                   地址表示24位位图资源在内存中的首地址，用十进制的数值表示
     *                   长度表示位图资源在内存中的长度，用十进制数值表示.
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示) . 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        最小百分比相似率. 表示匹配的颜色占总颜色数的百分比. 其中透明色也算作匹配色. 取值为0到100. 100表示必须完全匹配. 0表示任意颜色都匹配. 只有大于sim的相似率的才会被匹配
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)和对应坐标(图片左上角坐标)，没有找到返回null
     */
    public FindResult findPicSimMem(Rect rect, List<MemoryInfo> picInfos, String deltaColor, int sim, FindDirection dir) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(convertMemoryInfoList(picInfos)), new Variant(deltaColor), new Variant(sim), new Variant(dir.getValue()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindPicSimMem", (Object[]) variants);
        if (result == -1) {
            return null;
        }
        return new FindResult((int) result, Point.of(variants[8].getInt(), variants[9].getInt()), null);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,并且返回所有找到的图像的坐标. 这个函数要求图片是数据地址.
     * <p>
     * 注 : 内存中的图片格式必须是24位色，并且不能加密.
     * 此接口和FindPicMemEx类似. 只不过FindPicSimMemEx是以颜色百分比来进行匹配. 如果待查找区域内有杂色,只要颜色百分比达到要求,也一样可以匹配.
     * 这个接口是FindPicMemEx的进阶版本. 当sim为100时,那么FindPicSimMemEx就退化为FindPicMemEx
     * 此接口速度很慢,因为需要搜索任何一种可能. 所以尽可能把搜索范围要小一些. 以免耗时太长.
     *
     * @param rect       查找区域
     * @param picInfos   图片数据地址集合. 格式为"地址1,长度1|地址2,长度2.....|地址n,长度n". 可以用AppendPicAddr来组合.
     *                   地址表示24位位图资源在内存中的首地址，用十进制的数值表示
     *                   长度表示位图资源在内存中的长度，用十进制数值表示.
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示) . 如果这里的色偏是2位，表示使用灰度找图. 比如"20"
     * @param sim        最小百分比相似率. 表示匹配的颜色占总颜色数的百分比. 其中透明色也算作匹配色. 取值为0到100. 100表示必须完全匹配. 0表示任意颜色都匹配. 只有大于sim的相似率的才会被匹配
     * @param dir        查找方向
     * @return 找到的图片序号(从0开始索引)、匹配百分比和对应坐标(图片左上角坐标)(由于内存限制, 返回的图片数量最多为1500个左右)
     */
    public List<FindResult> findPicSimMemEx(Rect rect, List<MemoryInfo> picInfos, String deltaColor, int sim, FindDirection dir) {
        String result = dmSoft.callForString("FindPicSimMemEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                convertMemoryInfoList(picInfos), deltaColor, sim, dir.getValue());
        return convertFindResultList(result, true);
    }

    /**
     * 查找指定的形状. 形状的描述同按键的抓抓. 具体可以参考按键的抓抓.
     * 和按键的语法不同，需要用大漠综合工具的颜色转换.
     *
     * @param rect        查找区域
     * @param offsetColor 坐标偏移描述 可以支持任意多个点 格式和按键自带的Color插件意义相同
     *                    格式为"x1|y1|e1,……xn|yn|en"
     *                    比如"1|3|1,-5|-3|0"等任意组合都可以
     * @param sim         相似度,取值范围0.1-1.0
     * @param dir         查找方向
     * @return 找到的形状所在坐标，没有找到返回null
     */
    public Point findShape(Rect rect, String offsetColor, double sim, FindDirection dir) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(offsetColor), new Variant(sim), new Variant(dir.getValue()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindPicSimMem", (Object[]) variants);
        if (result == 0) {
            return null;
        }
        return Point.of(variants[7].getInt(), variants[8].getInt());
    }

    public List<Point> findShapeEx(Rect rect, String offsetColor, double sim, FindDirection dir) {
        // TODO
        return Collections.emptyList();
    }

    /**
     * 释放指定的图片,此函数不必要调用,除非你想节省内存.
     *
     * @param picNames 文件名比如"1.bmp|2.bmp|3.bmp" 等,可以使用通配符,比如
     *                 "*.bmp" 这个对应了所有的bmp文件
     *                 "a?c*.bmp" 这个代表了所有第一个字母是a 第三个字母是c 第二个字母任意的所有bmp文件
     *                 "abc???.bmp|1.bmp|aa??.bmp" 可以这样任意组合.
     */
    public void freePic(List<String> picNames) {
        dmSoft.callAndCheckResultEq1("FreePic", String.join("|", picNames));
    }

    /**
     * 获取范围(x1,y1,x2,y2)颜色的均值,返回格式"H.S.V"
     *
     * @param rect 指定的区域
     * @return 颜色字符串
     */
    public String getAveHsv(Rect rect) {
        return dmSoft.callForString("GetAveHSV", rect.x1(), rect.y1(), rect.x2(), rect.y2());
    }

    /**
     * 获取范围(x1,y1,x2,y2)颜色的均值,返回格式"RRGGBB"
     *
     * @param rect 指定的区域
     * @return 颜色字符串
     */
    public String getAveRgb(Rect rect) {
        return dmSoft.callForString("GetAveRGB", rect.x1(), rect.y1(), rect.x2(), rect.y2());
    }

    /**
     * 获取(x,y)的颜色,颜色返回格式"RRGGBB",注意,和按键的颜色格式相反
     *
     * @param point 要获取的坐标
     * @return 颜色字符串(注意这里都是小写字符 ， 和工具相匹配)
     */
    public String getColor(Point point) {
        return dmSoft.callForString("GetColor", point.x(), point.y());
    }

    /**
     * 获取(x,y)的HSV颜色,颜色返回格式"H.S.V"
     *
     * @param point 指定的坐標
     * @return 颜色字符串
     */
    public String getColorHsv(Point point) {
        return dmSoft.callForString("GetColorHSV", point.x(), point.y());
    }

    /**
     * 获取指定区域的颜色数量,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     *
     * @param rect  要获取的区域
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".也可以支持反色模式. 前面加@即可. 比如"@123456-000000|aabbcc-202020". 具体可以看下放注释.注意，这里只支持RGB颜色.
     * @param sim   相似度,取值范围0.1-1.0
     * @return 颜色数量
     */
    public int getColorNum(Rect rect, String color, double sim) {
        return (int) dmSoft.callForLong("GetColorNum", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                color, sim);
    }

    /**
     * 获取指定图片的尺寸，如果指定的图片已经被加入缓存，则从缓存中获取信息.
     * 此接口也会把此图片加入缓存. （当图色缓存机制打开时,具体参考EnablePicCache）
     *
     * @param picName 文件名 比如"1.bmp"
     * @return 图片尺寸
     */
    public Size getPicSize(String picName) {
        String result = dmSoft.callForString("GetPicSize", picName);
        String[] parts = result.split(",");
        return Size.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    /**
     * 获取指定区域的图像,用二进制数据的方式返回,（不适合按键使用）方便二次开发.
     *
     * @param rect 区域
     * @return 返回的是指定区域的二进制颜色数据地址, 每个颜色是4个字节, 表示方式为(00RRGGBB)
     */
    public long getScreenData(Rect rect) {
        return dmSoft.callForLong("GetScreenData", rect.x1(), rect.y1(), rect.x2(), rect.y2());
    }

    /**
     * 获取指定区域的图像,用24位位图的数据格式返回,方便二次开发.（或者可以配合SetDisplayInput的mem模式）
     * <p>
     * 需要注意的是,调用此接口获取的数据指针保存在当前对象中,到下次调用此接口时,内部就会释放.
     * 哪怕是转成字节集,这个地址也还是在此字节集中使用. 如果您要此地址一直有效，那么您需要自己拷贝字节集到自己的字节集中.
     *
     * @param rect 要获取的区域
     * @return 内存信息
     */
    public MemoryInfo getScreenDataBmp(Rect rect) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindPicSimMem", (Object[]) variants);
        if (result == 0) {
            return null;
        }
        return new MemoryInfo(variants[4].getInt(), variants[5].getInt());
    }

    /**
     * 转换图片格式为24位BMP格式.
     *
     * @param picName 要转换的图片名
     * @param bmpName 要保存的BMP图片名
     */
    public void imageToBmp(String picName, String bmpName) {
        dmSoft.callAndCheckResultEq1("ImageToBmp", picName, bmpName);
    }

    /**
     * 判断指定的区域，在指定的时间内(秒),图像数据是否一直不变.(卡屏). (或者绑定的窗口不存在也返回1)
     * <p>
     * 注:此函数的原理是不停的截取指定区域的图像，然后比较，如果改变就立刻返回0,否则等待直到指定的时间到达.
     *
     * @param rect    判断的区域
     * @param seconds 需要等待的时间,单位是秒
     * @return false-没有卡屏，图像数据在变化. true-卡屏. 图像数据在指定的时间内一直没有变化. 或者绑定的窗口不见了.
     */
    public boolean isDisplayDead(Rect rect, long seconds) {
        long result = dmSoft.callForLong("IsDisplayDead", rect.x1(), rect.y1(), rect.x2(), rect.y2(), seconds);
        return result == 1;
    }

    /**
     * 预先加载指定的图片,这样在操作任何和图片相关的函数时,将省去了加载图片的时间。调用此函数后,没必要一定要调用FreePic,插件自己会自动释放.
     * 另外,此函数不是必须调用的,所有和图形相关的函数只要调用过一次，图片会自动加入缓存.
     * 如果想对一个已经加入缓存的图片进行修改，那么必须先用FreePic释放此图片在缓存中占用
     * 的内存，然后重新调用图片相关接口，就可以重新加载此图片. （当图色缓存机制打开时,具体参考EnablePicCache）
     * <p>
     * 注: 如果在LoadPic后(图片名为相对路径时)，又设置SetPath为别的目录，会导致加入缓存的图片失效，等于没加载.
     *
     * @param picNames 文件名比如"1.bmp|2.bmp|3.bmp" 等,可以使用通配符,比如
     *                 "*.bmp" 这个对应了所有的bmp文件
     *                 "a?c*.bmp" 这个代表了所有第一个字母是a 第三个字母是c 第二个字母任意的所有bmp文件
     *                 "abc???.bmp|1.bmp|aa??.bmp" 可以这样任意组合.
     */
    public void loadPic(List<String> picNames) {
        dmSoft.callAndCheckResultEq1("LoadPic", String.join("|", picNames));
    }

    /**
     * 先加载指定的图片,这样在操作任何和图片相关的函数时,将省去了加载图片的时间。调用此函数后,没必要一定要调用FreePic,插件自己会自动释放.
     * 另外,此函数不是必须调用的,所有和图形相关的函数只要调用过一次，图片会自动加入缓存.
     * 如果想对一个已经加入缓存的图片进行修改，那么必须先用FreePic释放此图片在缓存中占用
     * 的内存，然后重新调用图片相关接口，就可以重新加载此图片. （当图色缓存机制打开时,具体参考EnablePicCache）
     * 此函数同LoadPic，只不过LoadPic是从文件中加载图片,而LoadPicByte从给定的内存中加载.
     * <p>
     * 注: 如果在LoadPicByte后(图片名为相对路径时)，又设置SetPath为别的目录，会导致加入缓存的图片失效，等于没加载.
     *
     * @param memoryInfo 图片内存信息
     * @param picName    文件名,指定这个地址对应的图片名. 用于找图时使用.
     */
    public void loadPicByte(MemoryInfo memoryInfo, String picName) {
        dmSoft.callAndCheckResultEq1("LoadPicByte", memoryInfo.getAddress(), memoryInfo.getSize(), picName);
    }

    /**
     * 根据通配符获取文件集合. 方便用于FindPic和FindPicEx
     *
     * @param picNames 文件名比如"1.bmp|2.bmp|3.bmp" 等,可以使用通配符,比如
     *                 "*.bmp" 这个对应了所有的bmp文件
     *                 "a?c*.bmp" 这个代表了所有第一个字母是a 第三个字母是c 第二个字母任意的所有bmp文件
     *                 "abc???.bmp|1.bmp|aa??.bmp" 可以这样任意组合.
     * @return 匹配到的文件名列表
     */
    public List<String> matchPicName(List<String> picNames) {
        String result = dmSoft.callForString("MatchPicName", String.join("|", picNames));
        return Arrays.stream(result.split("\\|"))
                .collect(Collectors.toList());
    }

    /**
     * 设置图色,以及文字识别时,需要排除的区域.(支持所有图色接口,以及文字相关接口,但对单点取色,或者单点颜色比较的接口不支持)
     *
     * @param mode 模式,取值如下:
     *             0: 添加排除区域
     *             1: 设置排除区域的颜色,默认颜色是FF00FF(此接口的原理是把排除区域设置成此颜色,这样就可以让这块区域失效)
     *             2: 清空排除区域
     * @param info 根据mode的取值来决定
     *             当mode为0时,此参数指添加的区域,可以多个区域,用"|"相连. 格式为"x1,y1,x2,y2|....."
     *             当mode为1时,此参数为排除区域的颜色,"RRGGBB"
     *             当mode为2时,此参数无效
     */
    public void setExcludeRegion(int mode, String info) {
        dmSoft.callAndCheckResultEq1("SetExcludeRegion", mode, info);
    }

    /**
     * 当执行FindPicXXX系列接口时,当图片个数少于count时,使用单线程查找,否则使用多线程。 这个count默认是4.
     *
     * @param count 图片数量. 最小不能小于2. 因为1个图片必定是单线程. 这个值默认是4.如果你不更改的话.
     */
    public void setFindPicMultiThreadCount(int count) {
        dmSoft.callAndCheckResultEq1("SetFindPicMultithreadCount", count);
    }

    /**
     * 当执行FindPicXXX系列接口时,当触发多线程查找条件时,设置开启的最大线程数量. 注意,不可以超过当前CPU核心数.
     *
     * @param limit 最大线程数,不能超过当前CPU核心数. 超过无效. 0表示无限制.
     */
    public void setFindPicMultiThreadLimit(int limit) {
        dmSoft.callAndCheckResultEq1("SetFindPicMultithreadLimit", limit);
    }

    /**
     * 设置图片密码，如果图片本身没有加密，那么此设置不影响不加密的图片，一样正常使用.
     * 注意,此函数必须在使用图片之前调用.
     *
     * @param pwd 图片密码
     */
    public void setPicPwd(String pwd) {
        dmSoft.callAndCheckResultEq1("SetPicPwd", pwd);
    }

    private String convertMemoryInfoList(List<MemoryInfo> memoryInfos) {
        return memoryInfos.stream()
                .map(MemoryInfo::toDmString)
                .collect(Collectors.joining("|"));
    }

    private List<FindResult> convertFindResultList(String resultStr, boolean withSim) {
        if (resultStr == null || resultStr.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(resultStr.split("\\|"))
                .map(item -> {
                    String[] parts = item.split(",");
                    if (withSim) {
                        return new FindResult(Integer.parseInt(parts[0]), Point.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[3])), Integer.parseInt(parts[1]));
                    } else {
                        return new FindResult(Integer.parseInt(parts[0]), Point.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), null);
                    }
                })
                .collect(Collectors.toList());
    }
}
