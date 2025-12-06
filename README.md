# dmsoft-java

Java调用大漠插件封装库

## 特性

- ✅ 完整的 Java 封装，类型安全
- ✅ 支持多线程，使用 ThreadLocal 确保线程安全
- ✅ 使用 MTA 模式，性能优异
- ✅ 自动管理 DLL 文件，无需手动配置
- ✅ 支持 try-with-resources，自动资源清理
- ✅ 模块化设计，功能清晰
- ✅ 使用枚举类型，避免魔法值

## 快速开始

### 构建项目

```bash
mvn clean install
```

构建完成后，会在 `target/` 目录生成 JAR 文件：`dmsoft-java-1.2.jar`

### 在其他项目中使用

只需要在 `pom.xml` 中添加依赖即可，无需任何额外操作：

```xml
<dependency>
    <groupId>cn.com.qjun</groupId>
    <artifactId>dmsoft-java</artifactId>
    <version>1.2</version>
</dependency>
```

**注意**：本项目依赖从 Nexus 私服获取，包括 `jacob.jar` 和 DLL 文件。所有依赖都会自动处理，无需手动配置。

### 基本使用

#### 单线程使用

```java
import cn.com.qjun.dmsoft.DmSoftWrapper;

// 使用 try-with-resources 自动管理资源
try (DmSoftWrapper dmSoft = new DmSoftWrapper()) {
    // 注册插件（可选，如果需要使用高级功能）
    dmSoft.basicFunctions().reg("注册码", "附加码");
    
    // 设置工作目录
    dmSoft.basicFunctions().setPath("工作目录");
    
    // 获取插件版本
    String version = dmSoft.basicFunctions().ver();
    System.out.println("大漠插件版本: " + version);
}
```

#### 多线程使用

```java
import cn.com.qjun.dmsoft.DmSoftThreadSafe;
import cn.com.qjun.dmsoft.DmSoftInitFunction;

// 创建线程安全的包装类，支持初始化函数
DmSoftInitFunction initFunction = dmSoft -> {
    dmSoft.basicFunctions().reg("注册码", "附加码");
    dmSoft.basicFunctions().setPath("工作目录");
};

// 创建共享的 DmSoftThreadSafe 实例（可以在多个线程间共享）
DmSoftThreadSafe dmSoft = new DmSoftThreadSafe(initFunction);

// 在每个线程中使用
new Thread(() -> {
    try {
        // 每个线程会自动创建独立的 DmSoftWrapper 实例
        String version = dmSoft.basicFunctions().ver();
        System.out.println("线程 " + Thread.currentThread().getId() + " 获取版本: " + version);
    } finally {
        // 重要：线程退出时必须调用 close() 来释放该线程的资源
        dmSoft.close();
    }
}).start();
```

**重要提示**：`DmSoftThreadSafe` 使用 `ThreadLocal` 管理每个线程的实例，因此：
- 可以在多个线程间共享同一个 `DmSoftThreadSafe` 实例
- **每个线程退出时必须调用 `close()`** 来释放该线程的资源
- 不要使用 `try-with-resources`，因为它在方法结束时就会调用 `close()`，而不是在线程退出时

## 功能模块

本项目将大漠插件功能按模块划分，通过对应的 Functions 类访问：

### 基础操作 (DmBasicFunctions)

```java
// 获取插件版本
String version = dmSoft.basicFunctions().ver();

// 注册插件
dmSoft.basicFunctions().reg("注册码", "附加码");

// 设置工作目录
dmSoft.basicFunctions().setPath("工作目录");

// 获取最后错误
long error = dmSoft.basicFunctions().getLastError();

// 设置是否显示错误信息
dmSoft.basicFunctions().setShowErrorMsg(false);
```

### 窗口操作 (DmWindowFunctions)

```java
import cn.com.qjun.dmsoft.enums.WindowFilter;

// 查找窗口
long hwnd = dmSoft.windowFunctions().findWindow("窗口类名", "窗口标题");

// 获取鼠标指向的窗口句柄
long hwnd = dmSoft.windowFunctions().getMousePointWindow();

// 枚举窗口
List<Long> hwnds = dmSoft.windowFunctions().enumWindow(
    0,              // 父窗口句柄，0表示桌面
    "窗口标题",      // 窗口标题（模糊匹配）
    "窗口类名",      // 窗口类名（模糊匹配）
    WindowFilter.VISIBLE  // 过滤条件
);

// 移动窗口
dmSoft.windowFunctions().moveWindow(hwnd, 100, 100);

// 设置窗口状态
dmSoft.windowFunctions().setWindowState(hwnd, 1);  // 1=激活
```

### 图色操作 (DmColourFunctions)

```java
import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.enums.FindDirection;
import cn.com.qjun.dmsoft.model.FindResult;

// 查找颜色
Point point = dmSoft.colourFunctions().findColor(
    Rect.of(0, 0, 800, 600),  // 查找区域
    "ffffff-000000",           // 颜色描述
    0.9,                       // 相似度
    FindDirection.L_TO_R_AND_B_TO_T  // 查找方向
);

// 查找图片
FindResult result = dmSoft.colourFunctions().findPic(
    Rect.of(0, 0, 800, 600),
    Arrays.asList("图片1.bmp", "图片2.bmp"),
    "101010",                  // 偏色
    0.9,
    FindDirection.L_TO_R_AND_B_TO_T
);

if (result.isFound()) {
    FindResult.Item item = result.getItems().get(0);
    Point point = item.getPint();
    System.out.println("找到图片，位置: " + point);
}

// 查找多点颜色
Point point = dmSoft.colourFunctions().findMultiColor(
    Rect.of(0, 0, 800, 600),
    "d4c8b3-101010",           // 主颜色
    "0|2|94886b-101010,2|0|8c846c-101010",  // 偏移颜色
    0.9,
    FindDirection.L_TO_R_AND_B_TO_T
);

// 截图
dmSoft.colourFunctions().capture(
    Rect.of(0, 0, 800, 600),
    "screenshot.bmp"
);
```

### 后台操作 (DmBackgroundFunctions)

```java
import cn.com.qjun.dmsoft.enums.DisplayMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import cn.com.qjun.dmsoft.enums.KeypadMode;

// 绑定窗口
long hwnd = dmSoft.windowFunctions().findWindow("窗口类名", "窗口标题");
int result = dmSoft.backgroundFunctions().bindWindow(
    hwnd,
    DisplayMode.DX,    // 显示模式
    MouseMode.DX,      // 鼠标模式
    KeypadMode.DX,     // 键盘模式
    0                  // 附加信息
);

if (result == 1) {
    System.out.println("绑定成功");
}

// 解除绑定
dmSoft.backgroundFunctions().unBindWindow();
```

### 输入操作 (DmInputFunctions)

```java
// 键盘输入
dmSoft.inputFunctions().keyPress(13);  // 回车键

// 输入字符串
dmSoft.inputFunctions().sendString(hwnd, "Hello World");

// 鼠标点击
dmSoft.inputFunctions().leftClick(100, 200);

// 鼠标移动
dmSoft.inputFunctions().moveTo(300, 400);

// 鼠标按下/弹起
dmSoft.inputFunctions().leftDown();
dmSoft.inputFunctions().leftUp();
```

### 文字/OCR 操作 (DmTextFunctions)

```java
// 解析插件返回结果中的坐标个数
int count = dmSoft.textFunctions().getResultCount(resultString);

// 解析插件返回结果中指定索引的坐标
Point point = dmSoft.textFunctions().getResultPos(resultString, 0);
```

**注意**：OCR 和文字查找功能需要通过大漠插件的其他接口实现，`DmTextFunctions` 主要提供结果解析功能。

### AI 操作 (DmAiFunctions)

```java
import cn.com.qjun.dmsoft.model.AiFindResult;

// AI 查找图片（需要先加载 AI 模型）
AiFindResult result = dmSoft.aiFunctions().aiFindPic(
    Rect.of(0, 0, 800, 600),
    0,  // 模型索引
    0.9 // 相似度
);
```

### 文件操作 (DmFileFunctions)

```java
// 读取文件
String content = dmSoft.fileFunctions().readFile("文件路径");

// 写入文件
dmSoft.fileFunctions().writeFile("文件路径", "内容");

// 检查文件是否存在
boolean exists = dmSoft.fileFunctions().isFileExist("文件路径");
```

### 内存操作 (DmMemoryFunctions)

```java
import cn.com.qjun.dmsoft.model.MemoryInfo;

// 读取内存
byte[] data = dmSoft.memoryFunctions().readData(
    hwnd,
    "00400000",  // 地址
    1024         // 长度
);

// 写入内存
dmSoft.memoryFunctions().writeData(
    hwnd,
    "00400000",
    new byte[]{0x90, 0x90}
);
```

### 系统操作 (DmSystemFunctions)

```java
// 获取系统信息
String os = dmSoft.systemFunctions().getOsType();
String cpu = dmSoft.systemFunctions().getCpuType();

// 延时
dmSoft.systemFunctions().delay(1000);  // 延时1秒
```

### 其他操作 (DmOtherFunctions)

```java
// 释放引用
dmSoft.otherFunctions().releaseRef();
```

## 使用示例

### 示例 1：基本窗口操作

```java
import cn.com.qjun.dmsoft.DmSoftWrapper;

try (DmSoftWrapper dmSoft = new DmSoftWrapper()) {
    // 注册插件
    dmSoft.basicFunctions().reg("注册码", "附加码");
    
    // 查找窗口
    long hwnd = dmSoft.windowFunctions().findWindow("Notepad", null);
    if (hwnd != 0) {
        System.out.println("找到窗口，句柄: " + hwnd);
        
        // 激活窗口
        dmSoft.windowFunctions().setWindowState(hwnd, 1);
        
        // 移动窗口
        dmSoft.windowFunctions().moveWindow(hwnd, 100, 100);
    }
}
```

### 示例 2：图色识别和点击

```java
import cn.com.qjun.dmsoft.DmSoftWrapper;
import cn.com.qjun.dmsoft.enums.DisplayMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import cn.com.qjun.dmsoft.enums.KeypadMode;
import cn.com.qjun.dmsoft.enums.FindDirection;
import cn.com.qjun.commons.geometry.Rect;

try (DmSoftWrapper dmSoft = new DmSoftWrapper()) {
    dmSoft.basicFunctions().reg("注册码", "附加码");
    dmSoft.basicFunctions().setPath("工作目录");
    
    // 绑定窗口
    long hwnd = dmSoft.windowFunctions().findWindow("窗口类名", "窗口标题");
    int bindResult = dmSoft.backgroundFunctions().bindWindow(
        hwnd, DisplayMode.DX, MouseMode.DX, KeypadMode.DX, 0
    );
    
    if (bindResult == 1) {
        try {
            // 查找图片
            FindResult result = dmSoft.colourFunctions().findPic(
                Rect.of(0, 0, 1920, 1080),
                Arrays.asList("按钮.bmp"),
                "101010",
                0.9,
                FindDirection.L_TO_R_AND_B_TO_T
            );
            
            if (result.isFound()) {
                // 点击找到的位置
                Point point = result.getItems().get(0).getPint();
                dmSoft.inputFunctions().leftClick(point.x(), point.y());
            }
        } finally {
            // 解除绑定
            dmSoft.backgroundFunctions().unBindWindow();
        }
    }
}
```

### 示例 3：解析查找结果

```java
import cn.com.qjun.dmsoft.DmSoftWrapper;
import cn.com.qjun.commons.geometry.Point;

try (DmSoftWrapper dmSoft = new DmSoftWrapper()) {
    dmSoft.basicFunctions().reg("注册码", "附加码");
    dmSoft.basicFunctions().setPath("工作目录");
    
    // 执行查找操作（例如查找图片）
    FindResult result = dmSoft.colourFunctions().findPic(
        Rect.of(0, 0, 800, 600),
        Arrays.asList("图片.bmp"),
        "101010",
        0.9,
        FindDirection.L_TO_R_AND_B_TO_T
    );
    
    // 解析结果
    if (result.isFound()) {
        for (FindResult.Item item : result.getItems()) {
            System.out.println("找到图片: " + item.getName());
            System.out.println("位置: " + item.getPint());
        }
    }
}
```

### 示例 4：多线程使用

```java
import cn.com.qjun.dmsoft.DmSoftThreadSafe;
import cn.com.qjun.dmsoft.DmSoftInitFunction;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 定义初始化函数
DmSoftInitFunction initFunction = dmSoft -> {
    dmSoft.basicFunctions().reg("注册码", "附加码");
    dmSoft.basicFunctions().setPath("工作目录");
};

// 创建共享的线程安全包装类（可以在多个线程间共享）
DmSoftThreadSafe dmSoft = new DmSoftThreadSafe(initFunction);

ExecutorService executor = Executors.newFixedThreadPool(5);

for (int i = 0; i < 10; i++) {
    final int taskId = i;
    executor.submit(() -> {
        try {
            // 每个线程都有独立的 DmSoftWrapper 实例
            String version = dmSoft.basicFunctions().ver();
            System.out.println("线程 " + taskId + " 获取版本: " + version);
        } finally {
            // 重要：线程退出时必须调用 close() 来释放该线程的资源
            dmSoft.close();
        }
    });
}

executor.shutdown();
```

**注意**：如果使用线程池，也可以在线程池关闭时统一处理，但更推荐在每个任务结束时调用 `close()`。

## 依赖说明

### 核心依赖

- **jacob 1.21**: COM 组件调用库，从 Nexus 私服获取
- **jna 5.14.0**: 本地库调用（用于加载 DmReg.dll）
- **commons-geometry 1.1**: 几何对象（Point、Rect、Size 等）
- **vavr 0.10.7**: 函数式编程支持
- **commons-lang3 3.20.0**: 工具类

### DLL 文件

所有必需的 DLL 文件会自动处理：

- `jacob-1.21-x86.dll` - 从 Maven 依赖中获取（通过 classifier `x86` 和 type `dll`）
- `DmReg.dll` - 位于 `src/main/resources/win32-x86/`
- `dm.dll` - 位于 `src/main/resources/win32-x86/`

DLL 文件会在运行时自动从 JAR 包中提取到临时目录并加载，无需手动配置。

## 打包

项目使用标准 Maven 打包：

```bash
mvn clean package
```

生成的 JAR 文件位于 `target/dmsoft-java-1.2.jar`

## 线程模型

本项目使用 **MTA (Multi Threaded Apartment)** 模式：

- ✅ 性能优异，适合多线程场景
- ✅ `DmSoftThreadSafe` 使用 `ThreadLocal` 确保每个线程有独立的 `DmSoftWrapper` 实例
- ✅ 线程安全，支持并发访问

**注意**：
- 单线程使用 `DmSoftWrapper`，可以使用 `try-with-resources` 自动管理资源
- 多线程使用 `DmSoftThreadSafe`，每个线程首次访问时会自动初始化 COM 对象
- **多线程场景下，每个线程退出时必须调用 `close()` 来释放该线程的资源**，不要使用 `try-with-resources`

## 枚举类型

项目提供了丰富的枚举类型，避免使用魔法值：

- `DisplayMode`: 显示模式（NORMAL, GDI, GDI2, DX2, DX3, DX）
- `MouseMode`: 鼠标模式（NORMAL, WINDOWS, WINDOWS2, WINDOWS3, DX, DX2）
- `KeypadMode`: 键盘模式（NORMAL, WINDOWS, WINDOWS2, WINDOWS3, DX, DX2）
- `FindDirection`: 查找方向（L_TO_R_AND_T_TO_B, L_TO_R_AND_B_TO_T 等）
- `WindowFilter`: 窗口过滤条件
- `GetWindowFlag`: 获取窗口信息标志
- `GetWindowStateFlag`: 获取窗口状态标志

## 注意事项

1. **资源管理**: 
   - 单线程使用 `DmSoftWrapper` 时，可以使用 `try-with-resources` 自动管理资源
   - 多线程使用 `DmSoftThreadSafe` 时，**每个线程退出时必须手动调用 `close()`** 来释放该线程的资源
2. **多线程**: 多线程场景使用 `DmSoftThreadSafe`，单线程使用 `DmSoftWrapper`
3. **工作目录**: 确保工作目录存在，并包含所需的图片、字库等文件
4. **注册码**: 需要有效的大漠插件注册码和附加码才能使用高级功能
5. **DLL 文件**: DLL 文件会自动从 JAR 包中提取，无需手动配置

## 常见问题

### Q: 编译时找不到 jacob 相关类？

A: 确保 Maven 可以访问 Nexus 私服，并且 `com.jacob:jacob:1.21` 依赖已正确配置。检查 `pom.xml` 中的仓库配置。

### Q: 运行时找不到 DLL 文件？

A: DLL 文件会自动从 JAR 包中提取。如果出现问题，检查：
1. JAR 包是否包含 `win32-x86/` 目录下的 DLL 文件
2. 系统临时目录是否有写入权限
3. 查看日志中的错误信息

### Q: 多线程使用时出现问题？

A: 确保使用 `DmSoftThreadSafe` 而不是 `DmSoftWrapper`。`DmSoftThreadSafe` 使用 `ThreadLocal` 和 MTA 模式，天然支持多线程。如果出现问题，检查：
1. **是否在每个线程退出时调用了 `close()`**（重要！）
2. 是否在同一个线程中创建和使用实例
3. 不要在多线程场景使用 `try-with-resources`，因为它在方法结束时就会调用 `close()`，而不是在线程退出时

### Q: 临时文件没有被清理？

A: 代码中已使用 `deleteOnExit()` 确保清理。如果 JVM 异常退出，可以手动清理系统临时目录下的 `dmsoft-java` 文件夹。

### Q: 绑定窗口失败？

A: 检查：
1. 窗口句柄是否有效
2. 绑定模式是否适合目标窗口
3. 是否有安全软件拦截
4. 查看 `getLastError()` 返回的错误码

## 许可证

本项目仅提供大漠插件的 Java 封装，使用大漠插件需要遵守大漠插件的相关协议。

## 作者

RenQiang

## 更新日志

### 1.2
- 更新依赖版本
- 优化代码结构
- 完善文档

### 1.1
- 支持从 Nexus 私服获取 jacob 依赖
- 优化 DLL 文件加载机制
- 改进多线程支持
