package com.back.service;

import com.back.model.Admins;
import com.back.core.Service;


public interface AdminsService extends Service<Admins> {
    Admins findByUsername(String username);
    void register(Admins admins);
}
