package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.DocumentMatItemRawSearchRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.List;

@Service
public class CrossDocConvertRequest<TargetServiceModel extends ServiceModule, TargetItem extends ServiceEntityNode,
        TargetItemServiceModel extends ServiceModule> {

    public interface IGetTargetDocItemList {

        List<ServiceEntityNode> execute(DocumentMatItemRawSearchRequest searchRequest, LogonInfo logonInfo) throws
                SearchConfigureException, DocActionException;

    }

    public interface IFilterTargetDoc {

        boolean execute(ServiceEntityNode targetDoc, DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest) throws
                SearchConfigureException, DocActionException;

    }

    /**
     * Callback interface to check and filter if the newly created document stored in the provided <code>DocCrateContext</code>
     * can be used to contain the newly created target document item.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    public interface IFilterRootDocContext {

        boolean execute(CrossDocBatchConvertProfProxy.DocContentCreateContext docContentCreateContext,
                        DocMatItemNode prevProfMatItemNode, DocMatItemNode sourceMatItemNode) ;

    }

    public interface IConvertToTargetItem {

        DocMatItemNode execute(ConvertItemContext convertItemContext) throws DocActionException;

    }

    public interface IConvertToTarItemServiceModel<TargetServiceModel extends ServiceModule,
            TargetItemServiceModel extends ServiceModule> {

        TargetItemServiceModel execute(DocMatItemNode sourceMatItemNode, TargetServiceModel targetServiceModel,
                           TargetItemServiceModel targetItemServiceModel) throws DocActionException;

    }

    public interface IParseBatchGenRequest<TargetServiceModel extends ServiceModule> {

        void execute(DocumentMatItemBatchGenRequest genRequest, TargetServiceModel targetServiceModel) throws DocActionException;

    }

    public interface IFilterSelectedMatItem {

        boolean execute(DocMatItemNode docMatItemNode, MaterialStockKeepUnit materialStockKeepUnit, ServiceModule serviceModule);

    }

    public interface IGenerateTargetServiceModel<SourceServiceModel extends ServiceModule,
            TargetServiceModel extends ServiceModule>{

        TargetServiceModel execute(SourceServiceModel sourceServiceModel,
                                   DocumentContentSpecifier<SourceServiceModel, ?, ?> sourceDocSpecifier)
                throws ServiceEntityConfigureException;

    }

    public interface IConvertToTargetDoc {

        ServiceEntityNode execute(ConvertDocumentContext convertDocumentContext)
                throws ServiceEntityConfigureException;

    }

    protected int targetDocType;

    protected int sourceDocType;

    /**
     * A custom callback method used to retrieve the target document material item list based on specific context information.
     * This target item list is used to search for potential target document root lists, which can hold the newly created material items.\
     */
    private IGetTargetDocItemList getTargetDocItemList;

    /**
     * The `filterTargetDoc` method serves as the custom callback to filter an existing target document
     *  that can potentially be reused to store a newly generated target document item.
     */
    private IFilterTargetDoc filterTargetDoc;

    /**
     * Callback interface to check and filter if the newly created document stored in the provided <code>DocCrateContext</code>
     * can be used to contain the newly created target document item.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    private IFilterRootDocContext filterRootDocContext;

    /**
     * Acts as the custom callback used to filter each provided source document material item if it is valid to create target document item.
     */
    private IFilterSelectedMatItem filterSelectedMatItem;

    /**
     * Acts as the custom callback for initializing and setting
     * properties on a newly created target document instance.
     */
    private IGenerateTargetServiceModel<?,
            TargetServiceModel> generateTargetServiceModel;

    /**
     * Acts as the custom callback for converting properties
     * from for newly created target document from the existing source document when the target document is created.
     */
    private IConvertToTargetDoc convertToTargetDoc;

    /**
     * Customized callback method used to update the properties of the target material item
     * when establishing the cross-document relationship.
     */
    private IConvertToTargetItem covertToTargetItem;

    /**
     * Customized callback used to update the properties of the target material item
     * when establishing the cross-document relationship.
     */
    private IConvertToTargetItem covertToSourceItem;

    /**
     * Customized callback used to convert to the target material service item
     * when establishing the cross-document relationship.
     */
    private IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel>  convertToTarItemServiceModel;

    /**
     * Customized callback used to convert input request payload information to target document Service Module.
     */
    private IParseBatchGenRequest<TargetServiceModel> parseBatchGenRequest;

    public CrossDocConvertRequest() {
        this.setDefFilterTargetDoc();
        this.setDefConvertToTarItemServiceModel();
        this.setDefGenerateTargetServiceModel();
        this.setDefConvertToTargetDoc();
        this.setDefParseBatchGenRequest();
        this.setDefCovertToTargetItem();
        this.setDefCovertToSourceItem();
        this.setDefFilterSelectedMatItem();
    }

    public CrossDocConvertRequest(int targetDocType, IFilterTargetDoc filterTargetDoc,
                                  IGetTargetDocItemList getTargetDocItemList,
                                  IFilterSelectedMatItem filterSelectedMatItem,
                                  IGenerateTargetServiceModel<?, TargetServiceModel> generateTargetServiceModel,
                                  IConvertToTargetItem covertToTargetItem,
                                  IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel>  convertToTarItemServiceModel,
                                  IParseBatchGenRequest<TargetServiceModel> parseBatchGenRequest) {
        this.targetDocType = targetDocType;
        this.getTargetDocItemList = getTargetDocItemList;
        this.filterTargetDoc = filterTargetDoc;
        this.filterSelectedMatItem = filterSelectedMatItem;
        this.generateTargetServiceModel = generateTargetServiceModel;
        this.covertToTargetItem = covertToTargetItem;
        this.convertToTarItemServiceModel = convertToTarItemServiceModel;
        this.parseBatchGenRequest = parseBatchGenRequest;
    }

    public int getSourceDocType() {
        return sourceDocType;
    }

    public void setSourceDocType(int sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    /**
     *  Sets the default callback logic in each child class.
     *  The `filterTargetDoc` method serves as the custom callback to filter an existing target document
     *  that can potentially be reused to store a newly generated target document item.
     */
    protected void setDefFilterTargetDoc() {
        this.filterTargetDoc = null;
    }

    /**
     * Sets the default callback logic in each child class.
     * The `filterRootDocContext` Callback interface to check and filter if the newly created document stored in the provided <code>DocCrateContext</code>
     * can be used to contain the newly created target document item.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    protected void setDefFilterRootDocContext() {
        this.filterRootDocContext = null;
    }

    /**
     * [NOT used yet]: Sets the default callback logic in each child class.
     * The `generateTargetServiceModel` method acts as the custom callback for initializing and setting
     * properties on a newly created target document service module.
     */
    protected void setDefGenerateTargetServiceModel() {
        this.generateTargetServiceModel = null;
    }

    /**
     *  Sets the default callback logic in each child class.
     * `convertToTargetDoc` is the customized callback method used to convert properties for newly created target
     *  document from the existing source document when the target document is created.
     */
    protected void setDefConvertToTargetDoc() {
        this.convertToTargetDoc = null;
    }

    /**
     *  Sets the default callback logic in each child class.
     * `covertToTargetItem` is the customized callback method used to update the properties of the target material item
     *  when establishing the cross-document relationship.
     */
    protected void setDefCovertToTargetItem() {
        this.covertToTargetItem = null;
    }

    /**
     *  Sets the default callback logic in each child class.
     * `covertToSourceItem` is the customized callback method used to update the properties of the source material item
     *  when establishing the cross-document relationship.
     */
    protected void setDefCovertToSourceItem() {
        this.covertToSourceItem = null;
    }

    /**
     * Sets the default callback logic in each child class.
     * `getTargetDocItemList` is the custom callback method used to retrieve the target document material item list based on specific context information.
     * This target item list is used to search for potential target document root lists, which can hold the newly created material items.\
     */
    protected void setDefGetTargetDocItemList() {
        this.getTargetDocItemList = null;
    }

    /**
     * Sets the default callback logic in each child class.
     * `convertToTarItemServiceModel` is the Customized callback method used to convert to the target material Service Model item
     * when establishing the cross-document relationship.
     */
    public void setDefConvertToTarItemServiceModel() {
        this.convertToTarItemServiceModel = null;
    }

    /**
     * Sets the default callback logic in each child class.
     * `parseBatchGenRequest` is the Customized callback used to convert input request payload information to target document Service Module.
     */
    public void setDefParseBatchGenRequest() {
        this.parseBatchGenRequest = null;
    }

    /**
     *  Sets the default callback logic in each child class.
     * `filterSelectedMatItem` is the Custom callback used to filter each provided source document material item if it is valid to create target document item.
     */
    public void setDefFilterSelectedMatItem() {
        this.filterSelectedMatItem = null;
    }

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    /**
     * The `filterTargetDoc` method serves as the custom callback to filter an existing target document
     *  that can potentially be reused to store a newly generated target document item.
     */
    public IFilterTargetDoc getFilterTargetDoc() {
        return filterTargetDoc;
    }

    public void setFilterTargetDoc(IFilterTargetDoc filterTargetDoc) {
        this.filterTargetDoc = filterTargetDoc;
    }
   
    /**
     * The `filterRootDocContext` Callback interface to check and filter if the newly created document stored in the provided <code>DocCrateContext</code>
     * can be used to contain the newly created target document item.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    public IFilterRootDocContext getFilterRootDocContext() {
        return filterRootDocContext;
    }

    public void setFilterRootDocContext(IFilterRootDocContext filterRootDocContext) {
        this.filterRootDocContext = filterRootDocContext;
    }

    /**
     * [NOT used yet].
     * The `generateTargetServiceModel` method acts as the custom callback for initializing and setting properties on a newly created target document service module.
     */
    public IGenerateTargetServiceModel<?, TargetServiceModel> getGenerateTargetServiceModel() {
        return generateTargetServiceModel;
    }

    public void setGenerateTargetServiceModel(
            IGenerateTargetServiceModel<?, TargetServiceModel> generateTargetServiceModel) {
        this.generateTargetServiceModel = generateTargetServiceModel;
    }

    /**
     * The `covertToTargetItem` is the customized callback method used to update the properties of the target material item
     * when establishing the cross-document relationship.
     * to set the default callback logic in each child class, refer to the method: <code>setDefCovertToTargetItem</code>
     */
    public IConvertToTargetItem getCovertToTargetItem() {
        return covertToTargetItem;
    }

    public void setCovertToTargetItem(IConvertToTargetItem covertToTargetItem) {
        this.covertToTargetItem = covertToTargetItem;
    }

    /**
     * The `covertToSourceItem` is the customized callback method used to update the properties of the source material item
     * when establishing the cross-document relationship.
     * to set the default callback logic in each child class, refer to the method: <code>setDefCovertToSourceItem</code>
     */
    public IConvertToTargetItem getCovertToSourceItem() {
        return covertToSourceItem;
    }

    public void setCovertToSourceItem(IConvertToTargetItem covertToSourceItem) {
        this.covertToSourceItem = covertToSourceItem;
    }

    /**
     * The `convertToTarItemServiceModel` is the Customized callback method used to convert to the target material Service Model item.
     * to set the default callback logic in each child class, refer to the method: <code>setDefConvertToTarItemServiceModel</code>
     */
    public IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel> getConvertToTarItemServiceModel() {
        return convertToTarItemServiceModel;
    }

    public void setConvertToTarItemServiceModel(
            IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModel) {
        this.convertToTarItemServiceModel = convertToTarItemServiceModel;
    }

    /**
     * The `parseBatchGenRequest` is the Customized callback used to convert input request payload information to target document Service Module.
     * to set the default callback logic in each child class, refer to the method: <code>setDefParseBatchGenRequest</code>
     */
    public IParseBatchGenRequest<TargetServiceModel> getParseBatchGenRequest() {
        return parseBatchGenRequest;
    }

    public void setParseBatchGenRequest(IParseBatchGenRequest<TargetServiceModel> parseBatchGenRequest) {
        this.parseBatchGenRequest = parseBatchGenRequest;
    }

    /**
     * The `filterSelectedMatItem` is the Customized callback used to filter each provided source document material item if it is valid to create target document item.
     * to set the default callback logic in each child class, refer to the method: <code>setDefGetFilterSelectedMatItem</code>
     */
    public IFilterSelectedMatItem getFilterSelectedMatItem() {
        return filterSelectedMatItem;
    }

    public void setFilterSelectedMatItem(IFilterSelectedMatItem filterSelectedMatItem) {
        this.filterSelectedMatItem = filterSelectedMatItem;
    }

    /**
     * `getTargetDocItemList` is the custom callback method used to retrieve the target document material item list based on specific context information.
     * This target item list is used to search for potential target document root lists, which can hold the newly created material items.\
     */
    public IGetTargetDocItemList getGetTargetDocItemList() {
        return getTargetDocItemList;
    }

    public void setGetTargetDocItemList(IGetTargetDocItemList getTargetDocItemList) {
        this.getTargetDocItemList = getTargetDocItemList;
    }

    /**
     * The `convertToTargetDoc` is the customized callback method used to convert properties for newly created target document from the existing source document
     * to set the default callback logic in each child class, refer to the method: <code>setDefConvertToTargetDoc</code>
     */
    public IConvertToTargetDoc getConvertToTargetDoc() {
        return convertToTargetDoc;
    }

    public void setConvertToTargetDoc(IConvertToTargetDoc convertToTargetDoc) {
        this.convertToTargetDoc = convertToTargetDoc;
    }

    public static class InputOption{

        // Indicates whether you need to update UUID in the next doc mat item, such as for inventory transfer item.
        private boolean updateNextItemUUID;

        private boolean skipIfPrevProfEmpty;

        private boolean reservePrevDoc;

        public InputOption() {
            this.updateNextItemUUID = false;
            this.skipIfPrevProfEmpty = false;
            this.reservePrevDoc = false;
        }

        public InputOption(boolean updateNextItemUUID) {
            this.updateNextItemUUID = updateNextItemUUID;
        }

        public InputOption(boolean updateNextItemUUID, boolean skipIfPrevProfEmpty) {
            this.updateNextItemUUID = updateNextItemUUID;
            this.skipIfPrevProfEmpty = skipIfPrevProfEmpty;
        }

        public boolean getUpdateNextItemUUID() {
            return updateNextItemUUID;
        }

        public void setUpdateNextItemUUID(boolean updateNextItemUUID) {
            this.updateNextItemUUID = updateNextItemUUID;
        }

        public boolean getSkipIfPrevProfEmpty() {
            return skipIfPrevProfEmpty;
        }

        public void setSkipIfPrevProfEmpty(boolean skipIfPrevProfEmpty) {
            this.skipIfPrevProfEmpty = skipIfPrevProfEmpty;
        }

        public boolean getReservePrevDoc() {
            return reservePrevDoc;
        }

        public void setReservePrevDoc(boolean reservePrevDoc) {
            this.reservePrevDoc = reservePrevDoc;
        }
    }

    /**
     * Document Item Conversion Context class: used for conversion cross-document material item.
     */
    public static class ConvertItemContext {

        private DocMatItemNode sourceMatItemNode;

        private DocMatItemNode targetMatItemNode;

        private DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest;

        public ConvertItemContext(DocMatItemNode sourceMatItemNode, DocMatItemNode targetMatItemNode, DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest) {
            this.sourceMatItemNode = sourceMatItemNode;
            this.targetMatItemNode = targetMatItemNode;
            this.documentMatItemBatchGenRequest = documentMatItemBatchGenRequest;
        }

        public DocMatItemNode getSourceMatItemNode() {
            return sourceMatItemNode;
        }

        public void setSourceMatItemNode(DocMatItemNode sourceMatItemNode) {
            this.sourceMatItemNode = sourceMatItemNode;
        }

        public DocMatItemNode getTargetMatItemNode() {
            return targetMatItemNode;
        }

        public void setTargetMatItemNode(DocMatItemNode targetMatItemNode) {
            this.targetMatItemNode = targetMatItemNode;
        }

        public DocumentMatItemBatchGenRequest getDocumentMatItemBatchGenRequest() {
            return documentMatItemBatchGenRequest;
        }

        public void setDocumentMatItemBatchGenRequest(DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest) {
            this.documentMatItemBatchGenRequest = documentMatItemBatchGenRequest;
        }
    }

    /**
     * Document Root node Conversion Context class: used for conversion cross-document after target document is created.
     */
    public static class ConvertDocumentContext {

        private ServiceEntityNode targetDocument;

        private ServiceModule sourceServiceModel;

        private DocumentContentSpecifier<?, ?, ?> sourceDocSpecifier;

        public ConvertDocumentContext(ServiceEntityNode targetDocument, ServiceModule sourceServiceModel, DocumentContentSpecifier<?, ?, ?> sourceDocSpecifier) {
            this.targetDocument = targetDocument;
            this.sourceServiceModel = sourceServiceModel;
            this.sourceDocSpecifier = sourceDocSpecifier;
        }

        public ServiceEntityNode getTargetDocument() {
            return targetDocument;
        }

        public void setTargetDocument(ServiceEntityNode targetDocument) {
            this.targetDocument = targetDocument;
        }

        public ServiceModule getSourceServiceModel() {
            return sourceServiceModel;
        }

        public void setSourceServiceModel(ServiceModule sourceServiceModel) {
            this.sourceServiceModel = sourceServiceModel;
        }

        public DocumentContentSpecifier<?, ?, ?> getSourceDocSpecifier() {
            return sourceDocSpecifier;
        }

        public void setSourceDocSpecifier(DocumentContentSpecifier<?, ?, ?> sourceDocSpecifier) {
            this.sourceDocSpecifier = sourceDocSpecifier;
        }

    }

}
