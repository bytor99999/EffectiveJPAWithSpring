package com.pwp.domain;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Mark Spritzler
 * Date: 8/1/12
 * Time: 10:44 AM
 */
@NamedQueries({
        @NamedQuery(name="person.findByZipCode",
                    query="SELECT p " +
                            "FROM Person p " +
                            "JOIN p.addresses a " +
                           "WHERE a.zipCode = :zipCode"),
        @NamedQuery(name="person.findByZipCodeWithAddresses",
                    query="SELECT distinct p " +
                            "FROM Person p " +
                      "JOIN FETCH p.addresses a " +
                           "WHERE a.zipCode = :zipCode"),
        @NamedQuery(name="person.findByFirstName",
                    query="SELECT p " +
                            "FROM Person p " +
                           "WHERE p.firstName = :firstName"),
       @NamedQuery(name="person.findByFirstNameFetchAddresses",
                   query="SELECT distinct p " +
                           "FROM Person p " +
                     "JOIN FETCH p.addresses a " +
                          "WHERE p.firstName = :firstName")
})
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Set<Address> addresses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "person_id")
    private Set<Address> forTestingNonCascadeaddresses;

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        if (this.addresses == null) {
            this.addresses = new HashSet<Address>();
        }
        this.addresses.add(address);
    }

    public Set<Address> getForTestingNonCascadeaddresses() {
        return forTestingNonCascadeaddresses;
    }

    public void setForTestingNonCascadeaddresses(Set<Address> forTestingNoneCascadeaddresses) {
        this.forTestingNonCascadeaddresses = forTestingNoneCascadeaddresses;
    }

    public void addNonCascadeAddress(Address address) {
        if (this.forTestingNonCascadeaddresses == null) {
            this.forTestingNonCascadeaddresses = new HashSet<Address>();
        }
        this.forTestingNonCascadeaddresses.add(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
