package com.siddhatech.person;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.siddhatech.exceptions.GlobalException;
import com.util.crypto.extension.runtime.AESUtil;
import com.util.crypto.extension.runtime.RSAUtil;
import com.util.crypto.extension.runtime.exception.CryptoExceptionMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


@Path("/api/person/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PersonResource {


    private static final Logger log
            = LoggerFactory.getLogger(PersonResource.class);
    @Inject
    PersonService personService;



    @Inject
    public RSAUtil rsaUtil;

    @Inject
    @Named("aesUtil1")
    AESUtil aesUtil1;

    @Inject
    @Named("aesUtil2")
    AESUtil aesUtil2;

    @Inject
    @Named("aesUtil3")
    AESUtil aesUtil3;



    @GET
    @Path("/encrypt/{text}")
    public Response encrypt(@PathParam("text") String text) {
        try {
            log.info("::::test Data:::{}", text);
            String encryptedText1 = aesUtil1.encrypt(text);
            String encryptedText2 = aesUtil2.encrypt(text);
            String encryptedText3 = aesUtil3.encrypt(text);

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("encryptedText1", encryptedText1);
            responseMap.put("encryptedText2", encryptedText2);
            responseMap.put("encryptedText3", encryptedText3);

            return Response.ok(responseMap).build();
        } catch (CryptoExceptionMessage e) {
            log.error("Crypto error in AES encryption: {}", e.getMessage());
            throw new GlobalException(500, e.getMessage(),"Something went wrong :: Crypto error in AES encryption");
        } catch (Exception e) {
            log.error("Unexpected error in AES encryption: {}", e.getMessage());
            throw new GlobalException(500, "Unable to encrypt the data.", e.getMessage());
        }
    }

    @POST
    @Path("/decrypt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response decrypt(Map<String, String> encryptedTexts) {
        try {
            log.info("Received encrypted data for decryption: " + encryptedTexts);

            String decryptedText1 = aesUtil1.decrypt(encryptedTexts.get("encryptedText1"));
            String decryptedText2 = aesUtil2.decrypt(encryptedTexts.get("encryptedText2"));
            String decryptedText3 = aesUtil3.decrypt(encryptedTexts.get("encryptedText3"));

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("decryptedText1", decryptedText1);
            responseMap.put("decryptedText2", decryptedText2);
            responseMap.put("decryptedText3", decryptedText3);

            return Response.ok(responseMap).build();
        } catch (CryptoExceptionMessage e) {
            log.error("Crypto error in AES decryption: {}", e);
            throw new GlobalException(500, e.getMessage(), "Something went wrong :: Crypto error in AES decryption");
        } catch (Exception e) {
            log.error("Unexpected error in AES decryption: {}", e);
            throw new GlobalException(500, "Unable to decrypt the data.", e.getMessage());
        }
    }


    @POST
    @Path("/encryptRSA")
    @Produces(MediaType.TEXT_PLAIN)
    public Response encryptData(String data) {
        try {

            log.info("Encrypted key check point {}", data);

            String encrypted = rsaUtil.encrypt(data);
            return Response.ok(encrypted).build();
        } catch (CryptoExceptionMessage e) {
            log.error("Crypto error in RSA encryption: {}", e.getMessage());
            throw new GlobalException(500, e.getMessage(),"Something went wrong :: Crypto error in RSA encryption");
        } catch (Exception e) {
            log.error("Unexpected error in  RSA encryption: {}", e.getMessage());
            throw new GlobalException(500, "Unable to encrypt the data.", e.getMessage());
        }
    }

    @POST
    @Path("/decryptRSA")
    public Response decryptData(String encryptedData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(encryptedData);
            String encrypted = jsonNode.get("data").asText();

            log.info("Received Encrypted Data: {}", encrypted);

            String decrypted = rsaUtil.decrypt(encrypted);
            return Response.ok(decrypted).build();
        } catch (CryptoExceptionMessage e) {
            log.error("Crypto error in RSA decryption: {}", e.getMessage());
            throw new GlobalException(500, e.getMessage(),"Something went wrong :: Crypto error in RSA decryption");
        } catch (Exception e) {
            log.error("Unexpected error in  RSA decryption: {}", e.getMessage());
            throw new GlobalException(500, "Unable to decrypt the data.", e.getMessage());
        }
    }


/*

    @POST
    @Path("/save")
    public Response createPerson(Person person) {
        Person savePerson = personService.savePerson(person.name, person.address, person.productName);
        return Response.ok(savePerson).status(Response.Status.CREATED).build();
    }


    @GET
    @Path("/{name}")
    public Response getPersonByName(@PathParam("name") String name){
        log.info("Name of person {} ", name);
        Person personByName = personService.getPersonByName(name);
        return Response.ok(personByName).build();
    }

    @GET
    @Operation(
            summary = "Get person by ID",
            description = "Fetch a person from the database using their ID"
    )
    @Path("/byId/{id}")
    public Response getPersonById(@PathParam("id") long id){

        Person personById = personService.getPersonById(id);
        log.info("check point ");
        return Response.ok(personById).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deletePersonById(@PathParam("id") long id){
        log.info("ID of person to be deleted {} ", id);
        personService.deletePerson(id);
        return Response.ok("Person entity deleted for id::" + id).build();
    }


    private static final SecretKey SECRET_KEY;

    static {
        try{
            SECRET_KEY = AESUtil.generateKey();
        }catch (Exception e){
            throw new RuntimeException("Error generating key",e);
        }
    }

    private PublicKey getPublicKey() throws Exception{
        log.info("getting public key :: {} ", PUBLIC_KEY);
        byte[] keyBytes = Base64.getDecoder().decode(PUBLIC_KEY);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private PrivateKey getPrivateKey() throws Exception {
        log.info("getting private key :: {}", PRIVATE_KEY);
        byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

 */

}
