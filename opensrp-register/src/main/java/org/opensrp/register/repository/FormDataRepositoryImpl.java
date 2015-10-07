package org.opensrp.register.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.opensrp.common.AllConstants;
import org.opensrp.register.domain.Child;
import org.opensrp.register.domain.EligibleCouple;
import org.opensrp.register.domain.Mother;
import org.opensrp.repository.FormDataRepository;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.UUID.randomUUID;

@Repository
public class FormDataRepositoryImpl extends FormDataRepository{
    private static final String ID = "id";
    private static final String DETAILS = "details";
    private static final String DOCUMENT_TYPE = "type";
    private static final String ID_FIELD_ON_ENTITY = "caseId";
    private static final String CASE_ID_VIEW_NAME = "by_caseId";
    private Map<String, Field[]> fieldSetMap;
    private CouchDbConnector db;
    private Map<String, String> designDocMap;

    Logger logger = LoggerFactory.getLogger(FormDataRepository.class);

    @Autowired
    public FormDataRepositoryImpl(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
        this.db = db;
        initMaps();
    }

    private void initMaps() {
        designDocMap = new HashMap<>();
        fieldSetMap = new HashMap<>();
        designDocMap.put(AllConstants.FormEntityTypes.ELIGIBLE_COUPLE_TYPE, "EligibleCouple");
        designDocMap.put(AllConstants.FormEntityTypes.MOTHER_TYPE, "Mother");
        designDocMap.put(AllConstants.FormEntityTypes.CHILD_TYPE, "Child");
        designDocMap.put(AllConstants.FormEntityTypes.KARTU_IBU_TYPE, "EligibleCouple");

        fieldSetMap.put(AllConstants.FormEntityTypes.ELIGIBLE_COUPLE_TYPE, EligibleCouple.class.getDeclaredFields());
        fieldSetMap.put(AllConstants.FormEntityTypes.MOTHER_TYPE, Mother.class.getDeclaredFields());
        fieldSetMap.put(AllConstants.FormEntityTypes.CHILD_TYPE, Child.class.getDeclaredFields());
        fieldSetMap.put(AllConstants.FormEntityTypes.KARTU_IBU_TYPE, EligibleCouple.class.getDeclaredFields());

    }

    public String saveEntity(String entityType, String fields) {
        Map<String, String> updatedFieldsMap = getStringMapFromJSON(fields);
        String entityId = updatedFieldsMap.get(ID);
        String docEntityType = designDocMap.get(entityType);

        List<ViewResult.Row> viewQueryResult = getDBViewQueryResult(entityId, docEntityType);

        ObjectNode entity;
        ObjectNode details;
        if (viewQueryResult.size() != 0) {
            JsonNode document = viewQueryResult.get(0).getDocAsNode();
            entity = (ObjectNode) document;
            details = (ObjectNode) document.get(DETAILS);
        } else {
            entity = new ObjectNode(JsonNodeFactory.instance);
            details = new ObjectNode(JsonNodeFactory.instance);
            entity.put("_id", randomUUID().toString());
            entity.put(DOCUMENT_TYPE, docEntityType);
        }

        List<String> fieldList = getFieldsList(entityType);
        for (String fieldName : updatedFieldsMap.keySet()) {
            if (fieldList.contains(fieldName)) {
                entity.put(fieldName, updatedFieldsMap.get(fieldName));
            } else if (fieldName.equals(ID)) {
                entity.put(ID_FIELD_ON_ENTITY, updatedFieldsMap.get(fieldName));
            } else {
                details.put(fieldName, updatedFieldsMap.get(fieldName));
            }
        }
        entity.put(DETAILS, details);

        db.update(entity);
        return entityId;
    }

    private List<ViewResult.Row> getDBViewQueryResult(String id, String docEntityType) {
        return db.queryView(new ViewQuery().viewName(CASE_ID_VIEW_NAME).designDocId("_design/" + docEntityType).key(id)
                .queryParam(ID_FIELD_ON_ENTITY, id).includeDocs(true)).getRows();
    }

    private List<String> getFieldsList(String entityType) {
        List<String> fieldList = new ArrayList<>();
        Field[] fieldSet = fieldSetMap.get(entityType);
        for (Field field : fieldSet) {
            fieldList.add(field.getName());
        }
        return fieldList;
    }

    private Map<String, String> getStringMapFromJSON(String fields) {
        return new Gson().fromJson(fields, new TypeToken<Map<String, String>>() {
        }.getType());
    }
}
