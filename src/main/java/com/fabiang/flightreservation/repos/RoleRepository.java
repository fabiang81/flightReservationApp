package com.fabiang.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabiang.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
