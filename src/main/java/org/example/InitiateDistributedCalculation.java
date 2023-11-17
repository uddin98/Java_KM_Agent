package org.example;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InitiateDistributedCalculation extends Behaviour {

    double x;
    double delta;
    double y1_agent1 = 0;
    double y_agent1 = 0;
    double y2_agent1 = 0;
    double y1_agent2 = 0;
    double y_agent2 = 0;
    double y2_agent2 = 0;
    double y1_agent3 = 0;
    double y_agent3 = 0;
    double y2_agent3 = 0;
    double y1 = 0;
    double y = 0;
    double y2 = 0;

    public InitiateDistributedCalculation(double x, double delta) {
        this.x = x;
        this.delta = delta;
    }


    @Override
    public void onStart() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setConversationId("y_calc");
        String content_of_msg = x + "," + delta;
        msg.setContent(content_of_msg);
        msg.addReceiver(new AID("Agent1", false));
        msg.addReceiver(new AID("Agent2", false));
        msg.addReceiver(new AID("Agent3", false));
        getAgent().send(msg);
    }

    int i = 0;


    @Override
    public void action() {
        ACLMessage msg_y = getAgent().receive(MessageTemplate.MatchConversationId("counted_y"));
        if (msg_y != null) {
            System.out.println(getAgent().getLocalName() + " got message from " + msg_y.getSender().getLocalName() + " : " + msg_y.getContent());
            String content_x = msg_y.getContent(); //
            String[] values = content_x.split(",");

            switch (msg_y.getSender().getLocalName()) {
                case "Agent1":
                    y1_agent1 = Double.parseDouble(values[0]);
                    y_agent1 = Double.parseDouble(values[1]);
                    y2_agent1 = Double.parseDouble(values[2]);
                    i++;
                    break;
                case "Agent2":
                    y1_agent2 = Double.parseDouble(values[0]);
                    y_agent2 = Double.parseDouble(values[1]);
                    y2_agent2 = Double.parseDouble(values[2]);
                    i++;
                    break;
                case "Agent3":
                    y1_agent3 = Double.parseDouble(values[0]);
                    y_agent3 = Double.parseDouble(values[1]);
                    y2_agent3 = Double.parseDouble(values[2]);
                    i++;
                    break;
            }

            y1 = y1_agent1 + y1_agent2 + y1_agent3;
            y = y_agent1 + y_agent2 + y_agent3;
            y2 = y2_agent1 + y2_agent2 + y2_agent3;

            double maximum = Math.max(y1, Math.max(y, y2));

            if (maximum == y1) {
                x -= delta;
            }
            if (maximum == y) {
                delta *= 0.5;
            }
            if (maximum == y2) {
                x += delta;
            }
            y = maximum;

        }

    }

    @Override
    public boolean done() {
        return i >= 3;
    }

    @Override
    public int onEnd() {
        if (delta <= 0.01) {
            System.out.println("delta reached 0.01 at X = " + x + "; Y = " + y);
        } else {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setConversationId("start_action");
            AID receiver = new AID("", false);
            switch (getAgent().getLocalName()) {
                case "Agent1":
                    receiver = new AID("Agent2", false);
                    break;
                case "Agent2":
                    receiver = new AID("Agent3", false);
                    break;
                case "Agent3":
                    receiver = new AID("Agent1", false);
                    break;
            }
            msg.addReceiver(receiver);
            String content_y = x + "," + delta;
            msg.setContent(content_y);
            getAgent().send(msg);
        }
        return 0;
    }
}