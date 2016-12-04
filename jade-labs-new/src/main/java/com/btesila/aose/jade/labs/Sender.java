package com.btesila.aose.jade.labs;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by Bii on 12/4/2016.
 */
public class Sender extends Agent {
    protected void setup()
    {
        // First set-up answering behaviour

        addBehaviour(new CyclicBehaviour(this)
        {
            public void action() {
                ACLMessage msg= receive();
                if (msg!=null)
                    System.out.println( "== Answer" + " <- "
                            +  msg.getContent() + " from "
                            +  msg.getSender().getName() );
                block();
            }
        });

        // Send messages to "a1" and "a2"

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent( "Ping" );
        msg.addReceiver( new AID( "a1", AID.ISLOCALNAME) );
        send(msg);
        System.out.println("Sending message " + msg);
    }
}
