package cn.com.qjun.dmsoft.functions;

import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.model.FindResult;
import cn.com.qjun.dmsoft.model.MemoryInfo;
import cn.com.qjun.dmsoft.utils.DirectMemoryUtils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import lombok.NonNull;

/**
 * AI相关操作
 *
 * @author RenQiang
 * @date 2024/2/14
 */
public class DmAiFunctions extends AbstractDmFunctions {

    public DmAiFunctions(@NonNull ActiveXComponent dmSoft) {
        super(dmSoft);
    }

    /**
     * 需要先加载Ai模块. 在指定范围内检测对象.
     * <p>
     * 注:模块内部是全局的,所以调用此接口时得确保没有其它接口去访问此模型.
     * 如果多个线程里,UseModel的序号是相同的,那么如果同时执行此接口时,会排队执行.
     *
     * @param rect 查找区域
     * @param prob 置信度,也可以认为是相似度. 超过这个prob的对象才会被检测
     * @param iou  用于对多个检测框进行合并.  越大越不容易合并(很多框重叠). 越小越容易合并(可能会把正常的框也给合并). 所以这个值一般建议0.4-0.6之间.
     *             可以在Yolo综合工具里进行测试.
     * @return 检测到的对象列表
     */
    public FindResult aiYoloDetectObjects(Rect rect, float prob, float iou) {
        String result = callForString("AiYoloDetectObjects", FunctionArgs.of(rect, prob, iou));
        return DmResultParser.parseFindResult(result, parts -> new FindResult.Item(parts[0], Integer.parseInt(parts[1]), Rect.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]))));
    }

    /**
     * 需要先加载Ai模块. 把通过AiYoloDetectObjects的结果进行排序. 排序按照从上到下,从左到右.
     *
     * @param rect       查找区域
     * @param prob       置信度,也可以认为是相似度. 超过这个prob的对象才会被检测
     * @param iou        用于对多个检测框进行合并.  越大越不容易合并(很多框重叠). 越小越容易合并(可能会把正常的框也给合并). 所以这个值一般建议0.4-0.6之间.
     *                   可以在Yolo综合工具里进行测试.
     * @param lineHeight 行高信息. 排序时需要使用此行高. 用于确定两个检测框是否处于同一行. 如果两个框的Y坐标相差绝对值小于此行高,认为是同一行.
     * @return 排序后的识别结果
     */
    public FindResult aiYoloDetectObjectsAndSort(Rect rect, float prob, float iou, int lineHeight) {
        String result = callForString("AiYoloDetectObjects", FunctionArgs.of(rect, prob, iou));
        String sortedResult = callForString("AiYoloSortsObjects", FunctionArgs.of(result, lineHeight));
        return DmResultParser.parseFindResult(sortedResult, parts ->
                new FindResult.Item(parts[0], Integer.parseInt(parts[1]), Rect.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]))));
    }

    /**
     * 需要先加载Ai模块. 在指定范围内检测对象,把结果输出到BMP图像数据.用于二次开发.
     * <p>
     * 注:模块内部是全局的,所以调用此接口时得确保没有其它接口去访问此模型.
     * 如果多个线程里,UseModel的序号是相同的,那么如果同时执行此接口时,会排队执行.
     *
     * @param rect     查找区域
     * @param prob     置信度,也可以认为是相似度. 超过这个prob的对象才会被检测
     * @param iou      用于对多个检测框进行合并.  越大越不容易合并(很多框重叠). 越小越容易合并(可能会把正常的框也给合并). 所以这个值一般建议0.4-0.6之间.
     *                 可以在Yolo综合工具里进行测试.
     * @param drawProb 绘制的文字信息里是否包含置信度
     * @return 图片在内存中的信息
     */
    public MemoryInfo aiYoloDetectObjectsToDataBmp(Rect rect, float prob, float iou, boolean drawProb) {
        FunctionArgs args = FunctionArgs.of(rect, prob, iou, new Variant(0, true), new Variant(0, true), drawProb);
        callExpect1("AiYoloDetectObjectsToDataBmp", args);
        return args.getMemoryInfo(-3, -2);
    }

    /**
     * 需要先加载Ai模块. 在指定范围内检测对象,把结果输出到指定的BMP文件.
     *
     * @param rect     查找区域
     * @param prob     置信度,也可以认为是相似度. 超过这个prob的对象才会被检测
     * @param iou      用于对多个检测框进行合并.  越大越不容易合并(很多框重叠). 越小越容易合并(可能会把正常的框也给合并). 所以这个值一般建议0.4-0.6之间.
     *                 可以在Yolo综合工具里进行测试.
     * @param file     图片名,比如"test.bmp"
     * @param drawProb 绘制的文字信息里是否包含置信度
     */
    public void aiYoloDetectObjectsToFile(Rect rect, float prob, float iou, String file, boolean drawProb) {
        callExpect1("AiYoloDetectObjectsToFile", FunctionArgs.of(rect, prob, iou, file, drawProb));
    }

    /**
     * 需要先加载Ai模块. 卸载指定的模型
     * <p>
     * 注:模型内部是全局的,所以调用此接口时得确保没有其它接口去访问此模型.
     *
     * @param index 模型的序号. 最多支持20个. 从0开始
     */
    public void aiYoloFreeModel(int index) {
        callExpect1("AiYoloFreeModel", FunctionArgs.of(index));
    }

    /**
     * 需要先加载Ai模块. 从文件加载指定的模型.
     * <p>
     * 注:模块内部是全局的,所以调用此接口时得确保没有其它接口去访问此模型. 另外,加载onnx时得确保和这个onnx同名的class文件也在同目录下.
     * 比如加载xxxx.onnx,那么必须得有个相应的xxxx.class.
     *
     * @param index 模型的序号. 最多支持20个. 从0开始
     * @param file  模型文件名. 比如"xxxx.onnx"或者"xxxx.dmx"
     * @param pwd   模型的密码. 仅对dmx格式有效.
     */
    public void aiYoloSetModel(int index, String file, String pwd) {
        callExpect1("AiYoloSetModel", FunctionArgs.of(index, file, pwd));
    }

    /**
     * 需要先加载Ai模块. 从内存加载指定的模型. 仅支持dmx格式的内存
     * <p>
     * 注:模块内部是全局的,所以调用此接口时得确保没有其它接口去访问此模型.
     *
     * @param index     模型的序号. 最多支持20个. 从0开始
     * @param modelData dmx模型数据
     * @param pwd       dmx模型的密码
     */
    public void aiYoloSetModelMemory(int index, byte[] modelData, String pwd) {
        DirectMemoryUtils.loadToMemAndConsume(modelData, memoryInfo ->
                callExpect1("AiYoloSetModelMemory", FunctionArgs.of(index, memoryInfo, pwd)));
    }

    /**
     * 需要先加载Ai模块. 设置Yolo的版本
     *
     * @param ver Yolo的版本信息. 需要在加载Ai模块后,第一时间调用. 目前可选的值只有"v5-7.0"
     */
    public void aiYoloSetVersion(String ver) {
        callExpect1("AiYoloSetVersion", FunctionArgs.of(ver));
    }

    /**
     * 需要先加载Ai模块. 切换当前使用的模型序号.用于AiYoloDetectXX等系列接口.
     *
     * @param index 模型的序号. 最多支持20个. 从0开始
     */
    public void aiYoloUseModel(int index) {
        callExpect1("AiYoloUseModel", FunctionArgs.of(index));
    }

    /**
     * 加载Ai模块. Ai模块从后台下载.
     *
     * @param file ai模块的路径. 比如绝对路径c:\ai.module或者相对路径ai.module等.
     * @return 1  表示成功
     * -1 打开文件失败
     * -2 内存初始化失败
     * -3 参数错误
     * -4 加载错误
     * -5 Ai模块初始化失败
     * -6 内存分配失败
     */
    public int loadAi(String file) {
        return (int) callForLong("LoadAi", FunctionArgs.of(file));
    }

    /**
     * 从内存加载Ai模块. Ai模块从后台下载.
     *
     * @param modelData 模型数据
     * @return 1  表示成功
     * -1 打开文件失败
     * -2 内存初始化失败
     * -3 参数错误
     * -4 加载错误
     * -5 Ai模块初始化失败
     * -6 内存分配失败
     */
    public int loadAiMemory(byte[] modelData) {
        return DirectMemoryUtils.loadToMemAndApply(modelData, memoryInfo ->
                (int) callForLong("LoadAiMemory", FunctionArgs.of(memoryInfo)));
    }
}
