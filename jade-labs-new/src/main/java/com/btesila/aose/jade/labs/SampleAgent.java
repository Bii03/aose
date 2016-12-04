package com.btesila.aose.jade.labs;

import jade.core.Agent;

/**
 * Created by nwertzberger on 4/23/15.
 */
public class SampleAgent extends Agent {

    @Override
    public void setup() {
        final String otherAgentName = (String) this.getArguments()[0];
        addBehaviour(new IncrementBaseNumber(this, otherAgentName));
    }

    @Override
    public void takeDown() {
    }
}
