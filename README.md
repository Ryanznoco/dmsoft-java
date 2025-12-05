# dmsoft-java

Java调用大漠插件封装库

## 特性

- ✅ 完整的 Java 封装，类型安全
- ✅ 支持多线程，使用 ThreadLocal 确保线程安全
- ✅ 使用 MTA 模式，性能优异
- ✅ 自动管理 DLL 文件，无需手动配置
- ✅ 支持 try-with-resources，自动资源清理
- ✅ 模块化设计，功能清晰

## 快速开始

### 构建项目

```bash
mvn clean install
```

构建完成后，会在 `target/` 目录生成 JAR 文件：`dmsoft-java-1.1.jar`

### 在其他项目中使用

只需要在 `pom.xml` 中添加依赖即可，无需任何额外操作：

```xml
<dependency>
    <groupId>cn.com.qjun</groupId>
    <artifactId>dmsoft-java</artifactId>
    <version>1.1</version>
</dependency>
```

**注意**：本项目依赖从 Nexus 私服获取，包括 `jacob.jar` 和 DLL 文件。所有依赖都会自动处理，无需手动配置。

### 基本使用

```java


// 创建配置
DmOptions options = DmOptions.builder("工作目录", "注册码", "附加码")
        .build();

// 使用 try-with-resources 自动管理资源
try(
        DmSoft dmSoft = new DmSoft(options)){
        // 获取插件版本
        String version = dmSoft.opsForBasic().ver();
    System.out.

        println("大漠插件版本: "+version);
}
```

## 配置说明

### DmOptions 配置选项

使用 Builder 模式创建配置：

```java
DmOptions options = DmOptions.builder("工作目录", "注册码", "附加码")
    // 设置图片密码（可选）
    .withPicPwd("图片密码")
    
    // 设置字库密码（可选）
    .withDictPwd("字库密码")
    
    // 添加字库（可选）
    .addDict(0, "字库文件路径")
    .addDict(1, "另一个字库文件路径")
    
    // 加载 AI 模块（可选）
    .loadAi()
    .addAiModel(0, "AI模型文件路径", "模型密码")
    .addAiModel(1, "另一个AI模型文件路径", "模型密码")
    
    .build();
```

**配置项说明：**

| 配置项 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| `workDir` | String | ✅ | 工作目录，用于存放图片、字库等文件 |
| `regCode` | String | ✅ | 大漠插件注册码 |
| `addCode` | String | ✅ | 大漠插件附加码 |
| `picPwd` | String | ❌ | 图片密码，用于加密的图片文件 |
| `dictPwd` | String | ❌ | 字库密码，用于加密的字库文件 |
| `dicts` | Map<Integer, String> | ❌ | 字库文件映射，key 为字库索引 |
| `loadAi` | boolean | ❌ | 是否加载 AI 模块 |
| `aiModels` | Map<Integer, DmEncryptedFile> | ❌ | AI 模型文件映射 |

## 功能模块

本项目将大漠插件功能按模块划分，使用 `opsFor*()` 方法获取对应的操作对象：

### 基础操作 (BasicOperations)

```java
// 获取插件版本
String version = dmSoft.opsForBasic().ver();

// 注册插件
dmSoft.opsForBasic().reg("注册码", "附加码");

// 设置工作目录
dmSoft.opsForBasic().setPath("工作目录");
```

### 窗口操作 (WindowOperations)

```java
// 查找窗口
long hwnd = dmSoft.opsForWindow().findWindow("窗口类名", "窗口标题");

// 获取窗口句柄
long hwnd = dmSoft.opsForWindow().getMousePointWindow();

// 移动窗口
dmSoft.opsForWindow().moveWindow(hwnd, 100, 100);
```

### 图色操作 (ColourOperations)

```java
import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.dmsoft.enums.FindDirection;
import cn.com.qjun.dmsoft.model.FindResult;

// 查找颜色
Point point = dmSoft.opsForColour().findColor(
    Rect.of(0, 0, 800, 600),  // 查找区域
    "ffffff-000000",           // 颜色描述
    0.9,                       // 相似度
    FindDirection.L_TO_R_AND_B_TO_T  // 查找方向
);

// 查找图片
FindResult result = dmSoft.opsForColour().findPic(
    Rect.of(0, 0, 800, 600),
    Arrays.asList("图片1.bmp", "图片2.bmp"),
    "101010",                  // 偏色
    0.9,
    FindDirection.L_TO_R_AND_B_TO_T
);

// 查找多点颜色
Point point = dmSoft.opsForColour().findMultiColor(
    Rect.of(0, 0, 800, 600),
    "d4c8b3-101010",           // 主颜色
    "0|2|94886b-101010,2|0|8c846c-101010",  // 偏移颜色
    0.9,
    FindDirection.L_TO_R_AND_B_TO_T
);
```

### 后台操作 (BackgroundOperations)

```java
import cn.com.qjun.dmsoft.enums.DisplayMode;
import cn.com.qjun.dmsoft.enums.MouseMode;
import cn.com.qjun.dmsoft.enums.KeypadMode;

// 绑定窗口
long hwnd = dmSoft.opsForWindow().findWindow("窗口类名", "窗口标题");
dmSoft.opsForBackground().bindWindow(
    hwnd,
    DisplayMode.DX,    // 显示模式
    MouseMode.DX,      // 鼠标模式
    KeypadMode.DX,     // 键盘模式
    0                  // 附加信息
);

// 解除绑定
dmSoft.opsForBackground().unBindWindow();
```

### 输入操作 (InputOperations)

```java
// 键盘输入
dmSoft.opsForInput().keyPress(13);  // 回车键

// 鼠标点击
dmSoft.opsForInput().leftClick(100, 200);

// 鼠标移动
dmSoft.opsForInput().moveTo(300, 400);
```

### OCR 操作 (OcrOperations)

```java
import cn.com.qjun.dmsoft.model.OcrResult;

// OCR 识别
OcrResult result = dmSoft.opsForOcr().ocr(
    Rect.of(0, 0, 800, 600),
    "字库索引",
    0.9
);

// 查找文字
FindResult result = dmSoft.opsForOcr().findStr(
    Rect.of(0, 0, 800, 600),
    "要查找的文字",
    "字库索引",
    0.9
);
```

### AI 操作 (AiOperations)

```java
// 加载 AI 模块（需要在 DmOptions 中配置）
// 使用 AI 查找
AiFindResult result = dmSoft.opsForAi().aiFindPic(
    Rect.of(0, 0, 800, 600),
    0,  // 模型索引
    0.9
);
```

### 其他模块

- **FileOperations**: 文件操作
- **MemoryOperations**: 内存操作
- **SystemOperations**: 系统操作
- **OtherOperations**: 其他操作

## 使用示例

### 示例 1：基本窗口操作

```java
try (DmSoft dmSoft = new DmSoft(
    DmOptions.builder("工作目录", "注册码", "附加码").build()
)) {
    // 查找窗口
    long hwnd = dmSoft.opsForWindow().findWindow("Notepad", null);
    if (hwnd != 0) {
        System.out.println("找到窗口，句柄: " + hwnd);
        
        // 激活窗口
        dmSoft.opsForWindow().setWindowState(hwnd, 1);
    }
}
```

### 示例 2：图色识别和点击

```java
try (DmSoft dmSoft = new DmSoft(
    DmOptions.builder("工作目录", "注册码", "附加码").build()
)) {
    // 绑定窗口
    long hwnd = dmSoft.opsForWindow().findWindow("窗口类名", "窗口标题");
    dmSoft.opsForBackground().bindWindow(hwnd, DisplayMode.DX, MouseMode.DX, KeypadMode.DX, 0);
    
    try {
        // 查找图片
        FindResult result = dmSoft.opsForColour().findPic(
            Rect.of(0, 0, 1920, 1080),
            Arrays.asList("按钮.bmp"),
            "101010",
            0.9,
            FindDirection.L_TO_R_AND_B_TO_T
        );
        
        if (result.getIndex() >= 0) {
            // 点击找到的位置
            Point point = result.getPoint();
            dmSoft.opsForInput().leftClick(point.x(), point.y());
        }
    } finally {
        // 解除绑定
        dmSoft.opsForBackground().unBindWindow();
    }
}
```

### 示例 3：OCR 文字识别

```java
try (DmSoft dmSoft = new DmSoft(
    DmOptions.builder("工作目录", "注册码", "附加码")
        .withDictPwd("字库密码")
        .addDict(0, "字库文件路径")
        .build()
)) {
    // OCR 识别
    OcrResult result = dmSoft.opsForOcr().ocr(
        Rect.of(100, 100, 500, 200),
        "0",  // 字库索引
        0.9
    );
    
    System.out.println("识别结果: " + result.getText());
}
```

### 示例 4：多线程使用

```java
// DmSoft 使用 ThreadLocal 管理每个线程的实例，天然支持多线程
ExecutorService executor = Executors.newFixedThreadPool(5);

for (int i = 0; i < 10; i++) {
    final int taskId = i;
    executor.submit(() -> {
        try (DmSoft dmSoft = new DmSoft(
            DmOptions.builder("工作目录", "注册码", "附加码").build()
        )) {
            // 每个线程都有独立的 DmSoft 实例
            String version = dmSoft.opsForBasic().ver();
            System.out.println("线程 " + taskId + " 获取版本: " + version);
        }
    });
}

executor.shutdown();
```

## 依赖说明

### jacob.jar

- **来源**: 从 Nexus 私服获取（`com.jacob:jacob:1.21`）
- **DLL**: 通过 classifier `x86` 获取（`com.jacob:jacob:1.21:x86:dll`）
- **处理**: Maven 构建时自动将 DLL 复制到 classpath
- **优势**: 统一管理，无需在项目中包含本地文件

### DLL 文件

所有必需的 DLL 文件会自动处理：

- `jacob-1.21-x86.dll` - 从 Maven 依赖中获取
- `DmReg.dll` - 位于 `src/main/resources/win32-x86/`
- `dm.dll` - 位于 `src/main/resources/win32-x86/`

DLL 文件会在运行时自动从 JAR 包中提取到临时目录并加载，无需手动配置。

## 依赖说明

### jacob.jar

- **位置**: `libs/jacob.jar`
- **打包**: 使用 `maven-shade-plugin` 的 `includeSystemScope` 选项，会将 jacob.jar 包含到最终 JAR 中
- **原因**: jacob.jar 不在 Maven Central，使用 system scope 依赖
- **优势**: 其他项目引用时无需额外配置，所有依赖都包含在 JAR 中

### DLL 文件

所有必需的 DLL 文件位于 `src/main/resources/win32-x86/` 目录：
- `jacob-1.20-x86.dll` - Jacob 库的本地库
- `DmReg.dll` - 大漠插件注册 DLL
- `dm.dll` - 大漠插件主 DLL
- `RegDll.dll` - 注册 DLL

DLL 文件会在运行时自动从 JAR 包中提取到临时目录并加载，无需手动配置。

## 打包

项目使用标准 Maven 打包：

```bash
mvn clean package
```

生成的 JAR 文件位于 `target/dmsoft-java-1.1.jar`

## 线程模型

本项目使用 **MTA (Multi Threaded Apartment)** 模式：

- ✅ 性能优异，适合多线程场景
- ✅ 使用 `ThreadLocal` 确保每个线程有独立的 `ActiveXComponent` 实例
- ✅ 线程安全，支持并发访问

**注意**：每个线程首次访问时会自动初始化 COM 对象，无需手动管理。

## 注意事项

1. **资源管理**: 使用 `try-with-resources` 确保资源正确释放
2. **多线程**: 每个线程会自动创建独立的 COM 对象，无需担心线程安全问题
3. **工作目录**: 确保工作目录存在，并包含所需的图片、字库等文件
4. **注册码**: 需要有效的大漠插件注册码和附加码

## 常见问题

### Q: 编译时找不到 jacob 相关类？

A: 确保 Maven 可以访问 Nexus 私服，并且 `com.jacob:jacob:1.21` 依赖已正确配置。

### Q: 运行时找不到 DLL 文件？

A: DLL 文件会自动从 JAR 包中提取。如果出现问题，检查：
1. JAR 包是否包含 `win32-x86/` 目录下的 DLL 文件
2. 系统临时目录是否有写入权限
3. 查看日志中的错误信息

### Q: 多线程使用时出现问题？

A: 本项目使用 `ThreadLocal` 和 MTA 模式，天然支持多线程。如果出现问题，检查：
1. 是否正确使用 `try-with-resources`
2. 是否在同一个线程中创建和使用 `DmSoft` 实例

### Q: 临时文件没有被清理？

A: 代码中已使用 `deleteOnExit()` 确保清理。如果 JVM 异常退出，可以手动清理系统临时目录下的 `dmsoft-java` 文件夹。

## 许可证

本项目仅提供大漠插件的 Java 封装，使用大漠插件需要遵守大漠插件的相关协议。

## 作者

RenQiang

## 更新日志

### 1.1
- 支持从 Nexus 私服获取 jacob 依赖
- 优化 DLL 文件加载机制
- 改进多线程支持
