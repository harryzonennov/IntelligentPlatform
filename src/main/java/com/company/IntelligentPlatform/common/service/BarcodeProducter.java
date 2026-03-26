package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动和关闭条码枪扫描线程
 * @author ysc
 */
public class BarcodeProducter {

    private static final Logger logger = LoggerFactory.getLogger(BarcodeProducter.class);

    private boolean quit;
    private Thread thread;
    private ScanBarcodeService scanBarcodeService;

    public BarcodeProducter() {
        scanBarcodeService = new ScanBarcodeService();
    }

    /**
     * 启动生产者线程
     * 此方法在tomcat启动的时候被调用
     */
    public void startProduct() {
        // 防止重复启动
        if (thread != null && thread.isAlive()) {
            return;
        }
        logger.info("Starting barcode producer...");
        thread = new Thread() {
            @Override
            public void run() {
                logger.info("Barcode scanner thread started");
                while (!quit) {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (Exception e) {
                        quit = true;
                    }
                }
                scanBarcodeService.stopScanBarcodeService();
                logger.info("Barcode scanner thread stopped");
                System.exit(0);
            }
        };
        thread.start();
        new Thread() {
            @Override
            public void run() {
                scanBarcodeService.startScanBarcodeService();
            }
        }.start();
    }

    /**
     * 关闭生产者线程
     * 此方法在tomcat关闭的时候被调用
     */
    public void stopProduct() {
        if (thread != null) {
            thread.interrupt();
            logger.info("Stopping barcode producer...");
        }
    }
}
