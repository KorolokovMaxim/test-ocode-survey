package com.ocodetest.survey.repository;

import com.ocodetest.survey.entity.ERole;
import com.ocodetest.survey.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(ERole name);

}
