/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.metrics.meteo.publishers;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class ResmonListener extends EsperListener implements UpdateListener
{
    private final ResmonPublisherConfig config;
    private final ResmonPublisher publisher;

    public ResmonListener(ResmonPublisherConfig config) throws Exception
    {
        this.config = config;
        this.publisher = new ResmonPublisher();
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents != null) {
            for (EventBean newEvent : newEvents) {
                for (String attribute : newEvent.getEventType().getPropertyNames()) {
                    Object value = newEvent.get(attribute);

                    if (value != null) {
                        publisher.send(config.getPrefix() + "." + attribute, value);
                    }
                }
            }
        }
    }
}

