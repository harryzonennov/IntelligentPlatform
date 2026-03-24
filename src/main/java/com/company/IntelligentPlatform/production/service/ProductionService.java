package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityService;
import com.company.IntelligentPlatform.production.model.*;
import com.company.IntelligentPlatform.production.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductionService extends ServiceEntityService {

	@Autowired
	protected ProductionOrderRepository productionOrderRepository;

	@Autowired
	protected RepairProdOrderRepository repairProdOrderRepository;

	@Autowired
	protected ProductionPlanRepository productionPlanRepository;

	@Autowired
	protected BillOfMaterialOrderRepository billOfMaterialOrderRepository;

	@Autowired
	protected BillOfMaterialTemplateRepository billOfMaterialTemplateRepository;

	@Autowired
	protected ProdPickingOrderRepository prodPickingOrderRepository;

	@Autowired
	protected ProdJobOrderRepository prodJobOrderRepository;

	@Autowired
	protected ProcessRouteOrderRepository processRouteOrderRepository;

	@Autowired
	protected ProdWorkCenterRepository prodWorkCenterRepository;

	// ── ProductionOrder ────────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<ProductionOrder> getAllProductionOrders() {
		return productionOrderRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<ProductionOrder> getProductionOrderByUuid(String uuid) {
		return productionOrderRepository.findById(uuid);
	}

	@Transactional(readOnly = true)
	public List<ProductionOrder> getProductionOrdersByStatus(int status) {
		return productionOrderRepository.findByStatus(status);
	}

	public ProductionOrder createProductionOrder(ProductionOrder order, String userUUID, String orgUUID) {
		order.setStatus(ProductionOrder.STATUS_INITIAL);
		return insertSENode(productionOrderRepository, order, userUUID, orgUUID);
	}

	public ProductionOrder updateProductionOrder(ProductionOrder order, String userUUID, String orgUUID) {
		return updateSENode(productionOrderRepository, order, userUUID, orgUUID);
	}

	public void deleteProductionOrder(String uuid) {
		deleteSENode(productionOrderRepository, uuid);
	}

	// ── RepairProdOrder ────────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<RepairProdOrder> getAllRepairOrders() {
		return repairProdOrderRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<RepairProdOrder> getRepairOrderByUuid(String uuid) {
		return repairProdOrderRepository.findById(uuid);
	}

	public RepairProdOrder createRepairOrder(RepairProdOrder order, String userUUID, String orgUUID) {
		order.setStatus(ProductionOrder.STATUS_INITIAL);
		return insertSENode(repairProdOrderRepository, order, userUUID, orgUUID);
	}

	public RepairProdOrder updateRepairOrder(RepairProdOrder order, String userUUID, String orgUUID) {
		return updateSENode(repairProdOrderRepository, order, userUUID, orgUUID);
	}

	public void deleteRepairOrder(String uuid) {
		deleteSENode(repairProdOrderRepository, uuid);
	}

	// ── ProductionPlan ─────────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<ProductionPlan> getAllProductionPlans() {
		return productionPlanRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<ProductionPlan> getProductionPlanByUuid(String uuid) {
		return productionPlanRepository.findById(uuid);
	}

	@Transactional(readOnly = true)
	public List<ProductionPlan> getPlansByMainProdOrder(String refMainProdOrderUUID) {
		return productionPlanRepository.findByRefMainProdOrderUUID(refMainProdOrderUUID);
	}

	public ProductionPlan createProductionPlan(ProductionPlan plan, String userUUID, String orgUUID) {
		plan.setStatus(ProductionPlan.STATUS_INITIAL);
		return insertSENode(productionPlanRepository, plan, userUUID, orgUUID);
	}

	public ProductionPlan updateProductionPlan(ProductionPlan plan, String userUUID, String orgUUID) {
		return updateSENode(productionPlanRepository, plan, userUUID, orgUUID);
	}

	public void deleteProductionPlan(String uuid) {
		deleteSENode(productionPlanRepository, uuid);
	}

	// ── BillOfMaterialOrder ────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<BillOfMaterialOrder> getAllBOMs() {
		return billOfMaterialOrderRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<BillOfMaterialOrder> getBOMByUuid(String uuid) {
		return billOfMaterialOrderRepository.findById(uuid);
	}

	@Transactional(readOnly = true)
	public List<BillOfMaterialOrder> getBOMsByMaterialSKU(String refMaterialSKUUUID) {
		return billOfMaterialOrderRepository.findByRefMaterialSKUUUID(refMaterialSKUUUID);
	}

	public BillOfMaterialOrder createBOM(BillOfMaterialOrder bom, String userUUID, String orgUUID) {
		bom.setStatus(BillOfMaterialOrder.STATUS_INITIAL);
		return insertSENode(billOfMaterialOrderRepository, bom, userUUID, orgUUID);
	}

	public BillOfMaterialOrder updateBOM(BillOfMaterialOrder bom, String userUUID, String orgUUID) {
		return updateSENode(billOfMaterialOrderRepository, bom, userUUID, orgUUID);
	}

	public void deleteBOM(String uuid) {
		deleteSENode(billOfMaterialOrderRepository, uuid);
	}

	// ── BillOfMaterialTemplate ─────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<BillOfMaterialTemplate> getAllBOMTemplates() {
		return billOfMaterialTemplateRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<BillOfMaterialTemplate> getBOMTemplateByUuid(String uuid) {
		return billOfMaterialTemplateRepository.findById(uuid);
	}

	public BillOfMaterialTemplate createBOMTemplate(BillOfMaterialTemplate template, String userUUID, String orgUUID) {
		template.setStatus(BillOfMaterialOrder.STATUS_INITIAL);
		return insertSENode(billOfMaterialTemplateRepository, template, userUUID, orgUUID);
	}

	public BillOfMaterialTemplate updateBOMTemplate(BillOfMaterialTemplate template, String userUUID, String orgUUID) {
		return updateSENode(billOfMaterialTemplateRepository, template, userUUID, orgUUID);
	}

	public void deleteBOMTemplate(String uuid) {
		deleteSENode(billOfMaterialTemplateRepository, uuid);
	}

	// ── ProdPickingOrder ───────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<ProdPickingOrder> getAllPickingOrders() {
		return prodPickingOrderRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<ProdPickingOrder> getPickingOrderByUuid(String uuid) {
		return prodPickingOrderRepository.findById(uuid);
	}

	public ProdPickingOrder createPickingOrder(ProdPickingOrder order, String userUUID, String orgUUID) {
		order.setStatus(ProdPickingOrder.STATUS_INITIAL);
		return insertSENode(prodPickingOrderRepository, order, userUUID, orgUUID);
	}

	public ProdPickingOrder updatePickingOrder(ProdPickingOrder order, String userUUID, String orgUUID) {
		return updateSENode(prodPickingOrderRepository, order, userUUID, orgUUID);
	}

	public void deletePickingOrder(String uuid) {
		deleteSENode(prodPickingOrderRepository, uuid);
	}

	// ── ProdJobOrder ───────────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<ProdJobOrder> getJobOrdersByProductionOrder(String refProductionOrderUUID) {
		return prodJobOrderRepository.findByRefProductionOrderUUID(refProductionOrderUUID);
	}

	@Transactional(readOnly = true)
	public Optional<ProdJobOrder> getJobOrderByUuid(String uuid) {
		return prodJobOrderRepository.findById(uuid);
	}

	public ProdJobOrder createJobOrder(ProdJobOrder jobOrder, String userUUID, String orgUUID) {
		jobOrder.setStatus(ProdJobOrder.STATUS_INITIAL);
		return insertSENode(prodJobOrderRepository, jobOrder, userUUID, orgUUID);
	}

	public ProdJobOrder updateJobOrder(ProdJobOrder jobOrder, String userUUID, String orgUUID) {
		return updateSENode(prodJobOrderRepository, jobOrder, userUUID, orgUUID);
	}

	public void deleteJobOrder(String uuid) {
		deleteSENode(prodJobOrderRepository, uuid);
	}

	// ── ProcessRouteOrder ──────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<ProcessRouteOrder> getAllProcessRoutes() {
		return processRouteOrderRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<ProcessRouteOrder> getProcessRouteByUuid(String uuid) {
		return processRouteOrderRepository.findById(uuid);
	}

	public ProcessRouteOrder createProcessRoute(ProcessRouteOrder route, String userUUID, String orgUUID) {
		route.setStatus(ProcessRouteOrder.STATUS_INITIAL);
		return insertSENode(processRouteOrderRepository, route, userUUID, orgUUID);
	}

	public ProcessRouteOrder updateProcessRoute(ProcessRouteOrder route, String userUUID, String orgUUID) {
		return updateSENode(processRouteOrderRepository, route, userUUID, orgUUID);
	}

	public void deleteProcessRoute(String uuid) {
		deleteSENode(processRouteOrderRepository, uuid);
	}

	// ── ProdWorkCenter ─────────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<ProdWorkCenter> getAllWorkCenters() {
		return prodWorkCenterRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<ProdWorkCenter> getWorkCenterByUuid(String uuid) {
		return prodWorkCenterRepository.findById(uuid);
	}

	@Transactional(readOnly = true)
	public List<ProdWorkCenter> getWorkCenterChildren(String parentNodeUUID) {
		return prodWorkCenterRepository.findByParentNodeUUID(parentNodeUUID);
	}

	public ProdWorkCenter createWorkCenter(ProdWorkCenter workCenter, String userUUID, String orgUUID) {
		return insertSENode(prodWorkCenterRepository, workCenter, userUUID, orgUUID);
	}

	public ProdWorkCenter updateWorkCenter(ProdWorkCenter workCenter, String userUUID, String orgUUID) {
		return updateSENode(prodWorkCenterRepository, workCenter, userUUID, orgUUID);
	}

	public void deleteWorkCenter(String uuid) {
		deleteSENode(prodWorkCenterRepository, uuid);
	}

}
