package com.pwp.repository.springdata;

import com.pwp.domain.Address;
import com.pwp.domain.Person;
import com.pwp.repository.springdata.SpringDataPersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * User: Mark Spritzler
 * Date: 8/2/12
 * Time: 12:11 PM
 */
@ContextConfiguration(locations = {"classpath:config/springdata/springdata-test-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataPersonRepositoryTest {

    @Autowired
    private SpringDataPersonRepository springDataPersonRepository;

    @Before
    public void removeAll() {
        springDataPersonRepository.deleteAll();
    }

    @Test
    public void testSaveAndFind() {
        Person person = createPerson();
        Person savedPerson = springDataPersonRepository.save(person);
        Person retrievedPerson = springDataPersonRepository.findByFirstName(person.getFirstName()).iterator().next();
        assertEquals("retrievedPerson should match savedPerson", savedPerson, retrievedPerson);
        person = createPerson();
        springDataPersonRepository.save(person);
        person = createPerson();
        springDataPersonRepository.save(person);

        List<Person> people = new ArrayList<Person>();
        for (Person personInIterator : springDataPersonRepository.findAll()) {
            people.add(personInIterator);
        }

        assertNotNull(people);
        assertEquals("Expect three people", 3, people.size());

        List<Person> morePeopleNamedPaul = springDataPersonRepository.findByFirstName("Paul");
        assertNotNull(morePeopleNamedPaul);
        assertEquals("Expect three people", 3, morePeopleNamedPaul.size());

        List<Person> zipCode45004 = springDataPersonRepository.findByZipCode("45004");
        assertNotNull(zipCode45004);
        //assertEquals("Expect two people", 2, zipCode45004.size());

        List<Person> zipCode90007 = springDataPersonRepository.findByZipCode("90007");
        assertNotNull(zipCode90007);
        //assertEquals("Expect one person", 1, zipCode90007.size());
    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName("Paul");
        person.setLastName("Zain");

        Address address1 = new Address();
        address1.setStreet1("123 main Street");
        address1.setCity("Austin");
        address1.setState("TX");
        address1.setZipCode("45004");

        Address address2 = new Address();
        address2.setStreet1("123 Elm Street");
        address2.setCity("Los Angeles");
        address2.setState("CA");
        address2.setZipCode("90007");

        Address address3 = new Address();
        address3.setStreet1("123 First Street");
        address3.setCity("Austin");
        address3.setState("TX");
        address3.setZipCode("45004");

        person.addAddress(address1);
        person.addAddress(address2);
        person.addAddress(address3);
        return person;
    }
}
