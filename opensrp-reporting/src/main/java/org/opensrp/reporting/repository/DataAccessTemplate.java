package org.opensrp.reporting.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class DataAccessTemplate extends HibernateTemplate {

    @Autowired
    public DataAccessTemplate(SessionFactory sessionFactory) {
        super(sessionFactory);
        setAllowCreate(false);
    }
    public Object getUniqueResult(String namedQueryName, String[] parameterNames, Object[] parameterValues) {
        return DataAccessUtils.uniqueResult(findByNamedQueryAndNamedParam(namedQueryName, parameterNames, parameterValues));
    }
}
