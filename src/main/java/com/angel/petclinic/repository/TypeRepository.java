package com.angel.petclinic.repository;

import com.angel.petclinic.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    boolean existsByNameIgnoreCase(String name);
}
