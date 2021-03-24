// В этом классе (и еще MyServer и ClientHandler) решение п.1 и п.4 ДЗ 6 урока

package ru.GeekBrains.Rostislav.Java3Group3.lesson2.ServerSide;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class BaseAuthService implements AuthService{
    private static final Logger logger = LogManager.getLogger(BaseAuthService.class);
    public static Connection con;
    private PreparedStatement preparedStatement;

    @Override
    public void start() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java3lesson2", "root", "root");    // подключение к MySQL
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ошибка подключения к БД.");
            //System.out.println("Ошибка подключения к БД.");
        }
        logger.info("Сервис аутентификации запущен");
        //System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        try {
            con.close();                // отключение от БД
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия подключения к БД.");
        }
        logger.info("Сервис аутентификации остановлен");
        //System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public String getNickByLoginPass (String login1, String pass) {             // реализация через запрос в БД
        try {
            preparedStatement = con.prepareStatement("SELECT * FROM members where login = ?");
            preparedStatement.setString(1, login1);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            if ((rs.getString("password")).equals(pass)) {
                return rs.getString("nick");
            } else return null;
        } catch (SQLException e) {
            System.out.println("Ошибка запроса в БД.");
            return null;
        }
    }
}