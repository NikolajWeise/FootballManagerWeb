package de.weise.fm.web.model.play;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>
 * <tt>equals()</tt> and <tt>hashCode()</tt> use only the formation's name.
 */
public abstract class Formation implements Comparable<Formation>, Cloneable {

    private String name;
    private int skill = 50;
    private List<PositionWrapper> positions = new ArrayList<>(11);

    public Formation(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    /**
     * Do not use this method directly!
     *
     * @see Playbook#changeFormationName(Formation, String)
     */
    public final void setName(String name) {
        this.name = name;
    }

    public final int getSkill() {
        return skill;
    }

    public final void setSkill(int skill) {
        this.skill = skill;
    }

    public final List<PositionWrapper> getPositions() {
        return positions;
    }

    /** used for JSON serialization */
    public final void setPositions(List<PositionWrapper> positions) {
        this.positions = positions;
    }

    public void addPosition(Position position, FieldPosition fieldPosition) {
        positions.add(new PositionWrapper(position, fieldPosition));
    }

    public void removePosition(Position position, FieldPosition fieldPosition) {
        PositionWrapper pw = new PositionWrapper(position, fieldPosition);
        positions.remove(pw);
    }

    public void movePosition(Position position, FieldPosition oldFieldPosition, FieldPosition newFieldPosition) {
        removePosition(position, oldFieldPosition);
        addPosition(position, newFieldPosition);
    }

    /** Use only if there exists exactly one position with the given <tt>position</tt>. */
    protected void movePosition(Position position, FieldPosition newFieldPosition) {
        PositionWrapper removePw = null;
        for(PositionWrapper pw : positions) {
            if(pw.getPosition().equals(position)) {
                removePw = pw;
                break;
            }
        }
        movePosition(position, removePw.getFieldPosition(), newFieldPosition);
    }

    /**
     * Gets whether this formation contains the specified position or not.
     */
    public boolean hasPosition(Position position) {
        for(PositionWrapper pw : positions) {
            if(pw.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the formation. An invalid formation can neither be used nor saved when editing.
     *
     * @return <tt>true</tt> if valid, otherwise <tt>false</tt>.
     */
    public boolean validate() {
        if(positions.size() != 11) {
            return false;
        }
        for(PositionWrapper pw1 : getPositions()) {
            for(PositionWrapper pw2 : getPositions()) {
                if(!pw1.equals(pw2) && pw1.getFieldPosition().equals(pw2.getFieldPosition())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Gets whether this formation is an offense formation.
     */
    public abstract boolean isOffenseFormation();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name).append("\n");
        for(PositionWrapper pw : positions) {
            FieldPosition fieldPosition = pw.getFieldPosition();
            sb.append(pw.getPosition()).append(": ")
                .append(fieldPosition.getHeight()).append(", ")
                .append(fieldPosition.getWidth()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        Formation other = (Formation)obj;
        if(name == null) {
            if(other.name != null)
                return false;
        } else if(!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int compareTo(Formation other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public Formation clone() {
        try {
            Formation clone = (Formation)super.clone();
            clone.positions = new ArrayList<>(this.positions.size());
            for(PositionWrapper pw : this.positions) {
                clone.positions.add(pw.clone());
            }
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }
}
