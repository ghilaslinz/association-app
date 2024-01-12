package com.memberasso.fullstackbackend.repository;

import com.memberasso.fullstackbackend.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepositery extends JpaRepository<Child, Long> {
    // Vous pouvez ajouter ici des méthodes personnalisées si nécessaire
}
