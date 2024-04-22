package ra.baitapjdbc.service;

import ra.baitapjdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    public void insertUser(User user) throws SQLException;

    public User selectUser(int id) throws SQLException;

    public List<User> selectAllUsers() throws SQLException;

    public boolean deleteUser(int id) throws SQLException;
    public List<User> searchByCountry(String country) throws SQLException;
    public List<User> sortByName() throws SQLException;

    public boolean updateUser(User user) throws SQLException;
}
