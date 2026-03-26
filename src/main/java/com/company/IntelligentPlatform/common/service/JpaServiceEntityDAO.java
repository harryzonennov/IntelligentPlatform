package com.company.IntelligentPlatform.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic {@link ServiceEntityDAO} implementation backed by a
 * {@link JpaRepository} (for writes) and a JPA {@link EntityManager} (for
 * JPQL reads).
 *
 * <p>One instance is created per manager via Spring field injection of the
 * typed repository.  The manager's {@code @PostConstruct setServiceEntityDAO()}
 * method passes that repository into this class.</p>
 *
 * <p>Read methods build JPQL queries dynamically using the {@code nodeName}
 * parameter (which is the Java entity class name) following the same pattern
 * used by {@link HibernateDefaultImpDAO}.</p>
 */
public class JpaServiceEntityDAO implements ServiceEntityDAO {

    private static final Logger logger = LoggerFactory.getLogger(JpaServiceEntityDAO.class);

    private final EntityManager entityManager;

    @SuppressWarnings("rawtypes")
    private final JpaRepository repository;

    @SuppressWarnings("rawtypes")
    public JpaServiceEntityDAO(EntityManager entityManager, JpaRepository<?, String> repository) {
        this.entityManager = entityManager;
        this.repository = repository;
    }

    // ---- Write operations ----

    @Override
    @SuppressWarnings("unchecked")
    public void insertEntity(ServiceEntityNode seNode) {
        if (seNode != null) {
            repository.save(seNode);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void insertEntity(List<ServiceEntityNode> seNodeList) {
        if (seNodeList != null && !seNodeList.isEmpty()) {
            repository.saveAll(seNodeList);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(ServiceEntityNode seNode) {
        if (seNode != null) {
            repository.save(seNode);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(List<ServiceEntityNode> seNodeList) {
        if (seNodeList != null && !seNodeList.isEmpty()) {
            repository.saveAll(seNodeList);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteEntityNode(ServiceEntityNode seNode) {
        if (seNode != null) {
            repository.delete(seNode);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteEntityNodeList(List<ServiceEntityNode> seNodeList) {
        if (seNodeList != null && !seNodeList.isEmpty()) {
            repository.deleteAll(seNodeList);
        }
    }

    // ---- Read operations ----

    @Override
    @SuppressWarnings("unchecked")
    public List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue, String keyName, String nodeName) {
        return queryByKey(keyValue, keyName, nodeName, true, -1, -1, null, null, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue, String keyName, String nodeName,
            boolean preciseMatchFlag) {
        return queryByKey(keyValue, keyName, nodeName, preciseMatchFlag, -1, -1, null, null, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue, String keyName, String nodeName,
            int startIndex, int limitNumber, String orderByField, String orderDir, String client,
            boolean preciseMatchFlag) {
        return queryByKey(keyValue, keyName, nodeName, preciseMatchFlag, startIndex, limitNumber,
                orderByField, orderDir, client);
    }

    @Override
    public void deleteEntityNodeByKey(String keyValue, String keyName, String nodeName) {
        List<ServiceEntityNode> list = getEntityNodeListByKey(keyValue, keyName, nodeName);
        deleteEntityNodeList(list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ServiceEntityNode> getEntityNodeListByKeyList(List<ServiceBasicKeyStructure> keyList,
            String nodeName, boolean preciseMatchFlag) {
        return queryByKeyList(keyList, nodeName, preciseMatchFlag);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ServiceEntityNode> getEntityNodeListByKeyList(List<ServiceBasicKeyStructure> keyList,
            String nodeName, boolean preciseMatchFlag, boolean flag2) {
        return queryByKeyList(keyList, nodeName, preciseMatchFlag);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getEntityFieldListByKeyList(List<ServiceBasicKeyStructure> keyList, String nodeName,
            String fieldName, boolean preciseMatchFlag) {
        if (nodeName == null || fieldName == null) {
            return new ArrayList<>();
        }
        try {
            String alias = alias(nodeName);
            StringBuilder jpql = new StringBuilder("select ")
                    .append(alias).append(".").append(fieldName)
                    .append(" from ").append(nodeName).append(" ").append(alias);
            List<Object> params = appendWhereClause(jpql, alias, keyList, preciseMatchFlag);
            Query query = entityManager.createQuery(jpql.toString());
            setParams(query, params);
            List<?> raw = query.getResultList();
            List<String> result = new ArrayList<>();
            for (Object obj : raw) {
                if (obj != null) {
                    result.add(obj.toString());
                }
            }
            return result;
        } catch (Exception e) {
            logger.warn("getEntityFieldListByKeyList failed for {}: {}", nodeName, e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public int getRecordNum(Object keyValue, String keyName, String nodeName, String client,
            boolean preciseMatchFlag) {
        if (nodeName == null) {
            return 0;
        }
        try {
            String alias = alias(nodeName);
            StringBuilder jpql = new StringBuilder("select count(").append(alias).append(")")
                    .append(" from ").append(nodeName).append(" ").append(alias)
                    .append(" where ");
            List<Object> params = new ArrayList<>();
            appendCondition(jpql, alias, keyName, keyValue, preciseMatchFlag, params);
            if (client != null) {
                jpql.append(" and ").append(alias).append(".client = ?").append(params.size() + 1);
                params.add(client);
            }
            Query query = entityManager.createQuery(jpql.toString());
            setParams(query, params);
            Long count = (Long) query.getSingleResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            logger.warn("getRecordNum failed for {}: {}", nodeName, e.getMessage());
            return 0;
        }
    }

    @Override
    public int getRecordNum(List<ServiceBasicKeyStructure> keyList, String nodeName, boolean preciseMatchFlag) {
        if (nodeName == null) {
            return 0;
        }
        try {
            String alias = alias(nodeName);
            StringBuilder jpql = new StringBuilder("select count(").append(alias).append(")")
                    .append(" from ").append(nodeName).append(" ").append(alias);
            List<Object> params = appendWhereClause(jpql, alias, keyList, preciseMatchFlag);
            Query query = entityManager.createQuery(jpql.toString());
            setParams(query, params);
            Long count = (Long) query.getSingleResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            logger.warn("getRecordNum(keyList) failed for {}: {}", nodeName, e.getMessage());
            return 0;
        }
    }

    @Override
    public void admDeleteEntityByKey(String keyValue, String keyName, String nodeName) {
        List<ServiceEntityNode> list = getEntityNodeListByKey(keyValue, keyName, nodeName);
        deleteEntityNodeList(list);
    }

    @Override
    public void archiveDeleteEntityByKey(String keyValue, String keyName, String nodeName,
            String logonUserUUID, String resOrgUUID) {
        List<ServiceEntityNode> list = getEntityNodeListByKey(keyValue, keyName, nodeName);
        for (ServiceEntityNode node : list) {
            String client = node.getClient();
            if (client != null && client.matches("^[A-Z0-9]{3}$")) {
                node.setClient("-" + client);
                updateEntity(node);
            }
        }
    }

    @Override
    public void recoverEntityByKey(String keyValue, String keyName, String nodeName,
            String logonUserUUID, String resOrgUUID) {
        List<ServiceEntityNode> list = getEntityNodeListByKey(keyValue, keyName, nodeName);
        for (ServiceEntityNode node : list) {
            String client = node.getClient();
            if (client != null && client.matches("^-[A-Z0-9]{3}$")) {
                node.setClient(client.substring(1));
                updateEntity(node);
            }
        }
    }

    // ---- Internal helpers ----

    private static String alias(String nodeName) {
        return Character.toLowerCase(nodeName.charAt(0)) + nodeName.substring(1);
    }

    /**
     * Appends WHERE clause conditions from a key list and returns bound parameter values in order.
     */
    private List<Object> appendWhereClause(StringBuilder jpql, String alias,
            List<ServiceBasicKeyStructure> keyList, boolean preciseMatchFlag) {
        List<Object> params = new ArrayList<>();
        if (keyList != null && !keyList.isEmpty()) {
            jpql.append(" where ");
            for (int i = 0; i < keyList.size(); i++) {
                if (i > 0) {
                    jpql.append(" and ");
                }
                ServiceBasicKeyStructure key = keyList.get(i);
                appendCondition(jpql, alias, key.getKeyName(), key.getKeyValue(), preciseMatchFlag, params);
            }
        }
        return params;
    }

    /**
     * Appends a single JPQL condition using positional parameters.
     */
    private void appendCondition(StringBuilder jpql, String alias, String fieldName,
            Object value, boolean preciseMatchFlag, List<Object> params) {
        params.add(value);
        int pos = params.size();
        if (!preciseMatchFlag && value instanceof String) {
            jpql.append(alias).append(".").append(fieldName)
                    .append(" like ?").append(pos);
            // Replace the added plain value with a like-wrapped value
            params.set(pos - 1, "%" + value + "%");
        } else {
            jpql.append(alias).append(".").append(fieldName)
                    .append(" = ?").append(pos);
        }
    }

    @SuppressWarnings("unchecked")
    private List<ServiceEntityNode> queryByKey(Object keyValue, String keyName, String nodeName,
            boolean preciseMatchFlag, int startIndex, int limitNumber,
            String orderByField, String orderDir, String client) {
        if (nodeName == null || keyName == null) {
            return new ArrayList<>();
        }
        try {
            String alias = alias(nodeName);
            StringBuilder jpql = new StringBuilder("from ").append(nodeName).append(" ").append(alias)
                    .append(" where ");
            List<Object> params = new ArrayList<>();
            appendCondition(jpql, alias, keyName, keyValue, preciseMatchFlag, params);
            if (client != null) {
                jpql.append(" and ").append(alias).append(".client = ?").append(params.size() + 1);
                params.add(client);
            }
            if (orderByField != null) {
                jpql.append(" order by ").append(alias).append(".").append(orderByField);
                if (ORDER_DESC.equalsIgnoreCase(orderDir)) {
                    jpql.append(" desc");
                } else {
                    jpql.append(" asc");
                }
            }
            Query query = entityManager.createQuery(jpql.toString());
            setParams(query, params);
            if (startIndex >= 0) {
                query.setFirstResult(startIndex);
            }
            if (limitNumber > 0) {
                query.setMaxResults(limitNumber);
            }
            return (List<ServiceEntityNode>) query.getResultList();
        } catch (Exception e) {
            logger.warn("queryByKey failed for {}.{}: {}", nodeName, keyName, e.getMessage());
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    private List<ServiceEntityNode> queryByKeyList(List<ServiceBasicKeyStructure> keyList,
            String nodeName, boolean preciseMatchFlag) {
        if (nodeName == null) {
            return new ArrayList<>();
        }
        try {
            String alias = alias(nodeName);
            StringBuilder jpql = new StringBuilder("from ").append(nodeName).append(" ").append(alias);
            List<Object> params = appendWhereClause(jpql, alias, keyList, preciseMatchFlag);
            Query query = entityManager.createQuery(jpql.toString());
            setParams(query, params);
            return (List<ServiceEntityNode>) query.getResultList();
        } catch (Exception e) {
            logger.warn("queryByKeyList failed for {}: {}", nodeName, e.getMessage());
            return new ArrayList<>();
        }
    }

    private void setParams(Query query, List<Object> params) {
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }
    }
}
