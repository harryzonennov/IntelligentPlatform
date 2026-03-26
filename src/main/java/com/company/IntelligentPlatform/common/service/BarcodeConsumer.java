package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 此消费者线程会从此缓冲区中获取数据并执行数据的保存操作
 * 数据的保存调用BarcodeSaveService接口定义的save方法
 *
 * @author ysc
 */
public class BarcodeConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BarcodeConsumer.class);

    // 消费者线程
    private Thread thread;
    // 数据保存服务（可有多个）
    private List<BarcodeSaveService> barcodeSaveServices = new ArrayList<>();
    private boolean quit;

    /**
     * 停止消费者线程
     * 此方法在tomcat关闭的时候被调用
     */
    public void stopConsume() {
        if (thread != null) {
            thread.interrupt();
            // 释放资源
            for (BarcodeSaveService barcodeSaveService : barcodeSaveServices) {
                barcodeSaveService.finish();
            }
        }
    }

    /**
     * 启动消费者线程
     * 此方法在tomcat启动的时候被调用
     */
    public void startConsume() {
        // 防止重复启动
        if (thread != null && thread.isAlive()) {
            return;
        }
        logger.info("Barcode consumer thread starting");
        logger.info("Registering barcode save services");
        registerBarcodeSaveServcie();

        thread = new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        // 当缓冲区没有数据的时候，此方法会阻塞
                        String barcode = BarcodeBuffer.consume();
                        if (barcodeSaveServices.isEmpty()) {
                            logger.warn("No barcode save service registered");
                        }
                        for (BarcodeSaveService barcodeSaveService : barcodeSaveServices) {
                            barcodeSaveService.save(barcode);
                        }
                    } catch (InterruptedException e) {
                        quit = true;
                    }
                }
                logger.info("Barcode consumer thread stopped");
            }
        };
        thread.setName("consumer");
        thread.start();
    }

    /**
     * 消费者线程从缓冲区获取到数据后需要调用保存服务对数据进行处理
     */
    private void registerBarcodeSaveServcie() {
        List<String> classes = getBarcodeSaveServcieImplClasses();
        logger.info("Barcode save service implementations found: {}", classes.size());
        for (String clazz : classes) {
            try {
                BarcodeSaveService barcodeSaveService = (BarcodeSaveService) Class.forName(clazz).newInstance();
                barcodeSaveServices.add(barcodeSaveService);
            } catch (Exception e) {
                logger.error("Failed to instantiate barcode save service class: {}", clazz, e);
            }
        }
    }

    /**
     * 从类路径下的barcode.save.services文件中获取保存服务类名
     *
     * @return 多个保存服务实现类名
     */
    private List<String> getBarcodeSaveServcieImplClasses() {
        List<String> result = new ArrayList<>();
        BufferedReader reader = null;
        InputStream in = null;
        try {
            // 放在WEB-INF/classes下的"barcode.save.services"会覆盖jar包中的"barcode.save.services"
            URL url = Thread.currentThread().getContextClassLoader().getResource("barcode.save.services");
            logger.debug("Barcode save services config URL: {}", url != null ? url.getPath() : "null");
            if (url == null) {
                logger.warn("Barcode save services config file not found on classpath: barcode.save.services");
                return result;
            }
            in = url.openStream();
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = reader.readLine();
            while (line != null) {
                // 忽略空行和以#号开始的注释行
                if (!"".equals(line.trim()) && !line.trim().startsWith("#")) {
                    result.add(line);
                }
                line = reader.readLine();
            }
        } catch (Exception ex) {
            logger.error("Failed to load barcode save service config", ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                logger.warn("Failed to close barcode config reader", ex);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.warn("Failed to close barcode config input stream", ex);
            }
        }
        return result;
    }
}
