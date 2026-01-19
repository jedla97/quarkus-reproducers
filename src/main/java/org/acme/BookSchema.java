package org.acme;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoSchema;

@ProtoSchema(includeClasses = { Book.class })
public interface BookSchema extends GeneratedSchema {
}