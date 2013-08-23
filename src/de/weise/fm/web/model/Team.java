package de.weise.fm.web.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Play;
import de.weise.fm.web.model.play.Playbook;
import de.weise.fm.web.model.play.Position;

/**
 * The team holds all {@link Player Players}, the {@link Playbook} and the training settings.
 */
public class Team {

    private String name;

    private List<Player> players = new ArrayList<>(64);
    private Playbook playbook = new Playbook();

    // XXX refactor training to an own class when getting more complex
    private Formation formationOffTraining;
    private Formation formationDefTraining;
    private Play playOffTraining;
    private Play playDefTraining;

    private TrainingPlan trainingPlan = new TrainingPlan();

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets an unmodifiable list of all players.
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Gets an unmodifiable list of all players with the specified position.
     */
    public List<Player> getPlayers(Position position) {
        List<Player> players = new ArrayList<>(4);
        for(Player p : this.players) {
            if(p.getPosition().equals(position)) {
                players.add(p);
            }
        }
        return Collections.unmodifiableList(players);
    }

    /**
     * Adds a player to the team.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Sorts the player according their overall skill value.
     * <p>
     * The usual use is only after initialization.
     */
    public void sortPlayers() {
        Collections.sort(players);
    }

    /**
     * Lines up the given <tt>movedPlayer</tt> before or after (param <tt>after</tt>) the given <tt>targetPlayer</tt>.<br>
     * This is used to set up position lineups.
     */
    public void lineupPlayer(Player movedPlayer, Player targetPlayer, boolean after) {
        if(!movedPlayer.equals(targetPlayer)) {
            players.remove(movedPlayer);
            int idx = players.indexOf(targetPlayer);
            if(after) {
                idx++;
            }
            players.add(idx, movedPlayer);
        }
    }

    public Playbook getPlaybook() {
        return playbook;
    }

    public Formation getFormationOffTraining() {
        return formationOffTraining;
    }

    public void setFormationOffTraining(Formation formationOffTraining) {
        this.formationOffTraining = formationOffTraining;
    }

    public Formation getFormationDefTraining() {
        return formationDefTraining;
    }

    public void setFormationDefTraining(Formation formationDefTraining) {
        this.formationDefTraining = formationDefTraining;
    }

    public Play getPlayOffTraining() {
        return playOffTraining;
    }

    public void setPlayOffTraining(Play playOffTraining) {
        this.playOffTraining = playOffTraining;
    }

    public Play getPlayDefTraining() {
        return playDefTraining;
    }

    public void setPlayDefTraining(Play playDefTraining) {
        this.playDefTraining = playDefTraining;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }
}
