package de.gridsolut.camel.ws.client;

/*
*  Copyright (c) 2013, GRIDSOLUT GmbH & CO.KG (http://www.gridsolut.de) All Rights Reserved.
*
*  GRIDSOLUT GmbH & CO.KG licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

import org.apache.axiom.om.OMElement;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Unger
 * Date: 19.07.13
 * Time: 12:02
 */
public class GenericAxis2Processor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(GenericAxis2Processor.class);

    private ServiceClient serviceClient;

    private String locationURL;

    private String wsdlURL;

    private boolean fireAndForget;

    public GenericAxis2Processor() {

    }

    private void init() throws Exception {
        if (this.locationURL == null) {
            log.error("Location URL is null.");
            throw new IllegalArgumentException("Location URL is null.");
        }
        EndpointReference endpoint = new EndpointReference(this.locationURL);
        Options options = new Options();
        options.setTo(endpoint);
        ServiceClient serviceClient = new ServiceClient();
        serviceClient.setOptions(options);
        this.serviceClient = serviceClient;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO: synchronization issue
        if (this.serviceClient == null) {
            this.init();
        }


        try {
            OMElement body = (OMElement) exchange.getIn().getBody();
            this.serviceClient.fireAndForget(body);
        } catch (Exception e) {
            log.error("Cannot invoke service.", e);
            throw new Exception(e);
        }

    }

    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    public void setServiceClient(ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    public String getLocationURL() {
        return locationURL;
    }

    public void setLocationURL(String locationURL) {
        this.locationURL = locationURL;
    }

    public String getWsdlURL() {
        return wsdlURL;
    }

    public void setWsdlURL(String wsdlURL) {
        this.wsdlURL = wsdlURL;
    }

    public boolean isFireAndForget() {
        return fireAndForget;
    }

    public void setFireAndForget(boolean fireAndForget) {
        this.fireAndForget = fireAndForget;
    }
}
