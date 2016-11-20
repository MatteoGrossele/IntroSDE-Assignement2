package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.*;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class MeasureTypesCollectionResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    String measureType;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public MeasureTypesCollectionResource(UriInfo uriInfo, Request request, int id, String measureType,EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
        this.measureType = measureType;
    }

    public MeasureTypesCollectionResource(UriInfo uriInfo, Request request,int id, String measureType) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.measureType = measureType;
    }

    /******** REQUEST -6- *********/
    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Measure> getMeasures() {
        List<Measure> history = Measure.getMeasureHistorybyPersonIdType(id,measureType);
        if (history == null)
            throw new RuntimeException("Get: Measure History with Person " + id + " and Type " + measureType +" not found");
        return history;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public List<Measure> getMeasuresHTML() {
         List<Measure> history = Measure.getMeasureHistorybyPersonIdType(id,measureType);
        if (history == null)
            throw new RuntimeException("Get: Measure History with Person " + id + " and Type " + measureType +" not found");
        return history;
    }

    /******** REQUEST -7- *********/
    @Path("{idMeasure}")
    @GET
    public  List<Measure>  getPerson(@PathParam("idMeasure") int mid) {
        List<Measure> history = Measure.getMeasureHistorybyPersonIdTypeMid(id ,measureType, mid);
         if (history == null)
            throw new RuntimeException("Get: Measure History with Person " + id + ", Type " + measureType +" and Mid " + mid +" not found");
        return history;
    }

    /******** REQUEST -8- *********/
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        System.out.println("--> Updating Person... " +this.id);
        System.out.println("--> "+person.toString());
        Person.updatePerson(person);
        Response res;
        Person existing = Person.getPersonById(this.id);

        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            person.setIdPerson(this.id);
            Person.updatePerson(person);
        }
        return res;
    }

   


}