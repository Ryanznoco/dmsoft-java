package cn.com.qjun.dmsoft.functions;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.dmsoft.model.FindResult;
import cn.com.qjun.dmsoft.model.ProcessInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 81062
 * @date 2025/12/5
 */
public class DmResultParser {

    public static Point parsePoint(String result) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        String[] parts = result.split(",");
        return Point.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public static List<String> parseStringList(String result) {
        if (StringUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return Arrays.stream(result.split("\\|"))
                .collect(Collectors.toList());
    }

    public static List<Long> parseLongList(String result) {
        return Arrays.stream(result.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public static ProcessInfo parseProcessInfo(String dmResult) {
        String[] parts = dmResult.split("\\|");
        return new ProcessInfo(parts[0], parts[1], Integer.parseInt(parts[2]), Long.parseLong(parts[3]));
    }

    public static FindResult parseFindResult(String dmResult, Function<String[], FindResult.Item> itemBuilder) {
        if (dmResult == null || dmResult.isEmpty()) {
            return FindResult.ofNone();
        }
        List<FindResult.Item> items = Arrays.stream(dmResult.split("\\|"))
                .map(part -> {
                    String[] parts = part.split(",");
                    return itemBuilder.apply(parts);
                })
                .collect(Collectors.toList());
        return FindResult.ofMany(items);
    }
}
