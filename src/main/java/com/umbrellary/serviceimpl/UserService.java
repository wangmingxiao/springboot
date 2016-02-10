package com.umbrellary.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.umbrellary.dao.IUserDao;
import com.umbrellary.entry.User;
import com.umbrellary.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserDao iUserDao;

    @Override
    public long setOne() {
        User user = null;
        try {
            user = new User();
            user.setEmail("rui.crater@gmail.com");
            user.setPassword("呵呵");
            iUserDao.save(user);

        } catch (Exception ex) {
            logger.debug(ex.toString());
        }
        return user.getId();
    }
}
