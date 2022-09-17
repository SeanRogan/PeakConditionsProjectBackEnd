package com.seanrogandev.peakconditions.dao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data

public class MemberProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,unique = true)
    private Long id;
    @JoinColumn(table = "member" , referencedColumnName = "id")
    private Long owner_id;
    private String preferences;
    //private HashMap<String, MountainPeak> favoriteMountains;
    private String temperaturePreferenceLow;
    private String temperaturePreferenceHigh;
    //private HashSet<String> weatherConditionPreferences;
    private String windConditionsPreferenceMax;

}
