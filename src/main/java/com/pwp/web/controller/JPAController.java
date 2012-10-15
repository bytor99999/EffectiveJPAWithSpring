package com.pwp.web.controller;

import com.pwp.DontTryThisAtHome;
import com.pwp.domain.Address;
import com.pwp.domain.Person;
import com.pwp.repository.jpa.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Set;

/**
 * User: Mark Spritzler
 * Date: 8/9/12
 * Time: 11:58 AM
 */
@Controller
@RequestMapping("/jpa")
public class JPAController {

    @Autowired
    private PersonRepository personRepository;

    private DontTryThisAtHome resultJson = new DontTryThisAtHome();

    @RequestMapping(value = "/people/cascade/{testNumber}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome cascadeTestButtons(@PathVariable("testNumber") int testNumber) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Cascade Test#: "+ testNumber + "----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        //save stuff
        if (testNumber == 1) {
            // Cascade None
            personRepository.saveCascadeNone();
            response.setType("Ran Cascade None");
        } else {
            // Cascade All
            personRepository.saveCascadeAll();
            response.setType("Ran Cascade All");
        }
        return response;
    }

    @RequestMapping(value = "/people/cache/{testNumber}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome cacheTestButtons(@PathVariable("testNumber") int testNumber) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Cache Testing Test#: "+ testNumber + "----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        switch (testNumber) {
            case 1: {
                //CacheMode one is data not in cache for a find,
                personRepository.cacheUseFindNotInCache();
                response.setType("Ran Cache Example 1.");
                break;
            }
            case 2: {
                //CacheMode two is data is in cache for find
                personRepository.cacheUseFindInCache();
                response.setType("Ran Cache Example 2.");
                break;
            }
            case 3: {
                //CacheMode three is query with some data in cache
                personRepository.cacheUseFindThenQuery();
                response.setType("Ran Cache Example 3.");
                break;
            }
        }
        return response;
    }

    @RequestMapping(value = "/people/flush/{testNumber}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome flushTestButtons(@PathVariable("testNumber") int testNumber) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Flush Mode tests#: "+ testNumber + "----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        switch (testNumber) {
            case 1: {
                //Flush one is only commit
                personRepository.flushModeAtCommit();
                response.setType("Ran Flush Example 1.");
                break;
            }
            case 2: {
                //Flush two is error before commit. With changes before the error. Big Use case.
                personRepository.flushModeAtCommitWithErrorBefore();
                response.setType("Ran Flush Example 2.");
                break;
            }
            case 3: {
                //Flush three is do changes then query again
                personRepository.flushModeChangesThenQuery();
                response.setType("Ran Flush Example 3.");
                break;
            }
            case 4: {
                //Flush four is transaction set to ReadOnly, but update in code
                // We saw this already with cascadeOptions. But now cascadeOptions is all
                personRepository.flushModeChangesInReadOnly();
                response.setType("Ran Flush Example 4.");
                break;
            }
        }
        return response;
    }

    @RequestMapping(value = "/people/find", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome getPersonFindAndGetReference() {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Difference between find and getReference ----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        personRepository.findVersusGetReference();
        response.setType("Ran Difference Between find and getReference.");
        return response;
    }


    @RequestMapping(value = "/people/eager", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome getAllPeopleEager() {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Get All People EAGER----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        List<Person> people = personRepository.findAllEager();
        response.setDomainObjects(people);
        return response;
    }

    @RequestMapping(value = "/people/lazy", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome getAllPeopleLazy() {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Get All People LAZY----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        List<Person> people = personRepository.findAllLazy(true);
        response.setDomainObjects(people);
        return response;
    }

    @RequestMapping(value = "/people/lazy/error", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome getAllPeopleLazyError() {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Get All People Lazy with Exception----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        List<Person> people = personRepository.findAllLazy(false);
        // Create Lazy Initialization Exception
        for (Person person : people) {
            System.out.println("Addresses for " + person.getFirstName() + " " + person.getLastName());
            Set<Address> addresses = person.getAddresses();
            for (Address address : addresses) {
                System.out.println(address.toString());
            }
        }
        response.setDomainObjects(people);
        return response;
    }

    @RequestMapping(value = "/find/{firstName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByFirstName(@PathVariable("firstName") String firstName) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Find By First Name - Lazy Addresses----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        List<Person> people = personRepository.findByFirstName(firstName, false);
        response.setDomainObjects(people);
        return response;
    }

    @RequestMapping(value = "/find/{firstName}/addresses", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByFirstNameWithAddresses(@PathVariable("firstName") String firstName) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Find By First Name With Addresses----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        List<Person> people = personRepository.findByFirstName(firstName, true);
        response.setDomainObjects(people);
        return response;
    }

    @RequestMapping(value = "/zipcode/{zipCode}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByZipCode(@PathVariable("zipCode") String zipCode) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Find by ZipCode - Lazy Addresses----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        List<Person> people = personRepository.findByZipCode(zipCode);
        response.setDomainObjects(people);
        return response;
    }

    @RequestMapping(value = "/zipcode/{zipCode}/addresses", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody DontTryThisAtHome findByZipCodeWithAddresses(@PathVariable("zipCode") String zipCode) {
        System.out.println("**************************************\n**************************************");
        System.out.println("----JPA - Find by ZipCode with Addresses----");
        DontTryThisAtHome response = new DontTryThisAtHome();
        List<Person> people = personRepository.findByZipCodeWithAddresses(zipCode);
        response.setDomainObjects(people);
        return response;
    }
}
