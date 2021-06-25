package com.mobi.persistence.utils.impl;

/*-
 * #%L
 * com.mobi.ontology.utils
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 iNovex Information Systems, Inc.
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

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;
import com.mobi.rdf.api.BNode;
import com.mobi.rdf.api.Statement;
import com.mobi.persistence.utils.api.SesameTransformer;
import com.mobi.rdf.api.IRI;
import com.mobi.rdf.api.Literal;
import com.mobi.rdf.api.Model;
import com.mobi.rdf.api.ModelFactory;
import com.mobi.rdf.api.Resource;
import com.mobi.rdf.api.Value;
import com.mobi.rdf.api.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;


@Component (provide = SesameTransformer.class)
public class SimpleSesameTransformer implements SesameTransformer {

    private static final org.openrdf.model.ValueFactory SESAME_VF = ValueFactoryImpl.getInstance();
    private ValueFactory mobiVF;
    private ModelFactory mobiMF;
    private static final Logger LOG = LoggerFactory.getLogger(SimpleSesameTransformer.class);
    
    @Activate
    public void activate() {
        LOG.info("Activating the SimpleSesameTransformer");
    }
 
    @Deactivate
    public void deactivate() {
        LOG.info("Deactivating the SimpleSesameTransformer");
    }

    @Reference
    protected void setMobiVF(ValueFactory valueFactory) {
        mobiVF = valueFactory;
    }

    @Reference
    protected void setMobiMF(ModelFactory modelFactory) {
        mobiMF = modelFactory;
    }

    public SimpleSesameTransformer() {}

    @Override
    public org.openrdf.model.Model sesameModel(Model m) {
        Set<org.openrdf.model.Statement> stmts = m.stream()
                .map(this::sesameStatement)
                .collect(Collectors.toSet());

        org.openrdf.model.Model sesameModel = new LinkedHashModel();
        sesameModel.addAll(stmts);

        return sesameModel;
    }

    @Override
    public Model mobiModel(org.openrdf.model.Model m) {
        Set<Statement> stmts = m.stream()
                .map(this::mobiStatement)
                .collect(Collectors.toSet());

        return mobiMF.createModel(stmts);
    }

    @Override
    public org.openrdf.model.Statement sesameStatement(Statement statement) {
        if (statement == null) {
            return null;
        } else if (!statement.getContext().isPresent()) {
            return SESAME_VF.createStatement(sesameResource(statement.getSubject()), sesameIRI(statement.getPredicate()),
                    sesameValue(statement.getObject()));
        } else {
            return SESAME_VF.createStatement(sesameResource(statement.getSubject()), sesameIRI(statement.getPredicate()),
                    sesameValue(statement.getObject()), sesameResource(statement.getContext().get()));
        }
    }

    @Override
    public Statement mobiStatement(org.openrdf.model.Statement statement) {
        if (statement == null) {
            return null;
        } else if (statement.getContext() == null) {
            return mobiVF.createStatement(mobiResource(statement.getSubject()), mobiIRI(statement.getPredicate()),
                    mobiValue(statement.getObject()));
        } else {
            return mobiVF.createStatement(mobiResource(statement.getSubject()), mobiIRI(statement.getPredicate()),
                    mobiValue(statement.getObject()), mobiResource(statement.getContext()));
        }
    }

    @Override
    public org.openrdf.model.Resource sesameResource(Resource resource) {
        if (resource == null) {
            return null;
        } else if (resource instanceof IRI) {
            return sesameIRI((IRI) resource);
        } else {
            return SESAME_VF.createBNode(((BNode) resource).getID());
        }
    }

    @Override
    public Resource mobiResource(org.openrdf.model.Resource resource) {
        if (resource == null) {
            return null;
        } else if (resource instanceof org.openrdf.model.IRI) {
            return mobiIRI((org.openrdf.model.IRI) resource);
        } else {
            return mobiVF.createBNode(((org.openrdf.model.BNode) resource).getID());
        }
    }


    @Override
    public org.openrdf.model.IRI sesameIRI(IRI iri) {
        if (iri == null) {
            return null;
        } else {
            return SESAME_VF.createIRI(iri.stringValue());
        }
    }

    @Override
    public IRI mobiIRI(org.openrdf.model.IRI sesameIRI) {
        if (sesameIRI == null) {
            return null;
        } else {
            return mobiVF.createIRI(sesameIRI.stringValue());
        }
    }

    @Override
    public org.openrdf.model.Value sesameValue(Value value) {
        if (value == null) {
            return null;
        } else if (value instanceof IRI) {
            return sesameIRI((IRI) value);
        } else if (value instanceof BNode) {
            return sesameResource((BNode) value);
        } else {
            // Else it's a Mobi Literal
            Literal literal = (Literal) value;
            if (literal.getLanguage().isPresent()) {
                return SESAME_VF.createLiteral(literal.stringValue(), literal.getLanguage().get());
            } else {
                org.openrdf.model.IRI datatype = SESAME_VF.createIRI(literal.getDatatype().stringValue());
                return SESAME_VF.createLiteral(literal.stringValue(), datatype);
            }
        }
    }

    @Override
    public Value mobiValue(org.openrdf.model.Value value) {
        if (value == null) {
            return null;
        } else if (value instanceof org.openrdf.model.IRI) {
            return mobiIRI((org.openrdf.model.IRI) value);
        } else if (value instanceof org.openrdf.model.BNode) {
            return mobiResource((org.openrdf.model.BNode) value);
        } else {
            // Else it's a Sesame Literal
            org.openrdf.model.Literal literal = (org.openrdf.model.Literal) value;
            if (literal.getLanguage().isPresent()) {
                return mobiVF.createLiteral(literal.stringValue(), literal.getLanguage().get());
            } else {
                IRI datatype = mobiVF.createIRI(literal.getDatatype().stringValue());
                return mobiVF.createLiteral(literal.stringValue(), datatype);
            }
        }
    }
}
