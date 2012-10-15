package com.pwp.web.controller;

import com.pwp.DontTryThisAtHome;
import com.pwp.domain.Address;
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
        System.out.println("----Will get LazyInitialization because findAll is lazy. Need service for Transaction ----");
        System.out.println("----The Exception doesn't show because it is occuring in Jackson conversion");
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

    @RequestMapping(value = "/find/{firstName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByFirstName(@PathVariable("firstName") String firstName) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Find By First Name----");
        List<Person> people = springDataPersonRepository.findByFirstNameEagerFetchAddresses(firstName);
        resultJson.setDomainObjects(people);
        return resultJson;
    }

    @RequestMapping(value = "/zipcode/{zipCode}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByZipCode(@PathVariable("zipCode") String zipCode) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----Spring Data - Find by ZipCode----");
        List<Person> people = springDataPersonRepository.findByZipCodeEagerFetchAddresses(zipCode);
        resultJson.setDomainObjects(people);
        return resultJson;
    }
}
