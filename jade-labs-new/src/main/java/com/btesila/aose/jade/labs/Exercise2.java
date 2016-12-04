package com.btesila.aose.jade.labs;

import jade.core.Agent;

/**
 * Created by Bii on 12/4/2016.
 */
public class Exercise2 extends Agent {
    protected void setup()
    {
        Object[] args = getArguments();
        String s;
        if (args != null) {
            for (int i = 0; i<args.length; i++) {
                s = (String) args[i];
                System.out.println("p" + i + ": " + s);
            }

            // Extracting the integer.
            int i = Integer.parseInt( (String) args[0] );
            System.out.println("i*i= " + i*i);
        }
    }
}
