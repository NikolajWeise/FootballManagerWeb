package de.weise.fm.web.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vaadin.server.VaadinSession;

import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Position;

/**
 * Provides static methods for initializing and accessing the application's datamodel.
 * <p>
 * No database (neither an SQL database nor an in-memory database) is used. The datamodel is simply holt in a static
 * map.
 */
public class Users {

    public static final String ATT_USER = "attUser";

    private static Map<String, User> users = new HashMap<>();
    private static List<User> unusedUsers; // synchronized

    private static boolean initialized = false;

    /**
     * Initializes the web application.<br>
     * In this case some {@link User Users} are created and set to our "database".
     *
     * @see #initDefaultUsers()
     */
    public static synchronized void initDefault() {
        if(initialized) {
            return;
        }

//        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

        initDefaultUsers();

        // fill list
        unusedUsers = Collections.synchronizedList(new ArrayList<User>(users.size()));
        unusedUsers.addAll(users.values());

        initialized = true;
    }

    /**
     * Initializes the default users for the web application.
     */
    private static void initDefaultUsers() {
        createUser("User1");
//        createUser("User2");
    }

    /**
     * Creates a {@link User} with the given name.<br>
     * The created user will get all attributes it needs, as a team, a playbook, and so on.
     */
    private static User createUser(String userName) {
        User user = new User(userName);
        users.put(userName, user);
        Team team = createTeam(user.getName() + "-Team");
        user.setTeam(team);
        return user;
    }

    /**
     * Creates a {@link Team} with the given name and its {@link Player Players}.
     */
    private static Team createTeam(String teamName) {
        Team team = new Team(teamName);

        // set default formation and play training
        Formation formationOff = team.getPlaybook().getFormation("I-Form");
        team.setFormationOffTraining(formationOff);
        team.setPlayOffTraining(team.getPlaybook().getPlays(formationOff).get(0));
        Formation formationDef = team.getPlaybook().getFormation("4-3");
        team.setFormationDefTraining(formationDef);
        team.setPlayDefTraining(team.getPlaybook().getPlays(formationDef).get(0));

        // create players
        Position position = Position.QB;
        for(int i = 0; i < 22; i++) { // offense
            if(i < 2) position = Position.QB;
            else if(i < 4) position = Position.HB;
            else if(i < 5) position = Position.FB;
            else if(i < 7) position = Position.C;
            else if(i < 9) position = Position.LG;
            else if(i < 11) position = Position.RG;
            else if(i < 13) position = Position.LT;
            else if(i < 15) position = Position.RT;
            else if(i < 18) position = Position.TE;
            else if(i < 22) position = Position.WR;
            Player player = createPlayer(position, team.getPlayers());
            team.addPlayer(player);
        }
        for(int i = 0; i < 19; i++) { // defense
            if(i < 3) position = Position.DT;
            else if(i < 6) position = Position.DE;
            else if(i < 9) position = Position.CB;
            else if(i < 11) position = Position.MLB;
            else if(i < 13) position = Position.RLB;
            else if(i < 15) position = Position.LLB;
            else if(i < 17) position = Position.SS;
            else if(i < 19) position = Position.FS;
            Player player = createPlayer(position, team.getPlayers());
            team.addPlayer(player);
        }
        for(int i = 0; i < 2; i++) { // special team
            if(i < 1) position = Position.P;
            else if(i < 2) position = Position.K;
            Player player = createPlayer(position, team.getPlayers());
            team.addPlayer(player);
        }
        team.sortPlayers();
        return team;
    }

    /**
     * Creates a player with the given {@link Position}. The player's attributes are set by using the
     * {@link RandomPlayerGenerator}.
     *
     * @param position
     * @param players Added players to compare player numbers.
     */
    private static Player createPlayer(Position position, List<Player> players) {
        Player player = new Player(RandomPlayerGenerator.getForename(), RandomPlayerGenerator.getSurname());
        player.setAge(RandomPlayerGenerator.getAge());
        player.setAttributes(RandomPlayerGenerator.getAttributes(position));
        player.setPosition(position);

        List<String> usedNumbers = new ArrayList<>(players.size());
        for(Player p : players) {
            usedNumbers.add(p.getNumber());
        }

        do { // do not use the same number more than once
            player.setNumber(RandomPlayerGenerator.getNumber(position));
        } while(usedNumbers.contains(player.getNumber()));

        return player;
    }

    /**
     * This is our "database". :-)
     * <p>
     * <b>DO NOT set users to sessions by calling this method!</b>
     *
     * @return unmodifiable map of users
     * @deprecated There's no need to get all users.
     */
    @Deprecated
    public static Map<String, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }

    /**
     * Gets an unused {@link User}. If all users are in use, a new one is created and put in our "database".
     */
    private static User getUnusedUser() {
        synchronized(unusedUsers) {
            User user;

            Iterator<User> iterator = unusedUsers.iterator();
            if(iterator.hasNext()) { // do we have a free user?
                user = iterator.next(); // get it
                unusedUsers.remove(user);
            } else {
                String userName = "User" + users.size() + 1;
                user = createUser(userName); // create a new user
                users.put(userName, user); // and put it in our "database"
            }
            return user;
        }
    }

    /**
     * Gets a free {@link User} (in other words: a user which is not set to another session) and sets it to the given
     * {@link VaadinSession session}. The returned user is set as used and cannot be used by another session until it is
     * freed by calling {@link #freeUserFromSession(VaadinSession)}.<br>
     * If the <tt>name</tt> parameter is given, the user with the specified name will be get. If it doesn't exist, the
     * first free user is returned.
     *
     * @param name The name of a specified user or <tt>null</tt> for a random one.
     * @param session The VaadinSession.
     * @return The user with the specified name or another free user.
     */
    public static User getUser(String name, VaadinSession session) {
        User user = null;

        if(name == null || !users.containsKey(name)) { // random access
            user = getUnusedUser();
        } else {
            User requestedUser = users.get(name); // get requested user

            synchronized(unusedUsers) {
                if(unusedUsers.contains(requestedUser)) { // is the requested user not in use?
                    unusedUsers.remove(requestedUser); // mark as used
                    user = requestedUser; // get it
                } else {
                    user = getUnusedUser(); // otherwise get a random user
                }
            }
        }

        session.setAttribute(ATT_USER, user); // set user to session
        return user;
    }

    /**
     * Frees the given {@link User} from the given {@link VaadinSession session} and sets the user as unused, which
     * means another session can get it.
     */
    public static void freeUserFromSession(VaadinSession session) {
        User user = getUserFromSession(session);
        session.setAttribute(ATT_USER, null);

        synchronized(unusedUsers) {
            unusedUsers.add(user);
        }
    }

    /**
     * Gets the {@link User} set in the given {@link VaadinSession session}.
     */
    public static User getUserFromSession(VaadinSession session) {
        return (User)session.getAttribute(ATT_USER);
    }
}
