package com.pwp.web.controller;

import com.pwp.domain.Person;
import com.pwp.repository.springdata.SpringDataPersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * User: Mark Spritzler
 * Date: 8/1/12
 * Time: 12:38 PM
 */
@ContextConfiguration(locations = {"classpath:config/springdata/springdata-test-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataControllerTest {

    @Autowired
    private SpringDataController springDataController;

    @Autowired
    private SpringDataPersonRepository springDataPersonRepository;

    @Before
    public void removeAll() {
        springDataPersonRepository.deleteAll();
    }

    @Test
    public void testSaveAndFind() {
        Person person = createPerson();
        person = createPerson();
        springDataPersonRepository.save(person);
        person = createPerson();
        springDataPersonRepository.save(person);
        List<Person> people = springDataController.getAllPeopleLazy().getDomainObjects();
        assertNotNull(people);
        assertEquals("Expect three people", 3, people.size());

        List<Person> morePeopleNamedPaul = springDataController.findByFirstName("Paul").getDomainObjects();
        assertNotNull(morePeopleNamedPaul);
        assertEquals("Expect three people", 3, morePeopleNamedPaul.size());

    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName("Paul");
        person.setLastName("Zain");
        return person;
    }

}
