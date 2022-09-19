package com.seanrogandev.peakconditions.dao;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MountainPeak that = (MountainPeak) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
