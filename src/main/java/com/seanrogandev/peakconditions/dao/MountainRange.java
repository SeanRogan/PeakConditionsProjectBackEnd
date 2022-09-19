package com.seanrogandev.peakconditions.dao;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MountainRange that = (MountainRange) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

