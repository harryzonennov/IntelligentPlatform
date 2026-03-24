package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.repository.LogonUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinPlatform LogonUserManager (ServiceEntityManager subclass)
 */
@Service
@Transactional
public class LogonUserService extends ServiceEntityService {

	@Autowired
	protected LogonUserRepository logonUserRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	public LogonUser create(LogonUser user, String userUUID, String orgUUID) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setStatus(LogonUser.STATUS_INIT);
		return insertSENode(logonUserRepository, user, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public LogonUser getByUuid(String uuid) {
		return getEntityNodeByUUID(logonUserRepository, uuid);
	}

	@Transactional(readOnly = true)
	public LogonUser getByName(String name) {
		return logonUserRepository.findByName(name).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<LogonUser> getAll() {
		return logonUserRepository.findAll();
	}

	public LogonUser update(LogonUser user, String userUUID, String orgUUID) {
		return updateSENode(logonUserRepository, user, userUUID, orgUUID);
	}

	public void changePassword(String uuid, String newRawPassword, String userUUID, String orgUUID) {
		LogonUser user = logonUserRepository.findById(uuid).orElseThrow();
		user.setPassword(passwordEncoder.encode(newRawPassword));
		user.setPasswordInitFlag(0);
		updateSENode(logonUserRepository, user, userUUID, orgUUID);
	}

	public void setStatus(String uuid, int status, String userUUID, String orgUUID) {
		LogonUser user = logonUserRepository.findById(uuid).orElseThrow();
		user.setStatus(status);
		updateSENode(logonUserRepository, user, userUUID, orgUUID);
	}

	public void delete(String uuid) {
		deleteSENode(logonUserRepository, uuid);
	}

}
