package com.btesila.aose.lab7r;

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

    private static final List<Stack<Block>> world = new ArrayList<>();

    public List<Stack<Block>> getWorld() {
        return world;
    }

    private static final MyWorld INSTANCE = new MyWorld();

    private MyWorld() { }

    public static MyWorld getInstance() {
        return INSTANCE;
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