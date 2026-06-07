package com.company.IntelligentPlatform.platform.service;

// TODO-DAO: import platform.foundation.DAO.HibernateDefaultImpDAO;
// TODO-DAO: import platform.foundation.DAO.PackageRegisterationEntityDAO;
import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.PackageRegisterationEntity;
import com.company.IntelligentPlatform.platform.model.PackageRegisterationEntityConfigureProxy;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

public class PackageRegisterationEntityManager extends ServiceEntityManager {

	public PackageRegisterationEntityManager() {
		super.seConfigureProxy = new PackageRegisterationEntityConfigureProxy();
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	public Object getSERegisterDAO(String packageName) // TODO-DAO: was HibernateDefaultImpDAO
			throws ServiceEntityInstallationException {
		Class seRegDAOType;
		String seRegDAOTpStr = ServiceEntityStringHelper.EMPTYSTRING;
		try {
			PackageRegisterationEntity packRegEntity = (PackageRegisterationEntity) this
					.getEntityNodeByKey(packageName,
							IServiceEntityNodeFieldConstant.ID,
							PackageRegisterationEntity.NODENAME, null, true);
			if (packRegEntity == null)
				throw new ServiceEntityInstallationException(
						ServiceEntityInstallationException.PARA_NON_PAK_REGISTERED,
						packageName);
			seRegDAOTpStr = packRegEntity.getSeRegisterDAOType();
			seRegDAOType = Class.forName(seRegDAOTpStr);
			Object seRegDAO = seRegDAOType.newInstance(); // TODO-DAO: was HibernateDefaultImpDAO
			return seRegDAO;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_FAIL_INIT_CLASS,
					seRegDAOTpStr);
		} catch (ServiceEntityConfigureException e) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

}