package com.taikang;

import com.taikang.dao.SysRoleMapper;
import com.taikang.dao.SysUserMapper;
import com.taikang.entity.SysRole;
import com.taikang.entity.SysUser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

/**
 * @author itw_gongxy
 * @date 2020/2/24 10:11
 */
@MapperScan("com.taikang.dao")
@SpringBootApplication
public class SpringSecurityApplication {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class,args);
    }

   // @PostConstruct
    public void addModel(){
        LocalDateTime time =LocalDateTime.now();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        SysUser sysUser = new SysUser(null,"zs",encoder.encode("123"),
                "zs",true,true,true,true,time,time,null);
        sysUserMapper.insertUser(sysUser);
        SysUser sysUser1 = new SysUser(null,"lisi",encoder.encode("456"),
                "lisi",true,true,true,true,time,time,null);
        sysUserMapper.insertUser(sysUser1);
        SysUser sysUser2 = new SysUser(null,"admin",encoder.encode("admin"),
                "admin",true,true,true,true,time,time,null);
        sysUserMapper.insertUser(sysUser2);

        SysRole sysRole = new SysRole();
        sysRole.setRolemome("普通用户");
        sysRole.setRolename("USER");
        sysRoleMapper.insertRole(sysRole);
        SysRole sysRole1 = new SysRole();
        sysRole.setRolemome("管理员");
        sysRole.setRolename("ADMIN");
        sysRoleMapper.insertRole(sysRole);
        SysRole sysRole2 = new SysRole();
        sysRole.setRolemome("只读");
        sysRole.setRolename("READ");
        sysRoleMapper.insertRole(sysRole);

    }
}
