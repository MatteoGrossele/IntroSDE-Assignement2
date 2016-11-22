package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Person;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }


    // Application integration
    /******** REQUEST -2- *********/
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.TEXT_XML })
    public Response getPerson() {
        Person person = this.getPersonById(id);
        if (person == null)
            return Response.status(404).build();
       return Response.ok().entity(person).build();
    }



    /******** REQUEST -3- *********/
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        Person existing = getPersonById(this.id);
        Response res;
        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.ok().entity(person).build();
            person.setIdPerson(this.id);
            Person.updatePerson(person);
        }
        return res;
    }


    /******** REQUEST -5- *********/
    @DELETE
    public void deletePerson() {
        Person c = getPersonById(id);
        Person.removePerson(c);
    }

    public Person getPersonById(int personId) {
        Person person = Person.getPersonById(personId);
        return person;
    }

    @Path("{measureType}")
    public MeasureTypesCollectionResource getMeasureType(@PathParam("measureType") String measuretype) {
        return new MeasureTypesCollectionResource(uriInfo, request, id, measuretype);
    }
}