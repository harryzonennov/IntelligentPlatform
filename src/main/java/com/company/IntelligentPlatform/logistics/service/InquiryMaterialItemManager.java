package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InquiryMaterialItemUIModel;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDateTime;

@Service
public class InquiryMaterialItemManager {

    public static final String METHOD_ConvInquiryMaterialItemToUI = "convInquiryMaterialItemToUI";

    public static final String METHOD_ConvUIToInquiryMaterialItem = "convUIToInquiryMaterialItem";

    public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected InquiryManager inquiryManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger;

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), Inquiry.NODENAME,
                        request.getUuid(), InquiryMaterialItem.NODENAME, inquiryManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<Inquiry>) inquiry -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(inquiry, null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<InquiryMaterialItem>) (inquiryMaterialItem, pageHeaderModel) ->
                        docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(inquiryMaterialItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }


    public void convInquiryMaterialItemToUI(
            InquiryMaterialItem inquiryMaterialItem,
            InquiryMaterialItemUIModel inquiryMaterialItemUIModel)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        convInquiryMaterialItemToUI(inquiryMaterialItem,
                inquiryMaterialItemUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convInquiryMaterialItemToUI(
            InquiryMaterialItem inquiryMaterialItem,
            InquiryMaterialItemUIModel inquiryMaterialItemUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (inquiryMaterialItem != null) {
            docFlowProxy.convDocMatItemToUI(inquiryMaterialItem,
                    inquiryMaterialItemUIModel, logonInfo);
            inquiryMaterialItemUIModel.setItemStatus(inquiryMaterialItem
                    .getItemStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = inquiryManager.initStatus(logonInfo
                        .getLanguageCode());
                inquiryMaterialItemUIModel.setItemStatusValue(statusMap
                        .get(inquiryMaterialItem.getItemStatus()));
            }
            if (inquiryMaterialItem.getRequireShippingTime() != null) {
                inquiryMaterialItemUIModel
                        .setRequireShippingTime(DefaultDateFormatConstant.DATE_FORMAT
                                .format(inquiryMaterialItem
                                        .getRequireShippingTime()));
            }
            inquiryMaterialItemUIModel.setRefUnitName(inquiryMaterialItem
                    .getRefUnitName());
            inquiryMaterialItemUIModel.setShippingPoint(inquiryMaterialItem
                    .getShippingPoint());
            inquiryMaterialItemUIModel.setNote(inquiryMaterialItem.getNote());
            inquiryMaterialItemUIModel.setCurrencyCode(inquiryMaterialItem
                    .getCurrencyCode());
            inquiryMaterialItemUIModel
                    .setFirstItemPrice(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inquiryMaterialItem
                                    .getFirstItemPrice()));
            inquiryMaterialItemUIModel
                    .setFirstUnitPrice(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inquiryMaterialItem
                                    .getFirstUnitPrice()));
            inquiryMaterialItemUIModel.setCurrencyCode(inquiryMaterialItem
                    .getCurrencyCode());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:inquiryMaterialItem
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToInquiryMaterialItem(
            InquiryMaterialItemUIModel inquiryMaterialItemUIModel,
            InquiryMaterialItem rawEntity) {
        docFlowProxy.convUIToDocMatItem(inquiryMaterialItemUIModel, rawEntity);
        if (!ServiceEntityStringHelper
                .checkNullString(inquiryMaterialItemUIModel
                        .getRequireShippingTime())) {
            try {
                rawEntity
                        .setRequireShippingTime(DefaultDateFormatConstant.DATE_FORMAT.parse(inquiryMaterialItemUIModel
                                        .getRequireShippingTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper
                .checkNullString(inquiryMaterialItemUIModel
                        .getPrevDocMatItemUUID())) {
            rawEntity.setPrevDocMatItemUUID(inquiryMaterialItemUIModel
                    .getPrevDocMatItemUUID());
        }
        rawEntity.setShippingPoint(inquiryMaterialItemUIModel
                .getShippingPoint());
        rawEntity.setShippingPoint(inquiryMaterialItemUIModel
                .getShippingPoint());
        rawEntity.setCurrencyCode(inquiryMaterialItemUIModel.getCurrencyCode());
        if(inquiryMaterialItemUIModel.getItemStatus() > 0){
            rawEntity.setItemStatus(inquiryMaterialItemUIModel.getItemStatus());
        }
        rawEntity.setRefUnitName(inquiryMaterialItemUIModel.getRefUnitName());
        rawEntity.setCurrencyCode(inquiryMaterialItemUIModel.getCurrencyCode());
        rawEntity.setFirstItemPrice(ServiceEntityDoubleHelper
                .trancateDoubleScale2(inquiryMaterialItemUIModel
                        .getFirstItemPrice()));
        rawEntity.setFirstUnitPrice(ServiceEntityDoubleHelper
                .trancateDoubleScale2(inquiryMaterialItemUIModel
                        .getFirstUnitPrice()));
    }

    public void convParentDocToItemUI(Inquiry inquiry,
                                    InquiryMaterialItemUIModel inquiryMaterialItemUIModel){
        this.convParentDocToItemUI(inquiry, inquiryMaterialItemUIModel, null);
    }

    public void convParentDocToItemUI(Inquiry inquiry,
                                    InquiryMaterialItemUIModel inquiryMaterialItemUIModel,
                                    LogonInfo logonInfo)  {
        if (inquiry != null) {
            docFlowProxy.convParentDocToItemUI(inquiry, inquiryMaterialItemUIModel, logonInfo);
        }
    }

}
