package de.weise.fm.web.model.play;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Holds {@link Formation Formations} and {@link Play Plays} and provides methods to manage them.
 * <p>
 * Managing Formations and Plays must not be done directly, but by using methods provided in this class:
 *
 * <pre>
 * remove..()
 * add..()
 * save..()
 * change..Name()
 * </pre>
 *
 * Do not modify lists and maps get by calling {@link #getOffenseFormations()}, {@link #getDefenseFormations()},
 * {@link #getPlays()} or {@link #getPlays(Formation)}. Those methods return unmodifiable collections anyway.
 */
public class Playbook {

    private static final Logger log = Logger.getLogger(Playbook.class);

    private List<Formation> formations = new ArrayList<>();
    private Map<Formation, List<Play>> plays = new HashMap<>();

    /**
     * Constructor adds default formations and plays.
     */
    public Playbook() {
        try {
            initializeFormations();
            initializePlays();
        } catch(InstantiationException | IllegalAccessException e) {
            log.error("error during initializing formations", e);
        } catch(Exception e) {
            log.error("error during initializing plays", e);
        }
    }

    private void initializeFormations()
            throws InstantiationException, IllegalAccessException {
        // add offense formations
        for(Class<?> formationClass : OffenseFormation.class.getClasses()) {
            Formation formation = (Formation)formationClass.newInstance();
            addFormation(formation);
        }

        // add defense formations
        for(Class<?> formationClass : DefenseFormation.class.getClasses()) {
            Formation formation = (Formation)formationClass.newInstance();
            addFormation(formation);
        }
    }

    private void initializePlays()
            throws NullPointerException {
        // add offense plays
        Formation formationIform = getFormation(OffenseFormation.I_Form.class);
        addPlay(new DefaultPlays.IForm_QuickSlants(formationIform));
        addPlay(new DefaultPlays.IForm_PaPowerO(formationIform));
        addPlay(new DefaultPlays.IForm_PowerO(formationIform));
        Formation formationStrong = getFormation(OffenseFormation.Strong.class);
        addPlay(new DefaultPlays.Strong_PaGiantDig(formationStrong));
        addPlay(new DefaultPlays.Strong_PaGiantSmash(formationStrong));
        addPlay(new DefaultPlays.Strong_HbBlast(formationStrong));
        Formation formationWeak = getFormation(OffenseFormation.Weak.class);
        addPlay(new DefaultPlays.Weak_PaBootSlide(formationWeak));
        addPlay(new DefaultPlays.Weak_PaGiantsYCross(formationWeak));
        addPlay(new DefaultPlays.Weak_HbGut(formationWeak));
        Formation formationGoalLine = getFormation(OffenseFormation.GoalLine.class);
        addPlay(new DefaultPlays.GoalLine_HbDive(formationGoalLine));
        addPlay(new DefaultPlays.GoalLine_FbDive(formationGoalLine));

        // add defense plays
        Formation formation43 = getFormation(DefenseFormation.Def_4_3.class);
        addPlay(new DefaultPlays.Def_2ManUnder(formation43));
        addPlay(new DefaultPlays.Def_Cover1(formation43));
        addPlay(new DefaultPlays.Def_Cover2(formation43));
        addPlay(new DefaultPlays.Def_Cover3(formation43));
        addPlay(new DefaultPlays.Def_EngageEight(formation43));
        addPlay(new DefaultPlays.Def_OlbFireMan(formation43));
        addPlay(new DefaultPlays.Def_FreeFire(formation43));
        addPlay(new DefaultPlays.Def_ThunderSmoke(formation43));
        addPlay(new DefaultPlays.Def_SafetyBlitz(formation43));

        // add default offense plays
        // TODO remove when every formation has real plays
        for(Formation formation : formations) {
            if(formation.isOffenseFormation() && !plays.containsKey(formation)) {
                plays.put(formation, new ArrayList<Play>(Arrays.asList(
                        new DefaultPlays.PassDefault(formation),
                        new DefaultPlays.RunDefault(formation)
                )));
            } else
            if(!formation.isOffenseFormation() && !plays.containsKey(formation)) {
                plays.put(formation, new ArrayList<Play>(Arrays.asList(
                        new DefaultPlays.DefensePlay("Man", formation)
                )));
            }
        }
    }

    /**
     * Gets an unmodifiable list of offense formations.
     */
    public List<Formation> getOffenseFormations() {
        return getFormations(true);
    }

    /**
     * Gets an unmodifiable list of defense formations.
     */
    public List<Formation> getDefenseFormations() {
        return getFormations(false);
    }

    /**
     * Gets an unmodifiable sorted list of all offense or defense formations.
     *
     * @param offense <tt>true</tt> if getting offense formations, otherwise <tt>false</tt>.
     * @return Unmodifiable sorted list of formations.
     */
    private List<Formation> getFormations(boolean offense) {
        List<Formation> list = new ArrayList<>();
        for(Formation formation : formations) {
            if((formation.isOffenseFormation() && offense)
                    || (!formation.isOffenseFormation() && !offense)) {
                list.add(formation);
            }
        }
        Collections.sort(list);
        return Collections.unmodifiableList(list);
    }

    /**
     * Gets the formation with the specified class. Just used for initialization to save a few characters of code.
     */
    private Formation getFormation(Class<? extends Formation> clazz)
            throws NullPointerException {
        for(Formation formation : formations) {
            if(formation.getClass().equals(clazz)) {
                return formation;
            }
        }
        throw new NullPointerException("formation not found with class " + clazz);
    }

    /**
     * Gets the formation with the specified name. Returns <tt>null</tt> if it does not exist.
     */
    public Formation getFormation(String name) {
        for(Formation formation : formations) {
            if(formation.getName().equals(name)) {
                return formation;
            }
        }
        return null;
    }

    /**
     * Removes the given formation and all its plays from the playbook. Note that all formations will be removed which
     * have the same name as the given formation.
     */
    public void removeFormation(Formation formation) {
        formations.remove(formation);
        plays.remove(formation);
    }

    /**
     * Adds the given formation to the playbook.
     */
    public void addFormation(Formation formation) {
        formations.add(formation);
    }

    /**
     * Saves the given formation.<br>
     * This will remove the formation which has the same name as the given one and adds the given formation to the
     * playbook.
     * <p>
     * All plays which correspond to the given formation will be updated when saving. This means that all actions of
     * every changed position will be removed. A changed position is a {@link PositionWrapper} with a new
     * {@link Position} and/or a new {@link FieldPosition}.
     */
    public void saveFormation(Formation formation) {
        // TODO check this
        if(getFormation(formation.getName()) == null) { // new formation
            addFormation(formation);
            return;
        }

        // 1) remove actions in plays from changed positions
        Set<PositionWrapper> keepPw = new HashSet<>(11); // positions to keep

        Formation oldFormation = getFormation(formation.getName()); // get the corresponding old formation
        for(PositionWrapper pw : formation.getPositions()) { // iterate over new positions
            for(PositionWrapper oldPw : oldFormation.getPositions()) { // iterate over old positions
                if(pw.equals(oldPw)) { // if new position equals old position (not changed)...
                    keepPw.add(pw); // ... keep it
                    break;
                }
            }
        }

        List<PositionWrapper> oldPositions =
                new ArrayList<>(oldFormation.getPositions()); // get all positions in old formation
        oldPositions.removeAll(keepPw); // remove kept positions from all old positions

        for(Play play : getPlays(formation)) { // iterate over plays for the formation
            for(PositionWrapper pw : oldPositions) { // iterate over old positions which should not be kept
                play.removeAction(pw); // remove action for the old position (which was changed)
            }
            play.setFormation(formation); // set new formation to play
        }

        // 2) finally save formation
        oldFormation = null;
        removeFormation(formation);
        addFormation(formation);
    }

    /**
     * Changes the name of the given formation to the specified <tt>newName</tt>.
     * <p>
     * Note to save a formation <b>before</b> changing its name! Otherwise the corresponding plays cannot be updated.
     *
     * @return Whether the name was changed.
     */
    public boolean changeFormationName(Formation formation, String newName) {
        if(!formation.getName().equals(newName)) {
            removeFormation(formation);
            List<Play> playList = plays.remove(formation);

            formation.setName(newName);
            addFormation(formation);
            if(playList != null) {
                plays.put(formation, playList);
            }

            return true;
        }
        return false;
    }

    /**
     * Gets an unmodifiable map of all formations with their assigned plays.
     */
    public Map<Formation, List<Play>> getPlays() {
        return Collections.unmodifiableMap(plays);
    }

    /**
     * Gets an unmodifiable list of all plays according to the given formation.
     */
    public List<Play> getPlays(Formation formation) {
        if(plays.containsKey(formation)) {
            return Collections.unmodifiableList(plays.get(formation));
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Adds the given play to the playbook.
     */
    public void addPlay(Play play) {
        List<Play> plays = this.plays.get(play.getFormation());
        if(plays == null) {
            plays = new ArrayList<>(1);
        }
        plays.add(play);
        this.plays.put(play.getFormation(), plays);
        Collections.sort(plays);
    }

    /**
     * Saves the given play.<br>
     * This will remove the play which has the same name as the given one and adds the given play to the playbook.
     */
    public void savePlay(Play play) {
        removePlay(play);
        addPlay(play);
    }

    /**
     * Removes the given play from the playbook. Note that all plays will be removed which have the same name as the
     * given play.
     */
    public void removePlay(Play play) {
        plays.get(play.getFormation()).remove(play);
    }

    /**
     * Changes the name of the given play to the specified <tt>newName</tt>.
     *
     * @return Whether the name was changed.
     */
    public boolean changePlayName(Play play, String newName) {
        if(!play.getName().equals(newName)) {
            removePlay(play);
            play.setName(newName);
            addPlay(play);

            return true;
        }
        return false;
    }
}
