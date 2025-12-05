package cn.com.qjun.dmsoft.utils;

import cn.com.qjun.dmsoft.model.MemoryInfo;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author RenQiang
 * @date 2025/11/23
 */
public class DirectMemoryUtils {

    /**
     * 将多个字节数组连续加载到内存中, 然后消费
     *
     * @param bytesList
     * @param consumer
     */
    public static void continueLoadToMemAndConsume(List<byte[]> bytesList, Consumer<List<MemoryInfo>> consumer) {
        continueLoadToMemAndApply(bytesList, memoryInfoList -> {
            consumer.accept(memoryInfoList);
            return null;
        });
    }

    /**
     * 将字节数组加载到内存中, 然后消费
     *
     * @param bytes
     * @param consumer
     */
    public static void loadToMemAndConsume(byte[] bytes, Consumer<MemoryInfo> consumer) {
        loadToMemAndApply(bytes, memoryInfo -> {
            consumer.accept(memoryInfo);
            return null;
        });
    }

    /**
     * 将多个字节数组连续加载到内存中, 然后应用Function
     *
     * @param bytesList
     * @param function
     * @param <T>
     * @return
     */
    public static <T> T continueLoadToMemAndApply(List<byte[]> bytesList, Function<List<MemoryInfo>, T> function) {
        int totalLength = bytesList.stream().mapToInt(bytes -> bytes.length).sum();
        try (Memory memory = new Memory(totalLength)) {
            AtomicInteger i = new AtomicInteger();
            List<MemoryInfo> memoryInfoList = bytesList.stream()
                    .map(bytes -> {
                        long address = Pointer.nativeValue(memory) + i.get();
                        memory.write(i.getAndAccumulate(bytes.length, Integer::sum), bytes, 0, bytes.length);
                        return new MemoryInfo(address, bytes.length);
                    })
                    .collect(Collectors.toList());
            return function.apply(memoryInfoList);
        }
    }

    /**
     * 将字节数组加载到内存中, 然后应用Function
     *
     * @param bytes
     * @param function
     * @param <T>
     * @return
     */
    public static <T> T loadToMemAndApply(byte[] bytes, Function<MemoryInfo, T> function) {
        try (Memory memory = new Memory(bytes.length)) {
            memory.write(0, bytes, 0, bytes.length);
            MemoryInfo memoryInfo = new MemoryInfo(Pointer.nativeValue(memory), memory.size());
            return function.apply(memoryInfo);
        }
    }
}
