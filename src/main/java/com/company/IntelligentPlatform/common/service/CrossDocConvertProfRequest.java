package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class CrossDocConvertProfRequest<TargetServiceModel extends ServiceModule,
        TargetItem extends ServiceEntityNode,
        TargetItemServiceModel extends ServiceModule> extends CrossDocConvertRequest<TargetServiceModel, TargetItem,
        TargetItemServiceModel>{

    public interface IConvertToTarItemServiceModelPrevProf<TargetServiceModel extends ServiceModule,
            TargetItemServiceModel extends ServiceModule>{

        TargetItemServiceModel execute(DocMatItemNode sourceMatItemNode, DocMatItemNode prevProfMatItemNode,
                                       TargetServiceModel targetServiceModel,
                                       TargetItemServiceModel targetItemServiceModel) throws DocActionException;

    }

    public interface IGetPrevProfMatItemBySource{

        DocMatItemNode execute(DocMatItemNode selectedSourceMatItem) throws ServiceEntityConfigureException;

    }

    public interface IGetSourceMatItemByPrevProf{

        DocMatItemNode execute(DocMatItemNode selectedSourceMatItem) throws ServiceEntityConfigureException;

    }
    
    /**
     * Callback interface to check and filter if the document stored in the provided <code>DocCrateContext</code> can be used to contain the newly created target document item in Prev Prof Context.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    public interface IFilterRootDocContextPrevProf{

        boolean execute(CrossDocBatchConvertProfProxy.DocContentCreateContext docContentCreateContext,
                        DocMatItemNode prevProfMatItemNode, DocMatItemNode sourceMatItemNode) ;

    }

    public interface IConvertToTargetItemProf<TargetItem extends DocMatItemNode>{

        CrossDocBatchConvertProfProxy.DocMatItemCreateContext execute(CrossDocBatchConvertProfProxy.DocMatItemCreateContext docMatItemCreateContext) throws DocActionException;

    }

    private CrossDocConvertRequest crossDocConvertRequest;
    /**
     *  Custom callback to retrieve the professional-previous document item based on the provided direct previous material item.
     *  If the custom logic fails to identify the professional-previous material item, the method will invoke the system API: docFlowProxy.findEndDocMatItemTillPrev as a fallback to locate the
     *  appropriate direct previous material item.
     *  Example Usage: Locate the purchase contract item associated with a warehouse store item.
     */
    private IGetPrevProfMatItemBySource getPrevProfMatItemBySource;

    /**
     * Custom callback to retrieve the direct previous material item based on the provided professional previous material item.
     * If the custom logic fails to identify the direct previous material item, the method will invoke the system API: docFlowProxy.findEndDocMatItemTillNext as a fallback to locate the
     * appropriate direct previous material item.
     * Example Usage: Locate the warehouse store item associated with a given purchase contract item.
     */
    private IGetSourceMatItemByPrevProf getSourceMatItemByPrevProf;

    /**
     * Customized callback to check and filter if the document stored in the provided <code>DocCrateContext</code> can be used to contain the newly created target document item in Prev Prof Context.
     * This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    private IFilterRootDocContextPrevProf filterRootDocContextPrevProf;

    /**
     * Customized callback for converting properties into a newly generated target document item.
     * This conversion utilizes the properties derived from both the professional previous document and the direct previous document.
     */
    private IConvertToTargetItemProf convertToTargetItemProf;

    private IConvertToTarItemServiceModelPrevProf<TargetServiceModel, TargetItemServiceModel>  convertToTarItemServiceModelPrevProf;

    public CrossDocConvertProfRequest() {
        super();
        this.setDefGetPrevProfMatItemBySource();
        this.setDefGetSourceMatItemByPrevProf();
        this.setDefFilterRootDocContextPrevProf();
        this.setDefConvertToTargetItemProf();
    }

    /**
     * Retrieves the standard Cross-Document Conversion request.
     * Typically, the Standard Cross-Document Conversion request is managed by a subclass of `CrossDocConvertRequest`.
     *
     * This method is used in the context of subclasses such as `CrossDocConvertProfRequest`, allowing them to
     * access a corresponding subclass of `CrossDocConvertRequest` through the method <code>getCrossDocConvertRequest</code>.
     * Once obtained, the subclass can extract the standard properties of the Cross-Document Conversion request.
     *
     * Example:
     * `PurchaseReturnOrderCrossConvertProfRequest` -> `PurchaseReturnOrderCrossConvertRequest`
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
    public IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel> getConvertToTarItemServiceModel() {
        return getCrossDocConvertRequest().getConvertToTarItemServiceModel();
    }

    @Override
    public void setConvertToTarItemServiceModel(
            IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModel) {
        getCrossDocConvertRequest().setConvertToTarItemServiceModel(convertToTarItemServiceModel);
    }

    public IConvertToTarItemServiceModelPrevProf<TargetServiceModel, TargetItemServiceModel> getConvertToTarItemServiceModelPrevProf() {
        return convertToTarItemServiceModelPrevProf;
    }

    public void setConvertToTarItemServiceModelPrevProf(
            IConvertToTarItemServiceModelPrevProf<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModelPrevProf) {
        this.convertToTarItemServiceModelPrevProf = convertToTarItemServiceModelPrevProf;
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
     * The `getSourceMatItemByPrevProf` method acts as a custom callback to retrieve the professional-previous document item based on the provided direct previous material item.
     * If the custom logic fails to identify the professional-previous material item, the method will invoke the system API: docFlowProxy.findEndDocMatItemTillPrev as a fallback to locate the
     * appropriate direct previous material item.
     * Example Usage: Locate the purchase contract item associated with a warehouse store item.
     */
    protected void setDefGetPrevProfMatItemBySource() {
        this.getPrevProfMatItemBySource = null;
    }

    /**
     * Sets the default callback logic in each child class.
     * Custom callback to retrieve the direct previous material item based on the provided professional previous material item.
     * If the custom logic fails to identify the direct previous material item, the method will invoke the system API: docFlowProxy.findEndDocMatItemTillNext as a fallback to locate the
     * appropriate direct previous material item.
     * Example Usage: Locate the warehouse store item associated with a given purchase contract item.
     */
    protected void setDefGetSourceMatItemByPrevProf() {
        this.getSourceMatItemByPrevProf = null;
    }

    /**
     *  Sets the default callback logic in each child class.
     * `filterRootDocContextPrevProf` is the customized callback to check and filter if the document stored in the provided <code>DocCrateContext</code>
     *  can be used to contain the newly created target document item in Prev Prof Context.
     *  This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     */
    protected void setDefFilterRootDocContextPrevProf() {
        this.filterRootDocContextPrevProf = null;
    }

    /**
     * Sets the default callback logic in each child class.
     * `convertToTargetItemProf` is the customized callback for converting properties into a newly generated target document item.
     * This conversion utilizes the properties derived from both the professional previous document and the direct previous document.
     */
    protected void setDefConvertToTargetItemProf() {
        this.convertToTargetItemProf = null;
    }

    /**
     * `filterRootDocContextPrevProf` is the customized callback to check and filter if the document stored in the provided <code>DocCrateContext</code>
     *  can be used to contain the newly created target document item in Prev Prof Context.
     *  This is typically utilized to retrieve a reusable document root node from a list of <code>DocCrateContext</code> instances when creating a batch of target document items.
     *  To set the default callback logic in each child class, refer to the method: <code>setDefFilterRootDocContextPrevProf</code>
     */
    public IFilterRootDocContextPrevProf getFilterRootDocContextPrevProf() {
        return filterRootDocContextPrevProf;
    }

    public void setFilterRootDocContextPrevProf(IFilterRootDocContextPrevProf filterRootDocContextPrevProf) {
        this.filterRootDocContextPrevProf = filterRootDocContextPrevProf;
    }

    /**
     * `convertToTargetItemProf` is the customized callback for converting properties into a newly generated target document item.
     * This conversion utilizes the properties derived from both the professional previous document and the direct previous document.
     * To set the default callback logic in each child class, refer to the method: <code>setDefConvertToTargetItemProf</code>
     */
    public IConvertToTargetItemProf getConvertToTargetItemProf() {
        return convertToTargetItemProf;
    }

    public void setConvertToTargetItemProf(IConvertToTargetItemProf convertToTargetItemProf) {
        this.convertToTargetItemProf = convertToTargetItemProf;
    }

    /**
     * `getPrevProfMatItemBySource` is the customized callback to retrieve the professional-previous document item based on the provided direct previous material item.
     * To set the default callback logic in each child class, refer to the method: <code>setDefGetPrevProfMatItemBySource</code>
     */
    public IGetPrevProfMatItemBySource getGetPrevProfMatItemBySource() {
        return getPrevProfMatItemBySource;
    }

    public void setGetPrevProfMatItemBySource(IGetPrevProfMatItemBySource getPrevProfMatItemBySource) {
        this.getPrevProfMatItemBySource = getPrevProfMatItemBySource;
    }

    /**
     * The `getSourceMatItemByPrevProf` method acts as a custom callback to retrieve the direct previous material item based on the provided professional previous material item.
     * to set the default callback logic in each child class, refer to the method: <code>setDefGetSourceMatItemByPrevProf</code>
     */
    public IGetSourceMatItemByPrevProf getGetSourceMatItemByPrevProf() {
        return getSourceMatItemByPrevProf;
    }

    public void setGetSourceMatItemByPrevProf(IGetSourceMatItemByPrevProf getSourceMatItemByPrevProf) {
        this.getSourceMatItemByPrevProf = getSourceMatItemByPrevProf;
    }
}
