package cn.com.qjun.dmsoft.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * @author RenQiang
 * @date 2023/1/10
 */
@Slf4j
public class RuntimeUtils {

    /**
     * 释放资源文件到临时目录
     *
     * @param sourceFilePath   资源文件路径
     * @param targetFilePrefix 目标文件前缀
     * @param targetSuffix     目标文件后缀
     * @throws IOException 释放资源文件失败
     */
    public static Path releaseFileToTempDir(String sourceFilePath, String targetFilePrefix, String targetSuffix) throws IOException {
        Path tempDirectory = Files.createTempDirectory("dmsoft-java");
        Path tempFile = Files.createTempFile(tempDirectory, targetFilePrefix, targetSuffix);
        try (InputStream input = RuntimeUtils.class.getResourceAsStream(sourceFilePath)) {
            if (input != null) {
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            } else {
                throw new RuntimeException("Could not find resource file: " + sourceFilePath);
            }
        }
        tempFile.toFile().deleteOnExit();
        tempDirectory.toFile().deleteOnExit();
        return tempFile;
    }

    /**
     * 加载类路径下的Properties文件
     *
     * @param filePath 文件路径（相对于类路径，以 / 开头）
     * @return Properties对象
     * @throws IOException 如果文件不存在或加载失败
     */
    public static Properties readProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = RuntimeUtils.class.getResourceAsStream(filePath)) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Could not find resource file: " + filePath);
            }
        }
        return properties;
    }
}
