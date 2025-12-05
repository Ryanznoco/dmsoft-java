package cn.com.qjun.dmsoft.functions;

import com.jacob.activeX.ActiveXComponent;
import lombok.NonNull;

/**
 * 其他操作（杂项）
 *
 * @author RenQiang
 * @date 2024/2/14
 */
public class DmOtherFunctions extends AbstractDmFunctions {

    public DmOtherFunctions(@NonNull ActiveXComponent dmSoft) {
        super(dmSoft);
    }

    /**
     * 激活指定窗口所在进程的输入法.
     *
     * @param hwnd        窗口句柄
     * @param inputMethod 输入法名字。具体输入法名字对应表查看注册表中以下位置:
     *                    HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Keyboard Layouts
     *                    下面的每一项下的Layout Text的值就是输入法名字
     *                    比如 "中文 - QQ拼音输入法"
     *                    以此类推.
     */
    public void activeInputMethod(long hwnd, String inputMethod) {
        callExpect1("ActiveInputMethod", FunctionArgs.of(hwnd, inputMethod));
    }

    /**
     * 检测指定窗口所在线程输入法是否开启
     *
     * @param hwnd        窗口句柄
     * @param inputMethod 输入法名字。具体输入法名字对应表查看注册表中以下位置:
     *                    HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Keyboard Layouts
     *                    下面的每一项下的Layout Text的值就是输入法名字
     *                    比如 "中文 - QQ拼音输入法"
     *                    以此类推.
     * @return 是否开启
     */
    public boolean checkInputMethod(long hwnd, String inputMethod) {
        return callForBool("CheckInputMethod", FunctionArgs.of(hwnd, inputMethod));
    }

    /**
     * 检测是否可以进入临界区,如果可以返回1,否则返回0. 此函数如果返回1，则调用对象就会占用此互斥信号量,直到此对象调用LeaveCri,否则不会释放.
     * 注意:如果调用对象在释放时，会自动把本对象占用的互斥信号量释放.
     * <p>
     * 关于如何前台多开,点这里.
     *
     * @return false-不可以 true-已经进入临界区
     */
    public boolean enterCri() {
        return callForBool("EnterCri", FunctionArgs.of());
    }

    /**
     * 执行指定的CMD指令,并返回cmd的输出结果.
     *
     * @param cmd          需要执行的CMD指令. 比如"dir"
     * @param currentDir   执行此cmd命令时,所在目录. 如果为空，表示使用当前目录. 比如""或者"c:"
     * @param timeoutMills 超时设置,单位是毫秒. 0表示一直等待. 大于0表示等待指定的时间后强制结束,防止卡死.
     * @return cmd指令的执行结果.  返回空字符串表示执行失败.
     */
    public String executeCmd(String cmd, String currentDir, long timeoutMills) {
        return callForString("ExecuteCmd", FunctionArgs.of(cmd, currentDir, timeoutMills));
    }

    /**
     * 检测系统中是否安装了指定输入法
     *
     * @param inputMethod 输入法名字。具体输入法名字对应表查看注册表中以下位置:
     *                    HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Keyboard Layouts
     *                    下面的每一项下的Layout Text的值就是输入法名字
     *                    比如 "中文 - QQ拼音输入法"
     *                    以此类推.
     * @return 是否安装
     */
    public boolean findInputMethod(String inputMethod) {
        return callForBool("FindInputMethod", FunctionArgs.of(inputMethod));
    }

    /**
     * 初始化临界区,必须在脚本开头调用一次.这个函数是强制把插件内的互斥信号量归0,无论调用对象是否拥有此信号量.
     * <p>
     * 关于如何前台多开,点这里.
     */
    public void initCri() {
        callExpect1("InitCri", FunctionArgs.of());
    }

    /**
     * 和EnterCri对应,离开临界区。此函数是释放调用对象占用的互斥信号量.
     * 注意，只有调用对象占有了互斥信号量，此函数才会有作用. 否则没有任何作用. 如果调用对象在释放时，会自动把本对象占用的互斥信号量释放.
     * <p>
     * 关于如何前台多开,点这里.
     */
    public void leaveCri() {
        callExpect1("LeaveCri", FunctionArgs.of());
    }

    /**
     * 强制降低对象的引用计数。此接口为高级接口，一般使用在高级语言，比如E vc等.
     * <p>
     * 每个对象内部，系统都会维护一个引用计数, 当计数为0时，才可以释放对象。 由于在某些高级语言中使用时，会由于各种原因，导致对象的引用计数出现异常，最后导致对象无法被释放.
     * 比如一个对象在使用过程中（比如调用了对象的某个接口过程中），突然所在线程被强制结束，导致对象引用计数无法被释放. 然后这个对象就永远无法被释放掉了。 最后导致资源泄漏.
     * 还有可能是，由于不正确的复制了对象，但复制过后的对象又没有正确的释放，也会导致计数异常。
     * 为了让对象能够正确的被释放，提供这个接口，强制释放引用计数。一般用在对象释放之前，并且此时不能有任何线程去调用此对象的任何接口。
     * 具体的使用例子，在最新版本的类库生成工具，生成以后有相对应平台的多线程模板，里面有详细介绍.
     */
    public void releaseRef() {
        callExpect1("ReleaseRef", FunctionArgs.of());
    }

    /**
     * 设置当前对象的退出线程标记，之后除了调用此接口的线程之外，调用此对象的任何接口的线程会被强制退出.
     * 此接口为高级接口，一般用在高级语言,比如e vc等.
     * <p>
     * 一般我们在写多线程程序时，如何正确的结束线程是个难题.  脚本语言一般没这种烦恼，但高级语言比如E vc等就很麻烦.
     * 一般来说，让线程自然的结束，那是最好的结果. 但是事实上，高级语言中很难做到。 因为调用的函数是一层套一层，很难返回.
     * 所以，我们退而求其次，让线程自己调用退出，这样虽然也有一定的资源泄漏（主要是线程中创建的局部变量，比如类对象等),但总比强制结束线程要好的多.
     * 所以，我们这个接口的目的也很明显，设置以后，除了调用线程之外的线程，如果调用到插件，那么线程就自己退出了。
     * 具体的使用例子，在最新版本的类库生成工具，生成以后有相对应平台的多线程模板，里面有详细介绍.
     *
     * @param mode 1和2都为开启标记,0为关闭标记。 1和2的区别是,1会解绑当前对象的绑定,2不会.
     */
    public void setExitThread(int mode) {
        callExpect1("SetExitThread", FunctionArgs.of(mode));
    }
}
