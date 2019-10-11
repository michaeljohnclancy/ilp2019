package uk.ac.ed.inf.powergrab;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static uk.ac.ed.inf.powergrab.Environment.randomGenerator;

public class StatelessAgent extends Agent {

    public StatelessAgent(String identifier, Position position) {
        super(identifier, position);
    }

    public void moveAndGatherResources(PowerGrabMap powerGrabMap){
        Pair<Direction, Optional<Station>> directionOptionalPair = getDirectionAndOptionalInRangeStation(powerGrabMap);

        move(directionOptionalPair.getKey());
        directionOptionalPair.getValue().ifPresent(station -> station.transferResourcesTo(this));
    }

    private Pair<Direction, Optional<Station>> getDirectionAndOptionalInRangeStation(PowerGrabMap powerGrabMap){
        return getOptimumDirectionAndInRangeStation(powerGrabMap)
                .orElse(new Pair<>(getRandomDirection(), Optional.empty()));
    }

    private Optional<Pair<Direction, Optional<Station>>> getOptimumDirectionAndInRangeStation(PowerGrabMap powerGrabMap) {
        return Stream.of(Direction.values())
                .map(direction -> new Pair<>(direction, getNearestStationInRangeOfLookAhead(direction, powerGrabMap)))
                .filter(directionOptionalPair -> directionOptionalPair.getValue().isPresent())
                .filter(directionOptionalPair -> directionOptionalPair.getValue().get().getKey().getBalance() > 0)
                .min(Comparator.comparing(directionOptionalPair -> directionOptionalPair.getValue().get().getValue()))
                .map(directionOptionalPair -> new Pair<>(directionOptionalPair.getKey(), directionOptionalPair.getValue().map(Pair::getKey)));
    }

     private Direction getRandomDirection(){
        return Direction.values()[randomGenerator.nextInt(Direction.values().length)];
    }

    private Optional<Pair<Station, Double>> getNearestStationInRangeOfLookAhead(Direction direction, PowerGrabMap powerGrabMap){
        return powerGrabMap.getNearestStationDistancePairIfInRange(getPosition().nextPosition(direction));
    }
}
