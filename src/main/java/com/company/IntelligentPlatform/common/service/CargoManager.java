package com.company.IntelligentPlatform.common.service;

import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.CargoUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.CargoRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.model.Cargo;
import com.company.IntelligentPlatform.common.model.CargoConfigureProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

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
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, cargoDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(cargoConfigureProxy);
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
