package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.Role;
import com.yanwenl.codingmanager.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("UserDetailsServiceImpl")
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    // TODO: the wrong user returning null will throw an exception in "user.getRoles()"
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        log.debug("login by user: " + userName);
        User user = userService.findUserByUserName(userName);
        log.debug("find user: " + user);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        log.debug("find grantedAuthority: " + authorities);
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user,
                                                   List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(),
                user.getActive(), true, true, true, authorities);
    }
}
