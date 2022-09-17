package com.seanrogandev.peakconditions.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class MountainPeak {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "peak_id" , unique = true, nullable = false)
    private Long id;
    @Column(name = "peak_name" , unique = true, nullable = false)
    private String peakName;
    @Column(name = "uri" , unique = true, nullable = false)
    private String uri;
    @ManyToOne
    @JoinColumn(name = "home_range_id")
    private MountainRange homeRange;

}
