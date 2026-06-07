package com.company.IntelligentPlatform.platform.service;

import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.platform.dto.CargoUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.platform.repository.CargoRepository;
import com.company.IntelligentPlatform.platform.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.platform.model.Cargo;
import com.company.IntelligentPlatform.platform.model.CargoConfigureProxy;
import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [Cargo]
 * 
 * @author
 * @date Thu Jan 17 23:09:58 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class CargoManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected CargoRepository cargoDAO;
	@Autowired
	protected CargoConfigureProxy cargoConfigureProxy;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	public CargoManager() {
		super.seConfigureProxy = new CargoConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setSeConfigureProxy(cargoConfigureProxy);
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, cargoDAO, this.getSeConfigureProxy()));
	}

	public void deleteTmpCargo(String cargoUUID, String client)
			throws ServiceEntityConfigureException {
		Cargo cargo = (Cargo) getEntityNodeByKey(cargoUUID,
				IServiceEntityNodeFieldConstant.UUID, Cargo.NODENAME, client, null);
		if (cargo != null && cargo.getRegularType() == Cargo.REGULAR_TYPE_TEMP) {
			admDeleteEntityByKey(cargoUUID,
					IServiceEntityNodeFieldConstant.UUID, Cargo.NODENAME);
		}
	}
	
	public Map<Integer, String> getCargoTypeMap() throws ServiceEntityInstallationException{
		Map<Integer, String> cargoTypeMap = serviceDropdownListHelper
				.getUIDropDownMap(CargoUIModel.class, "cargoType");
		return cargoTypeMap;
	}
	
	public String getCargoTypeLabel(int key) throws ServiceEntityInstallationException{
		Map<Integer, String> cargoTypeMap = getCargoTypeMap();
		return cargoTypeMap.get(key);
	}
}
