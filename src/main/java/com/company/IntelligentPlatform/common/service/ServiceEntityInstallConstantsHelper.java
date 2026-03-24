package com.company.IntelligentPlatform.common.service;

/**
 * TODO-LEGACY: Stub replacing legacy platform.foundation.Administration.InstallService.ServiceEntityInstallConstantsHelper.
 */
public class ServiceEntityInstallConstantsHelper {

    public static String convertPackageIntoPath(String packageName) {
        if (packageName == null) return null;
        // TODO-LEGACY: implement actual package-to-path conversion
        return packageName.replace('.', '/') + ".properties";
    }

    public static String getSrcAnyClassPath(Class<?> clazz) {
        // TODO-LEGACY: implement actual source path resolution
        if (clazz == null) return "";
        java.net.URL url = clazz.getResource("");
        return url != null ? url.getPath() : "";
    }
}
