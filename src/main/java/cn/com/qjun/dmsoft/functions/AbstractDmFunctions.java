package cn.com.qjun.dmsoft.functions;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author RenQiang
 * @date 2025/11/23
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDmFunctions {
    @NonNull
    protected final ActiveXComponent dmSoft;

    protected Variant call(String method) {
        Variant result = dmSoft.invoke(method);
        if (log.isDebugEnabled()) {
            log.debug("调用大漠函数完成: 函数名={}, 返回值={}", method, result);
        }
        return result;
    }

    protected Variant call(String method, FunctionArgs args) {
        if (ArrayUtils.isEmpty(args.variants())) {
            return call(method);
        }
        Variant result = dmSoft.invoke(method, args.variants());
        if (log.isDebugEnabled()) {
            log.debug("调用大漠函数完成: 函数名={}, 参数={}, 返回值={}", method, args.variants(), result);
        }
        return result;
    }

    protected long callForLong(String method, FunctionArgs args) {
        return call(method, args).getInt();
    }

    protected boolean callForBool(String method, FunctionArgs args) {
        return callForLong(method, args) == 1;
    }

    protected double callForDouble(String method, FunctionArgs args) {
        return call(method, args).getDouble();
    }

    protected float callForFloat(String method, FunctionArgs args) {
        return call(method, args).getFloat();
    }

    protected String callForString(String method, FunctionArgs args) {
        return call(method, args).getString();
    }

    protected void callExpect1(String method, FunctionArgs args) {
        callAndValidateResult(this::callForLong, 1L, method, args);
    }

    protected <T> void callAndValidateResult(BiFunction<String, FunctionArgs, T> callFun, T expectResult, String method, FunctionArgs args) {
        T result = callFun.apply(method, args);
        if (!Objects.equals(result, expectResult)) {
            long errorCode = getLastError();
            if (errorCode != 0) {
                throw new RuntimeException(String.format("调用大漠插件'%s'函数失败, 错误码: %d.", method, errorCode));
            } else {
                throw new RuntimeException(String.format("调用大漠插件'%s'函数结果为: '%s'不符合预期, 但未获取到错误码.", method, result.toString()));
            }
        }
    }

    private long getLastError() {
        return call("GetLastError").getInt();
    }
}
