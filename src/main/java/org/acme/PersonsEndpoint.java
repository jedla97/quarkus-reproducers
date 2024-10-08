package org.acme;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;


@GraphQLApi
public class PersonsEndpoint {
    @Query("error")
    public String throwError() throws PhilosophyException {
        throw new PhilosophyException();
    }
}
