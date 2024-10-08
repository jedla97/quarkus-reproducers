package org.acme;

import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("42")
public class PhilosophyException extends RuntimeException {
}
