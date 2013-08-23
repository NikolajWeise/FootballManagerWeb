package de.weise.fm.web.model;

import de.weise.fm.web.model.play.Position;

/**
 * A player bean with getters and setters for its attributes.
 * <p>
 * <tt>Player</tt> is comparable by its {@link Position} and overall skill.<br>
 * This means players are sorted by position and players with same position are sorted by their overall skill value.
 */
public class Player implements Comparable<Player> {

    private String name;
    private int age;
    private int number;
    private Attributes attributes = new Attributes();
    private Position position;
    private Attributes.Type individualTraining = null;

    public Player(String forename, String surname) {
        this.name = forename + " " + surname;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumber() {
        return (number < 10) ? "0" + number : "" + number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getOverall() {
        float c = 0;
        float overall = 0;

        for(Attributes.Type type : Attributes.Type.values()) {
            if(type.isPrimeAttribute(position)) {
                overall += attributes.getValue(type);
                c++;
            }
        }

        return Math.round(overall / c);
    }

    public Attributes.Type getIndividualTraining() {
        return individualTraining;
    }

    public void setIndividualTraining(Attributes.Type individualTraining) {
        this.individualTraining = individualTraining;
    }

    @Override
    public int compareTo(Player other) {
        if(this.position != null && other.position != null) {
            int c = this.position.compareTo(other.position);
            if(c != 0) {
                return c;
            }
        }
        return other.getOverall() - this.getOverall();
    }

    @Override
    public String toString() {
        return name + " (" + position + ")";
    }
}
