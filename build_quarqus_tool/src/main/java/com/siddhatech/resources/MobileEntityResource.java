package com.siddhatech.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mobileEntity")
public class MobileEntityResource {

    List<MobileEntity> listOfMobiles = new ArrayList<MobileEntity>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfMobile() {
        return Response.ok(listOfMobiles).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMobile(MobileEntity mobileEntity) {
        listOfMobiles.add(mobileEntity);
        return Response.ok(mobileEntity).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMobile(@PathParam("id") int id, MobileEntity mobileEntityToUpdate) {
        listOfMobiles = listOfMobiles.stream().map(mobile -> {
            if (mobile.getId() == id) {
                return mobileEntityToUpdate;
            } else {
                return mobile;
            }
        }).collect(Collectors.toList());
        return Response.ok(listOfMobiles).build();
    }

    @DELETE
    @Path("/{id}")
    public Response mobileToDelete(@PathParam("id") int mobileId) {
        Optional<MobileEntity> mobileEntityToDelete = listOfMobiles
                .stream()
                .filter(mobileEntity -> mobileEntity.getId() == mobileId)
                .findFirst();

        System.out.println("Mobile is present or not ::"+mobileEntityToDelete.isPresent());
        if(mobileEntityToDelete.isPresent()){
            boolean mobileToDelete = listOfMobiles.remove(mobileEntityToDelete.get());
            return Response.ok(listOfMobiles).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMobileById(@PathParam("id") int mobileId) {
        Optional<MobileEntity> mobileEntityToDelete = listOfMobiles
                .stream()
                .filter(mobileEntity -> mobileEntity.getId() == mobileId)
                .findFirst();

        System.out.println("Mobile is present or not ::"+mobileEntityToDelete.isPresent());
        if(mobileEntityToDelete.isPresent()){
            MobileEntity mobileEntity = mobileEntityToDelete.get();
            return Response.ok(mobileEntity).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
