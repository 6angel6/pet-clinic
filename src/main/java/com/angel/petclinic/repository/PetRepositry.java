package com.angel.petclinic.repository;

import com.angel.petclinic.dto.response.PetResponse;
import com.angel.petclinic.model.Pet;
import com.angel.petclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepositry extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserId(long userId);
}
