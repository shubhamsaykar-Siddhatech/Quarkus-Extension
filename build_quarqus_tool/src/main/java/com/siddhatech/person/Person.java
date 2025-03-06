package com.siddhatech.person;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.getByName", query = "from Person where name = ?1"),
        @NamedQuery(name = "Person.deleteById", query = "delete from Person p where p.id = ?1")
})

public class Person extends PanacheEntity {


    private static final Logger log = LoggerFactory.getLogger(Person.class);
    public String name;
    public String address;
    public String productName;

    public static Person findByName(String name) {
        return find("#Person.getByName", name).firstResult();
    }

    public static void deleteById(long id) {
        log.info("check point in POJO {} ", id);
        delete("#Person.deleteById", id);
    }

}
