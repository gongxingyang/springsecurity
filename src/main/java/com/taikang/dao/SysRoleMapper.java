package com.taikang.dao;

import com.taikang.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author itw_gongxy
 * @date 2020/2/24 15:03
 */
@Repository
public interface SysRoleMapper {

    List<SysRole> findRoleByUserId(Integer userId);


    void insertRole(SysRole sysRole);
}
