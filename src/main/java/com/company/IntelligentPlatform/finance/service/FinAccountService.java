package com.company.IntelligentPlatform.finance.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityService;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.finance.repository.FinAccountRepository;
import com.company.IntelligentPlatform.finance.repository.FinAccountTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Replaces: ThorsteinFinance FinAccountManager (ServiceEntityManager subclass)
 *
 * FinAccount has a tri-step approval workflow:
 *   audit → record → verify
 * Each step is triggered by an explicit action method.
 */
@Service
@Transactional
public class FinAccountService extends ServiceEntityService {

	@Autowired
	protected FinAccountRepository finAccountRepository;

	@Autowired
	protected FinAccountTitleRepository finAccountTitleRepository;

	public FinAccount create(FinAccount account, String userUUID, String orgUUID) {
		account.setAuditStatus(FinAccount.AUDIT_UNDONE);
		account.setRecordStatus(FinAccount.RECORDED_UNDONE);
		account.setVerifyStatus(FinAccount.VERIFIED_UNDONE);
		account.setFinanceTime(LocalDateTime.now());
		return insertSENode(finAccountRepository, account, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public FinAccount getByUuid(String uuid) {
		return getEntityNodeByUUID(finAccountRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<FinAccount> getByClient(String client) {
		return finAccountRepository.findByClient(client);
	}

	public FinAccount update(FinAccount account, String userUUID, String orgUUID) {
		return updateSENode(finAccountRepository, account, userUUID, orgUUID);
	}

	public void delete(String uuid) {
		deleteSENode(finAccountRepository, uuid);
	}

	// --- tri-step workflow actions ---

	public void audit(String uuid, String auditBy, String auditNote, boolean approve,
			String userUUID, String orgUUID) {
		FinAccount account = finAccountRepository.findById(uuid).orElseThrow();
		account.setAuditStatus(approve ? FinAccount.AUDIT_DONE : FinAccount.AUDIT_REJECT);
		account.setAuditBy(auditBy);
		account.setAuditTime(LocalDateTime.now());
		account.setAuditNote(auditNote);
		updateSENode(finAccountRepository, account, userUUID, orgUUID);
	}

	public void record(String uuid, String recordBy, String recordNote,
			String userUUID, String orgUUID) {
		FinAccount account = finAccountRepository.findById(uuid).orElseThrow();
		account.setRecordStatus(FinAccount.RECORDED_DONE);
		account.setRecordBy(recordBy);
		account.setRecordTime(LocalDateTime.now());
		account.setRecordNote(recordNote);
		updateSENode(finAccountRepository, account, userUUID, orgUUID);
	}

	public void verify(String uuid, String verifyBy, String verifyNote,
			String userUUID, String orgUUID) {
		FinAccount account = finAccountRepository.findById(uuid).orElseThrow();
		account.setVerifyStatus(FinAccount.VERIFIED_DONE);
		account.setVerifyBy(verifyBy);
		account.setVerifyTime(LocalDateTime.now());
		account.setVerifyNote(verifyNote);
		updateSENode(finAccountRepository, account, userUUID, orgUUID);
	}

	// --- FinAccountTitle ---

	public FinAccountTitle createTitle(FinAccountTitle title, String userUUID, String orgUUID) {
		return insertSENode(finAccountTitleRepository, title, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public FinAccountTitle getTitleByUuid(String uuid) {
		return getEntityNodeByUUID(finAccountTitleRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<FinAccountTitle> getTitlesByClient(String client) {
		return finAccountTitleRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<FinAccountTitle> getTitleChildren(String parentUUID) {
		return finAccountTitleRepository.findByParentAccountTitleUUID(parentUUID);
	}

	public FinAccountTitle updateTitle(FinAccountTitle title, String userUUID, String orgUUID) {
		return updateSENode(finAccountTitleRepository, title, userUUID, orgUUID);
	}

	public void deleteTitle(String uuid) {
		deleteSENode(finAccountTitleRepository, uuid);
	}

}
