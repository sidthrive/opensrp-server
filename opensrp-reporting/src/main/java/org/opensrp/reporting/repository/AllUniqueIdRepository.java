package org.opensrp.reporting.repository;

import org.opensrp.reporting.domain.ANM;
import org.opensrp.reporting.domain.LastId;
import org.opensrp.reporting.domain.UniqueId;
import org.opensrp.reporting.repository.DataAccessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllUniqueIdRepository {
    private DataAccessTemplate dataAccessTemplate;

    @Autowired
    public AllUniqueIdRepository(@Qualifier("serviceProvidedDataAccessTemplate") DataAccessTemplate dataAccessTemplate){
        this.dataAccessTemplate = dataAccessTemplate;
    }

    public List fetchUniqueIds(String anmIdentifier){
        return dataAccessTemplate.findByNamedQueryAndNamedParam(UniqueId.FIND_UNIQUE_ID_BY_ANM_IDENTIFIER,
                new String[]{"anmIdentifier"}, new Object[]{anmIdentifier});
    }

    public boolean saveLastId(String anmIdentifier, Long lastId){
        LastId id = new LastId();
        List obj = dataAccessTemplate.findByNamedQueryAndNamedParam(
                ANM.FIND_BY_ANM_ID,
                new String[]{"anmIdentifier"},
                new Object[]{anmIdentifier});
        if(obj == null || obj.isEmpty()){
            return false;
        }
        ANM anm = (ANM)obj.get(0);
        id.setAnm(anm);
        id.setLastId(lastId);

        try{
            dataAccessTemplate.save(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public List fetchLastUsedId(String anmIdentifier) {
        return dataAccessTemplate.findByNamedQueryAndNamedParam(LastId.FIND_LAST_USED_ID_BY_ANM_IDENTIFIER,
                new String[]{"anmIdentifier"}, new Object[]{anmIdentifier});
    }

}
