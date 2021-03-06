/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.view.facelets.tag.jsf.core;

import java.io.IOException;
import java.util.Map;
import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.faces.view.facelets.TagAttribute;
import jakarta.faces.view.facelets.TagConfig;
import jakarta.faces.view.facelets.TagException;
import jakarta.faces.view.facelets.TagHandler;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;

/**
 *
 * @since 2.2
 * @author Leonardo Uribe
 */
@JSFFaceletTag(name = "f:attributes", bodyContent = "empty")
public final class AttributesHandler extends TagHandler 
{
    private final TagAttribute _value;
    
    public AttributesHandler(TagConfig config)
    {
        super(config);
        _value = getRequiredAttribute("value");
    }

    @Override
    public void apply(FaceletContext ctx, UIComponent parent) throws IOException
    {
        if (parent == null)
        {
            throw new TagException(this.tag, "Parent UIComponent was null");
        }

        // only process if the parent is new to the tree
        if (parent.getParent() == null)
        {
            Map<String, Object> map = (Map<String, Object>) _value.getObject(ctx, Map.class);
            Map<String, Object> attributesMap = parent.getAttributes();
            
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                if (!attributesMap.containsKey(entry.getKey()))
                {
                    if (entry.getValue() instanceof ValueExpression)
                    {
                        parent.setValueExpression(entry.getKey(), (ValueExpression) entry.getValue());
                    }
                    else
                    {
                        attributesMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }
}
