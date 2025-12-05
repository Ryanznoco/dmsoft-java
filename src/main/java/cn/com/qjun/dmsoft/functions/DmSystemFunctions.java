package cn.com.qjun.dmsoft.functions;

import com.jacob.activeX.ActiveXComponent;
import lombok.NonNull;

/**
 * 系统操作
 *
 * @author RenQiang
 * @date 2024/2/14
 */
public class DmSystemFunctions extends AbstractDmFunctions {

    public DmSystemFunctions(@NonNull ActiveXComponent dmSoft) {
        super(dmSoft);
    }

    /**
     * 蜂鸣器.
     *
     * @param f     频率
     * @param mills 时长(ms).
     */
    public void beep(int f, long mills) {
        callExpect1("Beep", FunctionArgs.of(f, mills));
    }

    /**
     * 检测当前系统是否有开启屏幕字体平滑.
     *
     * @return 是否开启
     */
    public boolean checkFontSmooth() {
        return callForBool("CheckFontSmooth", FunctionArgs.of());
    }

    /**
     * 检测当前系统是否有开启UAC(用户账户控制).
     * <p>
     * 注: 只有WIN7 WIN8 VISTA WIN2008以及以上系统才有UAC设置
     *
     * @return 是否开启
     */
    public boolean checkUac() {
        return callForBool("CheckUAC", FunctionArgs.of());
    }

    /**
     * 延时指定的毫秒,过程中不阻塞UI操作. 一般高级语言使用.按键用不到.
     * <p>
     * 注: 由于是com组件,调用此函数必须保证调用线程的模型为MTA.否则此函数可能会失效.
     *
     * @param mills 毫秒数. 必须大于0.
     */
    public void delay(long mills) {
        callExpect1("Delay", FunctionArgs.of(mills));
    }

    /**
     * 延时指定范围内随机毫秒,过程中不阻塞UI操作. 一般高级语言使用.按键用不到.
     * <p>
     * 注: 由于是com组件,调用此函数必须保证调用线程的模型为MTA.否则此函数可能会失效.
     *
     * @param minMills 最小毫秒数. 必须大于0
     * @param maxMills 最大毫秒数. 必须大于0
     */
    public void delays(long minMills, long maxMills) {
        callExpect1("Delays", FunctionArgs.of(minMills, maxMills));
    }

    /**
     * 设置当前的电源设置，禁止关闭显示器，禁止关闭硬盘，禁止睡眠，禁止待机. 不支持XP.
     */
    public void disableCloseDisplayAndSleep() {
        callExpect1("DisableCloseDisplayAndSleep", FunctionArgs.of());
    }

    /**
     * 关闭当前系统屏幕字体平滑.同时关闭系统的ClearType功能.
     * <p>
     * 注: 关闭之后要让系统生效，必须重启系统才有效.
     */
    public void disableFontSmooth() {
        callExpect1("DisableFontSmooth", FunctionArgs.of());
    }

    /**
     * 关闭电源管理，不会进入睡眠.
     * <p>
     * 注 :此函数调用以后，并不会更改系统电源设置.
     * 此函数经常用在后台操作过程中. 避免被系统干扰.
     */
    public void disablePowerSave() {
        callExpect1("DisablePowerSave", FunctionArgs.of());
    }

    /**
     * 关闭屏幕保护.
     * <p>
     * 注 : 调用此函数后，可能在系统中还是看到屏保是开启状态。但实际上屏保已经失效了.
     * 系统重启后，会失效。必须再重新调用一次.
     * 此函数经常用在后台操作过程中. 避免被系统干扰.
     */
    public void disableScreenSave() {
        callExpect1("DisableScreenSave", FunctionArgs.of());
    }

    /**
     * 开启当前系统屏幕字体平滑.同时开启系统的ClearType功能.
     * <p>
     * 注: 开启之后要让系统生效，必须重启系统才有效.
     */
    public void enableFontSmooth() {
        callExpect1("EnableFontSmooth", FunctionArgs.of());
    }

    /**
     * 退出系统(注销 重启 关机)
     *
     * @param type 取值为以下类型
     *             0 : 注销系统
     *             1 : 关机
     *             2 : 重新启动
     */
    public void exitOs(int type) {
        callExpect1("ExitOs", FunctionArgs.of(type));
    }

    /**
     * 获取剪贴板的内容
     *
     * @return 以字符串表示的剪贴板内容
     */
    public String getClipboard() {
        return callForString("GetClipboard", FunctionArgs.of());
    }

    /**
     * 获取当前CPU类型(intel或者amd).
     *
     * @return 0 : 未知
     * 1 : Intel cpu
     * 2 : AMD cpu
     */
    public int getCpuType() {
        return (int) callForLong("GetCpuType", FunctionArgs.of());
    }

    /**
     * 获取当前CPU的使用率. 用百分比返回.
     *
     * @return 0-100表示的百分比
     */
    public int getCpuUsage() {
        return (int) callForLong("GetCpuUsage", FunctionArgs.of());
    }

    /**
     * 得到系统的路径
     *
     * @param type 取值为以下类型
     *             0 : 获取当前路径
     *             1 : 获取系统路径(system32路径)
     *             2 : 获取windows路径(windows所在路径)
     *             3 : 获取临时目录路径(temp)
     *             4 : 获取当前进程(exe)所在的路径
     * @return 返回路径
     */
    public String getDir(int type) {
        return callForString("GetDir", FunctionArgs.of(type));
    }

    /**
     * 获取本机的指定硬盘的厂商信息. 要求调用进程必须有管理员权限. 否则返回空串.
     *
     * @param index 硬盘序号. 表示是第几块硬盘. 从0开始编号,最小为0,最大为5,也就是最多支持6块硬盘的厂商信息获取.
     * @return 字符串表达的硬盘厂商信息
     */
    public String getDiskModel(int index) {
        return callForString("GetDiskModel", FunctionArgs.of(index));
    }

    /**
     * 获取本机的指定硬盘的修正版本信息. 要求调用进程必须有管理员权限. 否则返回空串.
     *
     * @param index 硬盘序号. 表示是第几块硬盘. 从0开始编号,最小为0,最大为5,也就是最多支持6块硬盘的修正版本信息获取.
     * @return 字符串表达的修正版本信息
     */
    public String getDiskReversion(int index) {
        return callForString("GetDiskReversion", FunctionArgs.of(index));
    }

    /**
     * 获取本机的指定硬盘的序列号. 要求调用进程必须有管理员权限. 否则返回空串.
     *
     * @param index 硬盘序号. 表示是第几块硬盘. 从0开始编号,最小为0,最大为5,也就是最多支持6块硬盘的序列号获取.
     * @return 字符串表达的硬盘序列号
     */
    public String getDiskSerial(int index) {
        return callForString("GetDiskSerial", FunctionArgs.of(index));
    }

    /**
     * 获取本机的显卡信息.
     *
     * @return 字符串表达的显卡描述信息. 如果有多个显卡,用"|"连接
     */
    public String getDisplayInfo() {
        return callForString("GetDisplayInfo", FunctionArgs.of());
    }

    /**
     * 判断当前系统的DPI(文字缩放)是不是100%缩放.
     *
     * @return 缩放比例是不是100%
     */
    public boolean getDpi() {
        return callForBool("GetDPI", FunctionArgs.of());
    }

    /**
     * 判断当前系统使用的非UNICODE字符集是否是GB2312(简体中文)(由于设计插件时偷懒了,使用的是非UNICODE字符集，导致插件必须运行在GB2312字符集环境下).
     *
     * @return 字符集是不是GB2312(简体中文)
     */
    public boolean getLocale() {
        return callForBool("GetLocale", FunctionArgs.of());
    }

    /**
     * 获取本机的机器码.(带网卡). 此机器码用于插件网站后台. 要求调用进程必须有管理员权限. 否则返回空串.
     * <p>
     * 注: 此机器码包含的硬件设备有硬盘,显卡,网卡等. 其它不便透露. 重装系统不会改变此值.
     * 另要注意,插拔任何USB设备,(U盘，U盾,USB移动硬盘,USB键鼠等),以及安装任何网卡驱动程序,(开启或者关闭无线网卡等)都会导致机器码改变.
     *
     * @return 字符串表达的机器机器码
     */
    public String getMachineCode() {
        return callForString("GetMachineCode", FunctionArgs.of());
    }

    /**
     * 获取本机的机器码.(不带网卡) 要求调用进程必须有管理员权限. 否则返回空串.
     * <p>
     * 注: 此机器码包含的硬件设备有硬盘,显卡,等. 其它不便透露. 重装系统不会改变此值.
     * 另要注意,插拔任何USB设备,(U盘，U盾,USB移动硬盘,USB键鼠等),都会导致机器码改变.
     *
     * @return 字符串表达的机器机器码
     */
    public String getMachineCodeNoMac() {
        return callForString("GetMachineCodeNoMac", FunctionArgs.of());
    }

    /**
     * 获取当前内存的使用率. 用百分比返回.
     *
     * @return 0-100表示的百分比
     */
    public int getMemoryUsage() {
        return (int) callForLong("GetMemoryUsage", FunctionArgs.of());
    }

    /**
     * 从网络获取当前北京时间
     * <p>
     * 注: 如果程序无法访问时间服务器，那么返回"0000-00-00 00:00:00"
     *
     * @return 时间格式. 和now返回一致. 比如"2001-11-01 23:14:08"
     */
    public String getNetTime() {
        return callForString("GetNetTime", FunctionArgs.of());
    }

    /**
     * 根据指定时间服务器IP,从网络获取当前北京时间.
     * <p>
     * 注: 如果程序无法访问时间服务器，那么返回"0000-00-00 00:00:00"
     * 时间服务器的IP可以从网上查找NTP服务器.
     *
     * @param ip IP或者域名,并且支持多个IP或者域名连接
     * @return 时间格式. 和now返回一致. 比如"2001-11-01 23:14:08"
     */
    public String getNetTimeByIp(String ip) {
        return callForString("GetNetTimeByIp", FunctionArgs.of(ip));
    }

    /**
     * 得到操作系统的build版本号.  比如win10 16299,那么返回的就是16299. 其他类似
     * <p>
     * WIN11的BuildNumber从22000开始. 如果要判断是不是WIN11,直接判断BuildNumber是否大于等于22000即可.
     *
     * @return build 版本号
     * 失败返回0
     */
    public int getOsBuildNumber() {
        return (int) callForLong("GetOsBuildNumber", FunctionArgs.of());
    }

    /**
     * 得到操作系统的类型
     *
     * @return 0 : win95/98/me/nt4.0
     * 1 : xp/2000
     * 2 : 2003/2003 R2/xp-64
     * 3 : win7/2008 R2
     * 4 : vista/2008
     * 5 : win8/2012
     * 6 : win8.1/2012 R2
     * 7 : win10/2016 TP/win11
     */
    public int getOsType() {
        return (int) callForLong("GetOsType", FunctionArgs.of());
    }

    /**
     * 获取屏幕的色深.
     *
     * @return 返回系统颜色深度.(16或者32等)
     */
    public int getScreenDepth() {
        return (int) callForLong("GetScreenDepth", FunctionArgs.of());
    }

    /**
     * 获取屏幕的高度.
     *
     * @return 返回屏幕的高度
     */
    public int getScreenHeight() {
        return (int) callForLong("GetScreenHeight", FunctionArgs.of());
    }

    /**
     * 获取屏幕的宽度.
     *
     * @return 返回屏幕的宽度
     */
    public int getScreenWidth() {
        return (int) callForLong("GetScreenWidth", FunctionArgs.of());
    }

    /**
     * 获取指定的系统信息
     *
     * @param type   取值如下
     *               "cpuid" : 表示获取cpu序列号. method可取0和1
     *               "disk_volume_serial id" : 表示获取分区序列号. id表示分区序号. 0表示C盘.1表示D盘.以此类推. 最高取到5. 也就是6个分区. method可取0
     *               "bios_vendor" : 表示获取bios厂商信息. method可取0和1
     *               "bios_version" : 表示获取bios版本信息. method可取0和1
     *               "bios_release_date" : 表示获取bios发布日期. method可取0和1
     *               "bios_oem" : 表示获取bios里的oem信息. method可取0
     *               "board_vendor" : 表示获取主板制造厂商信息. method可取0和1
     *               "board_product" : 表示获取主板产品信息. method可取0和1
     *               "board_version" : 表示获取主板版本信息. method可取0和1
     *               "board_serial" : 表示获取主板序列号. method可取0
     *               "board_location" : 表示获取主板位置信息. method可取0
     *               "system_manufacturer" : 表示获取系统制造商信息. method可取0和1
     *               "system_product" : 表示获取系统产品信息. method可取0和1
     *               "system_serial" : 表示获取bios序列号. method可取0
     *               "system_uuid" : 表示获取bios uuid. method可取0
     *               "system_version" : 表示获取系统版本信息. method可取0和1
     *               "system_sku" : 表示获取系统sku序列号. method可取0和1
     *               "system_family" : 表示获取系统家族信息. method可取0和1
     *               "product_id" : 表示获取系统产品id. method可取0
     *               "system_identifier" : 表示获取系统标识. method可取0
     *               "system_bios_version" : 表示获取系统BIOS版本号. method可取0. 多个结果用"|"连接.
     *               "system_bios_date" : 表示获取系统BIOS日期. method可取0
     * @param method 获取方法. 一般从0开始取值
     * @return 字符串表达的系统信息.
     */
    public String getSystemInfo(String type, int method) {
        return callForString("GetSystemInfo", FunctionArgs.of(type, method));
    }

    /**
     * 获取当前系统从开机到现在所经历过的时间，单位是毫秒
     *
     * @return 时间(单位毫秒)
     */
    public long getTime() {
        return callForLong("GetTime", FunctionArgs.of());
    }

    /**
     * 判断当前系统是否是64位操作系统
     *
     * @return 是否是64为操作系统
     */
    public boolean is64Bit() {
        return callForBool("Is64Bit", FunctionArgs.of());
    }

    /**
     * 判断当前CPU是否支持vt,并且是否在bios中开启了vt. 仅支持intel的CPU.
     *
     * @return false-当前cpu不是intel的cpu,或者当前cpu不支持vt,或者bios中没打开vt. true-支持
     */
    public boolean isSupportVt() {
        return callForBool("IsSurrpotVt", FunctionArgs.of());
    }

    /**
     * 播放指定的MP3或者wav文件.
     *
     * @param mediaFile 指定的音乐文件，可以采用文件名或者绝对路径的形式.
     * @return 0 : 失败
     * 非0表示当前播放的ID。可以用Stop来控制播放结束.
     */
    public int play(String mediaFile) {
        return (int) callForLong("Play", FunctionArgs.of(mediaFile));
    }

    /**
     * 运行指定的应用程序.
     *
     * @param appPath 指定的可执行程序全路径.
     * @param mode    取值如下
     *                0 : 普通模式
     *                1 : 加强模式
     */
    public void runApp(String appPath, int mode) {
        callExpect1("RunApp", FunctionArgs.of(appPath, mode));
    }

    /**
     * 设置剪贴板的内容
     *
     * @param value 以字符串表示的剪贴板内容
     */
    public void setClipboard(String value) {
        callExpect1("SetClipboard", FunctionArgs.of(value));
    }

    /**
     * 设置当前系统的硬件加速级别.
     * <p>
     * 注: 此函数只在XP 2003系统有效.
     *
     * @param level 取值范围为0-5.  0表示关闭硬件加速。5表示完全打开硬件加速.
     */
    public void setDisplayAcceler(int level) {
        callExpect1("SetDisplayAcceler", FunctionArgs.of(level));
    }

    /**
     * 设置当前系统的非UNICOD字符集. 会弹出一个字符集选择列表,用户自己选择到简体中文即可.
     */
    public void setLocale() {
        callExpect1("SetLocale", FunctionArgs.of());
    }

    /**
     * 设置系统的分辨率 系统色深
     *
     * @param width  屏幕宽度
     * @param height 屏幕高度
     * @param depth  系统色深
     */
    public void setScreen(int width, int height, int depth) {
        callExpect1("SetScreen", FunctionArgs.of(width, height, depth));
    }

    /**
     * 设置当前系统的UAC(用户账户控制).
     * <p>
     * 注: 只有WIN7 WIN8 VISTA WIN2008以及以上系统才有UAC设置. 关闭UAC以后，必须重启系统才会生效.
     * 如果关闭了UAC，那么默认启动所有应用程序都是管理员权限，就不会再发生绑定失败这样的尴尬情况了.
     *
     * @param enable 是否开启
     */
    public void setUac(boolean enable) {
        callExpect1("SetUAC", FunctionArgs.of(enable));
    }

    /**
     * 显示或者隐藏指定窗口在任务栏的图标.
     *
     * @param hwnd 指定的窗口句柄
     * @param show 0为隐藏,1为显示
     */
    public void showTaskBarIcon(long hwnd, boolean show) {
        callExpect1("ShowTaskBarIcon", FunctionArgs.of(hwnd, show));
    }

    /**
     * 停止指定的音乐
     *
     * @param id Play返回的播放id.
     */
    public void stop(int id) {
        callExpect1("Stop", FunctionArgs.of(id));
    }
}
