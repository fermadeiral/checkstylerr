
package com.mobi.preference.api.ontologies;

/*-
 * #%L
 * com.mobi.preference.api
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2021 iNovex Information Systems, Inc.
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

import java.util.Optional;
import java.util.Set;
import com.mobi.rdf.api.Literal;
import com.mobi.rdf.api.Model;
import com.mobi.rdf.api.Resource;
import com.mobi.rdf.api.Value;
import com.mobi.rdf.api.ValueFactory;
import com.mobi.rdf.orm.OrmException;
import com.mobi.rdf.orm.Thing;
import com.mobi.rdf.orm.conversion.ValueConverterRegistry;
import com.mobi.rdf.orm.impl.ThingImpl;


/**
 * This implementation of the 'http://mobi.com/ontologies/preference#Preference' entity will allow developers to work in native java POJOs.
 * 
 */
public class PreferenceImpl
    extends ThingImpl
    implements Preference, Preference_Thing, Thing
{


    /**
     * Construct a new Preference with the subject IRI and the backing dataset
     * 
     * @param valueConverterRegistry
     *     The ValueConversionRegistry for this Preference
     * @param backingModel
     *     The backing dataset/model of this Preference
     * @param subjectIri
     *     The subject of this Preference
     * @param valueFactory
     *     The value factory to use for this Preference
     */
    public PreferenceImpl(final Resource subjectIri, final Model backingModel, final ValueFactory valueFactory, final ValueConverterRegistry valueConverterRegistry) {
        super(subjectIri, backingModel, valueFactory, valueConverterRegistry);
    }

    /**
     * Construct a new Preference with the subject IRI and the backing dataset
     * 
     * @param backingModel
     *     The backing dataset/model of this Preference
     * @param valueConversionRegistry
     *     The ValueConversionRegistry for this Preference
     * @param subjectIriStr
     *     The subject of this Preference
     * @param valueFactory
     *     The value factory to use for this Preference
     */
    public PreferenceImpl(final String subjectIriStr, final Model backingModel, final ValueFactory valueFactory, final ValueConverterRegistry valueConversionRegistry) {
        super(subjectIriStr, backingModel, valueFactory, valueConversionRegistry);
    }

    @Override
    public Optional<Literal> getHasDataValue()
        throws OrmException
    {
        final Optional<Value> value = getProperty(valueFactory.createIRI(Preference.hasDataValue_IRI));
        if (value.isPresent()) {
            return Optional.of(valueConverterRegistry.convertValue(value.get(), this, Literal.class));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void setHasDataValue(Literal arg)
        throws OrmException
    {
        setProperty(valueConverterRegistry.convertType(arg, this), valueFactory.createIRI(Preference.hasDataValue_IRI));
    }

    @Override
    public boolean clearHasDataValue() {
        return clearProperty(valueFactory.createIRI(Preference.hasDataValue_IRI));
    }

    @Override
    public boolean addHasObjectValue(Thing arg)
        throws OrmException
    {
        return this.addProperty(valueConverterRegistry.convertType(arg, this), valueFactory.createIRI(Preference.hasObjectValue_IRI));
    }

    @Override
    public boolean removeHasObjectValue(Thing arg)
        throws OrmException
    {
        return this.removeProperty(valueConverterRegistry.convertType(arg, this), valueFactory.createIRI(Preference.hasObjectValue_IRI));
    }

    @Override
    public Set<Thing> getHasObjectValue()
        throws OrmException
    {
        final Set<Value> value = getProperties(valueFactory.createIRI(Preference.hasObjectValue_IRI));
        return valueConverterRegistry.convertValues(value, this, Thing.class);
    }

    @Override
    public Set<Resource> getHasObjectValue_resource()
        throws OrmException
    {
        final Set<Value> value = getProperties(valueFactory.createIRI(Preference.hasObjectValue_IRI));
        return valueConverterRegistry.convertValues(value, this, Resource.class);
    }

    @Override
    public void setHasObjectValue(Set<Thing> arg)
        throws OrmException
    {
        setProperties(valueConverterRegistry.convertTypes(arg, this), valueFactory.createIRI(Preference.hasObjectValue_IRI));
    }

    @Override
    public boolean clearHasObjectValue() {
        return clearProperty(valueFactory.createIRI(Preference.hasObjectValue_IRI));
    }

    @Override
    public boolean addForUser(Thing arg)
        throws OrmException
    {
        return this.addProperty(valueConverterRegistry.convertType(arg, this), valueFactory.createIRI(Preference.forUser_IRI));
    }

    @Override
    public boolean removeForUser(Thing arg)
        throws OrmException
    {
        return this.removeProperty(valueConverterRegistry.convertType(arg, this), valueFactory.createIRI(Preference.forUser_IRI));
    }

    @Override
    public Set<Thing> getForUser()
        throws OrmException
    {
        final Set<Value> value = getProperties(valueFactory.createIRI(Preference.forUser_IRI));
        return valueConverterRegistry.convertValues(value, this, Thing.class);
    }

    @Override
    public Set<Resource> getForUser_resource()
        throws OrmException
    {
        final Set<Value> value = getProperties(valueFactory.createIRI(Preference.forUser_IRI));
        return valueConverterRegistry.convertValues(value, this, Resource.class);
    }

    @Override
    public void setForUser(Set<Thing> arg)
        throws OrmException
    {
        setProperties(valueConverterRegistry.convertTypes(arg, this), valueFactory.createIRI(Preference.forUser_IRI));
    }

    @Override
    public boolean clearForUser() {
        return clearProperty(valueFactory.createIRI(Preference.forUser_IRI));
    }

}
