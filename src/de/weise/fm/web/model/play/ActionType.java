package de.weise.fm.web.model.play;

import java.util.ArrayList;
import java.util.Collection;

import de.weise.fm.web.model.play.Play.PlayType;

/**
 * The type of action.
 * <p>
 * BLITZ, MAN and ZONE are defensive action types. The other types are offsensive.
 */
public enum ActionType {
    RUN_BLOCK, PASS_BLOCK, LEAD_BLOCK,
    RCV_HOOK, RCV_COMEBACK, RCV_IN, RCV_OUT, RCV_SLANT, RCV_FLAG, RCV_RUN,
    CRY_COUNTER, CRY_RUN,
    BLITZ, MAN, ZONE;

    public boolean isReceiveType() {
        return RCV_HOOK.equals(this)
                || RCV_COMEBACK.equals(this)
                || RCV_IN.equals(this)
                || RCV_OUT.equals(this)
                || RCV_SLANT.equals(this)
                || RCV_FLAG.equals(this)
                || RCV_RUN.equals(this);
    }

    public static ActionType get(String value) {
        for(ActionType actionType : values()) {
            if(actionType.name().equals(value)) {
                return actionType;
            }
        }
        throw new IllegalArgumentException(value + " is not an ActionType");
    }

    /**
     * Gets the available ActionTypes for the given {@link Position} and {@link PlayType}.
     */
    public static Collection<ActionType> getActionTypes(Position position, PlayType playType) {
        Collection<ActionType> actionTypes = new ArrayList<>(ActionType.values().length);

        if(playType.isPassPlay()) { // pass play
            if(position.isOffLine()) {
                actionTypes.add(PASS_BLOCK);
            } else if(position.isRunningBack() || position.isReceiver()) {
                actionTypes.add(RCV_HOOK);
                actionTypes.add(RCV_COMEBACK);
                actionTypes.add(RCV_IN);
                actionTypes.add(RCV_OUT);
                actionTypes.add(RCV_SLANT);
                actionTypes.add(RCV_FLAG);
                actionTypes.add(RCV_RUN);
                if(position.isRunningBack() || position.equals(Position.TE)) {
                    actionTypes.add(PASS_BLOCK);
                }
            }
        } else if(playType.isRunPlay()) { // run play
            if(position.isOffLine()) {
                actionTypes.add(RUN_BLOCK);
                actionTypes.add(LEAD_BLOCK);
            } else if(position.isRunningBack()) {
                actionTypes.add(LEAD_BLOCK);
                actionTypes.add(CRY_COUNTER);
                actionTypes.add(CRY_RUN);
            } else if(position.isReceiver()) {
                actionTypes.add(RUN_BLOCK);
                actionTypes.add(RCV_RUN);
            }
        } else if(playType.isDefensePlay()) { // defense play
            actionTypes.add(BLITZ);
            actionTypes.add(MAN);
            actionTypes.add(ZONE);
        }

        return actionTypes;
    }
}