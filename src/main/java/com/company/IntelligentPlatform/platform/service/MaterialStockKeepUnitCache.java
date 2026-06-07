package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.service.ServiceEntityCache;

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
