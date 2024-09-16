package com.udc.repository;

import com.udc.model.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonalDetailRepository extends JpaRepository<PersonalData, Long> {

}
