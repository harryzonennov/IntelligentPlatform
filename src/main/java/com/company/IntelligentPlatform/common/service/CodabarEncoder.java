package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: stub for org.jbarcode.encode.CodabarEncoder
public class CodabarEncoder implements BarcodeEncoder {
    private static final CodabarEncoder INSTANCE = new CodabarEncoder();
    public static CodabarEncoder getInstance() { return INSTANCE; }
}
