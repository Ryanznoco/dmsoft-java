package cn.com.qjun.dmsoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述内存中的一段数据
 *
 * @author RenQiang
 * @date 2024/2/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoryInfo implements Serializable {
    private static final long serialVersionUID = 1598192996273186922L;

    /**
     * 内存地址
     */
    private long address;
    /**
     * 内存大小
     */
    private long size;

    /**
     * 转换成大漠插件要求的格式
     *
     * @return
     */
    public String toDmString() {
        return String.format("%d,%d", address, size);
    }
}
