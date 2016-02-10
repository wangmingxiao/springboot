package com.umbrellary.serviceimpl;

import com.umbrellary.dao.IHomeDao;
import com.umbrellary.entry.Home;
import com.umbrellary.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("homeService")
public class HomeService implements IHomeService {

    @Autowired
    @Qualifier("homeDaoimpl")
    private IHomeDao iHomeDao;

    @Override
    public void setOne(String address, String number) {
        Home home = new Home();
        home.setAddress(address);
        home.setNumber(number);

        iHomeDao.setOne(home);
    }
}
