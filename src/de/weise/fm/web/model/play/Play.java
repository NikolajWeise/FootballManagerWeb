package de.weise.fm.web.model.play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>
 * <tt>equals()</tt> and <tt>hashCode()</tt> use the play's formation and name.
 */
public class Play implements Comparable<Play>, Cloneable {

    /**
     * The type of play.<br>
     * The type will affect QB's action after the snap. Also affects selectable {@link ActionType ActionTypes} when
     * editing plays.
     */
    public enum PlayType {
        RUN, DRAW, PASS, PA, DEFENSE;

        public static PlayType[] getOffenseTypes() {
            return new PlayType[]{ RUN, DRAW, PASS, PA };
        }

        public static PlayType get(String value) {
            for(PlayType playType : values()) {
                if(playType.name().equals(value)) {
                    return playType;
                }
            }
            throw new IllegalArgumentException(value + " is not an PlayType");
        }

        public boolean isPassPlay() {
            return PlayType.PA.equals(this) || PlayType.PASS.equals(this);
        }

        public boolean isRunPlay() {
            return PlayType.RUN.equals(this) || PlayType.DRAW.equals(this);
        }

        public boolean isDefensePlay() {
            return PlayType.DEFENSE.equals(this);
        }
    }

    private String name;
    private int skill = 50;
    private Formation formation;
    private Map<PositionWrapper, List<Action>> actions = new HashMap<>(16, 1);
    private PlayType playType;

    public Play(String name, Formation formation, PlayType playType) {
        this.name = name;
        this.formation = formation;
        this.playType = playType;
    }

    public String getName() {
        return name;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public Formation getFormation() {
        return formation;
    }

    public PlayType getPlayType() {
        return playType;
    }

    public void setPlayType(PlayType playType) {
        this.playType = playType;
    }

    public boolean isPassPlay() {
        return playType.isPassPlay();
    }

    public boolean isRunPlay() {
        return playType.isRunPlay();
    }

    public boolean isDefensePlay() {
        return playType.isDefensePlay();
    }

    public Map<PositionWrapper, List<Action>> getActions() {
        return actions;
    }

    public void addAction(PositionWrapper pw, Action action) {
        List<Action> actions = this.actions.get(pw);

        if(actions != null) {
            // all receive types (except RCV_RUN), counter-run, ZONE and man-to-man must be the only action
            ActionType newActionType = action.getActionType();
            ActionType oldActionType = actions.get(0).getActionType();
            if(
                    (  newActionType.isReceiveType() || oldActionType.isReceiveType()
                    || newActionType.equals(ActionType.CRY_COUNTER)
                    || oldActionType.equals(ActionType.CRY_COUNTER)
                    || newActionType.equals(ActionType.ZONE)
                    || oldActionType.equals(ActionType.ZONE)
                    || newActionType.equals(ActionType.MAN)
                    || oldActionType.equals(ActionType.MAN)
                    )
                    && !ActionType.RCV_RUN.equals(newActionType)
                    && !ActionType.RCV_RUN.equals(oldActionType)) {
                actions.clear();
            }
        } else {
            actions = new ArrayList<>(1);
        }

        actions.add(action);
        this.actions.put(pw, actions);
    }

    public void removeAction(PositionWrapper pw) {
        this.actions.remove(pw);
    }

    public void clearActions() {
        this.actions.clear();
    }

    public boolean validate() {
        if(isDefensePlay()) {
            return true; // defense plays are always valid
        }

        if(actions.size() < 10) { // there must be at least 10 list of actions (one per position minus QB)
            return false;
        }

        int foundPrimaryAction = 0;
        int foundCryRunAction = 0;
        for(List<Action> actions : this.actions.values()) {
            if(actions.isEmpty()) { // each player must have at least one action
                return false;
            }
            if(actions.get(0).isPrimaryAction()) {
                foundPrimaryAction++;
            }
            if(ActionType.CRY_RUN.equals(actions.get(0).getActionType())
                    || ActionType.CRY_COUNTER.equals(actions.get(0).getActionType())) {
                foundCryRunAction++;
            }
        }
        if(foundPrimaryAction != 1 && this.isPassPlay()) { // we need one primaryAction in pass plays
            return false;
        }
        if(foundPrimaryAction > 0 && this.isRunPlay()) { // no primary receiver when running
            return false;
        }
        if(foundCryRunAction != 1 && this.isRunPlay()) { // we need one CryRun-ActionType in run plays
            return false;
        }
        if(foundCryRunAction > 0 && this.isPassPlay()) { // no carry when passing
            return false;
        }

        if(!PlayType.PASS.equals(playType)) {
            if(!formation.hasPosition(Position.HB) && !formation.hasPosition(Position.FB)) {
                return false; // RUN, DRAW and PA need an RB
            }
        }

        // valid :)
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((formation == null) ? 0 : formation.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        Play other = (Play)obj;
        if(formation == null) {
            if(other.formation != null)
                return false;
        } else if(!formation.equals(other.formation))
            return false;
        if(name == null) {
            if(other.name != null)
                return false;
        } else if(!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public Play clone() {
        try {
            Play clone = (Play)super.clone();
            clone.formation = this.formation.clone();
            clone.actions = new HashMap<>(this.actions.size());
            for(PositionWrapper pw : this.actions.keySet()) {
                List<Action> actions = this.actions.get(pw);
                List<Action> clonedActions = new ArrayList<>(actions.size());
                for(Action action : actions) {
                    clonedActions.add(action.clone());
                }
                clone.actions.put(pw.clone(), clonedActions);
            }
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }

    @Override
    public int compareTo(Play other) {
        return this.name.compareTo(other.name);
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder(name).append("\n");
//        for(PositionWrapper pw : actions.keySet()) {
//        }
//        return sb.toString();
//    }

    /** used for JSON serialization */
    public void setName(String name) {
        this.name = name;
    }
    /** used for JSON serialization */
    public void setFormation(Formation formation) {
        this.formation = formation;
    }
    /** used for JSON serialization */
    public void setActions(Map<PositionWrapper, List<Action>> actions) {
        this.actions = actions;
    }
}
