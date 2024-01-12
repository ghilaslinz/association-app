package com.memberasso.fullstackbackend.controller;

import com.memberasso.fullstackbackend.exception.FamilyNotFoundException;
import com.memberasso.fullstackbackend.model.Family;
import com.memberasso.fullstackbackend.repository.FamilyRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.memberasso.fullstackbackend.model.Child;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class FamilyController {
    @Autowired
    private FamilyRepositery familyRepository;

    @PostMapping("/family")
    @Transactional
    public Family addFamily(@RequestBody Family newFamily) {
        if (newFamily.getChildren() != null) {
            newFamily.getChildren().forEach(child -> child.setFamily(newFamily));
        }
        return familyRepository.save(newFamily);
    }


    @GetMapping("/families")
    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    @GetMapping("/family/{id}")
    public Family getFamilyById(@PathVariable Long id) {
        return familyRepository.findById(id)
                .orElseThrow(() -> new FamilyNotFoundException(id));
    }

    @PutMapping("/family/{id}")
    @Transactional
    public Family updateFamily(@RequestBody Family updatedFamily, @PathVariable Long id) {
        return familyRepository.findById(id)
                .map(family -> {
                    // Update family information
                    family.setFamilyName(updatedFamily.getFamilyName());
                    family.setFatherName(updatedFamily.getFatherName());
                    family.setMotherName(updatedFamily.getMotherName());
                    family.setAddress(updatedFamily.getAddress());
                    family.setContactNumber(updatedFamily.getContactNumber());
                    family.setAdditionalNotes(updatedFamily.getAdditionalNotes());

                    // Cascade updates to children
                    List<Child> updatedChildren = updatedFamily.getChildren();
                    if (updatedChildren != null) {
                        for (Child updatedChild : updatedChildren) {
                            // Find the matching child in the original family
                            Child originalChild = family.findChildById(updatedChild.getId());
                            if (originalChild != null) {
                                // Update child information
                                originalChild.setName(updatedChild.getName());
                                originalChild.setDateOfBirth(updatedChild.getDateOfBirth());
                                // Update other necessary fields here
                            } else {
                                // Child not found, consider adding error handling or skip
                            }
                        }
                    }

                    // Save the updated family and cascade the changes to children
                    family = familyRepository.saveAndFlush(family);

                    return family;
                })
                .orElseThrow(() -> new FamilyNotFoundException(id));
    }





    @DeleteMapping("/family/{id}")
    public String deleteFamily(@PathVariable Long id) {
        familyRepository.deleteById(id);
        return "Family with ID " + id + " was deleted successfully";
    }
}
