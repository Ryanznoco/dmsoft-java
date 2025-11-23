package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.commons.geometry.Size;
import cn.com.qjun.dmsoft.model.ProcessInfo;
import cn.com.qjun.dmsoft.enums.GetWindowFlag;
import cn.com.qjun.dmsoft.enums.GetWindowStateFlag;
import cn.com.qjun.dmsoft.enums.WindowFilter;
import cn.com.qjun.dmsoft.enums.WindowFilterFlag;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 窗口操作
 *
 * @author RenQiang
 * @version v7.2405
 * @date 2024/2/11
 */
@RequiredArgsConstructor
public class WindowOperations {
    private final DmSoft dmSoft;

    /**
     * 把窗口坐标转换为屏幕坐标
     *
     * @param hwnd          窗口句柄
     * @param pointInWindow 在窗口中的坐标
     * @return 窗口坐标对应的屏幕坐标
     */
    public Point clientToScreen(long hwnd, Point pointInWindow) {
        int[] result = dmSoft.callForMultiInt("ClientToScreen", 2, true, hwnd, pointInWindow.x(), pointInWindow.y());
        return Point.of(result[0], result[1]);
    }

    /**
     * 根据指定进程名,枚举系统中符合条件的进程PID,并且按照进程打开顺序排序.
     *
     * @param name 进程名,比如qq.exe
     * @return 返回所有匹配的进程PID, 并按打开顺序排序
     */
    public List<Long> enumProcess(String name) {
        return dmSoft.callForStringAndConvertToLongList("EnumProcess", name);
    }

    /**
     * 根据指定条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     *
     * @param parent    获得的窗口句柄是该窗口的子窗口的窗口句柄,取0时为获得桌面句柄
     * @param title     窗口标题. 此参数是模糊匹配.
     * @param className 窗口类名. 此参数是模糊匹配.
     * @param filters   过滤条件
     * @return 返回所有匹配的窗口句柄
     */
    public List<Long> enumWindow(long parent, String title, String className, WindowFilter... filters) {
        return dmSoft.callForStringAndConvertToLongList("EnumWindow", parent, title, className, WindowFilter.calcValue(filters));
    }

    /**
     * 根据指定进程以及其它条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     *
     * @param processName 进程映像名.比如(svchost.exe). 此参数是精确匹配,但不区分大小写.
     * @param title       窗口标题. 此参数是模糊匹配.
     * @param className   窗口类名. 此参数是模糊匹配.
     * @param filters     过滤条件
     * @return 返回所有匹配的窗口句柄
     */
    public List<Long> enumWindowByProcess(String processName, String title, String className, WindowFilter... filters) {
        return dmSoft.callForStringAndConvertToLongList("EnumWindowByProcess", processName, title, className, WindowFilter.calcValue(filters));
    }

    /**
     * 根据指定进程pid以及其它条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     *
     * @param pid       进程pid
     * @param title     窗口标题. 此参数是模糊匹配
     * @param className 窗口类名. 此参数是模糊匹配
     * @param filters   过滤条件
     * @return
     */
    public List<Long> enumWindowByProcessId(long pid, String title, String className, WindowFilter... filters) {
        return dmSoft.callForStringAndConvertToLongList("EnumWindowByProcessId", pid, title, className, WindowFilter.calcValue(filters));
    }

    /**
     * 根据两组设定条件来枚举指定窗口.
     *
     * @param spec1           查找串1. (内容取决于flag1的值)
     * @param flag1           匹配方式1
     * @param fuzzy1          条件1是否模糊匹配
     * @param spec2           查找串2. (内容取决于flag2的值)
     * @param flag2           匹配方式2
     * @param fuzzy2          条件2是否模糊匹配
     * @param orderByOpenTime 是否把对枚举出的窗口进行排序,按照窗口打开顺序.
     * @return 返回所有匹配的窗口句柄
     */
    public List<Long> enumWindowSuper(String spec1, WindowFilterFlag flag1, boolean fuzzy1, String spec2, WindowFilterFlag flag2, boolean fuzzy2, boolean orderByOpenTime) {
        return dmSoft.callForStringAndConvertToLongList("EnumWindowSuper", spec1, flag1.getValue(), fuzzy1 ? 1 : 0, spec2, flag2.getValue(), fuzzy2 ? 1 : 0, orderByOpenTime ? 1 : 0);
    }

    /**
     * 查找符合类名或者标题名的顶层可见窗口
     *
     * @param className 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param title     窗口标题,如果为空，则匹配所有.这里的匹配是模糊匹配.
     * @return 整形数表示的窗口句柄，没找到返回0
     */
    public long findWindow(String className, String title) {
        return dmSoft.callForLong("FindWindow", className, title);
    }

    /**
     * 根据指定的进程名字，来查找可见窗口.
     *
     * @param processName 进程名. 比如(notepad.exe).这里是精确匹配,但不区分大小写.
     * @param className   窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param title       窗口标题,如果为空，则匹配所有.这里的匹配是模糊匹配.
     * @return 整形数表示的窗口句柄，没找到返回0
     */
    public long findWindowByProcess(String processName, String className, String title) {
        return dmSoft.callForLong("FindWindowByProcess", processName, className, title);
    }

    /**
     * 根据指定的进程Id，来查找可见窗口.
     *
     * @param processId 进程id.
     * @param className 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param title     窗口标题,如果为空，则匹配所有.这里的匹配是模糊匹配.
     * @return 整形数表示的窗口句柄，没找到返回0
     */
    public long findWindowByProcessId(long processId, String className, String title) {
        return dmSoft.callForLong("FindWindowByProcessId", processId, className, title);
    }

    /**
     * 查找符合类名或者标题名的顶层可见窗口,如果指定了parent,则在parent的第一层子窗口中查找.
     *
     * @param parent    父窗口句柄，如果为空，则匹配所有顶层窗口
     * @param className 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param title     窗口标题,如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @return 整形数表示的窗口句柄，没找到返回0
     */
    public long findWindowEx(long parent, String className, String title) {
        return dmSoft.callForLong("FindWindowEx", parent, className, title);
    }

    /**
     * 根据两组设定条件来查找指定窗口.
     *
     * @param spec1  查找串1. (内容取决于flag1的值)
     * @param flag1  匹配方式1
     * @param fuzzy1 条件1是否模糊匹配
     * @param spec2  查找串2. (内容取决于flag2的值)
     * @param flag2  匹配方式2
     * @param fuzzy2 条件2是否模糊匹配
     * @return
     */
    public long findWindowSuper(String spec1, WindowFilterFlag flag1, boolean fuzzy1, String spec2, WindowFilterFlag flag2, boolean fuzzy2) {
        return dmSoft.callForLong("FindWindowSuper", spec1, flag1.getValue(), fuzzy1 ? 1 : 0, spec2, flag2.getValue(), fuzzy2 ? 1 : 0);
    }

    /**
     * 获取窗口客户区域在屏幕上的位置
     *
     * @param hwnd 指定的窗口句柄
     * @return 窗口客户区
     */
    public Rect getClientRect(long hwnd) {
        int[] result = dmSoft.callForMultiInt("GetClientRect", 4, false, hwnd);
        return Rect.of(result[0], result[1], result[2], result[3]);
    }

    /**
     * 获取窗口客户区域的宽度和高度
     *
     * @param hwnd 指定的窗口句柄
     * @return 窗口客户区尺寸
     */
    public Size getClientSize(long hwnd) {
        int[] result = dmSoft.callForMultiInt("GetClientSize", 2, false, hwnd);
        return Size.of(result[0], result[1]);
    }

    /**
     * 获取顶层活动窗口中具有输入焦点的窗口句柄
     *
     * @return 返回整型表示的窗口句柄
     */
    public long getForegroundFocus() {
        return dmSoft.callForLong("GetForegroundFocus");
    }

    /**
     * 获取顶层活动窗口,可以获取到按键自带插件无法获取到的句柄
     *
     * @return 返回整型表示的窗口句柄
     */
    public long getForegroundWindow() {
        return dmSoft.callForLong("GetForegroundWindow");
    }

    /**
     * 获取鼠标指向的可见窗口句柄,可以获取到按键自带的插件无法获取到的句柄
     *
     * @return 返回整型表示的窗口句柄
     */
    public long getMousePointWindow() {
        return dmSoft.callForLong("GetMousePointWindow");
    }

    /**
     * 获取给定坐标的可见窗口句柄,可以获取到按键自带的插件无法获取到的句柄
     *
     * @param point 屏幕坐标
     * @return 返回整型表示的窗口句柄
     */
    public long getPointWindow(Point point) {
        return dmSoft.callForLong("GetPointWindow", point.x(), point.y());
    }

    /**
     * 根据指定的pid获取进程详细信息,(进程名,进程全路径,CPU占用率(百分比),内存占用量(字节))
     *
     * @param pid 进程pid
     * @return 进程信息
     */
    public ProcessInfo getProcessInfo(long pid) {
        String result = dmSoft.callForString("GetProcessInfo", pid);
        String[] parts = result.split("\\|");
        return new ProcessInfo(parts[0], parts[1], Integer.parseInt(parts[2]), Long.parseLong(parts[3]));
    }

    /**
     * 获取特殊窗口
     *
     * @param flag 取值定义如下
     *             0 : 获取桌面窗口
     *             1 : 获取任务栏窗口
     * @return 以整型数表示的窗口句柄
     */
    public long getSpecialWindow(int flag) {
        return dmSoft.callForLong("GetSpecialWindow", flag);
    }

    /**
     * 获取给定窗口相关的窗口句柄
     *
     * @param hwnd 窗口句柄
     * @param flag 标识
     * @return 返回整型表示的窗口句柄
     */
    public long getWindow(long hwnd, GetWindowFlag flag) {
        return dmSoft.callForLong("GetWindow", hwnd, flag.getValue());
    }

    /**
     * 获取窗口的类名
     *
     * @param hwnd 指定的窗口句柄
     * @return 窗口的类名
     */
    public String getWindowClass(long hwnd) {
        return dmSoft.callForString("GetWindowClass", hwnd);
    }

    /**
     * 获取指定窗口所在的进程ID.
     *
     * @param hwnd 窗口句柄
     * @return 返回整型表示的进程ID
     */
    public long getWindowProcessId(long hwnd) {
        return dmSoft.callForLong("GetWindowProcessId", hwnd);
    }

    /**
     * 获取指定窗口所在的进程的exe文件全路径
     *
     * @param hwnd 窗口句柄
     * @return 返回字符串表示的是exe全路径名
     */
    public String getWindowProcessPath(long hwnd) {
        return dmSoft.callForString("GetWindowProcessPath", hwnd);
    }

    /**
     * 获取窗口在屏幕上的位置
     *
     * @param hwnd 指定的窗口句柄
     * @return 窗口在屏幕上的区域
     */
    public Rect getWindowRect(long hwnd) {
        int[] result = dmSoft.callForMultiInt("GetWindowRect", 4, false, hwnd);
        return Rect.of(result[0], result[1], result[2], result[3]);
    }

    /**
     * 获取指定窗口的一些属性
     *
     * @param hwnd 指定的窗口句柄
     * @param flag 要获取的状态标识
     * @return 是否处于标识指定的状态
     */
    public boolean getWindowState(long hwnd, GetWindowStateFlag flag) {
        long result = dmSoft.callForLong("GetWindowState", hwnd, flag.getValue());
        return result == 1;
    }

    /**
     * 获取指定窗口所在的线程ID.
     *
     * @param hwnd 窗口句柄
     * @return 返回整型表示的是线程ID
     */
    public long getWindowThreadId(long hwnd) {
        return dmSoft.callForLong("GetWindowThreadId", hwnd);
    }

    /**
     * 获取窗口的标题
     *
     * @param hwnd 指定的窗口句柄
     * @return 窗口的标题
     */
    public String getWindowTitle(long hwnd) {
        return dmSoft.callForString("GetWindowTitle", hwnd);
    }

    /**
     * 移动指定窗口到指定位置
     *
     * @param hwnd  指定的窗口句柄
     * @param point 要移动到的坐标
     */
    public void moveWindow(long hwnd, Point point) {
        dmSoft.callAndCheckResultEq1("MoveWindow", hwnd, point.x(), point.y());
    }

    /**
     * 把屏幕坐标转换为窗口坐标
     *
     * @param hwnd          指定的窗口句柄
     * @param pointInScreen 屏幕中的坐标
     * @return
     */
    public Point screenToClient(long hwnd, Point pointInScreen) {
        int[] result = dmSoft.callForMultiInt("ScreenToClient", 2, true, hwnd, pointInScreen.x(), pointInScreen.y());
        return Point.of(result[0], result[1]);
    }

    /**
     * 向指定窗口发送粘贴命令. 把剪贴板的内容发送到目标窗口.
     *
     * @param hwnd 指定的窗口句柄. 如果为0,则对当前激活的窗口发送.
     */
    public void sendPaste(long hwnd) {
        dmSoft.callAndCheckResultEq1("SendPaste", hwnd);
    }

    /**
     * 向指定窗口发送文本数据
     * 注： 有时候发送中文，可能会大部分机器正常，少部分会乱码。这种情况一般有两个可能
     * 1. 系统编码没有设置为GBK
     * 2. 目标程序里可能安装了改变当前编码的软件，比如常见的是输入法. （尝试卸载）
     *
     * @param hwnd    指定的窗口句柄. 如果为0,则对当前激活的窗口发送.
     * @param content 发送的文本数据
     */
    public void sendString(long hwnd, String content) {
        dmSoft.callAndCheckResultEq1("SendString", hwnd, content);
    }

    /**
     * 向指定窗口发送文本数据
     * 注: 此接口为老的SendString，如果新的SendString不能输入，可以尝试此接口.
     * 有时候发送中文，可能会大部分机器正常，少部分会乱码。这种情况一般有两个可能
     * 1. 系统编码没有设置为GBK
     * 2. 目标程序里可能安装了改变当前编码的软件，比如常见的是输入法. （尝试卸载）
     *
     * @param hwnd    向指定窗口发送文本数据
     * @param content 发送的文本数据
     */
    @Deprecated
    public void sendString2(long hwnd, String content) {
        dmSoft.callAndCheckResultEq1("SendString2", hwnd, content);
    }

    /**
     * 向绑定的窗口发送文本数据.必须配合dx.public.input.ime属性
     *
     * @param content 发送的文本数据
     */
    public void sendStringIme(String content) {
        dmSoft.callAndCheckResultEq1("SendStringIme", content);
    }

    /**
     * 利用真实的输入法，对指定的窗口输入文字
     * 注: 如果要同时对此窗口进行绑定，并且绑定的模式是1 3 5 7 101 103，那么您必须要在绑定之前,先执行加载输入法的操作. 否则会造成绑定失败!.
     * 卸载时，没有限制.
     * 还有，在后台输入时，如果目标窗口有判断是否在激活状态才接受输入文字,那么可以配合绑定窗口中的假激活属性来保证文字正常输入. 诸如此类. 基本上用这个没有输入不了的文字.
     * 比如
     * BindWindow hwnd,"normal","normal","normal","dx.public.active.api|dx.public.active.message",0
     * dm.SendStringIme2 hwnd,"哈哈",0
     *
     * @param hwnd    窗口句柄
     * @param content 发送的文本数据
     * @param mode    取值意义如下:
     *                0 : 向hwnd的窗口输入文字(前提是必须先用模式200安装了输入法)
     *                1 : 同模式0,如果由于保护无效，可以尝试此模式.(前提是必须先用模式200安装了输入法)
     *                2 : 同模式0,如果由于保护无效，可以尝试此模式. (前提是必须先用模式200安装了输入法)
     *                200 : 向系统中安装输入法,多次调用没问题. 全局只用安装一次.
     *                300 : 卸载系统中的输入法. 全局只用卸载一次. 多次调用没关系.
     */
    public void sendStringIme2(long hwnd, String content, int mode) {
        dmSoft.callAndCheckResultEq1("SendStringIme2", hwnd, content, mode);
    }

    /**
     * 设置窗口客户区域的宽度和高度
     *
     * @param hwnd 设置窗口客户区域的宽度和高度
     * @param size 要设置的客户区尺寸
     */
    public void setClientSize(long hwnd, Size size) {
        dmSoft.callAndCheckResultEq1("SetClientSize", hwnd, size.width(), size.height());
    }

    /**
     * 设置窗口的大小
     *
     * @param hwnd 指定的窗口句柄
     * @param size 要设置的窗口尺寸
     */
    public void setWindowSize(long hwnd, Size size) {
        dmSoft.callAndCheckResultEq1("SetWindowSize", hwnd, size.width(), size.height());
    }

    /**
     * 设置窗口的状态
     *
     * @param hwnd 指定的窗口句柄
     * @param flag 取值定义如下
     *             0 : 关闭指定窗口
     *             1 : 激活指定窗口
     *             2 : 最小化指定窗口,但不激活
     *             3 : 最小化指定窗口,并释放内存,但同时也会激活窗口.(释放内存可以考虑用FreeProcessMemory函数)
     *             4 : 最大化指定窗口,同时激活窗口.
     *             5 : 恢复指定窗口 ,但不激活
     *             6 : 隐藏指定窗口
     *             7 : 显示指定窗口
     *             8 : 置顶指定窗口
     *             9 : 取消置顶指定窗口
     *             10 : 禁止指定窗口
     *             11 : 取消禁止指定窗口
     *             12 : 恢复并激活指定窗口
     *             13 : 强制结束窗口所在进程.
     *             14 : 闪烁指定的窗口
     *             15 : 使指定的窗口获取输入焦点
     */
    public void setWindowState(long hwnd, int flag) {
        dmSoft.callAndCheckResultEq1("SetWindowState", hwnd, flag);
    }

    /**
     * 设置窗口的标题
     *
     * @param hwnd  指定的窗口句柄
     * @param title 标题
     */
    public void setWindowText(long hwnd, String title) {
        dmSoft.callAndCheckResultEq1("SetWindowText", hwnd, title);
    }

    /**
     * 设置窗口的透明度
     * 注 :  此接口不支持WIN98
     *
     * @param hwnd  指定的窗口句柄
     * @param trans 透明度取值(0-255) 越小透明度越大 0为完全透明(不可见) 255为完全显示(不透明)
     */
    public void setWindowTransparent(long hwnd, int trans) {
        dmSoft.callAndCheckResultEq1("SetWindowTransparent", hwnd, trans);
    }
}
