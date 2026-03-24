package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

/**
 * Top-level alias for DocFlowProxy.SimpleDocConfigurePara to support existing imports
 */
public class SimpleDocConfigurePara extends DocFlowProxy.SimpleDocConfigurePara {
    public SimpleDocConfigurePara() { super(); }

    public SimpleDocConfigurePara(String baseNodeId, String convToUIMethod, Class<?>[] convToUIParas, Object logicManager) {
        super(baseNodeId, convToUIMethod, convToUIParas, logicManager);
    }

    public SimpleDocConfigurePara(String baseNodeId, String convToUIMethod, Class<?>[] convToUIParas,
                                   Object logicManager, UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback,
                                   UIModelNodeMapConfigure.IGetSENode docGetCallback) {
        super(baseNodeId, convToUIMethod, convToUIParas, logicManager, docMatItemGetCallback, docGetCallback);
    }
}
