package ru.szhernovoy.model.dao.interfaces;

import ru.szhernovoy.model.Address;

import java.util.Collection;

/**
 * Created by dort on 25.12.16.
 */
public interface UserDAO {
    public int createUser();
    public Collection getAll();
    public Address findUser(int id);
    public boolean updateUser();
    public boolean deleteUser();
}
