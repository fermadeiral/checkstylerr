package com.mobi.ontology.core.impl.owlapi.classexpression;

/*-
 * #%L
 * com.mobi.ontology.core.impl.owlapi
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

import com.mobi.ontology.core.api.propertyexpression.DataPropertyExpression;
import com.mobi.ontology.core.api.classexpression.DataMinCardinality;
import com.mobi.ontology.core.api.datarange.DataRange;
import com.mobi.ontology.core.api.types.ClassExpressionType;

import javax.annotation.Nonnull;


public class SimpleDataMinCardinality 
	extends SimpleDataCardinalityRestriction 
	implements DataMinCardinality {

	public SimpleDataMinCardinality(@Nonnull DataPropertyExpression property, int cardinality, @Nonnull DataRange dataRange)
	{
		super(property, cardinality, dataRange);
	}
	
	
	@Override
	public ClassExpressionType getClassExpressionType()
	{
		return ClassExpressionType.DATA_MIN_CARDINALITY;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) 
			return true;
		
		if (!super.equals(obj)) 
			return false;
		
		return obj instanceof DataMinCardinality;
	}

}
