//package com.siddhatech.person;
//
//
//import io.quarkus.test.InjectMock;
//import io.quarkus.test.junit.QuarkusTest;
//
//import jakarta.inject.Inject;
//import jakarta.ws.rs.core.Response;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.mockito.Mockito.*;
//
//
//@QuarkusTest
//class PersonResourceTest {
//
//
//    @InjectMock
//    PersonService personService;
//
//    @Inject
//    PersonResource personResource;
//
//    @Test
//    void testGetPersonById_Success() {
//        // Arrange
//        long personId = 1L;
//        Person mockPerson = new Person();
//        mockPerson.id = personId;
//        mockPerson.name = "shubham Saykar";
//        mockPerson.address = "Sr .no. 165, Hadapsar";
//        mockPerson.productName = "Innova";
//
//
//        PersonResource resource = new PersonResource();
//        resource.personService = personService;
//        Response response = resource.getPersonById(personId);
//
//        assertEquals(200, response.getStatus());
//        assertNotNull(response.getEntity());
//
//        Person person = (Person) response.getEntity();
//        assertEquals(mockPerson.id, person.id);
//        assertEquals(mockPerson.name, person.name);
//        assertEquals(mockPerson.address, person.address);
//        assertEquals(mockPerson.productName, person.productName);
//        verify(personService, times(1)).getPersonById(personId);
//    }
//
//    @Test
//    void testGetPersonByName() {
//        String personName = "shubham";
//        Person expectedPerson = new Person();
//        expectedPerson.name = personName;
//        expectedPerson.address = "Hadapsar";
//        expectedPerson.productName = "Classmate";
//
//        when(personService.getPersonByName(personName)).thenReturn(expectedPerson);
//
//        Response personByName = personResource.getPersonByName(personName);
//
//        assertEquals(Response.Status.OK.getStatusCode(), personByName.getStatus());
//        assertEquals(expectedPerson, personByName.getEntity());
//
//        verify(personService, times(1)).getPersonByName(personName);
//    }
//
//    @Test
//    void testDeletePersonBYId() {
//        long personId = 1L;
//        Response response = personResource.deletePersonById(personId);
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        assertEquals("Person entity deleted for id::" + personId, response.getEntity());
//        verify(personService, times(1)).deletePerson(personId);
//    }
//}
