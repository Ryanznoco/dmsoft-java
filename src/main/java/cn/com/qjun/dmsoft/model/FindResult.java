package cn.com.qjun.dmsoft.model;

import cn.com.qjun.commons.geometry.Point;
import cn.com.qjun.commons.geometry.Rect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 查找结果
 *
 * @author RenQiang
 * @date 2024/2/12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FindResult implements Serializable {
    private static final long serialVersionUID = 634481408187565047L;

    private boolean found;
    private List<Item> items;

    public static FindResult ofNone() {
        return new FindResult(false, Collections.emptyList());
    }

    public static FindResult ofOne(Item item) {
        return new FindResult(true, Collections.singletonList(item));
    }

    public static FindResult ofMany(List<Item> items) {
        return new FindResult(true, items);
    }

    @Data
    @ToString
    public static class Item {
        private int index;
        private String name;
        private Point pint;
        private Integer probability;
        private Rect rect;

        public Item(int index, Point point) {
            this.index = index;
            this.pint = point;
        }

        public Item(int index, String name, Point point) {
            this.index = index;
            this.name = name;
            this.pint = point;
        }

        public Item(String name, int probability, Rect rect) {
            this.name = name;
            this.probability = probability;
            this.rect = rect;
        }
    }
}
