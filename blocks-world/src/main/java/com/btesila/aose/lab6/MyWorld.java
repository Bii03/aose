package com.btesila.aose.lab6;//*H============================================================================
//*M                                EADS RESTRICTED
//*H============================================================================
//*H
//*S  $$Id: Created on 2016.11.07 by ramona.popa@airbus.com $$
//*H
//*C  COPYRIGHT
//*C  This software is copyrighted. It is the property of EADS Deutschland
//*C  GmbH which reserves all right and title to it. It must not be reproduced,
//*C  copied, published or released to third parties nor may the content
//*C  be disclosed to third parties without the prior written consent of
//*C  EADS Deutschland GmbH. Offenders are liable to the payment of damages.
//*C  All rights reserved in the event of the granting of a patent or the
//*C  registration of a utility model or design.
//*C  (c) EADS Deutschland GmbH 2016
//*H
//*H============================================================================

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * TODO Add class description.
 *
 * @author ramona.popa@airbus.com (original version)
 * @author ramona.popa@airbus.com (responsible)
 * @since 2016.11.07 @ 11:33
 */
public class MyWorld extends Stack<Block> {

    private static List<Stack<Block>> world = new ArrayList<>();

    public List<Stack<Block>> getWorld() {
        return world;
    }

    private static final MyWorld INSTANCE = new MyWorld();

    private MyWorld() { }

    public static MyWorld getInstance() {
        return INSTANCE;
    }

    public void setWorld(List<Stack<Block>> world) {
        this.world = world;
    }

    public void addToWorld(Stack<Block> element) {
        world.add(element);
    }

    public void displayWorld(){
        for (Stack<Block> blocks : world) {
            for (Block block : blocks) {
                System.out.print(block.getName());
                System.out.print(", ");
            }
            System.out.println();
        }
    }
}