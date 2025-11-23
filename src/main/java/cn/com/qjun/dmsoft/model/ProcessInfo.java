package cn.com.qjun.dmsoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进程信息
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInfo {
    /**
     * 进程名称
     */
    private String name;
    /**
     * 进程全路径
     */
    private String path;
    /**
     * CPU占用率(百分比)
     */
    private int cpuUtilization;
    /**
     * 内存占用量(字节)
     */
    private long memoryUsage;
}
