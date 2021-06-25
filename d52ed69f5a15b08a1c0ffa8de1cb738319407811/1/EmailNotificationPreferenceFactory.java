
package com.mobi.notification.impl.ontologies;

/*-
 * #%L
 * com.mobi.notification.impl
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2020 iNovex Information Systems, Inc.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import com.mobi.preference.api.ontologies.Preference;
import com.mobi.preference.api.ontologies.Preference_Thing;
import com.mobi.rdf.api.IRI;
import com.mobi.rdf.api.Model;
import com.mobi.rdf.api.ModelFactory;
import com.mobi.rdf.api.Resource;
import com.mobi.rdf.api.ValueFactory;
import com.mobi.rdf.orm.AbstractOrmFactory;
import com.mobi.rdf.orm.Thing;
import com.mobi.rdf.orm.conversion.ValueConverterRegistry;


/**
 * This {@link OrmFactory} implementation will construct EmailNotificationPreference objects.  It will be published as an OSGi service.  See http://mobi.com/ontologies/notification#EmailNotificationPreference for more information.
 * 
 */
@Component(provide = {
    com.mobi.rdf.orm.OrmFactory.class,
    com.mobi.rdf.orm.conversion.ValueConverter.class,
    EmailNotificationPreferenceFactory.class
})
public class EmailNotificationPreferenceFactory
    extends AbstractOrmFactory<EmailNotificationPreference>
{


    public EmailNotificationPreferenceFactory() {
        super(EmailNotificationPreference.class, EmailNotificationPreferenceImpl.class);
    }

    @Override
    public Optional<EmailNotificationPreference> getExisting(Resource resource, Model model, ValueFactory valueFactory, ValueConverterRegistry valueConverterRegistry) {
        return (model.filter(resource, valueFactory.createIRI(RDF_TYPE_IRI), this.getTypeIRI()).isEmpty()?Optional.empty():Optional.of(new EmailNotificationPreferenceImpl(resource, model, valueFactory, valueConverterRegistry)));
    }

    @Override
    public IRI getTypeIRI() {
        return valueFactory.createIRI(EmailNotificationPreference.TYPE);
    }

    @Override
    public Set<IRI> getParentTypeIRIs() {
        final Set<IRI> set = new HashSet<IRI>();
        set.add(valueFactory.createIRI(Notification_Thing.TYPE));
        set.add(valueFactory.createIRI(Preference.TYPE));
        set.add(valueFactory.createIRI(Preference_Thing.TYPE));
        set.add(valueFactory.createIRI(Thing.TYPE));
        return set;
    }

    @Override
    @Reference
    public void setModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    @Override
    @Reference
    public void setValueFactory(ValueFactory valueFactory) {
        this.valueFactory = valueFactory;
    }

    @Override
    @Reference
    public void setValueConverterRegistry(ValueConverterRegistry valueConverterRegistry) {
        this.valueConverterRegistry = valueConverterRegistry;
    }

}
