package com.btesila.aose.lab7r;

/**
 * TODO Add class description.
 *
 * @author ramona.popa@airbus.com (original version)
 * @author ramona.popa@airbus.com (responsible)
 * @since 2016.11.07 @ 11:24
 */
public abstract class Predicate {

    public abstract boolean on(Block A, Block B) ;


    public abstract boolean onTable(Block A);

    public abstract boolean  clear(Block A);


    public abstract Boolean hold(Block A);

    public abstract Boolean armEmpty();

}