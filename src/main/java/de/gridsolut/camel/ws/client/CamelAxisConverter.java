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
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.util.XMLUtils;
import org.apache.camel.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.stream.XMLStreamException;

/**
 * @author Tobias Unger
 * Date: 19.07.13
 * Time: 12:02
 */
@Converter
public class CamelAxisConverter {

    private static final Logger log = LoggerFactory.getLogger(CamelAxisConverter.class);

    @Converter
    public static OMElement stringToOMElement(String xmldata) throws Exception {
        try {
            return AXIOMUtil.stringToOM(xmldata);
        } catch (XMLStreamException e) {
            log.error("Cannot convert String to OMElement.", e);
            throw new Exception(e);
        }
    }

    @Converter
    public static OMElement domToOMElement(Document xmldata) throws Exception {
        try {
            return XMLUtils.toOM(xmldata.getDocumentElement());
        } catch (XMLStreamException e) {
            log.error("Cannot convert Document to OMElement.", e);
            throw new Exception(e);
        }
    }

    @Converter
    public static OMElement elementToOMElement(Element xmldata) throws Exception {
        try {
            return XMLUtils.toOM(xmldata);
        } catch (XMLStreamException e) {
            log.error("Cannot convert Element to OMElement.", e);
            throw new Exception(e);
        }
    }

    @Converter
    public static Element omElementToElement(OMElement xmldata) throws Exception {
        try {
            return XMLUtils.toDOM(xmldata);
        } catch (XMLStreamException e) {
            log.error("Cannot convert OMElement to Element.", e);
            throw new Exception(e);
        }
    }

}
