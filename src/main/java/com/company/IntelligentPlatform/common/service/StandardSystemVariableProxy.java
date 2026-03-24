package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StandardSystemVariableProxy {

	public static final String LogonUserUUID = "logonUserUUID";

	public static final String LogonOrganizationUUID = "logonOrganizationUUID";

	public static final String LogonOrganization = "logonOrganization";

	public static final String CurrentSystemTime = "currentSystemTime";

	public static final String varLogonUserUUID () {
		return "${" + LogonUserUUID + "}";
	}

	public static final String varLogonOrganizationUUID () {
		return "${" + LogonOrganizationUUID + "}";
	}

	public static final String varLogonOrganization () {
		return "${" + LogonOrganization + "}";
	}

	public static final String varCurrentSystemTime () {
		return "${" + CurrentSystemTime + "}";
	}

	/**
	 * Validates the format of the input key and extracts its content if valid.
	 *
	 * <p>The input key must follow the specific format: '${xxxx}', where:
	 * - '${' is the opening marker, 'xxxx' is any non-empty content inside the braces, and '}' is the closing marker.
	 * - If the key does not match the required format or is null/empty, the method either throws an exception or returns null depending on the condition.
	 *
	 * Example inputs and outputs:
	 * 	${username}		username	Matches the pattern, returns "username".
	 * 	${123}		123	Matches the pattern, returns "123".
	 * 	username	null	Doesn't match the pattern, returns null.
	 * `` (empty string)	Exception	Throws DocActionException.
	 * 	null		Exception	Throws DocActionException.
	 * 	${}		null	Matches the pattern but nothing inside the braces, invalid input.
	 *
	 * @param key The input string to validate. It should be non-empty and formatted  as '${xxxx}' (e.g., '${username}').
	 * @return The content inside the braces ('xxxx') if the key matches the format, or {@code null} if the format is invalid.
	 *
	 * @throws DocActionException if the input key is null, empty, or not provided.
	 */
	public String checkInputKeyFormat(String key) throws DocActionException {
		if (ServiceEntityStringHelper.checkNullString(key)) {
			throw new DocActionException(DocActionException.PARA_MISS_CONFIG, "key is empty");
		}
		Pattern pattern = Pattern.compile("\\$\\{(.+)\\}");
		Matcher matcher = pattern.matcher(key);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * Get the system variable value according to the input key, if input key doesn't match correct system variable
	 * format, will return null value, if the relative system variable can't be retrieved according to the input key,
	 * DocActionException will be raised.
	 * @param inputKey
	 * @param <T>
	 * @return
	 * @throws DocActionException
	 */
	public <T> T getSystemVariableValue(String inputKey, LogonInfo logonInfo)
			throws DocActionException {
		ISystemVariableConverter<?> systemVariableConverter = getSystemVariableConverter(inputKey);
		if (systemVariableConverter == null) {
			return null;
		}
		return (T) systemVariableConverter.getValue(logonInfo);
	}


	/**
	 * Retrieves the appropriate `system variable` converter class based on the provided input key.
	 *
	 * `System variables` are built-in variables provided by the framework.
	 * Some common examples include:
	 * - Logon user UUID
	 * - Logon organization UUID
	 * - Logon organization module
	 * - Current time module
	 *
	 * This method checks whether the provided input key matches the format for a system variable
	 * and, if valid, returns the corresponding converter. If the key does not match the expected format,
	 * the method returns `null`. If a matching converter cannot be found, a {@link DocActionException} is thrown.
	 *
	 * @param inputKey The input key in the expected system variable format.
	 * @return An instance of {@link ISystemVariableConverter<?>} corresponding to the input key, or `null`
	 *         if the input key does not match the expected format.
	 * @throws DocActionException If no matching system variable converter is found or the input key is misconfigured.
	 */
	public ISystemVariableConverter<?> getSystemVariableConverter(String inputKey) throws DocActionException {
		String key = checkInputKeyFormat(inputKey);
		if (ServiceEntityStringHelper.checkNullString(key)) {
			// In case the inputKey does not match system variable format, just return null value
			return null;
		}
		List<ISystemVariableConverter<?>> systemVariableConverterList = getSystemVariableConverterList();
		for (ISystemVariableConverter<?> systemVariableConverter: systemVariableConverterList) {
			if(key.equals(systemVariableConverter.getKey())) {
				return systemVariableConverter;
			}
		}
		throw new DocActionException(DocActionException.PARA_MISS_CONFIG, "System variable:" + key);
	}

	public int getValueCategory(String inputKey) throws DocActionException {
		ISystemVariableConverter<?> systemVariableConverter = getSystemVariableConverter(inputKey);
		if (systemVariableConverter == null) {
			return 0;
		}
		return systemVariableConverter.getValueCategory();
	}

	public List<ISystemVariableConverter<?>> getSystemVariableConverterList() {
		return ServiceCollectionsHelper.asList(new LogonUserUUIDVariableConverter(),
				new LogonOrgUUIDVariableConverter(),
				new LogonOrgVariableConverter(),
				new CurrentSystemTimeConverter());
	}


	public interface ISystemVariableConverter<T> {

		int VALUE_CAT_SIM_FIELD = 1;

		int VALUE_CAT_COM_OBJECT = 2;

		String getKey();

		T getValue(SerialLogonInfo serialLogonInfo);

		T getValue(LogonInfo logonInfo);

		/**
		 * When current value category is compound object, using this method to get the field name list
		 * @return field name list
		 */
        List<String> getFieldNameList();

		int getValueCategory();

	}

	public abstract class DefaultVariableConverter<T> implements ISystemVariableConverter<T>{

		@Override
		public int getValueCategory() {
			return VALUE_CAT_SIM_FIELD;
		}

		@Override
		public List<String> getFieldNameList() {
			return null;
		}
	}

	public class LogonUserUUIDVariableConverter extends DefaultVariableConverter<String> {


		@Override
		public String getKey() {
			return StandardSystemVariableProxy.LogonUserUUID;
		}

		@Override
		public String getValue(SerialLogonInfo serialLogonInfo) {
			return serialLogonInfo.getRefUserUUID();
		}

		@Override
		public String getValue(LogonInfo logonInfo) {
			return logonInfo.getRefUserUUID();
		}

	}

	public class LogonOrgUUIDVariableConverter extends DefaultVariableConverter<String> {


		@Override
		public String getKey() {
			return StandardSystemVariableProxy.LogonOrganizationUUID;
		}

		@Override
		public String getValue(SerialLogonInfo serialLogonInfo) {
			return serialLogonInfo.getResOrgUUID();
		}

		@Override
		public String getValue(LogonInfo logonInfo) {
			return logonInfo.getResOrgUUID();
		}
	}

	public class LogonOrgVariableConverter extends DefaultVariableConverter<Organization> {

		@Override
		public String getKey() {
			return StandardSystemVariableProxy.LogonOrganization;
		}

		@Override
		public Organization getValue(SerialLogonInfo serialLogonInfo) {
			return null;
		}

		@Override
		public Organization getValue(LogonInfo logonInfo) {
			return logonInfo.getHomeOrganization();
		}

		@Override
		public int getValueCategory() {
			return VALUE_CAT_COM_OBJECT;
		}

		@Override
		public List<String> getFieldNameList() {
			return ServiceCollectionsHelper.asList(Account.FIELD_fax, Account.FIELD_bankAccount,
					Account.FIELD_depositBank, Account.FIELD_address, Account.FIELD_taxNumber, Account.FIELD_telephone);
		}
	}

	public class CurrentSystemTimeConverter extends DefaultVariableConverter<Date> {


		@Override
		public String getKey() {
			return StandardSystemVariableProxy.CurrentSystemTime;
		}

		@Override
		public Date getValue(SerialLogonInfo serialLogonInfo) {
			return new Date();
		}

		@Override
		public Date getValue(LogonInfo logonInfo) {
			return new Date();
		}
	}


}
