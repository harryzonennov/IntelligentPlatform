package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ReferenceNode;
import com.company.IntelligentPlatform.platform.model.City;
import com.company.IntelligentPlatform.platform.model.Country;
import com.company.IntelligentPlatform.platform.model.Location;
import com.company.IntelligentPlatform.platform.model.Province;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [Location]
 *
 * @author
 * @date Sun Feb 10 12:34:18 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class LocationManager extends ServiceEntityManager {

    @Autowired
    protected CountryManager countryManager;

    @Autowired
    protected CityManager cityManager;

    @Autowired
    protected ProvinceManager provinceManager;

    public LocationManager() {

    }

    /**
     * Get all possible location
     *
     * @param referenceNode
     * @return
     * @throws ServiceEntityConfigureException
     */
    public Location getRefLocation(ReferenceNode referenceNode)
            throws ServiceEntityConfigureException {
        if (City.SENAME.equals(referenceNode.getRefSEName())) {
            return (Location) cityManager.getEntityNodeByKey(
                    referenceNode.getRefUUID(),
                    IServiceEntityNodeFieldConstant.UUID, City.NODENAME, null);
        }
        if (Country.SENAME.equals(referenceNode.getRefSEName())) {
            return (Location) countryManager.getEntityNodeByKey(
                    referenceNode.getRefUUID(),
                    IServiceEntityNodeFieldConstant.UUID, Country.NODENAME, null);
        }
        if (Province.SENAME.equals(referenceNode.getRefSEName())) {
            return (Location) provinceManager.getEntityNodeByKey(
                    referenceNode.getRefUUID(),
                    IServiceEntityNodeFieldConstant.UUID, Province.NODENAME, null);
        }
        return null;
    }

}
