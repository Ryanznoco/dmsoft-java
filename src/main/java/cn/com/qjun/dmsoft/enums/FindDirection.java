package cn.com.qjun.dmsoft.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 查找方向
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@Getter
@RequiredArgsConstructor
public enum FindDirection implements DmEnum<Integer> {
    /**
     * 从左到右,从上到下
     */
    L_TO_R_AND_T_TO_B(0),
    /**
     * 从左到右,从下到上
     */
    L_TO_R_AND_B_TO_T(1),
    /**
     * 从右到左,从上到下
     */
    R_TO_L_AND_T_TO_B(2),
    /**
     * 从右到左,从下到上
     */
    R_TO_L_AND_B_TO_T(3),
    /**
     * 从中心往外
     */
    CENTER_TO_AROUND(4),
    /**
     * 从上到下,从左到右
     */
    T_TO_B_AND_L_TO_R(5),
    /**
     * 从上到下,从右到左
     */
    T_TO_B_AND_R_TO_L(6),
    /**
     * 从下到上,从左到右
     */
    B_TO_T_AND_L_TO_R(7),
    /**
     * 从下到上,从右到左
     */
    B_TO_T_AND_R_TO_L(8);

    private final Integer value;
}
