package com.pwp;

import com.pwp.domain.Person;

import java.util.List;

/**
 * User: Mark Spritzler
 * Date: 10/9/12
 * Time: 9:46 PM
 */
public class DontTryThisAtHome {

    private String type = "Search Results";

    private List<Person> domainObjects;

    public void setDomainObjects(List<Person> people) {
        this.domainObjects = people;
    }

    public List<Person> getDomainObjects() {
        return domainObjects;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
