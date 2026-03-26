package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 把条形码数据保存到文件的实现（参考实现）
 * 可根据自己的需求做一个自定义实现
 * 只要实现BarcodeSaveService接口的方法
 * 并在barcode.save.services文件中指定使用的实现类即可
 * @author ysc
 */
public class BarcodeSaveToFile implements BarcodeSaveService {

    private static final Logger logger = LoggerFactory.getLogger(BarcodeSaveToFile.class);

    private Writer writer;

    /**
     * 保存到文件
     * @param barcode
     */
    @Override
    public void save(String barcode) {
        try {
            if (writer == null) {
                logger.debug("Opening barcode output file");
                writer = new OutputStreamWriter(new FileOutputStream("d:/barcode.txt", true));
            }
            writer.write(barcode + "\n");
            writer.flush();
        } catch (Exception ex) {
            logger.error("Failed to save barcode to file", ex);
        }
    }

    /**
     * 关闭文件
     */
    @Override
    public void finish() {
        logger.debug("Closing barcode output file");
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ex) {
            logger.error("Failed to close barcode output file", ex);
        }
    }
}
