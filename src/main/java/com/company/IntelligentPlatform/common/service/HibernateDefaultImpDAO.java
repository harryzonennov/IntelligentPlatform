package com.company.IntelligentPlatform.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA replacement for the legacy platform.foundation.DAO.HibernateDefaultImpDAO.
 *
 * <p>The legacy DAO executed raw Hibernate HQL via a Hibernate {@code Session}.
 * This implementation uses JPA {@link EntityManager#createQuery(String)} instead,
 * which accepts the same JPQL/HQL syntax — callers required no change.</p>
 *
 * <p>All methods follow a fail-safe contract: any query exception is caught and
 * logged internally; the method returns an empty list (or zero) rather than
 * propagating the exception to the caller. This matches the legacy behavior.</p>
 *
 * <p>Used by: {@link BsearchService} (cross-entity search),
 * {@link ServiceDefaultIdGenerateHelper} (document ID generation),
 * {@link ServiceItemIdGenerator} (item ID generation).</p>
 */
@Component
public class HibernateDefaultImpDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Executes a JPQL query that returns full entity rows cast to {@link ServiceEntityNode}.
     *
     * <p>Typical caller pattern:
     * <pre>
     *   "from PurchaseOrder po where po.client = 'C001' order by po.lastUpdateTime desc"
     * </pre>
     * The entity class name in the query must match the Java class name exactly
     * (JPQL uses the entity class, not the table name).</p>
     *
     * @param jpqlCommand a JPQL {@code from} query string; must not be null or blank
     * @return list of matching entities, or an empty list if the query returns no rows
     *         or throws an exception
     */
    @SuppressWarnings("unchecked")
    public List<ServiceEntityNode> getEntityNodeListBySQLCommand(String jpqlCommand) {
        if (jpqlCommand == null || jpqlCommand.isBlank()) {
            return new ArrayList<>();
        }
        try {
            Query query = entityManager.createQuery(jpqlCommand);
            return (List<ServiceEntityNode>) query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Executes a JPQL {@code from} query and returns only the {@code uuid} field
     * of each matched entity.
     *
     * <p>Used by {@link BsearchService} for paginated search: first the full result
     * set is reduced to a UUID list, then a sub-list is fetched for the current page.
     * Typical caller pattern:
     * <pre>
     *   "from SalesContract sc where sc.client = 'C001' order by sc.lastUpdateTime desc"
     * </pre>
     * The uuid is extracted by calling {@link ServiceEntityNode#getUuid()} on each row.</p>
     *
     * @param jpqlCommand a JPQL {@code from} query string; must not be null or blank
     * @return ordered list of UUID strings, or an empty list if no rows match or an
     *         exception occurs
     */
    @SuppressWarnings("unchecked")
    public List<String> getUUIDListBySQLCommand(String jpqlCommand) {
        if (jpqlCommand == null || jpqlCommand.isBlank()) {
            return new ArrayList<>();
        }
        try {
            Query query = entityManager.createQuery(jpqlCommand);
            List<ServiceEntityNode> resultList = (List<ServiceEntityNode>) query.getResultList();
            List<String> uuidList = new ArrayList<>();
            for (ServiceEntityNode node : resultList) {
                if (node != null && node.getUuid() != null) {
                    uuidList.add(node.getUuid());
                }
            }
            return uuidList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Executes a JPQL query and returns the total number of matching rows.
     *
     * <p>If {@code jpqlCommand} already starts with {@code "select count"} it is executed
     * as-is. Otherwise the method auto-wraps it:
     * <pre>
     *   "from PurchaseOrder po where po.status = 1"
     *   → "select count(po) from PurchaseOrder po where po.status = 1"
     * </pre>
     * The alias is extracted as the third whitespace-delimited token of the query.</p>
     *
     * @param jpqlCommand a JPQL query string (with or without a {@code count} projection);
     *                    must not be null or blank
     * @return the row count, or {@code 0} if the query returns no result or throws
     */
    public int getRecordNumBySQLCommand(String jpqlCommand) {
        if (jpqlCommand == null || jpqlCommand.isBlank()) {
            return 0;
        }
        try {
            String countQuery = jpqlCommand.trim();
            if (!countQuery.toLowerCase().startsWith("select count")) {
                // Extract the alias from "from ClassName alias ..."
                String[] tokens = countQuery.split("\\s+");
                String alias = (tokens.length >= 3) ? tokens[2] : "e";
                countQuery = "select count(" + alias + ") " + countQuery;
            }
            Query query = entityManager.createQuery(countQuery);
            Long count = (Long) query.getSingleResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Fetches entities whose {@code fieldName} field equals {@code keyValue},
     * building the JPQL query internally.
     *
     * <p>Equivalent to the legacy Criteria/HQL lookup:
     * <pre>
     *   "from LogonUser u where u.uuid = 'abc-123'"
     * </pre>
     * The alias is derived by lower-casing the first character of {@code nodeName}.</p>
     *
     * @param keyValue  the value to match against
     * @param fieldName the entity field name to filter on (e.g. {@code "uuid"}, {@code "id"})
     * @param nodeName  the entity class name (e.g. {@code "LogonUser"})
     * @return list of matching entities, or an empty list if none found or an exception occurs
     */
    @SuppressWarnings("unchecked")
    public List<ServiceEntityNode> getEntityNodeListByKey(String keyValue, String fieldName, String nodeName) {
        if (nodeName == null || fieldName == null) {
            return new ArrayList<>();
        }
        try {
            String varName = Character.toLowerCase(nodeName.charAt(0)) + nodeName.substring(1);
            String jpql = "from " + nodeName + " " + varName
                    + " where " + varName + "." + fieldName + " = :keyValue";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("keyValue", keyValue);
            return (List<ServiceEntityNode>) query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Overload of {@link #getEntityNodeListByKey(String, String, String)} that accepts
     * an additional boolean {@code flag} parameter.
     *
     * <p>The {@code flag} parameter was present in the legacy DAO signature for
     * cache-control purposes. It is ignored in this JPA implementation — Spring's
     * first-level cache (persistence context) handles caching transparently.</p>
     *
     * @param keyValue  the value to match against
     * @param fieldName the entity field name to filter on
     * @param nodeName  the entity class name
     * @param flag      legacy cache-control flag; ignored
     * @return list of matching entities, or an empty list if none found or an exception occurs
     */
    public List<ServiceEntityNode> getEntityNodeListByKey(String keyValue, String fieldName, String nodeName, boolean flag) {
        return getEntityNodeListByKey(keyValue, fieldName, nodeName);
    }

    /**
     * Executes a JPQL projection query that selects a single String-valued field
     * per row, and returns those values as a plain {@code List<String>}.
     *
     * <p>Typical caller patterns:
     * <pre>
     *   "select po.id from PurchaseOrder po where po.client = 'C001'"
     *   "select m.barcode from MaterialType m"
     * </pre>
     * Each result object is converted to a string via {@link Object#toString()}.
     * Null values are silently skipped.</p>
     *
     * @param jpqlCommand a JPQL {@code select field from ...} query string;
     *                    must not be null or blank
     * @return list of string values, or an empty list if no rows match or an exception occurs
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringListBySQLCommand(String jpqlCommand) {
        if (jpqlCommand == null || jpqlCommand.isBlank()) {
            return new ArrayList<>();
        }
        try {
            Query query = entityManager.createQuery(jpqlCommand);
            List<?> rawList = query.getResultList();
            List<String> result = new ArrayList<>();
            for (Object obj : rawList) {
                if (obj != null) {
                    result.add(obj.toString());
                }
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Executes an arbitrary JPQL query and returns the raw result list.
     *
     * <p>This is the most general-purpose method — use the typed variants above
     * wherever the expected return type is known. This method is retained for
     * legacy call sites that require an untyped result (e.g. multi-column projections).
     * Any exception is caught and an empty list is returned.</p>
     *
     * @param jpqlCommand any valid JPQL query string; must not be null or blank
     * @return raw result list from {@link Query#getResultList()}, or an empty list
     *         if the query fails
     */
    @SuppressWarnings("unchecked")
    public List<?> executeBySQLCommand(String jpqlCommand) {
        if (jpqlCommand == null || jpqlCommand.isBlank()) {
            return new ArrayList<>();
        }
        try {
            Query query = entityManager.createQuery(jpqlCommand);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
