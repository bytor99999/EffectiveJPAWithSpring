package com.pwp.repository.jpa;

import com.pwp.domain.Person;

import java.util.List;

/**
 * User: Mark Spritzler
 * Date: 9/19/12
 * Time: 10:46 PM
 */
public interface PersonRepository {

    public void saveCascadeNone();

    public void saveCascadeAll();

    public void cacheUseFindNotInCache();

    public void cacheUseFindInCache();

    public void cacheUseFindThenQuery();

    public void flushModeAtCommit();

    public void flushModeAtCommitWithErrorBefore();

    public void flushModeChangesThenQuery();

    public void flushModeChangesInReadOnly();

    public void findVersusGetReference();

    public List<Person> findAllLazy(boolean getAddresses);

    public List<Person> findAllEager();

    public List<Person> findByFirstName(String firstName, boolean getAddresses);

    public List<Person> findByZipCode(String zipCode);

    public List<Person> findByZipCodeWithAddresses(String zipCode);
}
