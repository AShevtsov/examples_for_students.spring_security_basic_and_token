package com.students.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.students.security.repositories.UserRepositoryStub;
import com.students.security.roles.SecurityRole;

import java.util.Collections;

@Service
public class UserSpringDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserSpringDetailsService.class);

    @Autowired
    UserRepositoryStub userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOG.trace("[ (userName : {})", s);

        com.students.model.User storedUser = userRepository.getUserByName(s);
        User user = new User(
                storedUser.getName(),
                storedUser.getPassword(),
                Collections.singleton(storedUser.getRole())
            );

        if (user == null) {
            LOG.error("User doesn't exist!");
            throw new UsernameNotFoundException("User doesn't exist!");
        }


        LOG.trace("] (user : {})", user);
        return user;
    }

}
