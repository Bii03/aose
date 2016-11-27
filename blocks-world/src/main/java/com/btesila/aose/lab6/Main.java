package com.btesila.aose.lab6;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args){

        // world
        MyWorld world = MyWorld.getInstance();
        Block A = new Block("A");
        Block B = new Block("B");
        Block C = new Block("C");
        Block D = new Block("D");
        Block E = new Block("E");
        Block F = new Block("F");

        // initial configuration
        Stack<Block> stackAB = new Stack<>();
        stackAB.add(A);
        stackAB.add(B);
        world.addToWorld(stackAB);

        Stack<Block> stackC = new Stack<>();
        stackC.add(C);
        world.addToWorld(stackC);

        Stack<Block> stackD = new Stack<>();
        stackD.add(D);
        world.addToWorld(stackD);

        Stack<Block> stackEF = new Stack<>();
        stackEF.add(E);
        stackEF.add(F);
        world.addToWorld(stackEF);

        System.out.println("Initial configuration:");
        world.displayWorld();

        Arm arm = new Arm();
        Arm arm2 = new Arm();
        arm.unstack(B,A);
        arm.stack(B,D);
        arm.pickUp(C);
        arm.stack(C,A);
        arm.unstack(F,E);
        arm.stack(F,C);
        arm.pickUp(E);
        arm.stack(E,B);

        System.out.println("Final configuration:");
        world.displayWorld();

    }

}