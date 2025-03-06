package com.siddhatech.products;

import com.siddhatech.exceptions.GlobalException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.List;
import java.util.Set;

@Path("/api/product/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ProductResource {


    private static final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    SecurityIdentity securityIdentity;

    private final ProductService productService;

    @Inject
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("log")
    public String logExample() {
        log.info("This is an INFO message");
        log.warn("This is a WARN message");
        log.error("This is an ERROR message");
        return "Check the logs!";
    }

    @GET
    public List<Product> getProducts(@HeaderParam("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        log.info("Received Token :: {}",token);
        String[] parts = token.split("\\.");
        if(parts.length !=3){
            log.warn("Invalid Token");
        }

        String header = new String(Base64.getUrlDecoder().decode(parts[0]));
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        String signature = parts[2];
        log.info("Header: {}" , header);
        log.info("Payload: {}" , payload);
        log.info("Signature: {}" , signature);

        return productService.getAllProducts();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"USER","ADMIN"})
    public Product getProductById(@PathParam("id") long id) {
        log.info("Check point 1 :: getting product Id {}", id);
        String username = securityIdentity.getPrincipal().getName();
        Set<String> roles = securityIdentity.getRoles();
        log.debug("User '{}' is trying to access this endpoint. Assigned roles: {}", username, roles);

        if (!roles.contains("ADMIN") && !roles.contains("USER")) {
            log.warn("User '{}' does not have required roles!", username);
        }
        return productService.getProductById(id);
    }

    @POST
    @RolesAllowed("ADMIN")
    public Product createProduct(Product product) {
        try {
            return productService.saveProduct(product);
        }catch (GlobalException e){
            throw new GlobalException(e.getErrorCode(),"Unable to add product.",e.getMessage());
        }
    }


    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Product updateProduct(@PathParam("id") long id, Product product) {
        return productService.updateProduct(id, product);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void deleteProduct(@PathParam("id") long id) {
            productService.deleteProduct(id);
    }
}

