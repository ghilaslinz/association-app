package com.memberasso.fullstackbackend.controller;

import com.memberasso.fullstackbackend.exception.ChildNotFoundException;
import com.memberasso.fullstackbackend.model.Child;
import com.memberasso.fullstackbackend.repository.ChildRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ChildController {

    @Autowired
    private ChildRepositery childRepository;

    @PostMapping("/child")
    public Child addChild(@RequestBody Child newChild) {
        return childRepository.save(newChild);
    }

    @GetMapping("/children")
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    @GetMapping("/child/{id}")
    public Child getChildById(@PathVariable Long id) {
        return childRepository.findById(id)
                .orElseThrow(() -> new ChildNotFoundException(id));
    }

    @PutMapping("/child/{id}")
    public Child updateChild(@RequestBody Child updatedChild, @PathVariable Long id) {
        try {
            // Log the incoming data
            System.out.println("Incoming Child Update Data:");
            System.out.println(updatedChild.toString());

            return childRepository.findById(id)
                    .map(child -> {
                        // Your existing update logic here

                        return childRepository.save(child);
                    })
                    .orElseThrow(() -> new ChildNotFoundException(id));
        } catch (Exception e) {
            // Log any exceptions that occur
            e.printStackTrace(); // You can customize this logging based on your preferences
            throw e; // Re-throw the exception for proper error handling
        }
    }



    @DeleteMapping("/child/{id}")
    public String deleteChild(@PathVariable Long id) {
        childRepository.deleteById(id);
        return "Child with ID " + id + " was deleted successfully";
    }
}
