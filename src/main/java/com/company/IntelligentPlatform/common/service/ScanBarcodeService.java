package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: Windows-only JNA barcode scanner service - not applicable in Spring Boot
// TODO-LEGACY: Original used com.sun.jna.platform.win32 to hook keyboard for barcode scanner devices
public class ScanBarcodeService {

    public void stopScanBarcodeService() {
        // TODO-LEGACY: was lib.UnhookWindowsHookEx(hhkKeyBoard)
    }

    public void startScanBarcodeService() {
        // TODO-LEGACY: was Windows JNA keyboard hook implementation
    }
}
