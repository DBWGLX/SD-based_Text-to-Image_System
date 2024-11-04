package com.back.dao;

import com.back.core.Mapper;
import com.back.model.Admins;
import org.apache.ibatis.annotations.Param;

public interface AdminsMapper extends Mapper<Admins> {
    Admins findByUsername(@Param("username") String username);
}