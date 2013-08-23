package de.weise.fm.web.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.weise.fm.web.model.play.Position;

/**
 * Attributes of a player. The attributes are held in a map of {@link Attributes.Type} and <tt>Integer</tt>.
 */
public class Attributes {

    /**
     * The type of an attribute.<br>
     * Every type holds {@link Position Positions} this type is a prime attribute for.<br>
     * To indicate a prime attribute use {@link #isPrimeAttribute(Position)}.
     */
    public enum Type {
        CONDITION(),
        STRENGTH (Position.C, Position.RG, Position.LG, Position.RT, Position.LT, Position.FB,
                  Position.DT, Position.DE),
        SPEED    (Position.HB, Position.WR,
                  Position.CB, Position.SS, Position.FS),
        AGILITY  (Position.HB, Position.WR,
                  Position.CB, Position.SS, Position.FS, Position.MLB, Position.RLB, Position.LLB),
        AWARENESS(Position.QB, Position.HB,
                  Position.MLB, Position.RLB, Position.LLB),
        CATCHING (Position.WR, Position.TE,
                  Position.CB, Position.SS, Position.FS),
        ROUTE_RUN(Position.WR, Position.TE),
        THROW_ACC(Position.QB),
        THROW_POW(Position.QB),
        BLOCK_RUN(Position.C, Position.RG, Position.LG, Position.RT, Position.FB, Position.TE),
        BLOCK_PAS(Position.C, Position.RG, Position.LG, Position.LT),
        TACKLE   (Position.DT, Position.DE, Position.MLB, Position.RLB, Position.LLB),
        COVER_MAN(Position.MLB, Position.RLB, Position.LLB, Position.CB),
        COVER_ZON(Position.MLB, Position.RLB, Position.LLB, Position.SS, Position.FS),
        PLAY_RECO(Position.QB,
                  Position.MLB, Position.RLB, Position.LLB),
        KICK_ACC (Position.K, Position.P),
        KICK_POW (Position.K, Position.P);

        private Set<Position> primeForPosition = new HashSet<>(16, 1);

        /**
         * @param positions The positions this attributes is a prime attribute for.
         */
        private Type(Position... positions) {
            for(Position p : positions) {
                primeForPosition.add(p);
            }
        }

        public static Type get(String value) {
            for(Type type : values()) {
                if(type.name().equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException(value + " is not an Attributes.Type");
        }

        /**
         * Gets whether this attribute type is a prime attribute for the given position.
         */
        public boolean isPrimeAttribute(Position position) {
            return primeForPosition.contains(position);
        }
    }

    private Map<Type, Integer> values = new HashMap<>(Type.values().length, 1);

    public Attributes() {
        for(Type type : Type.values()) {
            values.put(type, 99);
        }
    }

    public int getValue(Type type) {
        return values.get(type).intValue();
    }

    public int setValue(Type type, int value) {
        return values.put(type, new Integer(value));
    }
}
