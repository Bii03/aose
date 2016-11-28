package com.btesila.aose.lab7r;

import java.util.List;
import java.util.Stack;

/**
 * TODO Add class description.
 *
 * @author ramona.popa@airbus.com (original version)
 * @author ramona.popa@airbus.com (responsible)
 * @since 2016.11.07 @ 10:57
 */
public class Arm extends Predicate {

    Block armBlock; // arm free or not
    MyWorld world = MyWorld.getInstance();
    List<Stack<Block>> table = world.getWorld();
    Stack<Block> currentStack = null;

    public Block getArmBlock() {
        return armBlock;
    }

    public void setArmBlock(Block armBlock) {
        this.armBlock = armBlock;
    }

    public void stack(Block A, Block B) { // plaseaza A peste B
        for (Stack<Block> blocks : table) {
            if (blocks.contains(B)) {
                currentStack = blocks;
            }
        }
        if (this.hold(A) && clear(B)) {
            currentStack.push(A);
            setArmBlock(null);
        }
    }

    public void unstack(Block A, Block B) { // Apuca A de deasupra lui B
        for (Stack<Block> blocks : table) {
            if (blocks.contains(A) && blocks.contains(B)) {
                currentStack = blocks;
            }
        }
        if (this.armEmpty() && clear(A) && on(A, B)) {
            currentStack.pop(); // A
            setArmBlock(A);
        }
    }

    public void pickUp(Block A) { //apuca A asezat pe masa
        for (Stack<Block> blocks : table) {
            if (blocks.contains(A)) {
                currentStack = blocks;
            }
        }
        if (this.armEmpty() && clear(A) && onTable(A)) {
            currentStack.pop(); // A
            table.remove(currentStack);
            setArmBlock(A);
        }

    }

    public void putDown(Block A) { //aseza A pe masa
        if (this.hold(A)) {
            Stack<Block> newSt = new Stack<Block>();
            newSt.push(A);
            table.add(newSt);
            setArmBlock(null);
        }
    }


////////////// predicates impl

    public boolean on(Block A, Block B) {  // A peste B
        for (int i = 0; i < currentStack.size() - 1; i++)
            if (currentStack.get(i).equals(B) && currentStack.get(i + 1).equals(A)) return true;
        return false;
    }


    public boolean onTable(Block A) { // A pe masa
        if (currentStack.get(0).equals(A)) return true;
        return false;
    }

    public boolean clear(Block A) { // nimic peste A ( A in varf)
        if (currentStack.get(currentStack.size() - 1).equals(A)) return true;
        return false;
    }

    public Boolean hold(Block A) { // A in brat
        if (this.getArmBlock() != null && this.getArmBlock().equals(A)) return true;
        return false;
    }

    public Boolean armEmpty() { // brat liber
        if (this.getArmBlock() == null) return true;
        return false;
    }

}