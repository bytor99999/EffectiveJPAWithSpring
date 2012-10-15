package com.pwp.repository.springdata;

import com.pwp.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: Mark Spritzler
 * Date: 8/9/12
 * Time: 11:49 AM
 */
public interface SpringDataPersonRepository extends JpaRepository<Person, Long> {

    // DerivedQuery
    public List<Person> findByFirstName(String firstName);

    @Query("SELECT distinct p FROM Person p JOIN FETCH p.addresses")
    public List<Person> findAllEagerFetchAddresses();

    @Query("SELECT distinct p FROM Person p JOIN FETCH p.addresses a WHERE p.firstName = :firstName")
    public List<Person> findByFirstNameEagerFetchAddresses(@Param("firstName") String firstName);

    @Query("SELECT p from Person p JOIN p.addresses a WHERE a.zipCode = :zipCode")
    public List<Person> findByZipCode(@Param("zipCode") String zipCode);

    @Query("SELECT distinct p from Person p JOIN FETCH p.addresses a WHERE a.zipCode = :zipCode")
    public List<Person> findByZipCodeEagerFetchAddresses(@Param("zipCode") String zipCode);
}
