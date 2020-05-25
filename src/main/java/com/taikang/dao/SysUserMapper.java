package com.taikang.dao;

import com.taikang.entity.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author itw_gongxy
 * @date 2020/2/24 14:25
 */
@Repository
public interface SysUserMapper {
    /**
     * 插入
     * @param user
     */
     void insertUser(SysUser user);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser selectUserByuserName(String username);

}
