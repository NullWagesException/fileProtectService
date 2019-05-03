package com.zf.service;

import com.zf.pojo.User;

import java.util.List;

public interface IUserService {

    List<User> getAll();

    User check(User user);

    User get(User user);

    void insert(User user);

    void delete(Integer id);

    void update(User user);

}
