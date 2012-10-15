package com.pwp.repository.jpa;

import com.pwp.domain.Address;
import com.pwp.domain.Person;
import org.hsqldb.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * User: Mark Spritzler
 * Date: 9/19/12
 * Time: 10:50 PM
 */
@Repository
@Transactional(readOnly = true)
public class JpaPersonRepository implements PersonRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = false)
    public void saveCascadeNone() {
        List<Person> everyone = findAllEager();
        Person firstPerson = everyone.get(0);
        for (Address address: firstPerson.getForTestingNonCascadeaddresses()) {
            address.setCity("Washington");
        }

        Address address = new Address();
        address.setCity("Chicago");
        address.setState("IL");
        address.setStreet1("123 Park View");
        address.setStreet2("Unit A");
        address.setZipCode("32404");
        firstPerson.addNonCascadeAddress(address);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveCascadeAll() {
        List<Person> everyone = findAllEager();
        Person firstPerson = everyone.get(0);
        for (Address address: firstPerson.getAddresses()) {
            address.setCity("Washington");
        }

        Address address = new Address();
        address.setCity("Chicago");
        address.setState("IL");
        address.setStreet1("123 Park View");
        address.setStreet2("Unit A");
        address.setZipCode("32404");
        firstPerson.addAddress(address);
    }

    @Override
    public void cacheUseFindNotInCache() {
        entityManager.find(Person.class, new Long(1));
    }

    @Override
    public void cacheUseFindInCache() {
        List<Person> everyone = findAllEager();
        Person firstPerson = everyone.get(0);
        Person foundPerson = entityManager.find(Person.class, firstPerson.getId());
        System.out.println("***********************************");
        System.out.println("*********Are they the same*********");
        System.out.println("equals equals returns: " + (firstPerson == foundPerson));
    }

    @Override
    public void cacheUseFindThenQuery() {
        Person foundPerson = entityManager.find(Person.class, new Long(1));
        List<Person> everyone = findAllLazy(false);
        for (Person person: everyone) {
            if (person.getId().equals(foundPerson.getId())) {
                System.out.println("***********************************");
                System.out.println("*********Are they the same*********");
                System.out.println("equals equals returns: " + (person == foundPerson));
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void flushModeAtCommit() {
        List<Person> everyone = findAllEager();
        Person firstPerson = everyone.get(1);
        for (Address address: firstPerson.getAddresses()) {
            address.setCity("Chicago");
        }

        Address address = new Address();
        address.setCity("Chicago");
        address.setState("IL");
        address.setStreet1("123 Park View");
        address.setStreet2("Unit A");
        address.setZipCode("32404");
        firstPerson.addAddress(address);
        System.out.println("***************************************");
        System.out.println("End of Method, transaction will commit");
    }

    @Override
    @Transactional(readOnly = false)
    public void flushModeAtCommitWithErrorBefore() {
        List<Person> everyone = findAllEager();
        for (Person person : everyone) {
            for (Address address: person.getAddresses()) {
                address.setCity("Chicago");
            }

            Address address = new Address();
            address.setCity("Chicago");
            address.setState("IL");
            address.setStreet1("123 Park View");
            address.setStreet2("Unit A");
            address.setZipCode("32404");
            person.addAddress(address);
        }
        Person newPerson = new Person();
        newPerson.setFirstName("Freddie");
        newPerson.setLastName("Krueger");

        System.out.println("***************************************");
        System.out.println("Calling Persist on a new Person");
        entityManager.persist(newPerson);
        System.out.println("***************************************");
        System.out.println("About to throw Runtime Exception");
        throw new RuntimeException();
    }

    @Override
    @Transactional(readOnly = false)
    // @TODO not flushing changes before query
    public void flushModeChangesThenQuery() {
        List<Person> everyone = findAllEager();

        Person firstPerson = everyone.get(1);
        for (Address address: firstPerson.getAddresses()) {
            address.setCity("Philadelphia");
        }

        System.out.println("***************************************");
        System.out.println("About to run a query after changes in same transaction");
        everyone = findAllEager();

        for (Person person : everyone) {
            for (Address address: person.getAddresses()) {
                address.setCity("Chicago");
            }

            Address address = new Address();
            address.setCity("Chicago");
            address.setState("IL");
            address.setStreet1("123 Park View");
            address.setStreet2("Unit A");
            address.setZipCode("32404");
            person.addAddress(address);
        }

        System.out.println("***************************************");
        System.out.println("Made more changes after query in same transaction");

    }

    @Override
    public void flushModeChangesInReadOnly() {
        List<Person> everyone = findAllEager();
        for (Person person : everyone) {
            for (Address address: person.getAddresses()) {
                address.setCity("Chicago");
            }

            Address address = new Address();
            address.setCity("Chicago");
            address.setState("IL");
            address.setStreet1("123 Park View");
            address.setStreet2("Unit A");
            address.setZipCode("32404");
            person.addAddress(address);
        }

        System.out.println("***************************************");
        System.out.println("Made changes in Read Only transaction, Cascade All");
    }

    @Override
    public void findVersusGetReference() {
        System.out.println("***************************************");
        System.out.println("Calling EntityManager.find");
        Person foundPerson = entityManager.find(Person.class, new Long(1));
        System.out.println("***************************************");
        System.out.println("Calling EntityManager.getReference");
        Person referencedPerson = entityManager.getReference(Person.class, new Long(2));
    }

    @Override
    public List<Person> findAllLazy(boolean getAddresses) {
        List<Person> people = entityManager.createQuery("SELECT p FROM Person p").getResultList();
        if (getAddresses) {
            for (Person person : people) {
                for (Address address : person.getAddresses()) {
                }
            }
        }
        return people;
    }

    @Override
    public List<Person> findAllEager() {
        List<Person> people = entityManager.createQuery("SELECT distinct p FROM Person p JOIN FETCH p.addresses").getResultList();
        for (Person person : people) {
            for (Address address : person.getAddresses()) {
            }
        }
        return people;
    }

    @Override
    public List<Person> findByFirstName(String firstName, boolean getAddresses) {
        String namedQuery = "person.findByFirstName";
        if (getAddresses) {
            namedQuery = "person.findByFirstNameFetchAddresses";
        }

        return entityManager.createNamedQuery(namedQuery)
                .setParameter("firstName", firstName).getResultList();
    }

    @Override
    public List<Person> findByZipCode(String zipCode) {
        return entityManager.createNamedQuery("person.findByZipCode")
                .setParameter("zipCode", zipCode).getResultList();
    }

    @Override
    public List<Person> findByZipCodeWithAddresses(String zipCode) {
        return entityManager.createNamedQuery("person.findByZipCodeWithAddresses")
                .setParameter("zipCode", zipCode).getResultList();
    }
}
