package com.siddhatech.person;

public interface PersonService {

    Person savePerson(String name,String address, String productName);
    Person getPersonByName(String name);
    void deletePerson(long id);
    Person getPersonById(long id);
}
