package com.nickname.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by salexandru on 3/22/15.
 */
public class XMLParsers {
    private Map<String, List<String>> questions;
    private Map<String, List<String>> sets;

    public XMLParsers(String path) throws IOException, SAXException, ParserConfigurationException {
        Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(path));
        NodeList roots = dom.getDocumentElement().getElementsByTagName("ExpertSystem");


        if (null == roots || 0 == roots.getLength()) {
            return;
        }

        questions = new HashMap<>();
        sets = new HashMap<>();


        for (int i = 0; i < roots.getLength(); ++i) {
            Element root = (Element)roots.item(i);

            parseSets((Element)root.getElementsByTagName("Sets").item(0));
            parseQuestions((Element)root.getElementsByTagName("Questions").item(0));
            parseRules((Element)root.getElementsByTagName("Rules").item(0));
        }



    }

    private void parseRules (Element rules) {

    }

    private void parseQuestions (Element root) {
        NodeList children = root.getChildNodes();

        if (null == children || 0 == children.getLength()) {
            return;
        }

        for (int i = 0; i < children.getLength(); ++i) {
            Element question = (Element)children.item(i);
            String text = question.getElementsByTagName("Text").item(0).getNodeValue();

            if (!questions.containsKey(text)) {
                questions.put(text, new ArrayList<>());
            }
            NodeList answers = question.getElementsByTagName("Answer");

            if (null == answers || 0 == answers.getLength()) {
                continue;
            }

            List<String> ans = questions.get(text);

            for (int j = 0; j < answers.getLength(); ++j) {
                ans.add(answers.item(i).getNodeValue());
            }
        }
    }

    private void parseSets (Element root) {
        NodeList children = root.getChildNodes();

        if (null == children || 0 == children.getLength()) {
            return;
        }

        for (int i = 0; i < children.getLength(); ++i) {
            Element set = (Element)children.item(i);
            String name = set.getElementsByTagName("Name").item(0).getNodeValue();
            String values = set.getElementsByTagName("Value").item(0).getNodeValue();

            if (!sets.containsKey(set)) {
                sets.put(name, new ArrayList<>());
            }
            sets.get(set).addAll(Arrays.asList(values.split(";")));
        }
    }

    public Map<String, List<String>> getQuestions() {return questions;}
    public Map<String, List<String>> getSets()      {return sets;}
}
