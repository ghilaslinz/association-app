package com.memberasso.fullstackbackend.repository;

import com.memberasso.fullstackbackend.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepositery extends JpaRepository<Family,Long> {
}
