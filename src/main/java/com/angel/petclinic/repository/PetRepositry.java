package com.angel.petclinic.repository;

import com.angel.petclinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepositry extends JpaRepository<Pet, Long> {
}
