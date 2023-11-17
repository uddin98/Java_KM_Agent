package org.example;

import jade.core.Agent;

public class FunctionAgent extends Agent {
    protected void setup() {
        System.out.println("The Agent " + getLocalName() + " is born");
        this.addBehaviour(new CalcMyFunctionBehaviour());
        this.addBehaviour(new CatchInitiative());

        if (this.getLocalName().equals("Agent1")) {
            this.addBehaviour(new StartBehaviour(this, 3000));
        }
    }
}
