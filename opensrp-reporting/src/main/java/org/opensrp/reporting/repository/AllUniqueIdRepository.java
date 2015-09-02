package org.opensrp.reporting.repository;

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
}
