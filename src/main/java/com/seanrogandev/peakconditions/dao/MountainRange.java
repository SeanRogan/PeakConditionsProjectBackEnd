package com.seanrogandev.peakconditions.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
public class MountainRange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "range_id" , unique = true, nullable = false)
    private Long id;
    @Column(name = "range_name" , unique = true, nullable = false)
    private String rangeName;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<MountainPeak> peaksInRange = new HashSet<>();
}

