package io.quarkus.ts.spring.data.rest;

import io.quarkus.security.PermissionsAllowed;
import io.quarkus.ts.spring.data.primitivetypes.configuration.SessionIdBean;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Objects;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("magazine-resource")
public class MagazineResource {

    @Inject
    MagazineJpaRepository magazineJpaRepository;

    @Inject
    SessionIdBean sessionIdBean;

    @Path("{id}")
    @GET
    public Magazine getMagazine(@PathParam("id") long id) {
        return magazineJpaRepository.getReferenceById(id);
    }

    @GET
    public String test() {
        return sessionIdBean.getSessionId();
    }

}
