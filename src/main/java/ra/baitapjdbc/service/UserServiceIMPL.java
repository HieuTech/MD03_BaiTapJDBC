package ra.baitapjdbc.service;

import ra.baitapjdbc.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceIMPL implements IUserService{

    private String jdbcURL = "jdbc:mysql://localhost:3306/baitapJDBC_DB";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Minhhieutcb_1998";

    private static final String SELECT_USER_BY_ID = "select id,name,email, country from users where id = ?;";
    private static final String INSERT_USERS_SQL = "insert into users (name, email, country) values (?,?,?);";
    private static final  String SELECT_ALL_USER = "select * from users;";
    private static final String DELETE_USER_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USER_SQL = "update users set name = ?, email = ?, country = ? where id = ?;";

    private static final String FIND_USER_BY_COUNTRY = "select * from users where country LIKE ?;";
    private static final String SORT_USER_BY_NAME = "select * from users order by name";


    public UserServiceIMPL(){};

    protected Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }


    @Override
    public void insertUser(User user) {
        System.out.println(INSERT_USERS_SQL);
        //Lợi của của việc viết trong try with resource sẽ tự động đóng các tài nguyên thì kết thúc khối try, dù có xảy ra ngoại lệ ko
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getEmail());
        preparedStatement.setString(3, user.getCountry());
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User selectUser(int id) throws SQLException {
        User user = null;
        //establishing a connection
        try(Connection connection = getConnection();
            //Create statement,
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID) ){

            //execute query or update query
            preparedStatement.setInt(1,id);
            System.out.println(preparedStatement);

            //Process the resultset
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){

                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                user = new User(id,name,email,country);
            }

        }

        return user;
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        //tao connection
        try(Connection connection = getConnection() ;
            //tao statement
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER)
        ){
            //execute query
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                userList.add(new User(id,name,email,country));
            }
        }
        return userList;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException{
        boolean rowDelete = false;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL) ){
            preparedStatement.setInt(1, id);
            rowDelete = preparedStatement.executeUpdate() > 0;
        }
        return rowDelete;

    }

    @Override
    public List<User> searchByCountry(String country)  {
        List<User> userList = new ArrayList<>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_COUNTRY)){
            preparedStatement.setString(1,country);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country1 = resultSet.getString("country");
                User user = new User(name,email,country1);
                userList.add(user);
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return userList;
    }

    @Override
    public List<User> sortByName() throws SQLException {
        List<User> userList = new ArrayList<>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SORT_USER_BY_NAME)
        ){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                User user = new User(name,email,country);
                userList.add(user);
            }
        }

        return userList;
    }

    @Override
    public boolean updateUser(User user)  throws SQLException{
        boolean rowUpdate = false;

        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)
        ){
            preparedStatement.setInt(4,user.getId());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            rowUpdate = preparedStatement.executeUpdate() >0;

        }
        return rowUpdate;
    }

    private void printSQLException(SQLException ex){
        for (Throwable e : ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println(("Error Code: " + ((SQLException) e).getErrorCode() ));
                System.out.println("Message" + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null){
                    System.out.println("Cause" + t);
                    t = t.getCause();
                }
            }
        }
    }
}
