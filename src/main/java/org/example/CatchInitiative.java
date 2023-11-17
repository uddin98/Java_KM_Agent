package org.example;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CatchInitiative extends Behaviour {
    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(MessageTemplate.MatchConversationId("start_action"));
        if (msg != null) {
            String content_x = msg.getContent();
            String[] values = content_x.split(",");
            double x = Double.parseDouble(values[0]);
            double delta = Double.parseDouble(values[1]);

            getAgent().addBehaviour(new InitiateDistributedCalculation(x, delta));
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
