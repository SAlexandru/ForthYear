package com.nickname.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salexandru on 3/22/15.
 */
public class Rule {

    private String then;
    private List<String> conditions;

    public Rule() {
        conditions = new ArrayList<>();
    }

    public void setThen (String then) {
        this.then = then;
    }

    public void addCond (String cond) {
        conditions.add(cond);
    }

    public String getThen() {return then;}
    public List<String> getConditions() {return conditions;}
}
