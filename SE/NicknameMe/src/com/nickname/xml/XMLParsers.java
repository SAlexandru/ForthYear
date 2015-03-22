package com.nickname.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by salexandru on 3/22/15.
 */
public class XMLParsers {

    public XMLParsers(String path) throws IOException, SAXException, ParserConfigurationException {
        Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(path));
        Element root = dom.getDocumentElement();


    }
}
