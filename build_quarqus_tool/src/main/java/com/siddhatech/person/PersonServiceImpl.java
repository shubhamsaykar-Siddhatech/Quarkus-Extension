package com.siddhatech.person;

import com.siddhatech.exceptions.GlobalException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ApplicationScoped
public class PersonServiceImpl implements  PersonService{


    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Override
    @Transactional
    public Person savePerson(String name, String address, String productName ) {
        try{
            if(name==null || address==null || productName==null){
                throw  new IllegalArgumentException("Values must not be null");
            }
            Person person = new Person();
            person.name = name;
            person.address = address;
            person.productName = productName;
            person.persist();
            return person;

        } catch (IllegalArgumentException e) {
            throw new GlobalException(400, "Invalid Input: " + e.getMessage(), e.getMessage());
        } catch (Exception e) {
            throw new GlobalException(500, "Unable to save person", e.getMessage());
        }
    }

    @Override
    public Person getPersonByName(String name) {
        try {
            return Person.findByName(name);
        } catch (GlobalException e) {
            throw new GlobalException(e.getErrorCode(),"Unable to get person by name",e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deletePerson(long id) {
            log.info("check point of person {} ", id);
            Optional<PanacheEntityBase> byIdOptional = Person.findByIdOptional(id);
            if(byIdOptional.isPresent()){
                Person.deleteById(id);
            }else {
                throw new GlobalException(404,"Person not found","Person with id "+id+" not found");
            }
    }

    @Override
    @Transactional
    public Person getPersonById(long id) {
        Person byId = Person.findById(id);
        return byId;
    }
}
