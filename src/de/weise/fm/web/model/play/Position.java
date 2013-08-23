package de.weise.fm.web.model.play;

import java.util.Arrays;
import java.util.Collection;

/**
 * Positions of a formation.
 * <p>
 * This is not the position on the field but the "player's role".
 */
public enum Position {
    QB, FB, HB, C, RG, LG, RT, LT, TE, WR,
    DT, DE, MLB, RLB, LLB, CB, SS, FS,
    P, K;

    /**
     * Gets all positions used in an offense formation.
     */
    public static Collection<Position> getOffPositions() {
        return Arrays.asList( QB, FB, HB, C, RG, LG, RT, LT, TE, WR );
    }

    /**
     * Gets the substitutable offense positions. These are FB, HF, TE and WR. The offensive line and the QB cannot be
     * changed in a formation, because those positions are obligable.
     */
    public static Collection<Position> getOffSbstPositions() {
        return Arrays.asList( FB, HB, TE, WR );
    }

    /**
     * Gets all positions used in a defense formation.
     */
    public static Collection<Position> getDefPositions() {
        return Arrays.asList( DT, DE, MLB, RLB, LLB, CB, SS, FS );
    }

    /**
     * Gets all positions used in a special formation.
     */
    public static Collection<Position> getSpcPositions() {
        return Arrays.asList( P, K );
    }

    public static Position get(String value) {
        for(Position position : values()) {
            if(position.name().equals(value)) {
                return position;
            }
        }
        throw new IllegalArgumentException(value + " is not a Position");
    }

    /**
     * Gets whether this position is one of the offensive line.
     *
     * @return <tt>true</tt> if position equals one of: C, LT, RT, LG, RG
     */
    public boolean isOffLine() {
        return Position.C.equals(this)
                || Position.LT.equals(this)
                || Position.RT.equals(this)
                || Position.LG.equals(this)
                || Position.RG.equals(this);
    }

    /**
     * Gets whether this position is one of the running backs.
     *
     * @return <tt>true</tt> if position equals one of: HB, FB
     */
    public boolean isRunningBack() {
        return Position.HB.equals(this)
                || Position.FB.equals(this);
    }

    /**
     * Gets whether this position is one of the receivers.
     *
     * @return <tt>true</tt> if position equals one of: HB, FB, WR, TE
     */
    public boolean isReceiver() {
        return Position.HB.equals(this)
                || Position.FB.equals(this)
                || Position.WR.equals(this)
                || Position.TE.equals(this);
    }

    /**
     * Gets whether this position is one of the defensive line.
     *
     * @return <tt>true</tt> if position equals one of: DT, DE
     */
    public boolean isDefLine() {
        return Position.DT.equals(this)
                || Position.DE.equals(this);
    }

    /**
     * Gets whether this position is one of the linebackers.
     *
     * @return <tt>true</tt> if position equals one of: MLB, RLB, LLB
     */
    public boolean isLinebacker() {
        return Position.MLB.equals(this)
                || Position.RLB.equals(this)
                || Position.LLB.equals(this);
    }

    /**
     * Gets whether this position is one of the defensive backs.
     *
     * @return <tt>true</tt> if position equals one of: CB, FS, SS
     */
    public boolean isDefBack() {
        return Position.CB.equals(this)
                || Position.FS.equals(this)
                || Position.SS.equals(this);
    }

    /**
     * Gets whether this position is one of the safeties.
     *
     * @return <tt>true</tt> if position equals one of: FS, SS
     */
    public boolean isSafety() {
        return Position.FS.equals(this)
                || Position.SS.equals(this);
    }
}
