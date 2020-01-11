package com.students.security.repositories;

import com.students.model.User;
import com.students.security.roles.SecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryStub {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryStub.class);
    private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    static {
        users.put("admin", new User(1, "admin", "$2a$12$XvZhRvUnBwXMPS6KO9resOuqKYBP3dJlB4gcgItXfGQnnkvi2j76.", SecurityRole.ROLE_ADMIN));
        users.put("alex", new User(2, "alex", "$2a$12$TDDr7gNBFF3M/iRwH3g1GOrY27UDmV/TIqLL2NsOtRY9yFKKsXKO.", SecurityRole.ROLE_USER));
    }

    public User getUserByName(String name){
        LOG.debug("[ (name : {})", name);

        User user = users.get(name);

        LOG.debug("] (user : {})", user);
        return user;
    }

}
