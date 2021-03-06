/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksworld;

import java.util.ArrayList;
import operators.*;
import predicates.*;
import predicates.PredicateUsedColsNum;

/**
 *
 * @author gaspercat
 */
public class Algorithm {
    private ArrayList<State>     states;      // List of states transited
    private ArrayList<Operator>  operators;   // List of operators applied
    private ArrayList<Object>    stack;       // Stack of the algorithm
    private ArrayList<Predicate> solve_stack; // Stack of preconditions being solved
    private ArrayList<State>     prev_states; // States of the previous algorithm

    private State init_state;                 // Initial state
    private State goal_state;                 // Goal state
    private State curr_state;                 // Current state
    private boolean is_valid;                 // Has the algorithm found a valid solution?

    public Algorithm(){
        this.init_state = null;
        this.goal_state = null;
        this.is_valid = true;

        this.states = new ArrayList<State>();
        this.operators = new ArrayList<Operator>();
        this.stack = new ArrayList<Object>();
        this.solve_stack = new ArrayList<Predicate>();
        this.prev_states = new ArrayList<State>();
    }

    // * ** CONTROL METHODS
    // * ******************************************

    // Execute a complete problem
    public void run(State initial, State goal){
        this.init_state = initial;
        this.goal_state = goal;

        this.curr_state = initial;
        this.states.add(this.curr_state);

        this.stack.add(new Preconditions(goal.getPredicates()));
        this.stack.addAll(goal.getPredicates());

        execute();

        // Add goal state to plan
        this.states.add(goal);
    }

    // Execute a partial problem
    public void run(State initial, Operator op, ArrayList<Predicate> solve_stack, ArrayList<State> prev_states){
        this.init_state = initial;
        this.goal_state = null;

        this.curr_state = initial;
        this.states.add(this.curr_state);

        this.stack.add(op);
        this.stack.add(op.getPreconditions());
        this.stack.addAll(op.getPreconditions().getPredicates());

        this.solve_stack = solve_stack;
        this.prev_states = prev_states;

        execute();
    }

    public boolean isValid(){
        return this.is_valid;
    }

    public ArrayList<Operator> getOperators(){
        ArrayList<Operator> ret = new ArrayList<Operator>();

        for(int i=0;i<this.operators.size();i++){
            ret.add(this.operators.get(i).clone());
        }

        return ret;
    }

    public ArrayList<State> getStates(){
        ArrayList<State> ret = new ArrayList<State>();

        for(int i=0;i<this.states.size();i++){
            ret.add(this.states.get(i).clone());
        }

        return ret;
    }

    public void clear(){
        this.init_state = null;
        this.goal_state = null;
        this.is_valid = true;

        this.states.clear();
        this.operators.clear();
        this.stack.clear();
        this.solve_stack.clear();
        this.prev_states.clear();
    }

    // * ** ALGORITHM
    // * ******************************************

    public void execute(){
        while(this.stack.size() > 0){
            Object c = this.stack.remove(this.stack.size()-1);

            // If c is an operator
            if(c instanceof Operator){
                // Update current state
                System.out.println("Applying operator: " + c.toString());
                this.curr_state = new State(this.curr_state, (Operator)c);

                // If new state already visited, return
                if(isStateVisited(this.curr_state)){
                    System.out.println("OUCH!");
                    this.is_valid = false;
                    return;
                }

                // Add new state & operator to plan
                this.states.add(this.curr_state);
                this.operators.add((Operator)c);


            // If c is a condition not fully instanced
            }else if((c instanceof Predicate) && !((Predicate)c).isInstanced()){
                System.out.println("Instantiating condition: " + c);
                heuristicInstanceCondition((Predicate)c);
                this.stack.add(c);

            // If c is a condition fully instanced
            }else if((c instanceof Predicate) && ((Predicate)c).isInstanced()){
                System.out.println("Actual state:" +this.curr_state.toString());
                System.out.println("Checking condition: " + c);
                Predicate pred = (Predicate)c;
                if(!this.curr_state.hasPredicate(pred)){
                    //Add predicate to list of predicates being solved
                    this.solve_stack.add(pred);

                    // Run a branch for each possible operator
                    boolean found = false;
                    ArrayList<Operator> ops = heuristicSelectOperators(pred);
                    for(Operator op: ops){
                        if(found) break;

                        Algorithm alg;

                        do {
                            System.out.println("Adding new operator to the stack: " + op);
                            alg = new Algorithm();
                            ArrayList<State> pstates = new ArrayList<State>();
                            pstates.addAll(this.prev_states);
                            pstates.addAll(this.states);
                            alg.run(this.curr_state, op, this.solve_stack, pstates);
                            if(alg.isValid()){
                                ArrayList<State> tstates       = alg.getStates();
                                ArrayList<Operator> toperators = alg.getOperators();
                                tstates.remove(0);

                                this.states.addAll(tstates);
                                this.operators.addAll(toperators);
                                System.out.println("Curren plan: " + this.operators);
                                System.out.println("Operators added to plan: " + toperators);
                                this.curr_state = this.states.get(this.states.size()-1);

                                found = true;
                                break;
                            }else{
                                System.out.println("Operator discarted: " + op.toString());
                            }
                        }while(!alg.isValid() && op.hasInstancesLeft());
                    }

                    // Remove predicate from list of predicates being solved
                    this.solve_stack.remove(pred);

                    // If no valid operator found, we've got a problemo!
                    if(found == false){
                        this.is_valid = false;
                        return;
                    }
                }

            // If c is a list of conditions
            }else if(c instanceof Preconditions){
                System.out.println("Checking list of conditions: " + c);
                ArrayList<Predicate> unmet = this.curr_state.getUnmetConditions((Preconditions)c);
                if(unmet.size() > 0){
                    this.stack.add(c);
                    this.stack.addAll(unmet);
                }
            }
        }
    }

    // * ** HEURISTIC METHODS
    // * ******************************************

    private void heuristicInstanceCondition(Predicate pred){
        // Get related operator
        Operator op = null;
        for(int i=this.stack.size()-1;;i--){
            if(this.stack.get(i) instanceof Operator){
                op = (Operator)this.stack.get(i);
                break;
            }
        }

        // Define value at operator
        op.instanceValues(pred, this.curr_state);
    }

    private ArrayList<Operator> heuristicSelectOperators(Predicate pred){
        // TODO: Finish this heuristic
        ArrayList<Operator> op = new ArrayList<Operator>();
        Predicate p;

        switch(pred.getType()){

            // If piece must be free but it isn't
            case Predicate.FREE:
                op.add(new OperatorUnstack((Block)null, pred.getA()));
                break;

            // If free arm needed but is currently used
            case Predicate.FREE_ARM:
                Predicate tp = this.curr_state.getPredicate(Predicate.PICKED_UP);
                p = this.goal_state.matchPredicate(new PredicateOnTable(tp.getA()));
                if(p != null){
                    op.add(new OperatorLeave(tp.getA()));
                    //op.add(new OperatorStack(tp.getA(), null));
                }else{
                    op.add(new OperatorStack(tp.getA(), null));
                    //op.add(new OperatorLeave(tp.getA()));
                }
                break;

            // If free stack needed but 3 already used
            case Predicate.FREE_STACK:
                op.add(new OperatorPickUp((Block)null));
                break;

            // If a block must be over another
            case Predicate.ON:
                op.add(new OperatorStack(pred.getA(), pred.getB()));
                break;

            // If a block must be on the table
            case Predicate.ON_TABLE:
                op.add(new OperatorLeave(pred.getA()));
                break;

            // If a block must be picked up
            case Predicate.PICKED_UP:
                p = this.curr_state.matchPredicate(new PredicateOnTable(pred.getA()));
                if(p != null){
                    op.add(new OperatorPickUp(pred.getA()));
                    op.add(new OperatorUnstack(pred.getA(), null));
                }else{
                    op.add(new OperatorUnstack(pred.getA(), null));
                    op.add(new OperatorPickUp(pred.getA()));
                }
                break;
        }

        return op;
    }

    // * ** HELPER METHODS
    // * ******************************************

    private boolean
    isStateVisited(State s){
        for(State ts: this.states){
            if(ts.equals(s)) return true;
        }

        for(State ts: this.prev_states){
            if(ts.equals(s)) return true;
        }

        return false;
    }

    private boolean isPredicateBeingSolved(Predicate pred){
        for(Predicate p: this.solve_stack){
            if(p.equals(pred)) return true;
        }

        return false;
    }
}
