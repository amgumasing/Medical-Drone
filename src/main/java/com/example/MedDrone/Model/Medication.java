package com.example.MedDrone.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.processing.Pattern;

import java.util.Set;

@Entity
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Name can only contain letters, numbers, hyphens, and underscores.")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int weight;

//    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code can only contain uppercase letters, numbers, and underscores.")
    @Column(nullable = false, unique = true)
    private String code;


    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Drone> getDrones() {
        return drones;
    }

    public void setDrones(Set<Drone> drones) {
        this.drones = drones;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToMany
    @JoinTable(
            name = "drone_medication",
            joinColumns = @JoinColumn(name = "medication_id"),
            inverseJoinColumns = @JoinColumn(name = "drone_id")
    )
    private Set<Drone> drones;

}
