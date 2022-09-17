package com.seanrogandev.peakconditions.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

/**
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(unique = true , nullable = false)
    @MapsId
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    //username should be their main email address
    private String userName;
    @Column(columnDefinition = "VARCHAR", nullable = false)
    private String passWord;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private MemberProfile profile;

}
