package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Repository to manage all the instance of Message Template Handler Classes
 */
@Service
public class MessageTemplateHandlerRepository {

    public static final String Handler_prodOrderPickingToOutboundHandler = "prodOrderPickingToOutboundHandler";

    public static final String Handler_prodOrderPickingToPickingHandler = "prodOrderPickingToPickingHandler";

    public static final String Handler_prodOrderPickingLackInPlanHandler = "prodOrderPickingLackInPlanHandler";

    /**
     * Registration Area to register executable Units here
     */
    @Qualifier(Handler_prodOrderPickingToOutboundHandler)
    @Autowired(required = false)
    protected MessageTemplateHandler prodOrderPickingToOutboundHandler;

    @Qualifier(Handler_prodOrderPickingToPickingHandler)
    @Autowired(required = false)
    protected MessageTemplateHandler prodOrderPickingToPickingHandler;

    @Qualifier(Handler_prodOrderPickingLackInPlanHandler)
    @Autowired(required = false)
    protected MessageTemplateHandler prodOrderPickingLackInPlanHandler;

    protected Logger logger = LoggerFactory.getLogger(MessageTemplateHandlerRepository.class);

    public MessageTemplateHandler getHandlerById(String handlerId)
            throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException {
        Field field = ServiceEntityFieldsHelper.getServiceField(MessageTemplateHandlerRepository.class, handlerId);
        field.setAccessible(true);
        return (MessageTemplateHandler)field.get(this);
    }

    public List<MessageTemplateHandler> getAllHandlerList(){
        List<Field> allFieldList = this.getHandlerFieldList();
        if(ServiceCollectionsHelper.checkNullList(allFieldList)){
            return new ArrayList<MessageTemplateHandler>();
        }
        List<MessageTemplateHandler> resultList = new ArrayList<MessageTemplateHandler>();
        for(Field field:allFieldList){
            field.setAccessible(true);
            try {
                MessageTemplateHandler messageTemplateHandler = (MessageTemplateHandler) field.get(this);
                if(messageTemplateHandler != null){
                    resultList.add(messageTemplateHandler);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // log the issue and configure
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
        return resultList;
    }

    private List<Field> getHandlerFieldList(){
        List<Field> allFieldList = ServiceEntityFieldsHelper.getFieldsList(MessageTemplateHandlerRepository.class);
        if(ServiceCollectionsHelper.checkNullList(allFieldList)){
            return null;
        }
        List<Field> resultFieldList = new ArrayList<Field>();
        for(Field field:allFieldList){
            field.setAccessible(true);
            if(!MessageTemplateHandler.class.getSimpleName().equals(field.getType().getSimpleName())){
                continue;
            }
            resultFieldList.add(field);
        }
        return resultFieldList;

    }
}
