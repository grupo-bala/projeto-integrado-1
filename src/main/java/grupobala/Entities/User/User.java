package grupobala.Entities.User;

import grupobala.Entities.User.IUser.IUser;

public class User implements IUser {

    private static boolean INSTANCIATED;
    private static String USERNAME;
    private static String NAME;
    private static double VALUE;
    private static int ID;

    public User(String username, String name, double value, int ID) {
        if (!User.INSTANCIATED) {
            User.USERNAME = username;
            User.NAME = name;
            User.VALUE = value;
            User.ID = ID;
        }
    }

    @Override
    public int getID() {
        return User.ID;
    }

    @Override
    public double getValue() {
        return User.VALUE;
    }

    @Override
    public String getName() {
        return User.NAME;
    }

    @Override
    public String getUsername() {
        return User.USERNAME;
    }

    @Override
    public void close() {
        User.INSTANCIATED = false;
    }
}