package com.taikang.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author itw_gongxy
 * @date 2020/2/24 13:56
 */
@Data
public class SysUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String realname;
    private boolean isenable;
    private boolean isexpired;
    private boolean islock;
    private boolean iscredentials;
    private LocalDateTime createtime;
    private LocalDateTime logintime;

    private List<GrantedAuthority> authoritys;

    public SysUser(Integer id,String username, String password,
                   String realname, boolean isenable,
                   boolean isexpired, boolean islock,
                   boolean iscredentials, LocalDateTime createtime,
                   LocalDateTime logintime, List<GrantedAuthority> authoritys) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.isenable = isenable;
        this.isexpired = isexpired;
        this.islock = islock;
        this.iscredentials = iscredentials;
        this.createtime = createtime;
        this.logintime = logintime;
        this.authoritys = authoritys;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritys;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isexpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return islock;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return iscredentials;
    }
    @Override
    public boolean isEnabled() {
        return isenable;
    }
}
