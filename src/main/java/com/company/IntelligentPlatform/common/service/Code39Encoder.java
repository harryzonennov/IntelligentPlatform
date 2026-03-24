package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: stub for org.jbarcode.encode.Code39Encoder
public class Code39Encoder implements BarcodeEncoder {
    private static final Code39Encoder INSTANCE = new Code39Encoder();
    public static Code39Encoder getInstance() { return INSTANCE; }
}
