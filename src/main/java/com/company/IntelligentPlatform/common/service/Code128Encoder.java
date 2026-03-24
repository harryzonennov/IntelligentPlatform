package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: stub for org.jbarcode.encode.Code128Encoder
public class Code128Encoder implements BarcodeEncoder {
    private static final Code128Encoder INSTANCE = new Code128Encoder();
    public static Code128Encoder getInstance() { return INSTANCE; }
}
