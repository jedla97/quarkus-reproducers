package org.acme.crud;

import io.quarkus.arc.Arc;
import io.vertx.ext.web.RoutingContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import io.quarkus.security.PermissionsAllowed;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.Collection;
import java.util.Collections;

@Path("/crud")
public class CRUDResource {

    @PermissionsAllowed("create")
    @PermissionsAllowed("update")
    @POST
    @Path("/modify/repeated")
    public String createOrUpdate() {
        return "modified";
    }

    @PermissionsAllowed(value = {"create", "update"}, inclusive=true)
    @POST
    @Path("/modify/inclusive")
    public String createOrUpdate(Long id) {
        return id + " modified";
    }

    @PermissionsAllowed({"see:detail", "see:all", "read"})
    @GET
    @Path("/id/{id}")
    public String getItem(String id) {
        return "item-detail-" + id;
    }

    @PermissionsAllowed(value = "list", permission = CustomPermission.class)
    @Path("/list")
    @GET
    public Collection<String> list(@QueryParam("query-options") String queryOptions) {
        // your business logic comes here
        return Collections.emptySet();
    }

    public static class CustomPermission extends BasicPermission {

        public CustomPermission(String name) {
            super(name);
        }

        @Override
        public boolean implies(Permission permission) {
            var event = Arc.container().instance(RoutingContext.class).get();
            var publicContent = "public-content".equals(event.request().params().get("query-options"));
            var hasPermission = getName().equals(permission.getName());
            return hasPermission && publicContent;
        }
    }
}