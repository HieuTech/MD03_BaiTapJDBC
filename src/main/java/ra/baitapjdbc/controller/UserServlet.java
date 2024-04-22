package ra.baitapjdbc.controller;

import ra.baitapjdbc.model.User;
import ra.baitapjdbc.service.IUserService;
import ra.baitapjdbc.service.UserServiceIMPL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "UserServet", value = "/users")


public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService iUserService;

    public void init() {
        iUserService = new UserServiceIMPL();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(req, res);
                    break;
                case "edit":
                    showEditForm(req, res);
                    break;
                case "delete":
                    deleteUser(req,res);
                    break;
                case "view":
                    seeDetailUser(req,res);
                    break;
                case "sortByName":
                    sortByName(req,res);
                    break;
                default:
                    listUser(req, res);

                    break;

            }
        } catch (IOException | ServletException | SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");

        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(req, res);
                    break;

                case "edit":
                    updateUser(req, res);
                    break;
                default:

                    break;
            }
        } catch (IOException | ServletException | SQLException e) {
            e.printStackTrace();
        }
        searchByCountry(req,res);
    }

    private void sortByName(HttpServletRequest req, HttpServletResponse res) throws SQLException, ServletException, IOException {

        List<User> userList = this.iUserService.sortByName();
        req.setAttribute("userList",userList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/list.jsp");
        requestDispatcher.forward(req,res);
    }

    private void searchByCountry( HttpServletRequest req, HttpServletResponse res)   {
        String searchCountry = req.getParameter("search");
        try{

            List<User> userList = this.iUserService.searchByCountry(searchCountry);
            req.setAttribute("userList",userList);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/list.jsp");
            requestDispatcher.forward(req,res);
        }catch (SQLException | IOException |ServletException e){

        }

    }

    private void seeDetailUser(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, SQLException{
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("user",this.iUserService.selectUser(id));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/view.jsp");
        requestDispatcher.forward(req,res);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        this.iUserService.deleteUser(id);
        List<User> userList = iUserService.selectAllUsers();
        req.setAttribute("userList",userList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/list.jsp");
        requestDispatcher.forward(req,res);

    }

    private void insertUser(HttpServletRequest req, HttpServletResponse res)
            throws SQLException, IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User user = new User(name, email, country);
        iUserService.insertUser(user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/create.jsp");;
        requestDispatcher.forward(req, res);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User user = new User(id, name, email, country);
        this.iUserService.updateUser(user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/edit.jsp");
        requestDispatcher.forward(req, res);


    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = this.iUserService.selectUser(id);
        req.setAttribute("user",user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/edit.jsp");
        requestDispatcher.forward(req, res);

    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/create.jsp");
        requestDispatcher.forward(req, res);


    }


    private void listUser(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException,SQLException {
        List<User> userList = this.iUserService.selectAllUsers();

            req.setAttribute("userList", userList);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/user/list.jsp");
           requestDispatcher.forward(req,res);

    }


    public void destroy(){};

}

