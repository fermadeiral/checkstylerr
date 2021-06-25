/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.clustering.web.infinispan;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.wildfly.clustering.infinispan.spi.distribution.Key;
import org.wildfly.clustering.infinispan.spi.persistence.SimpleKeyFormat;
import org.wildfly.clustering.marshalling.Externalizer;
import org.wildfly.clustering.web.IdentifierExternalizerProvider;

/**
 * Base externalizer for cache keys containing session identifiers.
 * @author Paul Ferraro
 */
public abstract class SessionKeyExternalizer<K extends Key<String>> extends SimpleKeyFormat<K> implements Externalizer<K> {

    static final Externalizer<String> EXTERNALIZER = StreamSupport.stream(ServiceLoader.load(IdentifierExternalizerProvider.class, IdentifierExternalizerProvider.class.getClassLoader()).spliterator(), false).findFirst().get().getExternalizer();

    private final Function<String, K> resolver;

    protected SessionKeyExternalizer(Class<K> targetClass, Function<String, K> resolver) {
        super(targetClass, resolver, Key::getValue);
        this.resolver = resolver;
    }

    @Override
    public void writeObject(ObjectOutput output, K key) throws IOException {
        EXTERNALIZER.writeObject(output, key.getValue());
    }

    @Override
    public K readObject(ObjectInput input) throws IOException, ClassNotFoundException {
        return this.resolver.apply(EXTERNALIZER.readObject(input));
    }
}
