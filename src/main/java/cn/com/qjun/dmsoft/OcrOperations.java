package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.model.*;
import cn.com.qjun.dmsoft.utils.InfoParseUtils;
import com.jacob.com.Variant;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文字识别相关操作
 *
 * @author RenQiang
 * @date 2024/2/14
 */
@RequiredArgsConstructor
public class OcrOperations {
    private final DmSoft dmSoft;

    /**
     * 给指定的字库中添加一条字库信息.
     * <p>
     * 注意: 此函数尽量在小字库中使用，大字库中使用AddDict速度比较慢
     * 另，此函数是向指定的字库所在的内存中添加,而不是往文件中添加. 添加以后立刻就可以用于文字识别。无须再SetDict
     * 如果要保存添加进去的字库信息，需要调用SaveDict
     *
     * @param index    字库的序号,取值为0-99,目前最多支持100个字库
     * @param dictInfo 字库描述串，具体参考大漠综合工具中的字符定义
     */
    public void addDict(int index, String dictInfo) {
        dmSoft.callAndCheckResultEq1("AddDict", index, dictInfo);
    }

    /**
     * 清空指定的字库.
     * <p>
     * 注意: 此函数尽量在小字库中使用，大字库中使用AddDict速度比较慢
     * 另外，此函数支持清空内存中的字库，而不是字库文件本身.
     *
     * @param index 字库的序号,取值为0-99,目前最多支持100个字库
     */
    public void clearDict(int index) {
        dmSoft.callAndCheckResultEq1("ClearDict", index);
    }

    /**
     * 允许当前调用的对象使用全局字库。  如果你的程序中对象太多,并且每个对象都用到了同样的字库,可以考虑用全局字库,这样可以节省大量内存.
     * <p>
     * 注 : 一旦当前对象开启了全局字库,那么所有的和文字识别，字库相关的接口，通通都认为是对全局字库的操作.
     * 如果所有对象都要需要全局字库,可以选一个主对象开启使用全局字库，并且设置好字库，其他对象只需要开启全局字库即可.
     * 注意,第一个开启全局字库，并且设置字库的主对象不可以被释放,并且此主对象在修改字库(SetDict AddDict ClearDict SetDictMem)时,其它任何对象都不可以对全局字库进行操作.
     * 也就是说,必须是设置好全局字库后,其它对象才可以开启全局字库.
     * 同时，设置好全局字库后,任何对象都不可以修改字库. 只能使用访问字库这样的接口(UseDict等).
     *
     * @param enable 是否打开
     */
    public void enableShareDict(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableShareDict", enable ? 1 : 0);
    }

    /**
     * 根据指定的范围,以及指定的颜色描述，提取点阵信息，类似于大漠工具里的单独提取.
     *
     * @param rect  查找的区域
     * @param color 颜色格式串.注意，RGB和HSV,以及灰度格式都支持.
     * @param word  待定义的文字,不能为空，且不能为关键符号"$"
     * @return 识别到的点阵信息，可用于AddDict
     * 如果失败，返回空
     */
    public String fetchWord(Rect rect, String color, String word) {
        return dmSoft.callForString("FetchWord", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                color, word);
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意个字符串的组合),并返回符合color_format的坐标位置,相似度sim同Ocr接口描述.
     * (多色,差色查找类似于Ocr接口,不再重述)
     * <p>
     * 注: 此函数的原理是先Ocr识别，然后再查找。所以速度比FindStrFast要慢，尤其是在字库
     * 很大，或者模糊度不为1.0时。
     * 一般字库字符数量小于100左右，模糊度为1.0时，用FindStr要快一些,否则用FindStrFast.
     *
     * @param rect        查找区域
     * @param keywords    待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param colorFormat 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 找到的字符串索引和对应位置，没找到返回null
     */
    public FindResult findStr(Rect rect, List<String> keywords, String colorFormat, double sim) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(String.join("|", keywords)), new Variant(colorFormat), new Variant(sim),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindStr", (Object[]) variants);
        if (result == -1) {
            return null;
        }
        return new FindResult((int) result, Point.of(variants[7].getInt(), variants[8].getInt()), null);
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意字符串的组合),并返回符合color_format的所有坐标位置,相似度sim同Ocr接口描述.
     * (多色,差色查找类似于Ocr接口,不再重述)
     * <p>
     * 注: 此函数的原理是先Ocr识别，然后再查找。所以速度比FindStrExFast要慢，尤其是在字库
     * 很大，或者模糊度不为1.0时。
     * 一般字库字符数量小于100左右，模糊度为1.0时，用FindStrEx要快一些,否则用FindStrFastEx.
     *
     * @param rect        查找区域
     * @param keywords    待查找的字符串, 可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param colorFormat 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 返回所有找到的坐标集合
     */
    public List<FindResult> findStrEx(Rect rect, List<String> keywords, String colorFormat, double sim) {
        String result = dmSoft.callForString("FindStrEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                String.join("|", keywords), colorFormat, sim);
        return convertFindResultList(result);
    }

    /**
     * 同FindStr。
     * <p>
     * 注: 此函数比FindStr要快很多，尤其是在字库很大时，或者模糊识别时，效果非常明显。
     * 推荐使用此函数。
     * 另外由于此函数是只识别待查找的字符，所以可能会有如下情况出现问题。
     * 比如 字库中有"张和三" 一共3个字符数据，然后待识别区域里是"张和三",如果用FindStr查找
     * "张三"肯定是找不到的，但是用FindStrFast却可以找到，因为"和"这个字符没有列入查找计划中
     * 所以，在使用此函数时，也要特别注意这一点。
     *
     * @param rect        查找区域
     * @param keywords    待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param colorFormat 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 找到的字符串索引和对应位置，没找到返回null
     */
    public FindResult findStrFast(Rect rect, List<String> keywords, String colorFormat, double sim) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(String.join("|", keywords)), new Variant(colorFormat), new Variant(sim),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindStrFast", (Object[]) variants);
        if (result == -1) {
            return null;
        }
        return new FindResult((int) result, Point.of(variants[7].getInt(), variants[8].getInt()), null);
    }

    /**
     * 同FindStrEx
     * <p>
     * 注: 此函数比FindStrEx要快很多，尤其是在字库很大时，或者模糊识别时，效果非常明显。
     * 推荐使用此函数。
     * 另外由于此函数是只识别待查找的字符，所以可能会有如下情况出现问题。
     * 比如 字库中有"张和三" 一共3个字符数据，然后待识别区域里是"张和三",如果用FindStrEx查找
     * "张三"肯定是找不到的，但是用FindStrFastEx却可以找到，因为"和"这个字符没有列入查找计划中
     * 所以，在使用此函数时，也要特别注意这一点。
     *
     * @param rect        查找区域
     * @param keywords    待查找的字符串, 可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param colorFormat 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 返回所有找到的坐标集合
     */
    public List<FindResult> findStrFastEx(Rect rect, List<String> keywords, String colorFormat, double sim) {
        String result = dmSoft.callForString("FindStrFastEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                String.join("|", keywords), colorFormat, sim);
        return convertFindResultList(result);
    }

    /**
     * 同FindStr，但是不使用SetDict设置的字库，而利用系统自带的字库，速度比FindStr稍慢.
     *
     * @param rect        查找区域
     * @param keywords    待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param colorFormat 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @param fontName    系统字体名,比如"宋体"
     * @param fontSize    系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准.如果获取尺寸看视频教程.
     * @param fontFlag    字体类别 取值可以是以下值的组合,比如1+2+4+8,2+4. 0表示正常字体.
     *                    1 : 粗体
     *                    2 : 斜体
     *                    4 : 下划线
     *                    8 : 删除线
     * @return 找到的字符串索引和对应位置，没找到返回null
     */
    public FindResult findStrWithFont(Rect rect, List<String> keywords, String colorFormat, double sim, String fontName, int fontSize, int fontFlag) {
        Variant[] variants = new Variant[]{new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()),
                new Variant(String.join("|", keywords)), new Variant(colorFormat), new Variant(sim), new Variant(fontName), new Variant(fontSize), new Variant(fontFlag),
                new Variant(0, true), new Variant(0, true)};
        long result = dmSoft.callForLong("FindStrWithFont", (Object[]) variants);
        if (result == -1) {
            return null;
        }
        return new FindResult((int) result, Point.of(variants[10].getInt(), variants[11].getInt()), null);
    }

    /**
     * 同FindStrEx，但是不使用SetDict设置的字库，而利用系统自带的字库，速度比FindStrEx稍慢
     *
     * @param rect        查找区域
     * @param keywords    待查找的字符串, 可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param colorFormat 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @param fontName    系统字体名,比如"宋体"
     * @param fontSize    系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准.如果获取尺寸看视频教程.
     * @param fontFlag    字体类别 取值可以是以下值的组合,比如1+2+4+8,2+4. 0表示正常字体.
     *                    1 : 粗体
     *                    2 : 斜体
     *                    4 : 下划线
     *                    8 : 删除线
     * @return 返回所有找到的坐标集合
     */
    public List<FindResult> findStrWithFontEx(Rect rect, List<String> keywords, String colorFormat, double sim, String fontName, int fontSize, int fontFlag) {
        String result = dmSoft.callForString("FindStrWithFontEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                String.join("|", keywords), colorFormat, sim, fontName, fontSize, fontFlag);
        return convertFindResultList(result);
    }

    /**
     * 获取指定字库中指定条目的字库信息.
     *
     * @param index     字库序号(0-99)
     * @param fontIndex 字库条目序号(从0开始计数,数值不得超过指定字库的字库上限,具体参考GetDictCount)
     * @return 返回字库条目信息. 失败返回空串.
     */
    public String getDict(int index, int fontIndex) {
        return dmSoft.callForString("GetDict", index, fontIndex);
    }

    /**
     * 获取指定的字库中的字符数量.
     *
     * @param index 字库序号(0-99)
     * @return 字库数量
     */
    public int getDictCount(int index) {
        return (int) dmSoft.callForLong("GetDictCount", index);
    }

    /**
     * 根据指定的文字，以及指定的系统字库信息，获取字库描述信息.
     *
     * @param keyword  需要获取的字符串
     * @param fontName 系统字体名,比如"宋体"
     * @param fontSize 系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准.如何获取尺寸看视频教程.
     * @param fontFlag 字体类别 取值可以是以下值的组合,比如1+2+4+8,2+4. 0表示正常字体.
     *                 1 : 粗体
     *                 2 : 斜体
     *                 4 : 下划线
     *                 8 : 删除线
     * @return 返回字库信息, 每个字符的字库信息用"|"来分割
     */
    public String getDictInfo(String keyword, String fontName, int fontSize, int fontFlag) {
        return dmSoft.callForString("GetDictInfo", keyword, fontName, fontSize, fontFlag);
    }

    /**
     * 获取当前使用的字库序号(0-99)
     *
     * @return 字库序号(0 - 99)
     */
    public int getNowDict() {
        return (int) dmSoft.callForLong("GetNowDict");
    }

    /**
     * 在使用GetWords进行词组识别以后,可以用此接口进行识别词组数量的计算.
     *
     * @param str GetWords接口调用以后的返回值
     * @return 返回词组数量
     */
    public int getWordResultCount(String str) {
        return (int) dmSoft.callForLong("GetWordResultCount", str);
    }

    /**
     * 在使用GetWords进行词组识别以后,可以用此接口进行识别各个词组的坐标
     *
     * @param str   GetWords的返回值
     * @param index 表示第几个词组
     * @return 坐标
     */
    public Point getWordResultPos(String str, int index) {
        int[] result = dmSoft.callForMultiInt("GetWordResultPos", 2, false, str, index);
        return Point.of(result[0], result[1]);
    }

    /**
     * 在使用GetWords进行词组识别以后,可以用此接口进行识别各个词组的内容
     *
     * @param str   GetWords的返回值
     * @param index 表示第几个词组
     * @return 返回的第index个词组内容
     */
    public String getWordResultStr(String str, int index) {
        return dmSoft.callForString("GetWordResultStr", str, index);
    }

    /**
     * 根据指定的范围,以及设定好的词组识别参数(一般不用更改,除非你真的理解了)
     * 识别这个范围内所有满足条件的词组. 比较适合用在未知文字的情况下,进行不定识别
     *
     * @param rect        识别区域
     * @param colorFormat 颜色格式串.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度 0.1-1.0
     * @return 识别到的格式串, 要用到专用函数来解析
     */
    public String getWords(Rect rect, String colorFormat, double sim) {
        return dmSoft.callForString("GetWords", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                colorFormat, sim);
    }

    /**
     * 根据指定的范围,以及设定好的词组识别参数(一般不用更改,除非你真的理解了)
     * 识别这个范围内所有满足条件的词组. 这个识别函数不会用到字库。只是识别大概形状的位置
     *
     * @param rect        识别区域
     * @param colorFormat 颜色格式串.注意，RGB和HSV,以及灰度格式都支持.
     * @return 识别到的格式串, 要用到专用函数来解析
     */
    public String getWordsNoDict(Rect rect, String colorFormat) {
        return dmSoft.callForString("GetWordsNoDict", rect.x1(), rect.y1(), rect.x2(), rect.y2(), colorFormat);
    }

    /**
     * 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串,并且相似度为sim,sim取值范围(0.1-1.0),
     * 这个值越大越精确,越大速度越快,越小速度越慢,请斟酌使用!
     *
     * @param rect        识别区域
     * @param colorFormat 颜色格式串. 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串
     */
    public String ocr(Rect rect, String colorFormat, double sim) {
        return dmSoft.callForString("Ocr", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                colorFormat, sim);
    }

    /**
     * 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串,并且相似度为sim,sim取值范围(0.1-1.0),
     * 这个值越大越精确,越大速度越快,越小速度越慢,请斟酌使用!
     * 这个函数可以返回识别到的字符串，以及每个字符的坐标.
     * <p>
     * 注: OcrEx不再像Ocr一样,支持换行分割了.
     *
     * @param rect        识别区域
     * @param colorFormat 颜色格式串.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串和坐标
     */
    public List<OcrResult> ocrEx(Rect rect, String colorFormat, double sim) {
        String result = dmSoft.callForString("OcrEx", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                colorFormat, sim);
        return convertOcrResultList(result);
    }

    /**
     * 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串,并且相似度为sim,sim取值范围(0.1-1.0),
     * 这个值越大越精确,越大速度越快,越小速度越慢,请斟酌使用!
     * 这个函数可以返回识别到的字符串，以及每个字符的坐标.这个同OcrEx,另一种返回形式.
     *
     * @param rect        识别区域
     * @param colorFormat 颜色格式串.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 识别到的字符串和每个字符坐标，没有识别到返回null
     */
    public OcrResultEach ocrExOne(Rect rect, String colorFormat, double sim) {
        String result = dmSoft.callForString("OcrExOne", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                colorFormat, sim);
        if (result == null || result.isEmpty()) {
            return null;
        }
        String[] parts = result.split("\\|");
        List<Point> charPoints = Arrays.stream(parts)
                .skip(1)
                .map(item -> InfoParseUtils.parsePoint(item, ","))
                .collect(Collectors.toList());
        return new OcrResultEach(parts[0], charPoints);
    }

    /**
     * 识别位图中区域(x1,y1,x2,y2)的文字
     *
     * @param rect        识别区域
     * @param picName     图片文件名
     * @param colorFormat 颜色格式串.注意，RGB和HSV,以及灰度格式都支持.
     * @param sim         相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串
     */
    public String ocrInFile(Rect rect, String picName, String colorFormat, double sim) {
        return dmSoft.callForString("OcrInFile", rect.x1(), rect.y1(), rect.x2(), rect.y2(),
                picName, colorFormat, sim);
    }

    /**
     * 保存指定的字库到指定的文件中.
     *
     * @param index 字库索引序号 取值为0-99对应100个字库
     * @param file  文件名
     */
    public void saveDict(int index, String file) {
        dmSoft.callAndCheckResultEq1("SaveDict", index, file);
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定文字的列距,默认列距是1
     *
     * @param colGap 文字列距
     */
    public void setColGapNoDict(int colGap) {
        dmSoft.callAndCheckResultEq1("SetColGapNoDict", colGap);
    }

    /**
     * 设置字库文件
     * <p>
     * 注: 此函数速度很慢，全局初始化时调用一次即可，切换字库用UseDict
     *
     * @param index 字库的序号,取值为0-99,目前最多支持100个字库
     * @param file  字库文件名
     */
    public void setDict(int index, String file) {
        dmSoft.callAndCheckResultEq1("SetDict", index, file);
    }

    /**
     * 从内存中设置字库.
     * <p>
     * 注: 此函数速度很慢，全局初始化时调用一次即可，切换字库用UseDict
     * 另外，此函数不支持加密的内存字库.
     *
     * @param index      字库的序号,取值为0-99,目前最多支持100个字库
     * @param memoryInfo 字库内存信息
     */
    public void setDictMem(int index, MemoryInfo memoryInfo) {
        dmSoft.callAndCheckResultEq1("SetDictMem", index, memoryInfo.getAddress(), memoryInfo.getSize());
    }

    /**
     * 设置字库的密码,在SetDict前调用,目前的设计是,所有字库通用一个密码.
     * <p>
     * 注意:如果使用了多字库,所有字库的密码必须一样. 此函数必须在SetDict之前调用,否则会解密失败.
     *
     * @param pwd 字库密码
     */
    public void setDictPwd(String pwd) {
        dmSoft.callAndCheckResultEq1("SetDictPwd", pwd);
    }

    /**
     * 高级用户使用,在使用文字识别功能前，设定是否开启精准识别.
     * <p>
     * 注意: 精准识别开启后，行间距和列间距会对识别结果造成较大影响，可以在工具中进行测试.
     *
     * @param exactOcr 是否开启精准识别
     */
    public void setExactOcr(boolean exactOcr) {
        dmSoft.callAndCheckResultEq1("SetExactOcr", exactOcr ? 1 : 0);
    }

    /**
     * 高级用户使用,在识别前,如果待识别区域有多行文字,可以设定列间距,默认的列间距是0,
     * 如果根据情况设定,可以提高识别精度。一般不用设定。
     * <p>
     * 注意：此设置如果不为0,那么将不能识别连体字 慎用.
     *
     * @param minColGap 最小列间距
     */
    public void setMinColGap(int minColGap) {
        dmSoft.callAndCheckResultEq1("SetMinColGap", minColGap);
    }

    /**
     * 高级用户使用,在识别前,如果待识别区域有多行文字,可以设定行间距,默认的行间距是1,
     * 如果根据情况设定,可以提高识别精度。一般不用设定。
     *
     * @param minRowGap 最小行间距
     */
    public void setMinRowGap(int minRowGap) {
        dmSoft.callAndCheckResultEq1("SetMinRowGap", minRowGap);
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定文字的行距,默认行距是1
     *
     * @param rowGap 文字行距
     */
    public void setRowGapNoDict(int rowGap) {
        dmSoft.callAndCheckResultEq1("SetRowGapNoDict", rowGap);
    }

    /**
     * 高级用户使用,在识别词组前,可设定词组间的间隔,默认的词组间隔是5
     *
     * @param wordGap 单词间距
     */
    public void setWordGap(int wordGap) {
        dmSoft.callAndCheckResultEq1("SetWordGap", wordGap);
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定词组间的间隔,默认的词组间隔是5
     *
     * @param wordGap 单词间距
     */
    public void setWordGapNoDict(int wordGap) {
        dmSoft.callAndCheckResultEq1("SetWordGapNoDict", wordGap);
    }

    /**
     * 高级用户使用,在识别词组前,可设定文字的平均行高,默认的词组行高是10
     *
     * @param lineHeight 行高
     */
    public void setWordLineHeight(int lineHeight) {
        dmSoft.callAndCheckResultEq1("SetWordLineHeight", lineHeight);
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定文字的平均行高,默认的词组行高是10
     *
     * @param lineHeight 行高
     */
    public void setWordLineHeightNoDict(int lineHeight) {
        dmSoft.callAndCheckResultEq1("SetWordLineHeightNoDict", lineHeight);
    }

    /**
     * 表示使用哪个字库文件进行识别(index范围:0-99)
     * 设置之后，永久生效，除非再次设定
     *
     * @param index 字库编号(0-99)
     */
    public void useDict(int index) {
        dmSoft.callAndCheckResultEq1("UseDict", index);
    }

    private List<OcrResult> convertOcrResultList(String resultStr) {
        if (resultStr == null || resultStr.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(resultStr.split("\\|"))
                .map(item -> {
                    String[] parts = item.split("\\$");
                    return new OcrResult(parts[0], Point.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                })
                .collect(Collectors.toList());
    }

    private List<FindResult> convertFindResultList(String resultStr) {
        if (resultStr == null || resultStr.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(resultStr.split("\\|"))
                .map(item -> {
                    String[] parts = item.split(",");
                    return new FindResult(Integer.parseInt(parts[0]), Point.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), null);
                })
                .collect(Collectors.toList());
    }
}
