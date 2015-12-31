package com.umbrellary.daoimpl;

import com.umbrellary.dao.IHomeDao;
import com.umbrellary.entry.Home;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository("homeDaoimpl")
public class HomeDaoimpl implements IHomeDao {

    private SessionFactory hibernateFactory;

    @Autowired
    public void SomeService(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public void setOne(Home home) {
        Session session = hibernateFactory.openSession();
        try {
            session.save(home);
        } finally {
            session.flush();
            session.close();
        }
    }
}
