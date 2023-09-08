package com.modak.challenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String email;
    @OneToMany(mappedBy = "receiver")
    private List<Notification> notifications = new ArrayList<>();

    /*
    Although this class now only has a single field,
    I decided to create a User object for greater scalability if I wanted to add more attributes to the User type in the future.
     */
}
