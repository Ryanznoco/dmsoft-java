package cn.com.qjun.dmsoft.functions;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.enums.KeypadMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import lombok.NonNull;

/**
 * 键鼠输入相关操作
 *
 * @author RenQiang
 * @date 2024/2/14
 */
public class DmInputFunctions extends AbstractDmFunctions {

    public DmInputFunctions(@NonNull ActiveXComponent dmSoft) {
        super(dmSoft);
    }

    /**
     * 设置当前系统鼠标的精确度开关. 如果所示。 此接口仅仅对前台MoveR接口起作用.
     *
     * @param enable 0 关闭指针精确度开关.  1打开指针精确度开关. 一般推荐关闭.
     * @return 设置之前的精确度开关.
     */
    public boolean enableMouseAccuracy(boolean enable) {
        return callForBool("EnableMouseAccuracy", FunctionArgs.of(enable));
    }

    /**
     * 获取鼠标位置.
     * <p>
     * 注: 此接口在3.1223版本之后，返回的值的定义修改。  同大多数接口一样,返回的x,y坐标是根据绑定的鼠标参数来决定.  如果绑定了窗口，那么获取的坐标是相对于绑定窗口，否则是屏幕坐标.
     * 另外，此函数获取的坐标是真实的鼠标坐标，对于某些自绘鼠标位置不一定准确。请自行测试.
     *
     * @return 鼠标坐标
     */
    public Point getCursorPos() {
        FunctionArgs args = FunctionArgs.of(new Variant(0, true), new Variant(0, true));
        callExpect1("GetCursorPos", args);
        return args.getPoint(-2, -1);
    }

    /**
     * 获取鼠标特征码. 当BindWindow或者BindWindowEx中的mouse参数含有dx.mouse.cursor时，
     * 获取到的是后台鼠标特征，否则是前台鼠标特征. 关于如何识别后台鼠标特征.
     * <p>
     * 注:此接口和GetCursorShapeEx(0)等效. 相当于工具里的方式1获取的特征码. 当此特征码在某些情况下无法区分鼠标形状时，可以考虑使用GetCursorShapeEx(1).
     * 另要特别注意,WIN7以及以上系统，必须在字体显示设置里把文字大小调整为默认(100%),否则特征码会变.如图所示.
     *
     * @return 成功时，返回鼠标特征码. 失败时，返回空的串.
     */
    public String getCursorShape() {
        return callForString("GetCursorShape", FunctionArgs.of());
    }

    /**
     * 获取鼠标特征码. 当BindWindow或者BindWindowEx中的mouse参数含有dx.mouse.cursor时，
     * 获取到的是后台鼠标特征，否则是前台鼠标特征.  关于如何识别后台鼠标特征.
     * <p>
     * 注: 当type为0时，和GetCursorShape等效.
     * 另要特别注意,WIN7以及以上系统，必须在字体显示设置里把文字大小调整为默认(100%),否则特征码会变.如图所示.
     *
     * @param type 获取鼠标特征码的方式. 和工具中的方式1 方式2对应. 方式1此参数值为0. 方式2此参数值为1.
     * @return 成功时，返回鼠标特征码. 失败时，返回空的串.
     */
    public String getCursorShapeEx(int type) {
        return callForString("GetCursorShapeEx", FunctionArgs.of(type));
    }

    /**
     * 获取鼠标热点位置.(参考工具中抓取鼠标后，那个闪动的点就是热点坐标,不是鼠标坐标)
     * 当BindWindow或者BindWindowEx中的mouse参数含有dx.mouse.cursor时，
     * 获取到的是后台鼠标热点位置，否则是前台鼠标热点位置.  关于如何识别后台鼠标特征.
     *
     * @return 成功时，返回形如"x,y"的字符串  失败时，返回null.
     */
    public Point getCursorSpot() {
        String result = callForString("GetCursorSpot", FunctionArgs.of());
        return DmResultParser.parsePoint(result);
    }

    /**
     * 获取指定的按键状态.(前台信息,不是后台)
     *
     * @param vkCode 虚拟按键码
     * @return true-按下 false-弹起
     */
    public boolean getKeyState(int vkCode) {
        return callForBool("GetKeyState", FunctionArgs.of(vkCode));
    }

    /**
     * 获取系统鼠标的移动速度.  如图所示红色区域. 一共分为11个级别. 从1开始,11结束. 这仅是前台鼠标的速度. 后台不用理会这个.
     *
     * @return 0:失败 其他值,当前系统鼠标的移动速度
     */
    public int getMouseSpeed() {
        return (int) callForLong("GetMouseSpeed", FunctionArgs.of());
    }

    /**
     * 按住指定的虚拟键码
     *
     * @param vkCode 虚拟按键码
     */
    public void keyDown(int vkCode) {
        callExpect1("KeyDown", FunctionArgs.of(vkCode));
    }

    /**
     * 按住指定的虚拟键码
     *
     * @param keyStr 字符串描述的键码. 大小写无所谓. 点这里查看具体对应关系.
     */
    public void keyDownChar(String keyStr) {
        callExpect1("KeyDownChar", FunctionArgs.of(keyStr));
    }

    /**
     * 按下指定的虚拟键码
     *
     * @param vkCode 虚拟按键码
     */
    public void keyPress(int vkCode) {
        callExpect1("KeyPress", FunctionArgs.of(vkCode));
    }

    /**
     * 按下指定的虚拟键码
     *
     * @param keyStr 字符串描述的键码. 大小写无所谓. 点这里查看具体对应关系.
     */
    public void keyPressChar(String keyStr) {
        callExpect1("KeyPressChar", FunctionArgs.of(keyStr));
    }

    /**
     * 根据指定的字符串序列，依次按顺序按下其中的字符.
     * <p>
     * 注: 在某些情况下，SendString和SendString2都无法输入文字时，可以考虑用这个来输入.
     * 但这个接口只支持标准ASCII可见字符,其它字符一律不支持.(包括中文)
     *
     * @param keyStr     需要按下的字符串序列. 比如"1234","abcd","7389,1462"等.
     * @param delayMills 每按下一个按键，需要延时多久. 单位毫秒.这个值越大，按的速度越慢。
     */
    public void keyPressStr(String keyStr, long delayMills) {
        callExpect1("KeyPressStr", FunctionArgs.of(keyStr, delayMills));
    }

    /**
     * 弹起来虚拟键vk_code
     *
     * @param vkCode 虚拟按键码
     */
    public void keyUp(int vkCode) {
        callExpect1("KeyUp", FunctionArgs.of(vkCode));
    }

    /**
     * 弹起来虚拟键key_str
     *
     * @param keyStr 字符串描述的键码. 大小写无所谓. 点这里查看具体对应关系.
     */
    public void keyUpChar(String keyStr) {
        callExpect1("KeyUpChar", FunctionArgs.of(keyStr));
    }

    /**
     * 按下鼠标左键
     */
    public void leftClick() {
        callExpect1("LeftClick", FunctionArgs.of());
    }

    /**
     * 双击鼠标左键
     */
    public void leftDoubleClick() {
        callExpect1("LeftDoubleClick", FunctionArgs.of());
    }

    /**
     * 按住鼠标左键
     */
    public void leftDown() {
        callExpect1("LeftDown", FunctionArgs.of());
    }

    /**
     * 弹起鼠标左键
     */
    public void leftUp() {
        callExpect1("LeftUp", FunctionArgs.of());
    }

    /**
     * 按下鼠标中键
     */
    public void middleClick() {
        callExpect1("MiddleClick", FunctionArgs.of());
    }

    /**
     * 按住鼠标中键
     */
    public void middleDown() {
        callExpect1("MiddleDown", FunctionArgs.of());
    }

    /**
     * 弹起鼠标中键
     */
    public void middleUp() {
        callExpect1("MiddleUp", FunctionArgs.of());
    }

    /**
     * 鼠标相对于上次的位置移动rx,ry.   如果您要使前台鼠标移动的距离和指定的rx,ry一致,最好配合EnableMouseAccuracy函数来使用.
     * <p>
     * 注: 此函数从6.1550开始，为了兼容某些特殊输入，不再自动设置鼠标的速度和精确度。如果您要使前台鼠标移动的距离和指定的rx,ry一致,那么最好配合=EnableMouseAccuracy函数来使用
     * 因为rx和ry的偏移量不一定就是鼠标真实的偏移,而是代表了物理鼠标DPI偏移. 如果您需要这个偏移和真实鼠标偏移一致，那么需要如下调用这个函数，如下所示:
     * old_accuracy = dm.EnableMouseAccuracy(0) // 关闭精确度开关
     * dm.MoveR 30,30
     * dm.EnableMouseAccuracy old_accuracy
     * 当然你也可以永久关闭精确度开关.  一般来说精确度开关默认都是关闭的.
     * 以上这些设置都仅对前台有效. 后台是不需要这样设置的.
     *
     * @param rx 相对于上次的X偏移
     * @param ry 相对于上次的Y偏移
     */
    public void moveR(int rx, int ry) {
        callExpect1("MoveR", FunctionArgs.of(rx, ry));
    }

    /**
     * 把鼠标移动到目的点(x,y)
     *
     * @param point 目标坐标
     */
    public void moveTo(Point point) {
        callExpect1("MoveTo", FunctionArgs.of(point));
    }

    /**
     * 把鼠标移动到目的范围内的任意一点
     *
     * @param rect 要移动到的区域
     * @return 返回要移动到的目标点. 格式为x,y.  比如MoveToEx 100,100,10,10,返回值可能是101,102
     */
    public Point moveToEx(Rect rect) {
        String result = callForString("MoveToEx", FunctionArgs.of(rect));
        return DmResultParser.parsePoint(result);
    }

    /**
     * 按下鼠标右键
     */
    public void rightClick() {
        callExpect1("RightClick", FunctionArgs.of());
    }

    /**
     * 按住鼠标右键
     */
    public void rightDown() {
        callExpect1("RightDown", FunctionArgs.of());
    }

    /**
     * 弹起鼠标右键
     */
    public void rightUp() {
        callExpect1("RightUp", FunctionArgs.of());
    }

    /**
     * 设置按键时,键盘按下和弹起的时间间隔。高级用户使用。某些窗口可能需要调整这个参数才可以正常按键。
     * <p>
     * 注 : 此函数影响的接口有KeyPress
     *
     * @param mode       键盘类型,取值有以下
     *                   "normal" : 对应normal键盘  默认内部延时为30ms
     *                   "windows": 对应windows 键盘 默认内部延时为10ms
     *                   "dx" :     对应dx 键盘 默认内部延时为50ms
     * @param delayMills 延时,单位是毫秒
     */
    public void setKeypadDelay(KeypadMode mode, long delayMills) {
        callExpect1("SetKeypadDelay",FunctionArgs.of(mode, delayMills));
    }

    /**
     * 设置鼠标单击或者双击时,鼠标按下和弹起的时间间隔。高级用户使用。某些窗口可能需要调整这个参数才可以正常点击。
     * <p>
     * 注 : 此函数影响的接口有LeftClick RightClick MiddleClick LeftDoubleClick
     *
     * @param mode       鼠标类型,取值有以下
     *                   "normal" : 对应normal鼠标 默认内部延时为 30ms
     *                   "windows": 对应windows 鼠标 默认内部延时为 10ms
     *                   "dx" :     对应dx鼠标 默认内部延时为40ms
     * @param delayMills 延时,单位是毫秒
     */
    public void setMouseDelay(MouseMode mode, long delayMills) {
        callExpect1("SetMouseDelay", FunctionArgs.of(mode, delayMills));
    }

    /**
     * 设置系统鼠标的移动速度.  如图所示红色区域. 一共分为11个级别. 从1开始,11结束。此接口仅仅对前台鼠标有效.
     *
     * @param speed 鼠标移动速度, 最小1，最大11.  居中为6. 推荐设置为6
     */
    public void setMouseSpeed(int speed) {
        callExpect1("SetMouseSpeed", FunctionArgs.of(speed));
    }

    /**
     * 设置前台键鼠的模拟方式.
     * 驱动功能支持的系统版本号为(win7/win8/win8.1/win10(10240)/win10(10586)/win10(14393)/win10(15063)/win10(16299)/win10(17134)/win10(17763)/win10(18362)/win10(18363)/win10(19041)/win10(19042) /win10(19043)/ win10(19045)/win11(22000)/win11(22621)/win11(22631)
     * 不支持所有的预览版本,仅仅支持正式版本.  除了模式3,其他模式同时支持32位系统和64位系统.
     * <p>
     * 除了模式0,其他方式需要加载驱动，所以调用进程必须有管理员权限,参考如何关闭UAC.
     * 加载驱动时，必须让安全软件放行. 否则模拟无效.
     * 硬件模拟1,没有对键鼠的接口类型有任何限制(PS/2 USB接口)都可以模拟，甚至不插任何键鼠设备都可以模拟.
     * 硬件模拟2(ps2),模式下的键盘基本是正常的,但鼠标兼容性很差,建议只适用此模式的键盘. 鼠标可以使用别的模式. 键盘和鼠标不要求必须插入真实的ps2设备.
     * 硬件模拟3, 设置以后，必须手动按下需要模拟的键盘和鼠标，否则会卡住.直到按下为止. 此后，再次设置不需要重新按下键盘鼠标，直到系统重启. 这个模拟要求被指定的键盘和鼠标不可以中途插拔，会造成模拟失效.  另外,用模拟3后，最好不要调用MoveTo或者MoveToE接口,改为用MoveR自己实现MoveTo或者MoveToEx,否则可能会造成鼠标移动到屏幕左上角的问题.
     * 此接口仅对本对象生效,实际上所有的接口都仅仅对本对象生效,除了DmGuard是全局的.
     *
     * @param mode 0 正常模式(默认模式)
     *             1 硬件模拟
     *             2 硬件模拟2(ps2)（仅仅支持标准的3键鼠标，即左键，右键，中键，带滚轮的鼠标,2键和5键等扩展鼠标不支持）
     *             3 硬件模拟3
     * @return 0  : 插件没注册
     * -1 : 32位系统不支持
     * -2 : 驱动释放失败.
     * -3 : 驱动加载失败.可能是权限不够. 参考UAC权限设置. 或者是被安全软件拦截.
     * 如果是WIN10 1607之后的系统，出现这个错误，可参考这里
     * -10: 设置失败
     * -7 : 系统版本不支持. 可以用winver命令查看系统内部版本号. 驱动只支持正式发布的版本，所有预览版本都不支持.
     * 1  : 成功
     */
    public int setSimMode(int mode) {
        return (int) callForLong("SetSimMode", FunctionArgs.of(mode));
    }

    /**
     * 等待指定的按键按下 (前台,不是后台)
     *
     * @param vkCode       虚拟按键码,当此值为0，表示等待任意按键。 鼠标左键是1,鼠标右键时2,鼠标中键是4.
     * @param timeoutMills 等待多久,单位毫秒. 如果是0，表示一直等待
     * @return 0:超时
     * 1:指定的按键按下 (当vk_code不为0时)
     * 按下的按键码:(当vk_code为0时)
     */
    public int waitKey(int vkCode, long timeoutMills) {
        long result = callForLong("WaitKey", FunctionArgs.of(vkCode, timeoutMills));
        return (int) result;
    }

    /**
     * 滚轮向下滚
     */
    public void wheelDown() {
        callExpect1("WheelDown", FunctionArgs.of());
    }

    /**
     * 滚轮向上滚
     */
    public void wheelUp() {
        callExpect1("WheelUp", FunctionArgs.of());
    }
}
