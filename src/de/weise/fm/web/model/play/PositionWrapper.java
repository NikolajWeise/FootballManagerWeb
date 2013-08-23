package de.weise.fm.web.model.play;

/**
 * Wraps the {@link Position} of a formation and its {@link FieldPosition}. This is needed to get the exact "player" of
 * a formation, because one {@link Position} may be defined more than once.
 * <p>
 * <tt>equals()</tt> and <tt>hashCode()</tt> use <tt>Position</tt> and <tt>FieldPosition</tt>.
 */
public class PositionWrapper
        implements Cloneable {

    private Position position;
    private FieldPosition fieldPosition;

    public PositionWrapper(Position position, FieldPosition fieldPosition) {
        this.position = position;
        this.fieldPosition = fieldPosition;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public FieldPosition getFieldPosition() {
        return fieldPosition;
    }

    public void setFieldPosition(FieldPosition fieldPosition) {
        this.fieldPosition = fieldPosition;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldPosition == null) ? 0 : fieldPosition.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
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
        PositionWrapper other = (PositionWrapper)obj;
        if(fieldPosition == null) {
            if(other.fieldPosition != null)
                return false;
        } else if(!fieldPosition.equals(other.fieldPosition))
            return false;
        if(position != other.position)
            return false;
        return true;
    }

    @Override
    public PositionWrapper clone() {
        try {
            PositionWrapper clone = (PositionWrapper)super.clone();
            clone.fieldPosition = fieldPosition.clone();
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }
}
