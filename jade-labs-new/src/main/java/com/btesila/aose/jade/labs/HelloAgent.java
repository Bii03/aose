package com.btesila.aose.jade.labs;

import jade.core.Agent;

/**
 * Created by Bii on 12/4/2016.
 */
public class HelloAgent extends Agent {
    protected void setup() {
        System.out.println("Hello World. ");
        System.out.println("My name is "+ getLocalName());
    }
}
