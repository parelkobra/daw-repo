import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.ioc.daw.db.DBConnection;
import org.ioc.daw.user.User;
import org.ioc.daw.user.UserDAO;

public class Main {
    private static final String CREATE_USER_FAILED = "User creation failed";
    private static final String GET_EMAIL = "Introduce email: ";
    private static final String GET_NAME = "Introduce name: ";
    private static final String GET_PASSWORD = "Introduce password: ";
    private static final String GET_USERNAME = "Introduce username: ";
    private static final String ACTIVE_USERS = "Active users:";
    private static final String CONFIRM_DELETE = "Do you want to delete this user? Type \"delete\" to confirm: ";
    private static final String EMAIL_ON_USE = "That email is already on use";
    private static final String EXIT_MSG = "Bye!";
    private static final String USER_DELETED = "User deleted";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String USERNAME_ON_USE = "That username is already on use";
    private static final String WRONG_OPTION = "Wrong option, try again";
    private static boolean exit = false;

    public static void main(String[] args) throws Exception {
	String connectionProperties = "db.properties";
	DBConnection dBConnection = new DBConnection(connectionProperties);
	UserDAO userDAO = new UserDAO(dBConnection);

	Scanner scanner = new Scanner(System.in);
	User user = null;
	int action, option = 0;

	while (!exit) {
	    action = getOption(getMenu());
	    System.out.println();
	    switch (action) {
	    case 1:
		System.out.println(ACTIVE_USERS);
		getActiveUsers(userDAO);
		break;
	    case 2:
		user = findUser(option, scanner, userDAO);
		if (user != null) {
		    System.out.println(user);
		}
		break;
	    case 3:
		createUser(userDAO, scanner);
		break;
	    case 4:
		user = findUser(option, scanner, userDAO);
		if (user != null) {
		    deleteUser(scanner, user, userDAO);
		}
		break;
	    case 5:
		exit(scanner);
		break;
	    default:
		System.out.println(WRONG_OPTION);
		break;
	    }
	}

	dBConnection.getConnection().close();
    }

    private static void getActiveUsers(UserDAO userDAO) throws SQLException {
	List<User> activeUsers = userDAO.findActiveUsers();
	for (User user : activeUsers) {
	    System.out.println(user);
	}
	System.out.println();
    }

    private static User findUser(int option, Scanner sc, UserDAO userDAO) throws Exception {
	User user = null;
	option = getOption(getFindUserMenu());
	if (option == 1) {
	    user = findUserByUsername(userDAO, getUsername(sc));
	} else if (option == 2) {
	    user = findUserByEmail(userDAO, getEmail(sc));
	} else {
	    System.out.println(WRONG_OPTION);
	}
	return user;
    }

    private static User findUserByUsername(UserDAO userDAO, String username) throws Exception {
	User user = userDAO.findUserByUsername(username);
	if (user == null) {
	    System.out.println(USER_NOT_FOUND);
	}
	return user;
    }

    private static User findUserByEmail(UserDAO userDAO, String email) throws Exception {
	User user = userDAO.findUserByEmail(email);
	if (user == null) {
	    System.out.println(USER_NOT_FOUND);
	}
	return user;
    }

    private static void createUser(UserDAO userDAO, Scanner scanner) throws Exception {
	Map<String, String> userInfo = getNewUserInfo(scanner, userDAO);
	if (userInfo != null) {
	    User user = userDAO.createUser(userInfo.get("username"), userInfo.get("password"), userInfo.get("name"),
		    userInfo.get("email"));
	    if (user != null) {
		System.out.println(System.lineSeparator() + "User " + user + " created");
	    } else {
		System.out.println(CREATE_USER_FAILED);
	    }
	}
    }

    private static void deleteUser(Scanner sc, User user, UserDAO userDAO) throws Exception {
	System.out.println(user);
	System.out.print(System.lineSeparator() + CONFIRM_DELETE);
	String confirmation = sc.next();
	if ("delete".equalsIgnoreCase(confirmation)) {
	    userDAO.deleteUser(user);
	    System.out.println(USER_DELETED);
	}
    }

    private static void exit(Scanner sc) throws SQLException {
	exit = true;
	sc.close();
	System.out.println(EXIT_MSG);
    }

    private static Map<String, String> getNewUserInfo(Scanner scanner, UserDAO userDAO) throws Exception {
	Map<String, String> userInfo = new HashMap<>();
	String username = getUsername(scanner);
	if (userDAO.findUserByUsername(username) != null) {
	    System.out.println(USERNAME_ON_USE + System.lineSeparator());
	    return null;
	}
	String email = getEmail(scanner);
	if (userDAO.findUserByEmail(email) != null) {
	    System.out.println(EMAIL_ON_USE + System.lineSeparator());
	    return null;
	}
	userInfo.put("username", username);
	userInfo.put("email", email);
	userInfo.put("password", getPassword(scanner));
	userInfo.put("name", getName(scanner));

	return userInfo;
    }

    private static String getUsername(Scanner sc) {
	System.out.print(GET_USERNAME);
	return sc.next();
    }

    private static String getEmail(Scanner sc) {
	System.out.print(GET_EMAIL);
	return sc.next();
    }

    private static String getPassword(Scanner sc) {
	System.out.print(GET_PASSWORD);
	return sc.next();
    }

    private static String getName(Scanner sc) {
	System.out.print(GET_NAME);
	return sc.next();
    }

    private static int readInt() {
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(System.in);
	int integer = sc.nextInt();
	return integer;
    }

    private static int getOption(String[] menu) {
	boolean exit = false;
	int option = 0;
	System.out.print(menu[0]);
	while (!exit) {
	    try {
		System.out.print(menu[1]);
		option = readInt();
		exit = true;
	    } catch (InputMismatchException e) {
		option = 0;
		System.out.println(WRONG_OPTION);
	    }
	}
	return option;
    }

    private static String[] getMenu() {
	String[] menu = new String[2];
	menu[0] = "";
	menu[0] += "Select one of the following options:" + System.lineSeparator();
	menu[0] += " [1] List active users" + System.lineSeparator();
	menu[0] += " [2] Find user" + System.lineSeparator();
	menu[0] += " [3] Create new user" + System.lineSeparator();
	menu[0] += " [4] Delete user" + System.lineSeparator();
	menu[0] += " [5] Exit" + System.lineSeparator();
	menu[1] = "";
	menu[1] += System.lineSeparator() + "Choose an option (1, 2, 3, 4, 5): ";
	return menu;
    }

    private static String[] getFindUserMenu() {
	String[] menu = new String[2];
	menu[0] = "";
	menu[0] += "[1] Find user by username" + System.lineSeparator();
	menu[0] += "[2] Find user by email" + System.lineSeparator();
	menu[1] = "";
	menu[1] += System.lineSeparator() + "Choose an option (1, 2): ";
	return menu;
    }

}