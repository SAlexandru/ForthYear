package com.nickname.xml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by salexandru on 3/22/15.
 */
public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        XMLParsers parsers = new XMLParsers("/Users/salexandru/Desktop/Projects/ForthYear/SE/NicknameMe/src/com/nickname/xml/ToTest.xml");

        List<Rule> rules = parsers.getRules();
        Map<String, List<String>>  questions = parsers.getQuestions();
        Map<String, List<String>> sets = parsers.getSets();

        for (Map.Entry<String, List<String>> set: sets.entrySet()) {
            System.out.println("Set Name: " + set.getKey());
            System.out.println("Set Values " + Arrays.deepToString(set.getValue().toArray()));
            System.out.println("\n\n");
        }

        for (Map.Entry<String, List<String>> question: questions.entrySet()) {
            System.out.println("Question name: " + question.getKey());
            System.out.println("Question ans: " + Arrays.deepToString(question.getValue().toArray()));
            System.out.println("\n\n");
        }

        for (Rule rule: rules) {
            System.out.println("Cond: " + Arrays.deepToString(rule.getConditions().toArray()));
            System.out.println("Then: " + rule.getThen());
            System.out.println("\n\n");
        }
    }

}
