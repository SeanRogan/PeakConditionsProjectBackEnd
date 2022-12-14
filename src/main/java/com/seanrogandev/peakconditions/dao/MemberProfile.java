package com.seanrogandev.peakconditions.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

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
    private String temperaturePreferenceLow;
    private String temperaturePreferenceHigh;
    private String windConditionsPreferenceMax;
    @OneToMany(targetEntity = MountainPeak.class)
    @JoinTable(name = "favorite_peaks",
    joinColumns = @JoinColumn(name = "profile_id"),
    inverseJoinColumns = @JoinColumn(name = "peak_id")
    )
    private Set<MountainPeak> favoritePeaks;
    @Temporal(TemporalType.DATE)
    private Date memberSince;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordReset;


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
