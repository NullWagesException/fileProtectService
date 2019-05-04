package com.zf.service.impl;

import com.zf.mapper.UplevelMapper;
import com.zf.pojo.Uplevel;
import com.zf.service.IUplevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class UplevelService implements IUplevelService {

    @Autowired
    private UplevelMapper uplevelMapper;

    @Override
    public Uplevel get(Uplevel uplevel) {
        return uplevelMapper.get(uplevel);
    }

    @Override
    public List<Uplevel> getAll() {
        return uplevelMapper.getAll();
    }

    @Override
    public void insert(Uplevel uplevel) {
        uplevelMapper.insert(uplevel);
    }

    @Override
    public void delete(Integer id) {
        uplevelMapper.delete(id);
    }

    @Override
    public void update(Uplevel uplevel) {
        uplevelMapper.update(uplevel);
    }
}
