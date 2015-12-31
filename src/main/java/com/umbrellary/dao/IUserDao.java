package com.umbrellary.dao;


import com.umbrellary.entry.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {

}
