package com.taikang.service;

import com.taikang.dao.SysRoleMapper;
import com.taikang.dao.SysUserMapper;
import com.taikang.entity.SysRole;
import com.taikang.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itw_gongxy
 * @date 2020/2/24 14:24
 */
@Service
public class JdbcUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectUserByuserName(username);
        if(sysUser!=null){
            List<SysRole> roleByUser = sysRoleMapper.findRoleByUserId(sysUser.getId());
            List<GrantedAuthority> authorities = new ArrayList<>();
            if(roleByUser.size()>0){
                roleByUser.forEach(
                        x->authorities.add(new SimpleGrantedAuthority("ROLE_"+x.getRolename())));
            }
            sysUser.setAuthoritys(authorities);
        }
        return sysUser;
    }
}
