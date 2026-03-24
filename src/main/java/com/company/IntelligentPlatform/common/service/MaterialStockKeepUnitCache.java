package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityCache;

import jakarta.annotation.PostConstruct;

@Service
public class MaterialStockKeepUnitCache extends ServiceEntityCache {

   @Autowired
   private MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @PostConstruct
    public void setServiceEntityManager() {
        super.setServiceEntityManager(materialStockKeepUnitManager);
    }
}
