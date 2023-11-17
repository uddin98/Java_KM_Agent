package org.example;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Date;
import java.util.Random;

public class StartBehaviour extends WakerBehaviour {
    public StartBehaviour(Agent a, long wakeupDate) {
        super(a, wakeupDate);
    }
    @Override
    protected void onWake() {

        ACLMessage first_message = new ACLMessage(ACLMessage.INFORM);
        first_message.setConversationId("start_action");


        Random random = new Random();

        double x = random.nextDouble();
        double delta = 1;

        first_message.addReceiver(new AID(getAgent().getLocalName(),false));
        String first_content = x + "," + delta;
        first_message.setContent(first_content);
        getAgent().send(first_message);
    }
}
