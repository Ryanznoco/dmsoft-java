package cn.com.qjun.dmsoft.functions;

import com.jacob.activeX.ActiveXComponent;
import lombok.NonNull;

/**
 * 内存相关操作
 *
 * @author RenQiang
 * @date 2024/2/14
 */
public class DmMemoryFunctions extends AbstractDmFunctions {

    public DmMemoryFunctions(@NonNull ActiveXComponent dmSoft) {
        super(dmSoft);
    }

    /**
     * 把双精度浮点数转换成二进制形式.
     *
     * @param value 需要转化的双精度浮点数
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口.
     */
    public String doubleToData(double value) {
        return callForString("DoubleToData", FunctionArgs.of(value));
    }

    /**
     * 搜索指定的二进制数据,默认步长是1.如果要定制步长，请用FindDataEx
     * <p>
     * DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param data         要搜索的二进制数据 以字符串的形式描述比如"00 01 23 45 67 86 ab ce f1"等.
     *                     这里也可以支持模糊查找,用??来代替单个字节. 比如"00 01 ?? ?? 67 86 ?? ce f1"等.
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findData(long hwnd, String addressRange, String data) {
        return callForString("FindData", FunctionArgs.of(hwnd, addressRange, data));
    }

    /**
     * 搜索指定的二进制数据.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param data         要搜索的二进制数据 以字符串的形式描述比如"00 01 23 45 67 86 ab ce f1"等
     *                     这里也可以支持模糊查找,用??来代替单个字节. 比如"00 01 ?? ?? 67 86 ?? ce f1"等.
     * @param step         搜索步长.
     * @param multiThread  表示是否开启多线程查找.  0不开启，1开启.
     *                     开启多线程查找速度较快，但会耗费较多CPU资源. 不开启速度较慢，但节省CPU.
     * @param fastScan     1 表示开启快速扫描(略过只读内存)  0表示所有内存类型全部扫描.
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findDataEx(long hwnd, String addressRange, String data, int step, boolean multiThread, boolean fastScan) {
        return callForString("FindDataEx", FunctionArgs.of(hwnd, addressRange, data, step, multiThread, fastScan));
    }

    /**
     * 搜索指定的双精度浮点数,默认步长是1.如果要定制步长，请用FindDoubleEx
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param minValue     搜索的双精度数值最小值
     * @param maxValue     搜索的双精度数值最大值
     *                     最终搜索的数值大与等于double_value_min,并且小于等于double_value_max
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findDouble(long hwnd, String addressRange, double minValue, double maxValue) {
        return callForString("FindDouble", FunctionArgs.of(hwnd, addressRange, minValue, maxValue));
    }

    /**
     * 搜索指定的双精度浮点数.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param minValue     搜索的双精度数值最小值
     * @param maxValue     搜索的双精度数值最大值
     *                     最终搜索的数值大与等于double_value_min,并且小于等于double_value_max
     * @param step         搜索步长.
     * @param multiThread  表示是否开启多线程查找.  0不开启，1开启.
     *                     开启多线程查找速度较快，但会耗费较多CPU资源. 不开启速度较慢，但节省CPU.
     * @param fastScan     1 表示开启快速扫描(略过只读内存)  0表示所有内存类型全部扫描.
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findDoubleEx(long hwnd, String addressRange, double minValue, double maxValue, int step, boolean multiThread, boolean fastScan) {
        return callForString("FindDoubleEx", FunctionArgs.of(hwnd, addressRange, minValue, maxValue, step, multiThread, fastScan));
    }

    /**
     * 搜索指定的单精度浮点数,默认步长是1.如果要定制步长，请用FindFloatEx
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param minValue     搜索的双精度数值最小值
     * @param maxValue     搜索的双精度数值最大值
     *                     最终搜索的数值大与等于double_value_min,并且小于等于double_value_max
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findFloat(long hwnd, String addressRange, float minValue, float maxValue) {
        return callForString("FindFloat", FunctionArgs.of(hwnd, addressRange, minValue, maxValue));
    }

    /**
     * 搜索指定的单精度浮点数.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param minValue     搜索的双精度数值最小值
     * @param maxValue     搜索的双精度数值最大值
     *                     最终搜索的数值大与等于double_value_min,并且小于等于double_value_max
     * @param step         搜索步长.
     * @param multiThread  表示是否开启多线程查找.  0不开启，1开启.
     *                     开启多线程查找速度较快，但会耗费较多CPU资源. 不开启速度较慢，但节省CPU.
     * @param fastScan     1 表示开启快速扫描(略过只读内存)  0表示所有内存类型全部扫描.
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findFloatEx(long hwnd, String addressRange, float minValue, float maxValue, int step, boolean multiThread, boolean fastScan) {
        return callForString("FindFloatEx", FunctionArgs.of(hwnd, addressRange, minValue, maxValue, step, multiThread, fastScan));
    }

    /**
     * 搜索指定的整数,默认步长是1.如果要定制步长，请用FindIntEx
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param minValue     搜索的双精度数值最小值
     * @param maxValue     搜索的双精度数值最大值
     *                     最终搜索的数值大与等于double_value_min,并且小于等于double_value_max
     * @param type         搜索的整数类型,取值如下
     *                     0 : 32位
     *                     1 : 16 位
     *                     2 : 8位
     *                     3 : 64位
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findInt(long hwnd, String addressRange, long minValue, long maxValue, int type) {
        return callForString("FindInt", FunctionArgs.of(hwnd, addressRange, minValue, maxValue, type));
    }

    /**
     * 搜索指定的整数.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param minValue     搜索的双精度数值最小值
     * @param maxValue     搜索的双精度数值最大值
     *                     最终搜索的数值大与等于double_value_min,并且小于等于double_value_max
     * @param type         搜索的整数类型,取值如下
     *                     0 : 32位
     *                     1 : 16 位
     *                     2 : 8位
     *                     3 : 64位
     * @param step         搜索步长.
     * @param multiThread  表示是否开启多线程查找.  0不开启，1开启.
     *                     开启多线程查找速度较快，但会耗费较多CPU资源. 不开启速度较慢，但节省CPU.
     * @param fastScan     1 表示开启快速扫描(略过只读内存)  0表示所有内存类型全部扫描.
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findIntEx(long hwnd, String addressRange, long minValue, long maxValue, int type, int step, boolean multiThread, boolean fastScan) {
        return callForString("FindIntEx", FunctionArgs.of(hwnd, addressRange, minValue, maxValue, type, step, multiThread, fastScan));
    }

    /**
     * 搜索指定的字符串,默认步长是1.如果要定制步长，请用FindStringEx
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param value        搜索的字符串
     * @param type         搜索的字符串类型,取值如下
     *                     0 : Ascii字符串
     *                     1 : Unicode字符串
     *                     2 : UTF8字符串
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findString(long hwnd, String addressRange, String value, int type) {
        return callForString("FindString", FunctionArgs.of(hwnd, addressRange, value, type));
    }

    /**
     * 搜索指定的字符串.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd         指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addressRange 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描)
     *                     如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     *                     "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param value        搜索的字符串
     * @param type         搜索的字符串类型,取值如下
     *                     0 : Ascii字符串
     *                     1 : Unicode字符串
     *                     2 : UTF8字符串
     * @param step         搜索步长.
     * @param multiThread  表示是否开启多线程查找.  0不开启，1开启.
     *                     开启多线程查找速度较快，但会耗费较多CPU资源. 不开启速度较慢，但节省CPU.
     * @param fastScan     1 表示开启快速扫描(略过只读内存)  0表示所有内存类型全部扫描.
     * @return 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String findStringEx(long hwnd, String addressRange, String value, int type, int step, boolean multiThread, boolean fastScan) {
        return callForString("FindStringEx", FunctionArgs.of(hwnd, addressRange, value, type, step, multiThread, fastScan));
    }

    /**
     * 把单精度浮点数转换成二进制形式.
     *
     * @param value 需要转化的单精度浮点数
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口.
     */
    public String floatToData(float value) {
        return callForString("FloatToData", FunctionArgs.of(value));
    }

    /**
     * 释放指定进程的不常用内存
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     */
    public void freeProcessMemory(long hwnd) {
        callExpect1("FreeProcessMemory", FunctionArgs.of(hwnd));
    }

    /**
     * 获取指定窗口所在进程的启动命令行
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @return 读取到的启动命令行
     */
    public String getCommandLine(long hwnd) {
        return callForString("GetCommandLine", FunctionArgs.of(hwnd));
    }

    /**
     * 根据指定的窗口句柄，来获取对应窗口句柄进程下的指定模块的基址
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd   窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param module 模块名
     * @return 模块的基址
     */
    public long getModuleBaseAddr(long hwnd, String module) {
        return callForLong("GetModuleBaseAddr", FunctionArgs.of(hwnd, module));
    }

    /**
     * 根据指定的窗口句柄，来获取对应窗口句柄进程下的指定模块的大小
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd   窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param module 模块名
     * @return 模块的大小
     */
    public long getModuleSize(long hwnd, String module) {
        return callForLong("GetModuleSize", FunctionArgs.of(hwnd, module));
    }

    /**
     * 根据指定的目标模块地址,获取目标窗口(进程)内的导出函数地址.
     *
     * @param hwnd        窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param baseAddress 目标模块地址,比如user32.dll的地址,可以通过GetModuleBaseAddr来获取.
     * @param funName     需要获取的导出函数名.  比如"SetWindowTextA".
     * @return 获取的地址. 如果失败返回0
     */
    public long getRemoteApiAddress(long hwnd, long baseAddress, String funName) {
        return callForLong("GetRemoteApiAddress", FunctionArgs.of(hwnd, baseAddress, funName));
    }

    /**
     * 把整数转换成二进制形式.
     *
     * @param value 需要转化的整型数
     * @param type  取值如下:
     *              0: 4字节整形数 (一般都选这个)
     *              1: 2字节整形数
     *              2: 1字节整形数
     *              3: 8字节整形数
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口.
     */
    public String intToData(long value, int type) {
        return callForString("IntToData", FunctionArgs.of(value, type));
    }

    /**
     * 根据指定pid打开进程，并返回进程句柄.
     *
     * @param pid 进程pid
     * @return 进程句柄, 可用于进程相关操作(读写操作等),记得操作完成以后，自己调用CloseHandle关闭句柄.
     */
    public long openProcess(long pid) {
        return callForLong("OpenProcess", FunctionArgs.of(pid));
    }

    /**
     * 读取指定地址的二进制数据
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     * 如果要读取的数据长度过长，比如几十K的数据，由于COM组件的限制，可能无法返回如此长的字符串. 解决办法是分批读取.
     *
     * @param hwnd    窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param address 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *                模块名必须用<>符号来圈起来
     *                例如:
     *                1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *                2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *                3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *                4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *                5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *                总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param length  二进制数据的长度
     * @return 读取到的数值, 以16进制表示的字符串 每个字节以空格相隔 比如"12 34 56 78 ab cd ef"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String readData(long hwnd, String address, int length) {
        return callForString("ReadData", FunctionArgs.of(hwnd, address, length));
    }

    /**
     * 读取指定地址的二进制数据
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     * 如果要读取的数据长度过长，比如几十K的数据，由于COM组件的限制，可能无法返回如此长的字符串. 解决办法是分批读取.
     *
     * @param hwnd    窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param address 地址
     * @param length  二进制数据的长度
     * @return 读取到的数值, 以16进制表示的字符串 每个字节以空格相隔 比如"12 34 56 78 ab cd ef"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String readDataAddr(long hwnd, long address, int length) {
        return callForString("ReadDataAddr", FunctionArgs.of(hwnd, address, length));
    }

    /**
     * 读取指定地址的二进制数据,只不过返回的是内存地址,而不是字符串.适合高级用户.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     * 需要注意的是,调用此接口获取的数据指针保存在当前对象中,到下次调用此接口时,内部就会释放.
     * 哪怕是转成字节集,这个地址也还是在此字节集中使用. 如果您要此地址一直有效，那么您需要自己拷贝字节集到自己的字节集中.
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 地址
     * @param len  二进制数据的长度
     * @return 读取到的数据指针. 返回0表示读取失败.
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public long readDataAddrToBin(long hwnd, long addr, int len) {
        return callForLong("ReadDataAddrToBin", FunctionArgs.of(hwnd, addr, len));
    }

    /**
     * 读取指定地址的二进制数据,只不过返回的是内存地址,而不是字符串.适合高级用户.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     * 需要注意的是,调用此接口获取的数据指针保存在当前对象中,到下次调用此接口时,内部就会释放.
     * 哪怕是转成字节集,这个地址也还是在此字节集中使用. 如果您要此地址一直有效，那么您需要自己拷贝字节集到自己的字节集中.
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param len  二进制数据的长度
     * @return 读取到的数据指针. 返回0表示读取失败.
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public long readDataToBin(long hwnd, String addr, int len) {
        return callForLong("ReadDataToBin", FunctionArgs.of(hwnd, addr, len));
    }

    /**
     * 读取指定地址的双精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @return 读取到的数值
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public double readDouble(long hwnd, String addr) {
        return callForDouble("ReadDouble", FunctionArgs.of(hwnd, addr));
    }

    /**
     * 读取指定地址的双精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 地址
     * @return 读取到的数值
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public double readDoubleAddr(long hwnd, long addr) {
        return callForDouble("ReadDoubleAddr", FunctionArgs.of(hwnd, addr));
    }

    /**
     * 读取指定地址的单精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @return 读取到的数值
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public float readFloat(long hwnd, String addr) {
        return callForFloat("ReadFloat", FunctionArgs.of(hwnd, addr));
    }

    /**
     * 读取指定地址的单精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 地址
     * @return 读取到的数值
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public float readFloatAddr(long hwnd, long addr) {
        return callForFloat("ReadFloatAddr", FunctionArgs.of(hwnd, addr));
    }

    /**
     * 读取指定地址的整数数值，类型可以是8位，16位  32位 或者64位
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type 整数类型,取值如下
     *             0 : 32位有符号
     *             1 : 16 位有符号
     *             2 : 8位有符号
     *             3 : 64位
     *             4 : 32位无符号
     *             5 : 16位无符号
     *             6 : 8位无符号
     * @return 读取到的数值
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public long readInt(long hwnd, String addr, int type) {
        return callForLong("ReadInt", FunctionArgs.of(hwnd, addr, type));
    }

    /**
     * 读取指定地址的整数数值，类型可以是8位，16位 32位 或者64位
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type 整数类型,取值如下
     *             0 : 32位有符号
     *             1 : 16 位有符号
     *             2 : 8位有符号
     *             3 : 64位
     *             4 : 32位无符号
     *             5 : 16位无符号
     *             6 : 8位无符号
     * @return 读取到的数值
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public long readIntAddr(long hwnd, long addr, int type) {
        return callForLong("ReadIntAddr", FunctionArgs.of(hwnd, addr, type));
    }

    /**
     * 读取指定地址的字符串，可以是GBK字符串或者是Unicode字符串.(必须事先知道内存区的字符串编码方式)
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type 字符串类型,取值如下
     *             0 : GBK字符串
     *             1 : Unicode字符串
     *             2 : UTF8字符串
     * @param len  需要读取的字节数目.如果为0，则自动判定字符串长度.
     * @return 读取到的字符串
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String readString(long hwnd, String addr, int type, int len) {
        return callForString("ReadString", FunctionArgs.of(hwnd, addr, type, len));
    }

    /**
     * 读取指定地址的字符串，可以是GBK字符串或者是Unicode字符串.(必须事先知道内存区的字符串编码方式)
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 地址
     * @param type 字符串类型,取值如下
     *             0 : GBK字符串
     *             1 : Unicode字符串
     *             2 : UTF8字符串
     * @param len  需要读取的字节数目.如果为0，则自动判定字符串长度.
     * @return 读取到的字符串
     * 如果要想知道函数是否执行成功，请查看GetLastError函数.
     */
    public String readStringAddr(long hwnd, long addr, int type, int len) {
        return callForString("ReadStringAddr", FunctionArgs.of(hwnd, addr, type, len));
    }

    /**
     * 设置是否把所有内存查找接口的结果保存入指定文件.
     * <p>
     * 注: 部分高级语言无法接纳FindXXX 接口返回的超长字符串，那么需要用这个函数转存入文件,然后再读取分析处理.
     * 同时，设置了此文件后，那么当下次调用FindXXX接口传入的地址参数时，并且地址参数不是范围参数,那么地址参数会从设置的文件中读取. 如果是范围参数,那么插件不会从设置的文件读取,会认为是首次查找.因为部分高级语言对参数的接收也有长度限制，无法接收超长字符串.
     *
     * @param file 设置要保存的搜索结果文件名. 如果为空字符串表示取消此功能
     */
    public void setMemoryFindResultToFile(String file) {
        callExpect1("SetMemoryFindResultToFile", FunctionArgs.of(file));
    }

    /**
     * 设置是否把所有内存接口函数中的窗口句柄当作进程ID,以支持直接以进程ID来使用内存接口.
     *
     * @param enable 取值如下
     *               0 : 关闭  1 : 开启
     */
    public void setMemoryHwndAsProcessId(boolean enable) {
        callExpect1("SetMemoryHwndAsProcessId", FunctionArgs.of(enable));
    }

    /**
     * 把字符串转换成二进制形式.
     *
     * @param value 需要转化的字符串
     * @param type  取值如下:
     *              0: 返回Ascii表达的字符串
     *              1: 返回Unicode表达的字符串
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口.
     */
    public String stringToData(String value, int type) {
        return callForString("StringToData", FunctionArgs.of(value, type));
    }

    /**
     * 根据指定的PID，强制结束进程.
     * <p>
     * 注:另外DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param pid 进程ID.
     */
    public void terminateProcess(long pid) {
        callExpect1("TerminateProcess", FunctionArgs.of(pid));
    }

    /**
     * 根据指定的PID，强制结束进程以及此进程创建的所有子进程.
     * <p>
     * 注:另外DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param pid 进程ID.
     */
    public void terminateProcessTree(long pid) {
        callExpect1("TerminateProcessTree", FunctionArgs.of(pid));
    }

    /**
     * 在指定的窗口所在进程分配一段内存.
     * <p>
     * 注:如果正常方式无法分配内存,可以尝试配合DmGuard中的memory护盾,突破部分窗口内存保护。
     * 用此函数分配的内存，必须用VirtualFreeEx来释放,以免目标进程内存泄漏.
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 预期的分配地址。 如果是0表示自动分配，否则就尝试在此地址上分配内存.
     * @param size 需要分配的内存大小.
     * @param type 需要分配的内存类型，取值如下:
     *             0 : 可读可写可执行
     *             1 : 可读可执行，不可写
     *             2 : 可读可写,不可执行
     * @return 分配的内存地址，如果是0表示分配失败.
     */
    public long virtualAllocEx(long hwnd, long addr, int size, int type) {
        return callForLong("VirtualAllocEx", FunctionArgs.of(hwnd, addr, size, type));
    }

    /**
     * 释放用VirtualAllocEx分配的内存.
     * <p>
     * 注:如果正常方式无法分配内存,可以尝试配合DmGuard中的memory护盾,突破部分窗口内存保护。
     * 用此函数分配的内存，必须用VirtualFreeEx来释放,以免目标进程内存泄漏.
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr VirtualAllocEx返回的地址
     */
    public void virtualFreeEx(long hwnd, long addr) {
        callExpect1("VirtualFreeEx", FunctionArgs.of(hwnd, addr));
    }

    /**
     * 修改指定的窗口所在进程的地址的读写属性,修改为可读可写可执行.
     * <p>
     * 注:如果正常方式无法修改内存的读写属性,可以尝试配合DmGuard中的memory护盾,突破部分窗口内存保护。
     *
     * @param hwnd       窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr       需要修改的地址
     * @param size       需要修改的地址大小.
     * @param type       修改的地址读写属性类型，取值如下:
     *                   0 : 可读可写可执行,此时old_protect参数无效
     *                   1 : 修改为old_protect指定的读写属性
     * @param oldProtect 指定的读写属性
     * @return 0 : 失败
     * 1 : 修改之前的读写属性
     */
    public int virtualProtectEx(long hwnd, long addr, int size, int type, int oldProtect) {
        return (int) callForLong("VirtualProtectEx", FunctionArgs.of(hwnd, addr, size, type, oldProtect));
    }

    /**
     * 获取指定窗口，指定地址的内存属性.
     * <p>
     * 注:如果正常方式无法修改内存的读写属性,可以尝试配合DmGuard中的memory护盾,突破部分窗口内存保护。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 需要查询的地址
     * @param pmbi 这是一个地址,指向的内容是MEMORY_BASIC_INFORMATION32或者MEMORY_BASIC_INFORMATION64.
     *             取决于要查询的进程是32位还是64位. 这个地址可以为0,忽略这个参数.
     *             下面是这2个结构体在vc下的定义:
     *             typedef struct _MEMORY_BASIC_INFORMATION32 {
     *             DWORD BaseAddress;
     *             DWORD AllocationBase;
     *             DWORD AllocationProtect;
     *             DWORD RegionSize;
     *             DWORD State;
     *             DWORD Protect;
     *             DWORD Type;
     *             } MEMORY_BASIC_INFORMATION32, *PMEMORY_BASIC_INFORMATION32;
     *             typedef struct DECLSPEC_ALIGN(16) _MEMORY_BASIC_INFORMATION64 {
     *             ULONGLONG BaseAddress;
     *             ULONGLONG AllocationBase;
     *             DWORD     AllocationProtect;
     *             DWORD     __alignment1;
     *             ULONGLONG RegionSize;
     *             DWORD     State;
     *             DWORD     Protect;
     *             DWORD     Type;
     *             DWORD     __alignment2;
     *             } MEMORY_BASIC_INFORMATION64, *PMEMORY_BASIC_INFORMATION64;
     * @return 查询的结果以字符串形式.  内容是"BaseAddress,AllocationBase,AllocationProtect,RegionSize,State,Protect,Type"
     * 数值都是10进制表达.
     */
    public String virtualQueryEx(long hwnd, long addr, int pmbi) {
        return callForString("VirtualQueryEx", FunctionArgs.of(hwnd, addr, pmbi));
    }

    /**
     * 对指定地址写入二进制数据
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param data 二进制数据，以字符串形式描述，比如"12 34 56 78 90 ab cd"
     */
    public void writeData(long hwnd, String addr, String data) {
        callExpect1("WriteData", FunctionArgs.of(hwnd, addr, data));
    }

    /**
     * 对指定地址写入二进制数据
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 地址
     * @param data 二进制数据，以字符串形式描述，比如"12 34 56 78 90 ab cd"
     */
    public void writeDataAddr(long hwnd, long addr, String data) {
        callExpect1("WriteDataAddr", FunctionArgs.of(hwnd, addr, data));
    }

    /**
     * 对指定地址写入二进制数据,只不过直接从数据指针获取数据写入,不通过字符串. 适合高级用户.
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 地址
     * @param data 数据指针
     * @param len  数据长度
     */
    public void writeDataAddrFromBin(long hwnd, long addr, long data, int len) {
        callExpect1("WriteDataAddrFromBin", FunctionArgs.of(hwnd, addr, data, len));
    }

    /**
     * 对指定地址写入二进制数据,只不过直接从数据指针获取数据写入,不通过字符串. 适合高级用户.
     *
     * @param hwnd 窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *             模块名必须用<>符号来圈起来
     *             例如:
     *             1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *             2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *             3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *             4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *             5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *             总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param data 数据指针
     * @param len  数据长度
     */
    public void writeDataFromBin(long hwnd, String addr, long data, int len) {
        callExpect1("WriteDataFromBin", FunctionArgs.of(hwnd, addr, data, len));
    }

    /**
     * 对指定地址写入双精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *              模块名必须用<>符号来圈起来
     *              例如:
     *              1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *              2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *              3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *              4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *              5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *              总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param value 双精度浮点数
     */
    public void writeDouble(long hwnd, String addr, double value) {
        callExpect1("WriteDouble", FunctionArgs.of(hwnd, addr, value));
    }

    /**
     * 对指定地址写入双精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  地址
     * @param value 双精度浮点数
     */
    public void writeDoubleAddr(long hwnd, long addr, double value) {
        callExpect1("WriteDoubleAddr", FunctionArgs.of(hwnd, addr, value));
    }

    /**
     * 对指定地址写入单精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *              模块名必须用<>符号来圈起来
     *              例如:
     *              1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *              2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *              3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *              4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *              5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *              总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param value 单精度浮点数
     */
    public void writeFloat(long hwnd, String addr, float value) {
        callExpect1("WriteFloat", FunctionArgs.of(hwnd, addr, value));
    }

    /**
     * 对指定地址写入单精度浮点数
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  地址
     * @param value 单精度浮点数
     */
    public void writeFloatAddr(long hwnd, long addr, float value) {
        callExpect1("WriteFloatAddr", FunctionArgs.of(hwnd, addr, value));
    }

    /**
     * 对指定地址写入整数数值，类型可以是8位，16位 32位 或者64位
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *              模块名必须用<>符号来圈起来
     *              例如:
     *              1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *              2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *              3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *              4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *              5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *              总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type  整数类型,取值如下
     *              0 : 32位
     *              1 : 16 位
     *              2 : 8位
     *              3 : 64位
     * @param value 整形数值
     */
    public void writeInt(long hwnd, String addr, int type, int value) {
        callExpect1("WriteInt", FunctionArgs.of(hwnd, addr, type, value));
    }

    /**
     * 对指定地址写入整数数值，类型可以是8位，16位 32位 或者64位
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  地址
     * @param type  整数类型,取值如下
     *              0 : 32位
     *              1 : 16 位
     *              2 : 8位
     *              3 : 64位
     * @param value 整形数值
     */
    public void writeIntAddr(long hwnd, long addr, int type, int value) {
        callExpect1("WriteIntAddr", FunctionArgs.of(hwnd, addr, type, value));
    }

    /**
     * 对指定地址写入字符串，可以是Ascii字符串或者是Unicode字符串
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减
     *              模块名必须用<>符号来圈起来
     *              例如:
     *              1.         "4DA678" 最简单的方式，用绝对数值来表示地址
     *              2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     *              3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     *              4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     *              5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     *              总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type  字符串类型,取值如下
     *              0 : Ascii字符串
     *              1 : Unicode字符串
     *              2 : UTF8字符串
     * @param value 字符串
     */
    public void writeString(long hwnd, String addr, int type, String value) {
        callExpect1("WriteString", FunctionArgs.of(hwnd, addr, type, value));
    }

    /**
     * 对指定地址写入字符串，可以是Ascii字符串或者是Unicode字符串
     * <p>
     * 注: DmGuard中的memory护盾也可以突破部分窗口内存保护，可以尝试使用。
     *
     * @param hwnd  窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr  地址
     * @param type  字符串类型,取值如下
     *              0 : Ascii字符串
     *              1 : Unicode字符串
     *              2 : UTF8字符串
     * @param value 字符串
     */
    public void writeStringAddr(long hwnd, long addr, int type, String value) {
        callExpect1("WriteStringAddr", FunctionArgs.of(hwnd, addr, type, value));
    }
}
