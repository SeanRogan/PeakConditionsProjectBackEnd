package com.seanrogandev.peakconditions.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

/**
 * The Member class is the representation of a user of the application, either a free tier user, paid tier user, or admin
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
