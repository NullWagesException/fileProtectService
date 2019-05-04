package com.zf.service;

import com.zf.pojo.Uplevel;
import com.zf.pojo.User;

import java.util.List;

public interface IUplevelService {

    Uplevel get(Uplevel uplevel);

    List<Uplevel> getAll();

    void insert(Uplevel uplevel);

    void delete(Integer id);

    void update(Uplevel uplevel);

}
