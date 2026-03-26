package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 扫码枪模拟的键盘按钮事件监听（0-9键和回车键）
 * 关键算法：条形码扫描器在很短的时间内输入了至少 barcodeMinLength 个字符以上信息，并且以"回车"作为结束字符，并且一次扫描要在 maxScanTime 毫秒内完成
 * 字符数及扫描时间可根据具体情况设置
 * @author ysc
 */
public class BarcodeKeyboardListener {

    private static final Logger logger = LoggerFactory.getLogger(BarcodeKeyboardListener.class);

    // 条形码数据缓冲区
    private StringBuilder barcode;
    // 扫描开始时间
    private long start;
    private Map<Integer, Integer> keyToLetter = new HashMap<>();
    // 一次扫描的最长时间
    private static int maxScanTime = 300;
    // 条形码的最短长度
    private static int barcodeMinLength = 6;

    /**
     * 初始键盘代码和字母的对应关系
     */
    public BarcodeKeyboardListener() {
        keyToLetter.put(48, 0);
        keyToLetter.put(49, 1);
        keyToLetter.put(50, 2);
        keyToLetter.put(51, 3);
        keyToLetter.put(52, 4);
        keyToLetter.put(53, 5);
        keyToLetter.put(54, 6);
        keyToLetter.put(55, 7);
        keyToLetter.put(56, 8);
        keyToLetter.put(57, 9);
    }

    /**
     * 此方法响应扫描枪事件
     * @param keyCode
     */
    public void onKey(int keyCode) {
        Integer letter = keyToLetter.get(keyCode);
        if (barcode == null) {
            barcode = new StringBuilder();
            start = System.currentTimeMillis();
        }
        long cost = System.currentTimeMillis() - start;
        if (cost > maxScanTime) {
            barcode = new StringBuilder();
            start = System.currentTimeMillis();
        }
        if (keyCode >= 48 && keyCode <= 57) {
            barcode.append(letter);
        }
        if (keyCode == 13) {
            if (barcode.length() >= barcodeMinLength && cost < maxScanTime) {
                cost = System.currentTimeMillis() - start;
                logger.debug("Barcode scanned in {}ms: {}", cost, barcode.toString());
                BarcodeBuffer.product(barcode.toString());
            }
            barcode = new StringBuilder();
        }
    }
}
