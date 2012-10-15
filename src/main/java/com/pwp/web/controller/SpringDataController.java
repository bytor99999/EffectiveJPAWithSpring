package com.pwp.web.controller;

import com.pwp.DontTryThisAtHome;
import com.pwp.domain.Person;
import com.pwp.repository.springdata.SpringDataPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Mark Spritzler
 * Date: 8/9/12
 * Time: 11:58 AM
 */
@Controller
@RequestMapping("/springData")
public class SpringDataController {

    @Autowired
    private SpringDataPersonRepository springDataPersonRepository;

    private DontTryThisAtHome resultJson = new DontTryThisAtHome();

    @RequestMapping(value = "/people/lazy", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome getAllPeopleLazy() {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Get All People Lazy----");
        List<Person> people = springDataPersonRepository.findAll();
        resultJson.setDomainObjects(people);
        return resultJson;
    }

    @RequestMapping(value = "/people/eager", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome getAllPeopleEager() {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Get All People Eager----");
        List<Person> people = springDataPersonRepository.findAllEagerFetchAddresses();
        resultJson.setDomainObjects(people);
        return resultJson;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody DontTryThisAtHome save(Person person) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Save Person----");
        Person personSaved = springDataPersonRepository.save(person);
        List<Person> people = new ArrayList<Person>();
        people.add(personSaved);
        resultJson.setDomainObjects(people);
        return resultJson;
    }

    @RequestMapping(value = "/find/{firstName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByFirstName(@PathVariable("firstName") String firstName) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Find By First Name----");
        List<Person> people = springDataPersonRepository.findByFirstNameEagerFetchAddresses(firstName);
        //List<Person> people = springDataPersonRepository.findByFirstName(firstName);
        // Temp to create Lazy Initialization Exception
        /*for (Person person : people) {
            System.out.println("Addresses for " + person.getFirstName() + " " + person.getLastName());
            List<Address> addresses = person.getAddresses();
            for (Address address : addresses) {
                System.out.println(address.toString());
            }
        }*/
        resultJson.setDomainObjects(people);
        return resultJson;
    }

    @RequestMapping(value="/deleteData", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String deleteAllPersons() {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Delete All People----");
        springDataPersonRepository.deleteAll();
        return "Deleted all data";
    }

    @RequestMapping(value = "/zipcode/{zipCode}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByZipCode(@PathVariable("zipCode") String zipCode) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Find by ZipCode----");
        List<Person> people = springDataPersonRepository.findByZipCodeEagerFetchAddresses(zipCode);
        //List<Person> people = springDataPersonRepository.findByZipCode(zipCode);
        // Temp to create Lazy Initialization Exception
        /*for (Person person : people) {
            System.out.println("Addresses for " + person.getFirstName() + " " + person.getLastName());
            List<Address> addresses = person.getAddresses();
            for (Address address : addresses) {
                System.out.println(address.toString());
            }
        }*/
        resultJson.setDomainObjects(people);
        return resultJson;
    }
}
