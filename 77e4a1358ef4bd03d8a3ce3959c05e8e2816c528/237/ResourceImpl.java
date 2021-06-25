/*
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zeebe.model.bpmn.impl.instance;

import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN20_NS;
import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_NAME;
import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_RESOURCE;

import io.zeebe.model.bpmn.instance.Resource;
import io.zeebe.model.bpmn.instance.ResourceParameter;
import io.zeebe.model.bpmn.instance.RootElement;
import java.util.Collection;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.ChildElementCollection;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

/**
 * The BPMN resource element
 *
 * @author Sebastian Menski
 */
public class ResourceImpl extends RootElementImpl implements Resource {

  protected static Attribute<String> nameAttribute;
  protected static ChildElementCollection<ResourceParameter> resourceParameterCollection;

  public ResourceImpl(final ModelTypeInstanceContext context) {
    super(context);
  }

  public static void registerType(final ModelBuilder modelBuilder) {
    final ModelElementTypeBuilder typeBuilder =
        modelBuilder
            .defineType(Resource.class, BPMN_ELEMENT_RESOURCE)
            .namespaceUri(BPMN20_NS)
            .extendsType(RootElement.class)
            .instanceProvider(
                new ModelTypeInstanceProvider<Resource>() {
                  @Override
                  public Resource newInstance(final ModelTypeInstanceContext instanceContext) {
                    return new ResourceImpl(instanceContext);
                  }
                });

    nameAttribute = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_NAME).required().build();

    final SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    resourceParameterCollection =
        sequenceBuilder.elementCollection(ResourceParameter.class).build();

    typeBuilder.build();
  }

  @Override
  public String getName() {
    return nameAttribute.getValue(this);
  }

  @Override
  public void setName(final String name) {
    nameAttribute.setValue(this, name);
  }

  @Override
  public Collection<ResourceParameter> getResourceParameters() {
    return resourceParameterCollection.get(this);
  }
}
