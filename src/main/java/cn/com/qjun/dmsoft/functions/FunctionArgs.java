package cn.com.qjun.dmsoft.functions;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import cn.com.qjun.commons.geometry.Size;
import cn.com.qjun.dmsoft.enums.DmEnum;
import cn.com.qjun.dmsoft.model.MemoryInfo;
import com.jacob.com.Variant;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ArrayUtils;

import java.util.stream.Stream;

/**
 * @author 81062
 * @date 2025/11/25
 */
@Getter
@Accessors(fluent = true)
public class FunctionArgs {
    private final Variant[] variants;

    public static FunctionArgs of(Object... args) {
        return new FunctionArgs(args);
    }

    private FunctionArgs(Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            this.variants = new Variant[0];
        } else {
            this.variants = Stream.of(args)
                    .flatMap(this::argToVariants)
                    .toArray(Variant[]::new);
        }
    }

    public Point getPoint(int xIndex, int yIndex) {
        return Point.of(getVariant(xIndex).getInt(), getVariant(yIndex).getInt());
    }

    public Size getSize(int widthIndex, int heightIndex) {
        return Size.of(getVariant(widthIndex).getInt(), getVariant(heightIndex).getInt());
    }

    public Rect getRect(int x1Index, int y1Index, int x2Index, int y2Index) {
        return Rect.ofPoint(getVariant(x1Index).getInt(), getVariant(y1Index).getInt(), getVariant(x2Index).getInt(), getVariant(y2Index).getInt());
    }

    public MemoryInfo getMemoryInfo(int addrIndex, int sizeIndex) {
        return new MemoryInfo(getVariant(addrIndex).getInt(), getVariant(sizeIndex).getInt());
    }

    public Variant getVariant(int index) {
        int len = variants.length;
        int i = index % len;
        if (i < 0) {
            i += len;
        }
        return variants[i];
    }

    private Stream<Variant> argToVariants(Object originArg) {
        if (originArg instanceof Point) {
            Point point = (Point) originArg;
            return Stream.of(new Variant(point.x()), new Variant(point.y()));
        } else if (originArg instanceof Rect) {
            Rect rect = (Rect) originArg;
            return Stream.of(new Variant(rect.x1()), new Variant(rect.y1()), new Variant(rect.x2()), new Variant(rect.y2()));
        } else if (originArg instanceof Size) {
            Size size = (Size) originArg;
            return Stream.of(new Variant(size.width()), new Variant(size.height()));
        } else if (originArg instanceof DmEnum<?>) {
            DmEnum<?> dmEnum = (DmEnum<?>) originArg;
            return Stream.of(new Variant(dmEnum.getValue()));
        } else if (originArg instanceof MemoryInfo) {
            MemoryInfo memoryInfo = (MemoryInfo) originArg;
            return Stream.of(new Variant(memoryInfo.getAddress()), new Variant(memoryInfo.getSize()));
        } else if (originArg instanceof Variant) {
            return Stream.of((Variant) originArg);
        } else if (originArg instanceof Boolean) {
            boolean arg = (boolean) originArg;
            return Stream.of(new Variant(arg ? 1 : 0));
        } else {
            return Stream.of(new Variant(originArg));
        }
    }
}
