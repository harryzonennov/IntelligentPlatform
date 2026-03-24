package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.CalendarSettingUIModel;
import com.company.IntelligentPlatform.common.dto.CalendarWorkTimeSettingUIModel;
// TODO-DAO: import ...CalendarSettingDAO;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.CalendarSetting;
import com.company.IntelligentPlatform.common.model.CalendarWorkTimeSetting;
import com.company.IntelligentPlatform.common.model.CalendarSettingConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [CalendarSetting]
 *
 * @author
 * @date Sun May 29 21:24:41 CST 2016
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class CalendarSettingManager extends ServiceEntityManager {

    // TODO-DAO: @Autowired

    // TODO-DAO:     protected CalendarSettingDAO calendarSettingDAO;

    @Autowired
    protected CalendarSettingConfigureProxy calendarSettingConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    public CalendarSettingManager() {
        super.seConfigureProxy = new CalendarSettingConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new CalendarSettingDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(calendarSettingDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(calendarSettingConfigureProxy);
    }

    public void convCalendarSettingToUI(CalendarSetting calendarSetting,
                                        CalendarSettingUIModel calendarSettingUIModel) {
        if (calendarSetting != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(calendarSetting, calendarSettingUIModel);
            calendarSettingUIModel.setFreeMondayFlag(calendarSetting
                    .getFreeMondayFlag());
            calendarSettingUIModel.setFreeTuesdayFlag(calendarSetting
                    .getFreeTuesdayFlag());
            calendarSettingUIModel.setFreeWednesdayFlag(calendarSetting
                    .getFreeWednesdayFlag());
            calendarSettingUIModel.setFreeThursdayFlag(calendarSetting
                    .getFreeThursdayFlag());
            calendarSettingUIModel.setFreeFridayFlag(calendarSetting
                    .getFreeFridayFlag());
            calendarSettingUIModel.setFreeSaturdayFlag(calendarSetting
                    .getFreeSaturdayFlag());
            calendarSettingUIModel.setFreeSundayFlag(calendarSetting
                    .getFreeSundayFlag());
        }
    }

    public void convUIToCalendarSetting(CalendarSetting rawEntity,
                                        CalendarSettingUIModel calendarSettingUIModel) {
        DocFlowProxy.convUIToServiceEntityNode(calendarSettingUIModel, rawEntity);
        rawEntity.setFreeMondayFlag(calendarSettingUIModel.getFreeMondayFlag());
        rawEntity.setFreeTuesdayFlag(calendarSettingUIModel
                .getFreeTuesdayFlag());
        rawEntity.setFreeWednesdayFlag(calendarSettingUIModel
                .getFreeWednesdayFlag());
        rawEntity.setFreeThursdayFlag(calendarSettingUIModel
                .getFreeThursdayFlag());
        rawEntity.setFreeFridayFlag(calendarSettingUIModel.getFreeFridayFlag());
        rawEntity.setFreeSaturdayFlag(calendarSettingUIModel
                .getFreeSaturdayFlag());
        rawEntity.setFreeSundayFlag(calendarSettingUIModel.getFreeSundayFlag());
    }

    public void convCalendarWorkTimeSettingToUI(
            CalendarWorkTimeSetting calendarWorkTimeSetting,
            CalendarWorkTimeSettingUIModel calendarWorkTimeSettingUIModel)
            throws ServiceEntityInstallationException {
        if (calendarWorkTimeSetting != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(calendarWorkTimeSetting, calendarWorkTimeSettingUIModel);
            calendarWorkTimeSettingUIModel
                    .setDailyShift(calendarWorkTimeSetting.getDailyShift());
            Map<Integer, String> dailyShiftMap = serviceDropdownListHelper
                    .getUIDropDownMap(CalendarWorkTimeSettingUIModel.class,
                            "dailyShift");
            String dailyShiftValue = dailyShiftMap.get(calendarWorkTimeSetting
                    .getDailyShift());
            calendarWorkTimeSettingUIModel.setDailyShiftValue(dailyShiftValue);
            calendarWorkTimeSettingUIModel
                    .setStartDate1(calendarWorkTimeSetting.getStartDate1());
            calendarWorkTimeSettingUIModel.setEndDate1(calendarWorkTimeSetting
                    .getEndDate1());
            calendarWorkTimeSettingUIModel
                    .setStartDate2(calendarWorkTimeSetting.getStartDate2());
            calendarWorkTimeSettingUIModel.setEndDate2(calendarWorkTimeSetting
                    .getEndDate2());
            calendarWorkTimeSettingUIModel
                    .setStartDate3(calendarWorkTimeSetting.getStartDate3());
            calendarWorkTimeSettingUIModel.setEndDate3(calendarWorkTimeSetting
                    .getEndDate3());
            calendarWorkTimeSettingUIModel
                    .setStartDate4(calendarWorkTimeSetting.getStartDate4());
            calendarWorkTimeSettingUIModel.setEndDate4(calendarWorkTimeSetting
                    .getEndDate4());
            String workTimePeriod = generateWorkTimePeriod(calendarWorkTimeSetting);
            calendarWorkTimeSettingUIModel.setWorkTimePeriod(workTimePeriod);
        }
    }

    protected String getStartEndTimeStringCore(Date startTime, Date endTime) {
        String startTimeStr = ServiceEntityStringHelper.EMPTYSTRING;
        if (startTime != null) {
            startTimeStr = DefaultDateFormatConstant.HOUR_MIN_FORMAT
                    .format(startTime);
        }
        String endTimeStr = ServiceEntityStringHelper.EMPTYSTRING;
        if (endTime != null) {
            endTimeStr = DefaultDateFormatConstant.HOUR_MIN_FORMAT
                    .format(endTime);
        }
        String result = ServiceEntityStringHelper.EMPTYSTRING;
        if (!ServiceEntityStringHelper.checkNullString(startTimeStr)
                && !ServiceEntityStringHelper.checkNullString(endTimeStr)) {
            result = startTimeStr + "-" + endTimeStr;
            return result;
        }
        if (!ServiceEntityStringHelper.checkNullString(startTimeStr)) {
            return startTimeStr;
        }
        if (!ServiceEntityStringHelper.checkNullString(endTimeStr)) {
            return endTimeStr;
        }
        return result;
    }

    public String generateWorkTimePeriod(
            CalendarWorkTimeSetting calendarWorkTimeSetting) {
        if (calendarWorkTimeSetting != null) {
            String result = ServiceEntityStringHelper.EMPTYSTRING;
            if (calendarWorkTimeSetting.getDailyShift() == CalendarWorkTimeSetting.DAILYSHIFT1) {
                return getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate1(),
                        calendarWorkTimeSetting.getEndDate1());
            }
            if (calendarWorkTimeSetting.getDailyShift() == CalendarWorkTimeSetting.DAILYSHIFT2) {
                result = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate1(),
                        calendarWorkTimeSetting.getEndDate1());
                String result2 = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate2(),
                        calendarWorkTimeSetting.getEndDate2());
                if (!ServiceEntityStringHelper.checkNullString(result2)) {
                    result = result + ";" + result2;
                }
                return result;
            }
            if (calendarWorkTimeSetting.getDailyShift() == CalendarWorkTimeSetting.DAILYSHIFT3) {
                result = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate1(),
                        calendarWorkTimeSetting.getEndDate1());
                String result2 = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate2(),
                        calendarWorkTimeSetting.getEndDate2());
                if (!ServiceEntityStringHelper.checkNullString(result2)) {
                    result = result + ";" + result2;
                }
                String result3 = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate3(),
                        calendarWorkTimeSetting.getEndDate3());
                if (!ServiceEntityStringHelper.checkNullString(result3)) {
                    result = result + ";" + result3;
                }
                return result;
            }
            if (calendarWorkTimeSetting.getDailyShift() == CalendarWorkTimeSetting.DAILYSHIFT4) {
                result = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate1(),
                        calendarWorkTimeSetting.getEndDate1());
                String result2 = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate2(),
                        calendarWorkTimeSetting.getEndDate2());
                if (!ServiceEntityStringHelper.checkNullString(result2)) {
                    result = result + ";" + result2;
                }
                String result3 = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate3(),
                        calendarWorkTimeSetting.getEndDate3());
                if (!ServiceEntityStringHelper.checkNullString(result3)) {
                    result = result + ";" + result3;
                }
                String result4 = getStartEndTimeStringCore(
                        calendarWorkTimeSetting.getStartDate4(),
                        calendarWorkTimeSetting.getEndDate4());
                if (!ServiceEntityStringHelper.checkNullString(result4)) {
                    result = result + ";" + result4;
                }
                return result;
            }
        }
        return null;
    }

    public void convUIToCalendarWorkTimeSetting(
            CalendarWorkTimeSetting rawEntity,
            CalendarWorkTimeSettingUIModel calendarWorkTimeSettingUIModel) {
        DocFlowProxy.convUIToServiceEntityNode(calendarWorkTimeSettingUIModel, rawEntity);
        rawEntity.setDailyShift(calendarWorkTimeSettingUIModel.getDailyShift());
        rawEntity.setStartDate1(calendarWorkTimeSettingUIModel.getStartDate1());
        rawEntity.setEndDate1(calendarWorkTimeSettingUIModel.getEndDate1());
        rawEntity.setStartDate2(calendarWorkTimeSettingUIModel.getStartDate2());
        rawEntity.setEndDate2(calendarWorkTimeSettingUIModel.getEndDate2());
        rawEntity.setStartDate3(calendarWorkTimeSettingUIModel.getStartDate3());
        rawEntity.setEndDate3(calendarWorkTimeSettingUIModel.getEndDate3());
        rawEntity.setStartDate4(calendarWorkTimeSettingUIModel.getStartDate4());
        rawEntity.setEndDate4(calendarWorkTimeSettingUIModel.getEndDate4());
    }

    public CalendarWorkTimeSetting getWorkTimeSettingByShiftType(
            String baseUUID, int shiftType, String client)
            throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList( new
                ServiceBasicKeyStructure(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID));
        keyList.add(new ServiceBasicKeyStructure(shiftType, "dailyShift"));
        List<ServiceEntityNode> rawWorkTimeSettingList = getEntityNodeListByKeyList(
                keyList, CalendarWorkTimeSetting.NODENAME, client, null);
        if (ServiceCollectionsHelper.checkNullList(rawWorkTimeSettingList)) {
            return null;
        } else {
            return (CalendarWorkTimeSetting) rawWorkTimeSettingList.get(0);
        }
    }
}
