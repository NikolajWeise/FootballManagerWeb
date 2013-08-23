package de.weise.fm.web.model.play;

/**
 * The action for a specified position in a play.
 */
public class Action implements Cloneable {

    private ActionType actionType;
    private FieldPosition direction;
    private boolean primaryAction;

    public Action(ActionType actionType, FieldPosition direction) {
        this(actionType, direction, false);
    }

    public Action(ActionType actionType, FieldPosition direction, boolean primaryReceive) {
        this.actionType = actionType;
        this.direction = direction;
        this.primaryAction = primaryReceive;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public FieldPosition getDirection() {
        return direction;
    }

    public void setDirection(FieldPosition direction) {
        this.direction = direction;
    }

    public boolean isPrimaryAction() {
        return primaryAction;
    }

    public void setPrimaryAction(boolean primaryAction) {
        this.primaryAction = primaryAction;
    }

    @Override
    public Action clone() {
        try {
            Action clone = (Action)super.clone();
            clone.direction = this.direction.clone();
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }
}