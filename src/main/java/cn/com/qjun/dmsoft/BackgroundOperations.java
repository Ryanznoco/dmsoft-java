package cn.com.qjun.dmsoft;

import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.enums.DisplayMode;
import cn.com.qjun.dmsoft.enums.KeypadMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import lombok.RequiredArgsConstructor;

/**
 * 后台设置
 *
 * @author RenQiang
 * @date 2024/2/13
 */
@RequiredArgsConstructor
public class BackgroundOperations {
    private final DmSoft dmSoft;

    /**
     * 绑定指定的窗口,并指定这个窗口的屏幕颜色获取方式,鼠标仿真模式,键盘仿真模式,以及模式设定,高级用户可以参考BindWindowEx更加灵活强大,
     * 注意绑定窗口前必须从网站www.52hsxx.com获取注册码或淘宝获取eyy8.taobao.com 先用reg命令在程序开头注册VIP.否则绑定会崩溃闪退,请用大漠类库生成工具生成调用注册例子.
     * <p>
     * 如果返回0，可以调用GetLastError来查看具体失败错误码,帮助分析问题.
     * <p>
     * 注意:
     * 绑定之后,所有的坐标都相对于窗口的客户区坐标(不包含窗口边框)
     * 另外,绑定窗口后,必须加以下代码,以保证所有资源正常释放
     * 这个函数的意思是在脚本结束时,会调用这个函数。需要注意的是，目前的按键版本对于这个函数的执行不是线程级别的，也就是说，这个函数只会在主线程执行，子线程绑定的大漠对象，不保证完全释放。
     * Sub OnScriptExit()
     * dm_ret = dm.UnBindWindow()
     * End Sub
     * 另外 绑定dx会比较耗时间,请不要频繁调用此函数.
     * 还有一点特别要注意的是,有些窗口绑定之后必须加一定的延时,否则后台也无效.一般1秒到2秒的延时就足够.
     * <p>
     * 发现绑定失败的几种可能(一般是需要管理员权限的模式才有可能会失败)
     * 1.     系统登录的帐号必须有Administrators权限
     * 2.     一些防火墙会防止插件注入窗口所在进程，比如360防火墙等，必须把dm.dll设置为信任.
     * 3.     还有一个比较弱智的可能性，那就是插件没有注册到系统中，这时CreateObject压根就是失败的. 检测对象是否创建成功很简单，如下代码
     * set dm = createobject("dm.dmsoft")
     * ver = dm.Ver()
     * If len(ver) = 0 Then
     * MessageBox "创建对象失败,检查系统是否禁用了vbs脚本权限"
     * EndScript
     * End If
     * 4.     在沙盘中开的窗口进程，绑定一些需要管理员权限的模式，会失败。
     * 解决方法是要配置沙盘参数，具体如何配置参考沙盘绑定方法.
     * 5.     窗口所在进程有保护，这个我也无能为力.
     *
     * @param hwnd    指定的窗口句柄
     * @param display 屏幕颜色获取方式
     * @param mouse   鼠标仿真模式
     * @param keypad  键盘仿真模式
     * @param mode    模式。 取值有以下几种
     *                0 : 推荐模式此模式比较通用，而且后台效果是最好的.
     *                2 : 同模式0,如果模式0有崩溃问题，可以尝试此模式. 注意0和2模式，当主绑定(第一个绑定同个窗口的对象)绑定成功后，那么调用主绑定的线程必须一致维持,否则线程一旦推出,对应的绑定也会消失.
     *                101 : 超级绑定模式. 可隐藏目标进程中的dm.dll.避免被恶意检测.效果要比dx.public.hide.dll好. 推荐使用.
     *                103 : 同模式101，如果模式101有崩溃问题，可以尝试此模式.
     *                11 : 需要加载驱动,适合一些特殊的窗口,如果前面的无法绑定，可以尝试此模式. 此模式不支持32位系统
     *                13 : 需要加载驱动,适合一些特殊的窗口,如果前面的无法绑定，可以尝试此模式. 此模式不支持32位系统
     *                <p>
     *                需要注意的是: 模式101 103在大部分窗口下绑定都没问题。但也有少数特殊的窗口，比如有很多子窗口的窗口，对于这种窗口，在绑定时，一定要把
     *                鼠标指向一个可以输入文字的窗口，比如一个文本框，最好能激活这个文本框，这样可以保证绑定的成功.
     */
    public void bindWindow(long hwnd, DisplayMode display, MouseMode mouse, KeypadMode keypad, int mode) {
        dmSoft.callAndCheckResultEq1("BindWindow", hwnd, display.getValue(), mouse.getValue(), keypad.getValue(), mode);
    }

    /**
     * 绑定指定的窗口,并指定这个窗口的屏幕颜色获取方式,鼠标仿真模式,键盘仿真模式
     * 高级用户使用,注意绑定窗口前必须从网站www.52hsxx.com获取注册码或淘宝获取eyy8.taobao.com 先用reg命令在程序开头注册VIP.否则绑定会崩溃闪退,请用大漠类库生成工具生成调用注册例子.
     * <p>
     * 注意:
     * 绑定之后,所有的坐标都相对于窗口的客户区坐标(不包含窗口边框)
     * 另外,绑定窗口后,必须加以下代码,以保证所有资源正常释放
     * 这个函数的意思是在脚本结束时,会调用这个函数。需要注意的是，目前的按键版本对于这个函数的执行不是线程级别的，也就是说，这个函数只会在主线程执行，子线程绑定的大漠对象，不保证完全释放。高级语言中则需要自己控制在适当的时候解除绑定.
     * Sub OnScriptExit()
     * dm_ret = dm.UnBindWindow()
     * End Sub
     * 另外 绑定dx会比较耗时间,请不要频繁调用此函数.
     * 还有一点特别要注意的是,有些窗口绑定之后必须加一定的延时,否则后台也无效.一般1秒到2秒的延时就足够.
     * 发现绑定失败的几种可能(一般是需要管理员权限的模式才有可能会失败)
     * 1.     系统登录的帐号必须有Administrators权限
     * 2.     一些防火墙会防止插件注入窗口所在进程，比如360防火墙等，必须把dm.dll设置为信任.
     * 3.     还有一个比较弱智的可能性，那就是插件没有注册到系统中，这时CreateObject压根就是失败的. 检测对象是否创建成功很简单，如下代码
     * set dm = createobject("dm.dmsoft")
     * ver = dm.Ver()
     * If len(ver) = 0 Then
     * MessageBox "创建对象失败,检查系统是否禁用了vbs脚本权限"
     * EndScript
     * End If
     * 4.     在沙盘中开的窗口，绑定一些需要管理员权限的模式，会失败。
     * 解决方法是要配置沙盘参数，参考如何配置沙盘参数.
     * 5.     窗口所在进程有保护，这个我也无能为力.
     *
     * @param hwnd             指定的窗口句柄
     * @param display          屏幕颜色获取方式 取值有以下几种
     *                         "normal" : 正常模式,平常我们用的前台截屏模式
     *                         "gdi" : gdi模式,用于窗口采用GDI方式刷新时. 此模式占用CPU较大. 参考SetAero. win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试。
     *                         "gdi2" : gdi2模式,此模式兼容性较强,但是速度比gdi模式要慢许多,如果gdi模式发现后台不刷新时,可以考虑用gdi2模式.
     *                         "dx2" : dx2模式,用于窗口采用dx模式刷新,如果dx方式会出现窗口进程崩溃的状况,可以考虑采用这种.采用这种方式要保证窗口有一部分在屏幕外.win7 win8或者vista不需要移动也可后台. 此模式占用CPU较大. 参考SetAero. win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试。
     *                         "dx3" : dx3模式,同dx2模式,但是如果发现有些窗口后台不刷新时,可以考虑用dx3模式,此模式比dx2模式慢许多. 此模式占用CPU较大. 参考SetAero. win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试。
     *                         dx模式,用于窗口采用dx模式刷新,取值可以是以下任意组合，组合采用"|"符号进行连接. 支持BindWindow中的缩写模式. 比如dx代表" dx.graphic.2d| dx.graphic.3d"
     *                         1. "dx.graphic.2d"  2d窗口的dx图色模式
     *                         2. "dx.graphic.2d.2"  2d窗口的dx图色模式  是dx.graphic.2d的增强模式.兼容性更好.
     *                         3. "dx.graphic.3d"  3d窗口的dx图色模式,
     *                         4. "dx.graphic.3d.8"  3d窗口的dx8图色模式,  此模式对64位进程无效.
     *                         5. "dx.graphic.opengl"  3d窗口的opengl图色模式,极少数窗口采用opengl引擎刷新. 此图色模式速度可能较慢.
     *                         6. "dx.graphic.opengl.esv2"  3d窗口的opengl_esv2图色模式,极少数窗口采用opengl引擎刷新. 此图色模式速度可能较慢.
     *                         7. "dx.graphic.3d.10plus"  3d窗口的dx10 dx11 dx12图色模式
     * @param mouse            鼠标仿真模式 取值有以下几种
     *                         "normal" : 正常模式,平常我们用的前台鼠标模式
     *                         "windows": Windows模式,采取模拟windows消息方式 同按键的后台插件.
     *                         "windows3": Windows3模式，采取模拟windows消息方式,可以支持有多个子窗口的窗口后台
     *                         dx模式,取值可以是以下任意组合. 组合采用"|"符号进行连接. 支持BindWindow中的缩写模式,比如windows2代表"dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.state.message"
     *                         1. "dx.mouse.position.lock.api"  此模式表示通过封锁系统API，来锁定鼠标位置.
     *                         2. "dx.mouse.position.lock.message" 此模式表示通过封锁系统消息，来锁定鼠标位置.
     *                         3. "dx.mouse.focus.input.api" 此模式表示通过封锁系统API来锁定鼠标输入焦点.
     *                         4. "dx.mouse.focus.input.message"此模式表示通过封锁系统消息来锁定鼠标输入焦点.
     *                         5. "dx.mouse.clip.lock.api" 此模式表示通过封锁系统API来锁定刷新区域。注意，使用这个模式，在绑定前，必须要让窗口完全显示出来.
     *                         6. "dx.mouse.input.lock.api" 此模式表示通过封锁系统API来锁定鼠标输入接口.
     *                         7. "dx.mouse.state.api" 此模式表示通过封锁系统API来锁定鼠标输入状态.
     *                         8. "dx.mouse.state.message" 此模式表示通过封锁系统消息来锁定鼠标输入状态.
     *                         9. "dx.mouse.api"  此模式表示通过封锁系统API来模拟dx鼠标输入.
     *                         10. "dx.mouse.cursor"  开启此模式，可以后台获取鼠标特征码.
     *                         11. "dx.mouse.raw.input"  有些窗口需要这个才可以正常操作鼠标.
     *                         12. "dx.mouse.input.lock.api2"  部分窗口在后台操作时，前台鼠标会移动,需要这个属性.
     *                         13. "dx.mouse.input.lock.api3"  部分窗口在后台操作时，前台鼠标会移动,需要这个属性.
     * @param keypad           键盘仿真模式 取值有以下几种
     *                         "normal" : 正常模式,平常我们用的前台键盘模式
     *                         "windows": Windows模式,采取模拟windows消息方式 同按键的后台插件.
     *                         dx模式,取值可以是以下任意组合. 组合采用"|"符号进行连接. 支持BindWindow中的缩写模式.比如dx代表" dx.public.active.api|dx.public.active.message| dx.keypad.state.api|dx.keypad.api|dx.keypad.input.lock.api"
     *                         1. "dx.keypad.input.lock.api" 此模式表示通过封锁系统API来锁定键盘输入接口.
     *                         2. "dx.keypad.state.api" 此模式表示通过封锁系统API来锁定键盘输入状态.
     *                         3. "dx.keypad.api" 此模式表示通过封锁系统API来模拟dx键盘输入.
     *                         4. "dx.keypad.raw.input"  有些窗口需要这个才可以正常操作键盘.
     * @param publicProperties 公共属性 dx模式共有
     *                         取值可以是以下任意组合. 组合采用"|"符号进行连接 这个值可以为空
     *                         1. "dx.public.active.api" 此模式表示通过封锁系统API来锁定窗口激活状态.  注意，部分窗口在此模式下会耗费大量资源慎用.
     *                         2. "dx.public.active.message" 此模式表示通过封锁系统消息来锁定窗口激活状态.  注意，部分窗口在此模式下会耗费大量资源慎用. 另外如果要让此模式生效，必须在绑定前，让绑定窗口处于激活状态,否则此模式将失效. 比如dm.SetWindowState hwnd,1 然后再绑定.
     *                         3.  "dx.public.disable.window.position" 此模式将锁定绑定窗口位置.不可与"dx.public.fake.window.min"共用.
     *                         4.  "dx.public.disable.window.size" 此模式将锁定绑定窗口,禁止改变大小. 不可与"dx.public.fake.window.min"共用.
     *                         5.  "dx.public.disable.window.minmax" 此模式将禁止窗口最大化和最小化,但是付出的代价是窗口同时也会被置顶. 不可与"dx.public.fake.window.min"共用.
     *                         6.  "dx.public.fake.window.min" 此模式将允许目标窗口在最小化状态时，仍然能够像非最小化一样操作.. 另注意，此模式会导致任务栏顺序重排，所以如果是多开模式下，会看起来比较混乱，建议单开使用，多开不建议使用. 同时此模式不是万能的,有些情况下最小化以后图色会不刷新或者黑屏.
     *                         7.  "dx.public.hide.dll" 此模式将会隐藏目标进程的大漠插件，避免被检测..另外使用此模式前，请仔细做过测试，此模式可能会造成目标进程不稳定，出现崩溃。
     *                         8.  "dx.public.active.api2" 此模式表示通过封锁系统API来锁定窗口激活状态. 部分窗口遮挡无法后台,需要这个属性.
     *                         9.  "dx.public.input.ime" 此模式是配合SendStringIme使用. 具体可以查看SendStringIme接口.
     *                         10  "dx.public.graphic.protect" 此模式可以保护dx图色不被恶意检测.同时对dx.keypad.api和dx.mouse.api也有保护效果. 这个参数可能会导致某些情况下目标图色失效.一般出现在场景重新加载的时候. 重新绑定会恢复.
     *                         11  "dx.public.disable.window.show" 禁止目标窗口显示,这个一般用来配合dx.public.fake.window.min来使用.
     *                         12  "dx.public.anti.api" 此模式可以突破部分窗口对后台的保护.
     *                         13  "dx.public.km.protect" 此模式可以保护dx键鼠不被恶意检测.最好配合dx.public.anti.api一起使用. 此属性可能会导致部分后台功能失效.
     *                         14   "dx.public.prevent.block"  绑定模式1 3 5 7 101 103下，可能会导致部分窗口卡死. 这个属性可以避免卡死.
     *                         15   "dx.public.ori.proc"  此属性只能用在模式0 1 2 3和101下. 有些窗口在不同的界面下(比如登录界面和登录进以后的界面)，键鼠的控制效果不相同. 那可以用这个属性来尝试让保持一致. 注意的是，这个属性不可以滥用，确保测试无问题才可以使用. 否则可能会导致后台失效.
     *                         16  "dx.public.down.cpu" 此模式可以配合DownCpu来降低目标进程CPU占用.  当图色方式降低CPU无效时，可以尝试此种方式. 需要注意的是，当使用此方式降低CPU时，会让图色方式降低CPU失效
     *                         17  "dx.public.focus.message" 当后台绑定后,后台无法正常在焦点窗口输入文字时,可以尝试加入此属性. 此属性会强制键盘消息发送到焦点窗口. 慎用此模式,此模式有可能会导致后台键盘在某些情况下失灵.
     *                         18  "dx.public.graphic.speed" 只针对图色中的dx模式有效.此模式会牺牲目标窗口的性能，来提高DX图色速度，尤其是目标窗口刷新很慢时，这个参数就很有用了.
     *                         19  "dx.public.memory" 让本对象突破目标进程防护,可以正常使用内存接口. 当用此方式使用内存接口时，内存接口的速度会取决于目标窗口的刷新率.
     *                         20  "dx.public.inject.super" 突破某些难以绑定的窗口. 此属性仅对除了模式0和2的其他模式有效.
     *                         21  "dx.public.hack.speed" 类似变速齿轮，配合接口HackSpeed使用
     *                         22  "dx.public.inject.c" 突破某些难以绑定的窗口. 此属性仅对除了模式0和2的其他模式有效.
     * @param mode             模式。取值有以下几种
     *                         0 : 推荐模式此模式比较通用，而且后台效果是最好的.
     *                         2 : 同模式0,如果模式0有崩溃问题，可以尝试此模式.  注意0和2模式，当主绑定(第一个绑定同个窗口的对象)绑定成功后，那么调用主绑定的线程必须一直维持,否则线程一旦推出,对应的绑定也会消失.
     *                         101 : 超级绑定模式. 可隐藏目标进程中的dm.dll.避免被恶意检测.效果要比dx.public.hide.dll好. 推荐使用.
     *                         103 : 同模式101，如果模式101有崩溃问题，可以尝试此模式.
     *                         11 : 需要加载驱动,适合一些特殊的窗口,如果前面的无法绑定，可以尝试此模式. 此模式不支持32位系统
     *                         13 : 需要加载驱动,适合一些特殊的窗口,如果前面的无法绑定，可以尝试此模式. 此模式不支持32位系统
     *                         <p>
     *                         需要注意的是: 模式101 103在大部分窗口下绑定都没问题。但也有少数特殊的窗口，比如有很多子窗口的窗口，对于这种窗口，在绑定时，一定要把鼠标指向一个可以输入文字的窗口，比如一个文本框，最好能激活这个文本框，这样可以保证绑定的成功.
     */
    public void bindWindowEx(long hwnd, String display, String mouse, String keypad, String publicProperties, int mode) {
        dmSoft.callAndCheckResultEq1("BindWindowEx", hwnd, display, mouse, keypad, publicProperties, mode);
    }

    /**
     * 降低目标窗口所在进程的CPU占用.
     * <p>
     * 注意: 此接口必须在绑定窗口成功以后调用，而且必须保证目标窗口可以支持dx.graphic.3d或者dx.graphic.3d.8或者dx.graphic.2d或者dx.graphic.2d.2或者dx.graphic.opengl或者dx.graphic.opengl.esv2方式截图，或者使用dx.public.down.cpu(仅限type为0).否则降低CPU无效.
     * 因为降低CPU是通过降低窗口刷新速度或者在系统消息循环增加延时来实现，所以注意，开启此功能以后会导致窗口刷新速度变慢.
     *
     * @param type 当取值为0时,rate取值范围大于等于0 ,这个值越大表示降低CPU效果越好
     *             当取值为1时,rate取值范围大于等于0,表示以固定的FPS来降低CPU. rate表示FPS.  并且这时不能有dx.public.down.cpu.
     * @param rate 取值取决于type. 为0表示关闭
     */
    public void downCpu(int type, int rate) {
        dmSoft.callAndCheckResultEq1("DownCpu", type, rate);
    }

    /**
     * 设置是否暂时关闭或者开启后台功能. 默认是开启.  一般用在前台切换，或者脚本暂停和恢复时，可以让用户操作窗口.
     * <p>
     * 注: 注意切换到前台以后,相当于dm_ret = dm.BindWindow(hwnd,"normal","normal","normal",0),图色键鼠全部是前台.
     * 如果你经常有频繁切换后台和前台的操作，推荐使用这个函数.
     * 同时要注意,如果有多个对象绑定了同个窗口，其中任何一个对象禁止了后台,那么其他对象后台也同样失效.
     *
     * @param enable 0 全部关闭(图色键鼠都关闭,也就是说图色,键鼠都是前台,但是如果有指定dx.public.active.message时，在窗口前后台切换时，这个属性会失效.)
     *               -1 只关闭图色.(也就是说图色是normal前台. 键鼠不变)
     *               1 开启(恢复原始状态)
     *               5 同0，也是全部关闭，但是这个模式下，就算窗口在前后台切换时，属性dx.public.active.message的效果也一样不会失效.
     */
    public void enableBind(int enable) {
        dmSoft.callAndCheckResultEq1("EnableBind", enable);
    }

    /**
     * 设置是否开启后台假激活功能. 默认是关闭. 一般用不到. 除非有人有特殊需求. 注意看注释.
     * <p>
     * 注: 此接口的含义并不是关闭或者开启窗口假激活功能(dx.public.active.api或者dx.public.active.message). 而是说有些时候，本来窗口没有激活并且在没有绑定的状态下，可以正常使用的功能，而在窗口绑定以后,并且窗口在非激活状态下,此时由于绑定的锁定导致无法使用. 那么，你就需要把你的部分代码用EnableFakeActive来保护起来。这样就让插件认为你的这段代码是在窗口激活状态下执行.
     * <p>
     * 另外，此函数开启以后，有可能会让前台影响到后台. 所以如果不是特殊情况，最好是关闭.  开启这个还会把已经锁定的键盘鼠标(LockInput)强制解锁.
     * 有些时候，有人会故意利用这个前台影响后台的作用，做类似同步器的软件，那这个函数就很有作用了.
     * <p>
     * 另外,还有一些窗口对消息检测比较严格,那么需要开启这个接口才可以后台操作,或者组合键操作.
     *
     * @param enable 是否开启
     */
    public void enableFakeActive(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableFakeActive", enable ? 1 : 0);
    }

    /**
     * 设置是否关闭绑定窗口所在进程的输入法.
     * <p>
     * 注: 此函数必须在绑定后调用才有效果.
     *
     * @param enable 是否开启
     */
    public void enableIme(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableIme", enable ? 1 : 0);
    }

    /**
     * 是否在使用dx键盘时开启windows消息.默认开启.
     * <p>
     * 注: 此接口必须在绑定之后才能调用。特殊时候使用.
     *
     * @param enable 是否开启
     */
    public void enableKeypadMsg(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableKeypadMsg", enable ? 1 : 0);
    }

    /**
     * 键盘消息发送补丁. 默认是关闭.
     * <p>
     * 注: 此接口必须在绑定之后才能调用。
     *
     * @param enable 是否开启
     */
    public void enableKeypadPatch(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableKeypadPatch", enable ? 1 : 0);
    }

    /**
     * 键盘消息采用同步发送模式.默认异步.
     * <p>
     * 注: 此接口必须在绑定之后才能调用。
     * 有些时候，如果是异步发送，如果发送动作太快,中间没有延时,有可能下个动作会影响前面的.
     * 而用同步就没有这个担心.
     *
     * @param enable       是否开启
     * @param timeoutMills 位是毫秒,表示同步等待的最大时间.
     */
    public void enableKeypadSync(boolean enable, long timeoutMills) {
        dmSoft.callAndCheckResultEq1("EnableKeypadSync", enable ? 1 : 0, timeoutMills);
    }

    /**
     * 是否在使用dx鼠标时开启windows消息.默认开启.
     * <p>
     * 注: 此接口必须在绑定之后才能调用。特殊时候使用.
     *
     * @param enable 是否开启
     */
    public void enableMouseMsg(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableMouseMsg", enable ? 1 : 0);
    }

    /**
     * 标消息采用同步发送模式.默认异步.
     * <p>
     * 注: 此接口必须在绑定之后才能调用。
     * 有些时候，如果是异步发送，如果发送动作太快,中间没有延时,有可能下个动作会影响前面的.
     * 而用同步就没有这个担心.
     *
     * @param enable       是否开启
     * @param timeoutMills 单位是毫秒,表示同步等待的最大时间.
     */
    public void enableMouseSync(boolean enable, long timeoutMills) {
        dmSoft.callAndCheckResultEq1("EnableMouseSync", enable ? 1 : 0, timeoutMills);
    }

    /**
     * 键盘动作模拟真实操作,点击延时随机.
     * <p>
     * 注: 此接口对KeyPress KeyPressChar KeyPressStr起作用。具体表现是键盘按下和弹起的间隔会在
     * 当前设定延时的基础上,上下随机浮动50%. 假如设定的键盘延时是100,那么这个延时可能就是50-150之间的一个值.
     * 设定延时的函数是 SetKeypadDelay
     *
     * @param enable 是否开启
     */
    public void enableRealKeypad(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableRealKeypad", enable ? 1 : 0);
    }

    /**
     * 鼠标动作模拟真实操作,带移动轨迹,以及点击延时随机.
     * <p>
     * 注: 此接口同样对LeftClick RightClick MiddleClick LeftDoubleClick起作用。具体表现是鼠标按下和弹起的间隔会在
     * 当前设定延时的基础上,上下随机浮动50%. 假如设定的鼠标延时是100,那么这个延时可能就是50-150之间的一个值.
     * 设定延时的函数是 SetMouseDelay
     *
     * @param enable          0 关闭模拟
     *                        1 开启模拟(直线模拟)
     *                        2 开启模拟(随机曲线,更接近真实)
     *                        3 开启模拟(小弧度曲线,弧度随机)
     *                        4 开启模拟(大弧度曲线,弧度随机)
     * @param mouseDelayMills 单位是毫秒. 表示在模拟鼠标移动轨迹时,每移动一次的时间间隔.这个值越大,鼠标移动越慢. 必须大于0,否则会失败.
     * @param mouseStep       表示在模拟鼠标移动轨迹时,每移动一次的距离. 这个值越大，鼠标移动越快速.
     */
    public void enableRealMouse(int enable, long mouseDelayMills, int mouseStep) {
        dmSoft.callAndCheckResultEq1("EnableRealMouse", enable, mouseDelayMills, mouseStep);
    }

    /**
     * 设置是否开启高速dx键鼠模式。 默认是关闭.
     * <p>
     * 注: 此函数开启的后果就是，所有dx键鼠操作将不会等待，适用于某些特殊的场合(比如避免窗口无响应导致宿主进程也卡死的问题).
     * EnableMouseSync和EnableKeyboardSync开启以后，此函数就无效了.
     * 此函数可能在部分窗口下会有副作用，谨慎使用!!
     *
     * @param enable 是否开启
     */
    public void enableSpeedDx(boolean enable) {
        dmSoft.callAndCheckResultEq1("EnableSpeedDx", enable ? 1 : 0);
    }

    /**
     * 强制解除绑定窗口,并释放系统资源.
     * <p>
     * 注: 此接口一般用在BindWindow和BindWindowEx中，使用了模式1 3 5 7或者属性dx.public.hide.dll后，在线程或者进程结束后，没有正确调用UnBindWindow而导致下次绑定无法成功时，可以先调用这个函数强制解除绑定，并释放资源，再进行绑定.
     * 此接口不可替代UnBindWindow. 只是用在非常时刻. 切记.
     * 一般情况下可以无条件的在BindWindow或者BindWindowEx之前调用一次此函数。保证此刻窗口处于非绑定状态.
     * 另外，需要注意的是,此函数只可以强制解绑在同进程绑定的窗口.  不可在不同的进程解绑别的进程绑定的窗口.(会产生异常)
     *
     * @param hwnd 需要强制解除绑定的窗口句柄.
     */
    public void forceUnBindWindow(long hwnd) {
        dmSoft.callAndCheckResultEq1("ForceUnBindWindow", hwnd);
    }

    /**
     * 获取当前对象已经绑定的窗口句柄. 无绑定返回0
     *
     * @return
     */
    public long getBindWindow() {
        return dmSoft.callForLong("GetBindWindow");
    }

    /**
     * 获取绑定窗口的fps. (即时fps,不是平均fps).
     * <p>
     * 要想获取fps,那么图色模式必须是dx模式的其中一种.  比如dx.graphic.3d  dx.graphic.opengl等.
     *
     * @return fps
     */
    public int getFps() {
        return (int) dmSoft.callForLong("GetFps");
    }

    /**
     * 对目标窗口设置加速功能(类似变速齿轮),必须在绑定参数中有dx.public.hack.speed时才会生效
     * <p>
     * 注意: 此接口必须在绑定窗口成功以后调用，而且必须有参数dx.public.hack.speed. 不一定对所有窗口有效,具体自己测试.
     *
     * @param rate 取值范围大于0. 默认是1.0 表示不加速，也不减速. 小于1.0表示减速,大于1.0表示加速. 精度为小数点后1位. 也就是说1.5 和 1.56其实是一样的.
     */
    public void hackSpeed(double rate) {
        dmSoft.callAndCheckResultEq1("HackSpeed", "rate");
    }

    /**
     * 判定指定窗口是否已经被后台绑定. (前台无法判定)
     *
     * @param hwnd 窗口句柄
     * @return false-没绑定,或者窗口不存在. true-已经绑定.
     */
    public boolean isBind(long hwnd) {
        long result = dmSoft.callForLong("IsBind", hwnd);
        return result == 1;
    }

    /**
     * 锁定指定窗口的图色数据(不刷新).
     * <p>
     * 注意: 此接口只对图色为dx.graphic.3d  dx.graphic.3d.8 dx.graphic.2d  dx.graphic.2d.2 dx.graphic.3d.10plus有效.
     *
     * @param lock 是否锁定
     */
    public void lockDisplay(boolean lock) {
        dmSoft.callAndCheckResultEq1("LockDisplay", lock ? 1 : 0);
    }

    /**
     * 禁止外部输入到指定窗口
     * <p>
     * 注意:此接口只针对dx键鼠. 普通键鼠无效.
     * 有时候，绑定为dx2 鼠标模式时(或者没有锁定鼠标位置或状态时)，在脚本处理过程中，在某个时候需要临时锁定外部输入，以免外部干扰，那么这个函数就非常有用.
     * 比如某个信息，需要鼠标移动到某个位置才可以获取，但这时，如果外部干扰，那么很可能就会获取失败，所以，这时候就很有必要锁定外部输入.
     * 当然，锁定完以后，记得要解除锁定，否则外部永远都无法输入了，除非解除了窗口绑定.
     *
     * @param lock 0关闭锁定
     *             1 开启锁定(键盘鼠标都锁定)
     *             2 只锁定鼠标
     *             3 只锁定键盘
     *             4 同1,但当您发现某些特殊按键无法锁定时,比如(回车，ESC等)，那就用这个模式吧. 但此模式会让SendString函数后台失效，或者采用和SendString类似原理发送字符串的其他3方函数失效.
     *             5同3,但当您发现某些特殊按键无法锁定时,比如(回车，ESC等)，那就用这个模式吧. 但此模式会让SendString函数后台失效，或者采用和SendString类似原理发送字符串的其他3方函数失效.
     */
    public void lockInput(int lock) {
        dmSoft.callAndCheckResultEq1("LockInput", lock);
    }

    /**
     * 设置前台鼠标在屏幕上的活动范围.
     * 注: 调用此函数后，一旦有窗口切换或者窗口移动的动作，那么限制立刻失效.
     * 如果想一直限制鼠标范围在指定的窗口客户区域，那么你需要启动一个线程，并且时刻监视当前活动窗口，然后根据情况调用此函数限制鼠标范围.
     *
     * @param rect 锁定的区域
     */
    public void lockMouseRect(Rect rect) {
        dmSoft.callAndCheckResultEq1("LockMouseRect", rect.x1(), rect.y1(), rect.x2(), rect.y2());
    }

    /**
     * 设置开启或者关闭系统的Aero效果. (仅对WIN7及以上系统有效)
     * <p>
     * 注: 如果您发现当图色后台为dx2 gdi dx3时，如果有发现目标窗口刷新速度过慢,那可以考虑关闭系统Aero. (当然这仅仅是可能的原因)
     *
     * @param enable 是否开启
     */
    public void setAero(boolean enable) {
        dmSoft.callAndCheckResultEq1("SetAero", enable ? 1 : 0);
    }

    /**
     * 设置dx截图最长等待时间。内部默认是3000毫秒. 一般用不到调整这个.
     * <p>
     * 注: 此接口仅对图色为dx.graphic.3d   dx.graphic.3d.8  dx.graphic.2d   dx.graphic.2d.2有效. 其他图色模式无效.
     * 默认情况下，截图需要等待一个延时，超时就认为截图失败. 这个接口可以调整这个延时. 某些时候或许有用.比如当窗口图色卡死(这时获取图色一定都是超时)，并且要判断窗口卡死，那么这个设置就很有用了。
     *
     * @param mills 等待时间，单位是毫秒。 注意这里不能设置的过小，否则可能会导致截图失败,从而导致图色函数和文字识别失败.
     */
    public void setDisplayDelay(long mills) {
        dmSoft.callAndCheckResultEq1("SetDisplayDelay", mills);
    }

    /**
     * 设置opengl图色模式的强制刷新窗口等待时间. 内置为400毫秒.
     * <p>
     * 注: 此接口仅对图色为dx.graphic.opengl有效. 其他图色模式无效.
     * 默认情况下，openg截图时，如果对应的窗口处于不刷新的状态,那么我们的所有图色接口都会无法截图,从而超时导致失效。
     * 所以特意设置这个接口，如果截图的时间超过此接口设置的时间,那么插件会对绑定的窗口强制刷新,从而让截图成功.
     * 但是强制刷新窗口是有代价的，会造成窗口闪烁。
     * 如果您需要截图的窗口，刷新非常频繁，那么一般用不到强制刷新，所以可以用这个接口把等待时间设置大一些，从而避免窗口闪烁.
     * 反之,如果您需要截图的窗口偶尔才刷新一次(比如按某个按钮，才刷新一次),那么您就需要用这个接口把等待时间设置小一些，从而提高图色函数的效率，但代价就是窗口可能会闪烁.
     * 当这个接口设置的值超过SetDisplayDelay设置的值(默认是3000毫秒)时,那么opengl截图的方式就退化到老版本(大概是6.1540版本)的模式.(也就是不会强制刷新的版本).
     * 如果您发现你的程序截图会截取到以前的图片,那么建议把此值加大(建议值2000).
     * 如果您发现你的程序偶尔会闪烁,导致窗口出现白色区域,那么可以尝试把此值设置为大于SetDisplayDelay的值(默认是3000毫秒),这样可以彻底杜绝刷新.
     *
     * @param mills 等待时间，单位是毫秒。 这个值越小,强制刷新的越频繁，相应的窗口可能会导致闪烁.
     */
    public void setDisplayRefreshDelay(long mills) {
        dmSoft.callAndCheckResultEq1("SetDisplayRefreshDelay", mills);
    }

    /**
     * 设置当前对象用于输入的对象. 结合图色对象和键鼠对象,用一个对象完成操作.
     * <p>
     * 注: 此接口用于特殊用途.
     * 比如雷电模拟器. 最里层的窗口是一个64位窗口. 绑定这个窗口的图色可以用来图色后台. 但是这个窗口无法进行键鼠后台.
     * 能够键鼠窗口后台的是这个窗口的上一层32位窗口.但这个32位窗口在某些情况下图色会出问题.
     * 所以比较好的解决办法是创建2个对象. 一个绑定64位的窗口，用来进行图色使用. 另一个绑定32位的窗口,用来进行键鼠操作.
     * 但是如果对于写好的代码来说,更改起来很麻烦. 因为大部分情况下一个对象就够用了.为了让代码不用大幅度更改,就加了这样一个接口.
     * 让进行图色绑定的那个对象和进行键鼠操作的那个对象结合起来. 这样只用操作一个图色绑定的对象就行了.
     * 这里要注意的是,如果2个对象对应的窗口不是一个进程,那么绑定参数上没什么要求. 如果是一个进程,那么必须保证只有一个对象能够使用注入的参数.否则会引发冲突导致崩溃.
     * 还有rx和ry的具体含义. 解释如下:
     * 一般来说,我们调用MoveTo或者MoveToEx时,传递进来的x和y坐标都是来自于图色窗口,但是键鼠操作的那个窗口是另一个窗口. 如果这2个窗口左上角是重合的,那么无所谓
     * rx和ry就是0. 比如我们这里的雷电模拟器等窗口.
     * 但是如果不重合,那么我们传递给MoveTo或者MoveToEx的x和y就和键鼠操作的窗口的x和y不对应. 所以就必须从图色的x,y减去两个窗口的左上角偏移,这样才能对应键鼠操作的窗口.
     * 一般来说,rx和ry都是0. 可能有极少数有这种不为0的特例(我是暂时没发现). 这里的rx和ry必须是键鼠操作的窗口左上角减去图色窗口的左上角,不能是反的.
     * 另外在解绑时,会自动重置. 即图色窗口的对象自动和键鼠窗口的对象脱离.
     * 需要注意的是,因为两个对象进行了结合,那么就要确保两个对象的生命周期必须是一致的. 尤其千万不能在图色窗口操作时,键鼠对象被释放了. 那么会导致程序的崩溃.
     *
     * @param dmId 接口GetId的返回值
     * @param rx   两个对象绑定的窗口的左上角坐标的x偏移. 是用dm_id对应的窗口的左上角x坐标减去当前窗口左上角坐标的x坐标. 一般是0
     * @param ry   两个对象绑定的窗口的左上角坐标的y偏移. 是用dm_id对应的窗口的左上角y坐标减去当前窗口左上角坐标的y坐标. 一般是0
     */
    public void setInputDm(long dmId, int rx, int ry) {
        dmSoft.callAndCheckResultEq1("SetInputDm", dmId, rx, ry);
    }

    /**
     * 在不解绑的情况下,切换绑定窗口.(必须是同进程窗口)
     * <p>
     * 注:此函数一般用在绑定以后，窗口句柄改变了的情况。如果必须不解绑，那么此函数就很有用了。
     *
     * @param hwnd 需要切换过去的窗口句柄
     */
    public void switchBindWindow(long hwnd) {
        dmSoft.callAndCheckResultEq1("SwitchBindWindow", hwnd);
    }

    /**
     * 解除绑定窗口,并释放系统资源.一般在OnScriptExit调用
     */
    public void unBindWindow() {
        dmSoft.callAndCheckResultEq1("UnBindWindow");
    }
}
