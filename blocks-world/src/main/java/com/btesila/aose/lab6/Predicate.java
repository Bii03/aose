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

import java.util.Stack;

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