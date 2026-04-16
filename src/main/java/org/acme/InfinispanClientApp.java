package org.acme;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;
import org.infinispan.commons.configuration.StringConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class InfinispanClientApp {

    private static final Logger LOGGER = LoggerFactory.getLogger("InfinispanClientApp");

    @Inject
    RemoteCacheManager cacheManager;

    private static final String MYSHOP_CACHE_CONFIG = """
            <infinispan><cache-container>\
            <distributed-cache name="%s">\
            <encoding>
            <key media-type="application/x-protostream"/>
            <value media-type="application/x-protostream"/>
            </encoding>\
            <memory max-count="2" when-full="REMOVE"/>\
            </distributed-cache>\
            </cache-container></infinispan>""";

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Create or get cache named myshop with the x-protostream configuration");
        RemoteCache<Object, Object> myshop = cacheManager.administration().getOrCreateCache("myshop",
                new StringConfiguration(String.format(MYSHOP_CACHE_CONFIG, "myshop")));
    }
}
