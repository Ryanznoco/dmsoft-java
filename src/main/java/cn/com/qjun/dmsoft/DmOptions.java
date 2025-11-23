package cn.com.qjun.dmsoft;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 插件配置
 *
 * @author RenQiang
 * @date 2024/2/11
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DmOptions {
    /**
     * 工作目录
     */
    private final String workDir;
    /**
     * 注册码
     */
    private final String regCode;
    /**
     * 附加码
     */
    private final String addCode;
    /**
     * 图片密码
     */
    @Setter(AccessLevel.PRIVATE)
    private String picPwd;
    /**
     * 是否加载AI模块
     * 需要保证工作目录下存在modules\ai.module文件
     */
    @Setter(AccessLevel.PRIVATE)
    private boolean loadAi;
    /**
     * AI模型
     */
    @Setter(AccessLevel.PRIVATE)
    private Map<Integer, DmEncryptedFile> aiModels;
    /**
     * 字库密码
     */
    @Setter(AccessLevel.PRIVATE)
    private String dictPwd;
    /**
     * 字库
     */
    @Setter(AccessLevel.PRIVATE)
    private Map<Integer, String> dicts;
    
    /**
     * 创建配置构建器
     *
     * @param workDir 工作目录
     * @param regCode 注册码
     * @param addCode 附加码
     * @return 配置构建器
     */
    public static PluginOptionsBuilder builder(String workDir, String regCode, String addCode) {
        return new PluginOptionsBuilder(workDir, regCode, addCode);
    }
    
    public static class PluginOptionsBuilder {
        private final DmOptions dmOptions;
        
        public PluginOptionsBuilder(String workDir, String regCode, String addCode) {
            dmOptions = new DmOptions(workDir, regCode, addCode);
        }
        
        public PluginOptionsBuilder withPicPwd(String picPwd) {
            dmOptions.setPicPwd(picPwd);
            return this;
        }
        
        public PluginOptionsBuilder loadAi() {
            dmOptions.setLoadAi(true);
            dmOptions.setAiModels(new HashMap<>(8));
            return this;
        }
        
        public PluginOptionsBuilder addAiModel(int index, String file, String pwd) {
            dmOptions.getAiModels().put(index, new DmEncryptedFile(file, pwd));
            return this;
        }
        
        public PluginOptionsBuilder withDictPwd(String dictPwd) {
            dmOptions.setDictPwd(dictPwd);
            return this;
        }

        public PluginOptionsBuilder addDict(int index, String file) {
            if (dmOptions.getDicts() == null) {
                dmOptions.setDicts(new HashMap<>(8));
            }
            dmOptions.getDicts().put(index, file);
            return this;
        }
        
        public DmOptions build() {
            return dmOptions;
        }
    }

    /**
     * 加密文件
     */
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DmEncryptedFile {
        /**
         * 模型文件
         */
        private String file;
        /**
         * 模型密码
         */
        private String pwd;
    }
}
