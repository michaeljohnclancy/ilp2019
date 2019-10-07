package uk.ac.ed.inf.powergrab;

import java.util.List;

public class Environment {

    private final List<Agent> agents;
    private final PowerGrabMap powerGrabMap;

    public Environment(List<Agent> agents, PowerGrabMap powerGrabMap) {
        this.agents = agents;
        this.powerGrabMap = powerGrabMap;
    }

    public void step(){
        agents.forEach(powerGrabMap::transferFundsIfNearestStationInRange);
    }
}
