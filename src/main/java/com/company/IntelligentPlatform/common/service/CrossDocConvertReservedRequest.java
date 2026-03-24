package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.List;

public class CrossDocConvertReservedRequest <TargetServiceModel extends ServiceModule,
        TargetItem extends ServiceEntityNode,
        TargetItemServiceModel extends ServiceModule> extends CrossDocConvertRequest<TargetServiceModel, TargetItem,
        TargetItemServiceModel>{

    public interface IGetALLSourceMatItemListBySelectedReserved{

        List<ServiceEntityNode> execute(List<ServiceEntityNode> selectedReservedMatItemList, int reservedDocType) throws
                ServiceEntityConfigureException;

    }

    public interface IGetSourceMatItemListBySelectedReserved{

        List<ServiceEntityNode> execute(DocMatItemNode selectedReservedMatItem,
                                        List<ServiceEntityNode> allSourceMatItemListBySelectedReserved) throws ServiceEntityConfigureException;

    }

    public interface IFilterRootDocContextReserved{

        boolean execute(CrossDocBatchConvertReservedProxy.DocContentCreateContext docContentCreateContext,
                        DocMatItemNode reservedMatItemNode, DocMatItemNode sourceMatItemNode) ;

    }

    public interface IConvertToTargetItemReserved<TargetItem extends DocMatItemNode>{

        CrossDocBatchConvertReservedProxy.DocMatItemCreateContext execute(CrossDocBatchConvertReservedProxy.DocMatItemCreateContext docMatItemCreateContext,
                                                                          DocMatItemNode reservedMatItemNode,
                                                                          StorageCoreUnit storageCoreUnitContext) throws DocActionException;

    }

    public interface IConvertToTarItemServiceModelReserved<TargetServiceModel extends ServiceModule,
            TargetItemServiceModel extends ServiceModule>{

        TargetItemServiceModel execute(DocMatItemNode sourceMatItemNode, DocMatItemNode reservedMatItemNode,
                                       TargetServiceModel targetServiceModel,
                                       TargetItemServiceModel targetItemServiceModel) throws DocActionException;

    }

    public interface ILoadSourceItemReserved<TargetItem extends DocMatItemNode>{

        CrossDocBatchConvertReservedProxy.ReseveredDocItemContext execute(DocMatItemNode reservedMatItemNode, DocMatItemNode sourceMatItemNode,
                                                                          StorageCoreUnit storageCoreUnitContext) throws DocActionException;

    }

    public interface IGenerateTargetItemServiceModelReserved<TargetServiceModel extends ServiceModule,
            TargetItemServiceModel extends ServiceModule>{

        CrossDocBatchConvertReservedProxy.DocMatItemCreateContext execute(DocMatItemNode reservedMatItemNode,
                                                                          DocMatItemNode sourceMatItemNode,
                                                                          List<CrossDocBatchConvertReservedProxy.DocContentCreateContext> docContentCreateContextList );

    }

    private CrossDocConvertRequest crossDocConvertRequest;

    /**
     * Callback interface to retrieve all possible source (prev doc) material items based on the selected reserved material items.
     * Typical use case: Given a sales contract material item list, retrieve the corresponding list of all possible warehouse store items.
     */
    private IGetALLSourceMatItemListBySelectedReserved getALLSourceMatItemListBySelectedReserved;

    /**
     * Callback for filtering the corresponding source material item list from the complete source material item list based on a specific reserved document
     * material item instance during traversal.
     */
    private IGetSourceMatItemListBySelectedReserved getSourceMatItemListBySelectedReserved;

    /**
     * Customized callback used to check if the document stored in the provided <code>DocCrateContext</code> can be used to contain the newly created target document item.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    private IFilterRootDocContextReserved filterRootDocContextReserved;

    /**
     * Callback interface to customize the conversion of properties to a newly created target item based on the context of a reserved source document item
     * (e.g., reserved source item, requested amount).
     * This is invoked after the relationships between the reserved document item, target document item, and previous document material item have been established.
     * Additionally, it is executed after the standard property conversion process from the previous document item and reserved document item to the target item.
     */
    private IConvertToTargetItemReserved convertToTargetItemReserved;


    private ILoadSourceItemReserved loadSourceItemReserved;

    private IGenerateTargetItemServiceModelReserved<TargetServiceModel, TargetItemServiceModel> generateTargetItemServiceModelReserved;

    private IConvertToTarItemServiceModelReserved<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModelReserved;

    public CrossDocConvertReservedRequest() {
        super();
        this.setDefGetALLSourceMatItemListBySelectedReserved();
        this.setDefGetSourceMatItemListBySelectedReserved();
        this.setDefFilterRootDocContextReserved();
        this.setDefConvertToTargetItemReserved();
        this.setDefConvertToTarItemServiceModelReserved();
        this.setDefLoadSourceItemReserved();
    }

    public CrossDocConvertReservedRequest(int targetDocType, IFilterTargetDoc filterTargetDoc,
                                          IGetTargetDocItemList getTargetDocItemList,
                                          IFilterSelectedMatItem filterSelectedMatItem,
                                          IGenerateTargetServiceModel<?, TargetServiceModel> generateTargetServiceModel,
                                          IConvertToTargetItem covertToTargetItem,
                                          IGenerateTargetItemServiceModelReserved<TargetServiceModel, TargetItemServiceModel> generateTargetItemServiceModelReserved,
                                          IGetALLSourceMatItemListBySelectedReserved getALLSourceMatItemListBySelectedReserved,
                                          IGetSourceMatItemListBySelectedReserved getSourceMatItemListBySelectedReserved,
                                          IFilterRootDocContextReserved iFilterRootDocContextReserved,
                                          IConvertToTargetItemReserved convertToTargetItemReserved,
                                          IParseBatchGenRequest<TargetServiceModel> parseBatchGenRequest) {
        this.targetDocType = targetDocType;
        this.setGetTargetDocItemList(getTargetDocItemList);
        this.setFilterTargetDoc(filterTargetDoc);
        this.setFilterSelectedMatItem(filterSelectedMatItem);
        this.setGenerateTargetServiceModel(generateTargetServiceModel);
        this.setCovertToTargetItem(covertToTargetItem);
        this.setGenerateTargetItemServiceModelReserved(generateTargetItemServiceModelReserved);
        this.getALLSourceMatItemListBySelectedReserved = getALLSourceMatItemListBySelectedReserved;
        this.getSourceMatItemListBySelectedReserved = getSourceMatItemListBySelectedReserved;
        this.filterRootDocContextReserved = iFilterRootDocContextReserved;
        this.convertToTargetItemReserved = convertToTargetItemReserved;
        this.setParseBatchGenRequest(parseBatchGenRequest);
    }

    /**
     * Retrieves the standard Cross-Document Conversion request.
     * Typically, the Standard Cross-Document Conversion request is managed by a subclass of `CrossDocConvertRequest`.
     *
     * This method is used in the context of subclasses such as `CrossDocConvertReservedRequest`, allowing them to
     * access a corresponding subclass of `CrossDocConvertRequest` through the method <code>getCrossDocConvertRequest</code>.
     * Once obtained, the subclass can extract the standard properties of the Cross-Document Conversion request.
     *
     * Example:
     * `OutboundDeliveryCrossConvertReservedRequest` -> `OutboundDeliveryCrossConvertRequest`
     *
     * @return An instance of `CrossDocConvertRequest` representing the standard Cross-Document Conversion request.
     */
    public CrossDocConvertRequest getCrossDocConvertRequest() {
        return crossDocConvertRequest;
    }

    public void setCrossDocConvertRequest(CrossDocConvertRequest crossDocConvertRequest) {
        this.crossDocConvertRequest = crossDocConvertRequest;
    }

    @Override
    public IFilterTargetDoc getFilterTargetDoc() {
        return getCrossDocConvertRequest().getFilterTargetDoc();
    }

    @Override
    public void setFilterTargetDoc(IFilterTargetDoc filterTargetDoc) {
        getCrossDocConvertRequest().setFilterTargetDoc(filterTargetDoc);
    }

    @Override
    public IGenerateTargetServiceModel<?, TargetServiceModel> getGenerateTargetServiceModel() {
        return getCrossDocConvertRequest().getGenerateTargetServiceModel();
    }

    @Override
    public void setGenerateTargetServiceModel(
            IGenerateTargetServiceModel<?, TargetServiceModel> generateTargetServiceModel) {
        getCrossDocConvertRequest().setGenerateTargetServiceModel(generateTargetServiceModel);
    }

    @Override
    public IConvertToTargetItem getCovertToTargetItem() {
        return getCrossDocConvertRequest().getCovertToTargetItem();
    }

    @Override
    public void setCovertToTargetItem(IConvertToTargetItem covertToTargetItem) {
        getCrossDocConvertRequest().setCovertToTargetItem(covertToTargetItem);
    }

    @Override
    public IParseBatchGenRequest<TargetServiceModel> getParseBatchGenRequest() {
        return getCrossDocConvertRequest().getParseBatchGenRequest();
    }

    @Override
    public void setParseBatchGenRequest(IParseBatchGenRequest<TargetServiceModel> parseBatchGenRequest) {
        getCrossDocConvertRequest().setParseBatchGenRequest(parseBatchGenRequest);
    }

    @Override
    public IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel> getConvertToTarItemServiceModel() {
        return getCrossDocConvertRequest().getConvertToTarItemServiceModel();
    }

    @Override
    public void setConvertToTarItemServiceModel(
            IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModel) {
        getCrossDocConvertRequest().setConvertToTarItemServiceModel(convertToTarItemServiceModel);
    }

    public IConvertToTarItemServiceModelReserved<TargetServiceModel, TargetItemServiceModel> getConvertToTarItemServiceModelReserved() {
        return convertToTarItemServiceModelReserved;
    }

    public void setConvertToTarItemServiceModelReserved(
            IConvertToTarItemServiceModelReserved<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModelReserved) {
        this.convertToTarItemServiceModelReserved = convertToTarItemServiceModelReserved;
    }

    @Override
    public IFilterSelectedMatItem getFilterSelectedMatItem() {
        return getCrossDocConvertRequest().getFilterSelectedMatItem();
    }

    @Override
    public void setFilterSelectedMatItem(IFilterSelectedMatItem filterSelectedMatItem) {
        getCrossDocConvertRequest().setFilterSelectedMatItem(filterSelectedMatItem);
    }

    @Override
    public IGetTargetDocItemList getGetTargetDocItemList() {
        return getCrossDocConvertRequest().getGetTargetDocItemList();
    }

    public IGenerateTargetItemServiceModelReserved<TargetServiceModel, TargetItemServiceModel> getGenerateTargetItemServiceModelReserved() {
        return generateTargetItemServiceModelReserved;
    }

    public void setGenerateTargetItemServiceModelReserved(
            IGenerateTargetItemServiceModelReserved<TargetServiceModel, TargetItemServiceModel> generateTargetItemServiceModelReserved) {
        this.generateTargetItemServiceModelReserved = generateTargetItemServiceModelReserved;
    }

    @Override
    public IConvertToTargetDoc getConvertToTargetDoc() {
        return getCrossDocConvertRequest().getConvertToTargetDoc();
    }

    @Override
    public void setConvertToTargetDoc(IConvertToTargetDoc convertToTargetDoc) {
        getCrossDocConvertRequest().setConvertToTargetDoc(convertToTargetDoc);
    }

    @Override
    public void setGetTargetDocItemList(IGetTargetDocItemList getTargetDocItemList) {
        getCrossDocConvertRequest().setGetTargetDocItemList(getTargetDocItemList);
    }

    /**
     * Sets the default callback logic in each child class.
     * `getALLSourceMatItemListBySelectedReserved` is Callback interface to retrieve all possible source (prev doc) material items based on the selected reserved material items.
     * Typical use case: Given a sales contract material item list, retrieve the corresponding list of all possible warehouse store items.
     */
    protected void setDefGetALLSourceMatItemListBySelectedReserved() {
        this.getALLSourceMatItemListBySelectedReserved = null;
    }

    /**
     *  Sets the default callback logic in each child class.
     * `setDefGetSourceMatItemListBySelectedReserved` is the callback for filtering the corresponding source material item list from the complete
     *  source material item list based on a specific reserved document material item instance during traversal.
     */
    protected void setDefGetSourceMatItemListBySelectedReserved() {
        this.getSourceMatItemListBySelectedReserved = null;
    }

    /**
     *  Sets the default callback logic in each child class.
     * `filterRootDocContextReserved` is the customized callback used to check if the document stored in the provided <code>DocCrateContext</code>
     *  can be used to contain the newly created target document item.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    protected void setDefFilterRootDocContextReserved() {
        this.filterRootDocContextReserved = null;
    }

    /**
     * Sets the default callback logic in each child class.
     * Callback interface to customize the conversion of properties to a newly created target item based on the context of a reserved source document item
     * (e.g., reserved source item, requested amount).
     * This is invoked after the relationships between the reserved document item, target document item, and previous document material item have been established.
     * Additionally, it is executed after the standard property conversion process from the previous document item and reserved document item to the target item.
     */
    protected void setDefConvertToTargetItemReserved() {
        this.convertToTargetItemReserved = null;
    }

    /**
     * Set Default Callback: Logic Copy from source doc to target doc
     */
    protected void setDefConvertToTarItemServiceModelReserved() {
        this.convertToTarItemServiceModelReserved = null;
    }

    protected void setDefLoadSourceItemReserved() {this.loadSourceItemReserved = null;}

    /**
     * `getALLSourceMatItemListBySelectedReserved` is Callback interface to retrieve all possible source (prev doc) material items based on the selected reserved material items.
     *  Typical use case: Given a sales contract material item list, retrieve the corresponding list of all possible warehouse store items.
     */
    public IGetALLSourceMatItemListBySelectedReserved getGetALLSourceMatItemListBySelectedReserved() {
        return getALLSourceMatItemListBySelectedReserved;
    }

    public void setGetALLSourceMatItemListBySelectedReserved(
            IGetALLSourceMatItemListBySelectedReserved getALLSourceMatItemListBySelectedReserved) {
        this.getALLSourceMatItemListBySelectedReserved = getALLSourceMatItemListBySelectedReserved;
    }

    /**
     * When traverse each reserved doc material item instance,
     * this callback is used to filter the corresponding source material item list from the full source material item list as well as current reserved doc material item instance
     */
    public IGetSourceMatItemListBySelectedReserved getGetSourceMatItemListBySelectedReserved() {
        return getSourceMatItemListBySelectedReserved;
    }

    public void setGetSourceMatItemListBySelectedReserved(
            IGetSourceMatItemListBySelectedReserved getSourceMatItemListBySelectedReserved) {
        this.getSourceMatItemListBySelectedReserved = getSourceMatItemListBySelectedReserved;
    }

    /**
     * `filterRootDocContextReserved` is the customized callback used to check if the document stored in the provided <code>DocCrateContext</code>
     *  can be used to contain the newly created target document item.
     *  This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    public IFilterRootDocContextReserved getFilterRootDocContextReserved() {
        return filterRootDocContextReserved;
    }

    public void setFilterRootDocContextReserved(IFilterRootDocContextReserved filterRootDocContextReserved) {
        this.filterRootDocContextReserved = filterRootDocContextReserved;
    }

    /**
     * Callback interface to customize the conversion of properties to a newly created target item based on the context of a reserved source document item
     * (e.g., reserved source item, requested amount).
     * This is invoked after the relationships between the reserved document item, target document item, and previous document material item have been established.
     * Additionally, it is executed after the standard property conversion process from the previous document item and reserved document item to the target item.
     */
    public IConvertToTargetItemReserved getConvertToTargetItemReserved() {
        return convertToTargetItemReserved;
    }

    public void setConvertToTargetItemReserved(IConvertToTargetItemReserved convertToTargetItemReserved) {
        this.convertToTargetItemReserved = convertToTargetItemReserved;
    }

    public ILoadSourceItemReserved getLoadSourceItemReserved() {
        return loadSourceItemReserved;
    }

    public void setLoadSourceItemReserved(ILoadSourceItemReserved loadSourceItemReserved) {
        this.loadSourceItemReserved = loadSourceItemReserved;
    }
}
