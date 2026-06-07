package com.company.IntelligentPlatform.platform.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.platform.repository.CityRepository;
import com.company.IntelligentPlatform.platform.service.JpaServiceEntityDAO;

import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.City;
import com.company.IntelligentPlatform.platform.model.Country;
import com.company.IntelligentPlatform.platform.model.Location;
import com.company.IntelligentPlatform.platform.model.Province;
import com.company.IntelligentPlatform.platform.model.CityConfigureProxy;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [City]
 *
 * @author
 * @date Sun Feb 10 14:06:07 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class CityManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected CityRepository cityDAO;
    @Autowired
    ProvinceManager provinceManager;

    @Autowired
    CountryManager countryManager;

    @Autowired
    CityConfigureProxy cityConfigureProxy;

    public CityManager() {
        super.seConfigureProxy = new CityConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setSeConfigureProxy(cityConfigureProxy);
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, cityDAO, this.getSeConfigureProxy()));
    }

    /**
     * Get the parent province information of city, if parent province exist
     *
     * @param city
     * @return
     * @throws ServiceEntityConfigureException
     */
    public Province getProvince(City city)
            throws ServiceEntityConfigureException {
        Location parentLocation = getLocation(city.getParentLocationUUID());
        if (parentLocation == null) {
            return null;
        }
        if (parentLocation.getLocationLevel() == Location.LOC_LEVEL_PROVINCE) {
            return (Province) parentLocation;
        }
        // if the current location is still city, then loop to find parent
        // location
        while (parentLocation.getLocationLevel() != Location.LOC_LEVEL_CITY) {
            parentLocation = getLocation(parentLocation.getParentLocationUUID());
            if (parentLocation.getLocationLevel() == Location.LOC_LEVEL_PROVINCE)
                return (Province) parentLocation;
        }
        return null;
    }

    /**
     * Get any type location by UUID
     *
     * @param uuid
     * @return
     * @throws ServiceEntityConfigureException
     */
    public Location getLocation(String uuid)
            throws ServiceEntityConfigureException {
        // Check if this location exist as City
        ServiceEntityNode locationNode = this.getEntityNodeByKey(uuid,
                IServiceEntityNodeFieldConstant.UUID,
                ServiceEntityNode.NODENAME_ROOT, null);
        if (locationNode != null) {
            City city = (City) locationNode;
            return city;
        }
        // Check if this location exist as Province
        locationNode = provinceManager.getEntityNodeByKey(uuid,
                IServiceEntityNodeFieldConstant.UUID,
                ServiceEntityNode.NODENAME_ROOT, null);
        if (locationNode != null) {
            Province province = (Province) locationNode;
            return province;
        }
        // Check if this location exist as Country
        locationNode = countryManager.getEntityNodeByKey(uuid,
                IServiceEntityNodeFieldConstant.UUID,
                ServiceEntityNode.NODENAME_ROOT, null);
        if (locationNode != null) {
            Country country = (Country) locationNode;
            return country;
        }
        return null;
    }

    /**
     * By default algorithm to get city instance from persistence, by default checking city name firstly, then
     * checking city reference uuid
     *
     * @param refCityName
     * @param refCityUUID
     * @return
     * @throws ServiceEntityConfigureException
     */
    public City getCityByDefaultWay(String refCityName, String refCityUUID) throws ServiceEntityConfigureException {
        if (refCityName != null && !refCityName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            ServiceEntityNode locationNode = this.getEntityNodeByKey(refCityName,
                    IServiceEntityNodeFieldConstant.NAME,
                    City.NODENAME, null, true);
            if (locationNode != null) {
                return (City) locationNode;
            }
        }
        if (refCityUUID != null && !refCityUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            ServiceEntityNode locationNode = this.getEntityNodeByKey(refCityUUID,
                    IServiceEntityNodeFieldConstant.UUID,
                    City.NODENAME, null);
            if (locationNode != null) {
                return (City) locationNode;
            }
        }
        return null;
    }

    public City getCityInfoByName(String refCityName) throws ServiceEntityConfigureException {
        ServiceEntityNode locationNode = this.getEntityNodeByKey(refCityName,
                IServiceEntityNodeFieldConstant.NAME,
                City.NODENAME, null, true);
        if (locationNode != null) {
            return (City) locationNode;
        } else {
            return null;
        }
    }

}
