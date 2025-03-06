package org.siddhatech.utility.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.siddhatech.utility.utilClasses.AESUtil;
import org.siddhatech.utility.utilClasses.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("api/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CryptoResource {

    private static final Logger log
            = LoggerFactory.getLogger(CryptoResource.class);

    @Inject
    private AESUtil aesUtil;

    @Inject
    private RSAUtil rsaUtil;



    //    api call for testing
    @GET
    @Path("/aes/{text}")
    public Response encrypt() {
        try {
            String text ="Starting encryption method";
            log.info("Starting encryption method:::");
            String aesSecretKey = "OyKnvkewp/sqcqieQuaDACF9Jksr5Oh5FXi6XIpovqk=";
            String encryptedText = aesUtil.encrypt(text,aesSecretKey);
            String decryptedText = aesUtil.decrypt(encryptedText,aesSecretKey);

            return Response.ok("RSA Encrypted: " + encryptedText + "\nRSA Decrypted: " + decryptedText).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Encryption failed").build();
        }
    }


}
