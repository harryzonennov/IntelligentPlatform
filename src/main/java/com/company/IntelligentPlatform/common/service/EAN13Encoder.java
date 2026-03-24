package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: stub for org.jbarcode.encode.EAN13Encoder
public class EAN13Encoder implements BarcodeEncoder {
    private static final EAN13Encoder INSTANCE = new EAN13Encoder();
    public static EAN13Encoder getInstance() { return INSTANCE; }
}
