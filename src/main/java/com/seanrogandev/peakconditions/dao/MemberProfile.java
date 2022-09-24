package com.seanrogandev.peakconditions.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MemberProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,unique = true)
    private Long id;
    @JoinColumn(table = "member" , referencedColumnName = "id")
    private Long owner_id;
    private String preferences;
    //@ManyToMany
    //private HashSet<MountainPeak> favoriteMountains;
    private String temperaturePreferenceLow;
    private String temperaturePreferenceHigh;
    private String windConditionsPreferenceMax;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MemberProfile that = (MemberProfile) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
