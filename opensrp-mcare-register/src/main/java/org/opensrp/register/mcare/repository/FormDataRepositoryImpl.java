package org.opensrp.register.mcare.repository;

import static java.util.UUID.randomUUID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.repository.FormDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Repository
public class FormDataRepositoryImpl extends FormDataRepository{
    private static final String ID = "id";
    private static final String DETAILS = "details";
    private static final String DOCUMENT_TYPE = "type";
    private static final String ID_FIELD_ON_ENTITY = "CASEID";
    private static final String CASE_ID_VIEW_NAME = "by_cASEID";
    private Map<String, Field[]> fieldSetMap;
    private CouchDbConnector db;
    private Map<String, String> designDocMap;

    @Autowired
    public FormDataRepositoryImpl(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
        this.db = db;
        initMaps();
    }

    private void initMaps() {
        designDocMap = new HashMap<>();
        fieldSetMap = new HashMap<>();
        
        designDocMap.put(AllConstants.FormEntityTypes.HOUSE_HOLD_TYPE, "HouseHold");
        designDocMap.put(AllConstants.FormEntityTypes.ELCO_TYPE, "Elco");
        fieldSetMap.put(AllConstants.FormEntityTypes.HOUSE_HOLD_TYPE, HouseHold.class.getDeclaredFields());
        fieldSetMap.put(AllConstants.FormEntityTypes.ELCO_TYPE, Elco.class.getDeclaredFields());
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
