package org.example;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CalcMyFunctionBehaviour extends Behaviour {
    @Override
    public void action() {
        ACLMessage message_x = getAgent().receive(MessageTemplate.MatchConversationId("y_calc"));
        if (message_x != null) {
            String content_x = message_x.getContent();
            String[] values = content_x.split(",");
            double x = Double.parseDouble(values[0]);
            double delta = Double.parseDouble(values[1]);

            double y1 = 0;
            double y = 0;
            double y2 = 0;

            if (getAgent().getLocalName().equals("Agent1")) {
                y1 = Calculation.Calculation1(x-delta);
                y = Calculation.Calculation1(x);
                y2 = Calculation.Calculation1(x+delta);
            }
            else if (getAgent().getLocalName().equals("Agent2")) {
                y1 = Calculation.Calculation2(x-delta);
                y = Calculation.Calculation2(x);
                y2 = Calculation.Calculation2(x+delta);
            }
            else if (getAgent().getLocalName().equals("Agent3")) {
                y1 = Calculation.Calculation2(x-delta);
                y = Calculation.Calculation2(x);
                y2 = Calculation.Calculation2(x+delta);
            }
            ACLMessage message_y = new ACLMessage(ACLMessage.INFORM);
            message_y.setConversationId("counted_y");
            AID receiver = new AID(message_x.getSender().getLocalName(), false);
            message_y.addReceiver(receiver);
            String content_y = y1 + "," + y + "," + y2;
            message_y.setContent(content_y);
            getAgent().send(message_y);

        } else {
            block();
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
