package com.btesila.aose.lab7r;
import java.util.Stack;

/**
 * TODO Add class description.
 *
 * @author ramona.popa@airbus.com (original version)
 * @author ramona.popa@airbus.com (responsible)
 * @since 2016.11.07 @ 13:17
 */
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
        Block G = new Block("G");

        // initial configuration
       /* Stack<Block> stackA = new Stack<>();
        stackA.add(D);
        stackA.add(C);
        stackA.add(B);
        stackA.add(A);
        world.addToWorld(stackA);*/

        Stack<Block> stackB = new Stack<>();
        stackB.add(C);
        stackB.add(B);
        stackB.add(A);
        world.addToWorld(stackB);

        Stack<Block> stackC= new Stack<>();
        stackC.add(G);
        stackC.add(F);
        stackC.add(E);
        stackC.add(D);
        world.addToWorld(stackC);

        System.out.println("Initial configuration:");
        world.displayWorld();
        Arm arm = new Arm();
        Arm arm2 = new Arm();

        /*
        arm.unstack(A,B);
        arm.putDown(A);
        arm2.unstack(B,C);
        arm2.putDown(B);
        arm.pickUp(A);
        arm.stack(A,B);
        arm2.unstack(C,D);
        arm2.putDown(C);
        arm.pickUp(D);
        arm.stack(D,C);
        */

        arm.unstack(A, B);
        arm.putDown(A);
        arm2.unstack(D,E);
        arm2.putDown(D);
        arm.unstack(E, F);
        arm.putDown(E);
        arm2.unstack(F, G);
        arm2.putDown(F);
        arm.pickUp(G);
        arm.stack(G,A);
        arm2.pickUp(D);
        arm2.stack(D, G);
        arm.unstack(B,C);
        arm.stack(B, D);
        arm2.pickUp(C);
        arm2.stack(C, F);
        arm.pickUp(E);
        arm.stack(E, C);


        System.out.println("Final configuration:");
        world.displayWorld();

    }

}