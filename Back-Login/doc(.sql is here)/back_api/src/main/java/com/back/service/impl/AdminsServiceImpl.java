package com.back.service.impl;

import com.back.dao.AdminsMapper;
import com.back.model.Admins;
import com.back.service.AdminsService;
import com.back.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class AdminsServiceImpl extends AbstractService<Admins> implements AdminsService {
    @Resource
    private AdminsMapper adminsMapper;

    @Override
    public Admins findByUsername(String username) {
        return adminsMapper.findByUsername(username);
    }

    @Override
    public void register(Admins admins) {
        adminsMapper.insert(admins);
    }
}
