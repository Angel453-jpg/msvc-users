package com.angel.springcloud.msvc.users.repositories;

import com.angel.springcloud.msvc.users.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
