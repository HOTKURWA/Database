package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    public static void main(String[] args) {
        ButtonFrame frame = new ButtonFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class ButtonFrame extends JFrame {

    public ButtonFrame() {
        setSize(800, 600);
        setTitle("Database");
        BasePanel panel = new BasePanel();
        panel.setLayout(null);
        add(panel);
    }
}

class BasePanel extends JPanel {

    private boolean user;

    String UserName, url = "jdbc:postgresql://localhost:5432/DataBase", login = "postgres", password = "14589";

    int CodeTypeWork, UserID;

    public BasePanel() {
        LoginPanel();
    }

    private void LoginPanel() {

        JTextField LoginField = new JTextField(10);
        JPasswordField PasswordField = new JPasswordField(10);
        JButton LoginButton = new JButton("Войти");
        LoginButton.setBackground(Color.WHITE);

        LoginField.setBounds(325, 110, 150, 30);
        PasswordField.setBounds(325, 150, 150, 30);
        LoginButton.setBounds(350, 200, 100, 30);

        add(LoginField);
        add(PasswordField);
        add(LoginButton);

        LoginButton.addActionListener((ActionEvent e) -> {
            try {
                String StringLogin = (LoginField.getText());
                String StringPassword = (PasswordField.getText());

                Class.forName("org.postgresql.Driver");
                try (Connection con = DriverManager.getConnection(url, login, password)) {
                    Statement stmt1 = con.createStatement();
                    Statement stmt2 = con.createStatement();

                    ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"Customer\"");
                    ResultSet Execurs = stmt2.executeQuery("SELECT * FROM \"public\".\"Executor\"");

                    while (Custrs.next()) {

                        String strLogin = Custrs.getString("Логин заказчика");
                        String strPassword = Custrs.getString("Пароль заказчика");

                        if (StringLogin.equals(strLogin) & StringPassword.equals(strPassword)) {
                            UserName = Custrs.getString("Ф.И.О. заказчика");
                            UserID = Custrs.getInt("Код заказчика");
                            user = true;
                            Developer();
                        }
                    }

                    stmt1.close();
                    Custrs.close();

                    while (Execurs.next()) {

                        String strLogin = Execurs.getString("Логин исполнителя");
                        String strPassword = Execurs.getString("Пароль исполнителя");

                        if (StringLogin.equals(strLogin) & StringPassword.equals(strPassword)) {
                            UserName = Execurs.getString("Ф.И.О. Исполнителя");
                            UserID = Execurs.getInt("Код исполнителя");
                            user = false;
                            Customer();
                        }
                    }

                    stmt2.close();
                    Execurs.close();
                    con.close();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void Developer() {
        SomeButton();
        System.out.println("Hi Developer");
    }

    @SuppressWarnings("empty-statement")
    private void Customer() {
        SomeButton();

        JButton AddOrderButton = new JButton("Сделать заказ");
        JButton MyOrderButton = new JButton("Мои заказы");

        AddOrderButton.setBounds(325, 110, 150, 30);
        MyOrderButton.setBounds(325, 160, 150, 30);

        AddOrderButton.setBackground(Color.WHITE);
        MyOrderButton.setBackground(Color.WHITE);

        add(AddOrderButton);
        add(MyOrderButton);

        AddOrderButton.addActionListener((ActionEvent e) -> {

            SomeButton();
            try {
                int max = 0;
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection(url, login, password);
                Statement stmt1 = con.createStatement();
                ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"Job\"");
                while (Custrs.next()) {
                    max = Custrs.getInt("Код работы");
                    if (max > CodeTypeWork) {
                        CodeTypeWork = max;
                    }
                }
                CodeTypeWork++;
                stmt1.close();
                con.close();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            JLabel textOrdering = new JLabel("Оформление заказа № " + CodeTypeWork);
            JLabel textdescription = new JLabel("Напишите, какую программу вы хотите:");
            JTextArea TextFieldOrdering = new JTextArea();
            JButton SendButton = new JButton("Отправить");

            textOrdering.setFont(new Font("Serif", Font.BOLD, 20));
            TextFieldOrdering.setLineWrap(true);
            TextFieldOrdering.setWrapStyleWord(true);
            TextFieldOrdering.setFont(new Font("Serif", Font.ITALIC, 16));
            SendButton.setBackground(Color.WHITE);

            textOrdering.setBounds(320, 25, 300, 30);
            textdescription.setBounds(10, 70, 300, 30);
            TextFieldOrdering.setBounds(10, 100, 760, 400);
            SendButton.setBounds(320, 520, 100, 30);

            add(textOrdering);
            add(textdescription);
            add(TextFieldOrdering);
            add(SendButton);

            repaint();

            SendButton.addActionListener((ActionEvent e1) -> {
                try {
                    String SrtingFieldOrdering;
                    SrtingFieldOrdering = TextFieldOrdering.getText();
                    java.util.Date dt = new java.util.Date();

                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Connection con = DriverManager.getConnection(url, login, password);
                    String query1 = "INSERT INTO \"Job\"(\"Код работы\", \"Описание\",  \"Трудоемкость работы\") VALUES(?, ?, ?)";
                    String query2 = "INSERT INTO \"Project\"(\"Код проекта\", \"Код заказчика\",\"Название проекта\") VALUES(?, ?, ?)";
                    PreparedStatement pst1 = con.prepareStatement(query1);
                    PreparedStatement pst2 = con.prepareStatement(query2);

                    pst1.setInt(1, CodeTypeWork);
                    pst1.setString(2, SrtingFieldOrdering);
                    pst1.setInt(3, 1);
                    pst1.executeUpdate();

                    pst2.setInt(1, CodeTypeWork);
                    pst2.setInt(2, UserID);
                    pst2.setString(3, SrtingFieldOrdering);
                    pst2.executeUpdate();

                    con.close();

                    Customer();
                } catch (SQLException ex) {
                    Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });

        MyOrderButton.addActionListener((ActionEvent e) -> {
            SomeButton();

            String[] columnNames = {
                "Код проекта",
                "Название проекта",
                "Дата заказа",
                "Стоимость проекта",
                "Этап разработки"
            };

            String[][] data = new String[21][5];
            data[0][0] = "Код проекта";
            data[0][1] = "Название проекта";
            data[0][2] = "Дата заказа";
            data[0][3] = "Стоимость проекта";
            data[0][4] = "Этап разработки";

            for (int i = 1; i < 21; i++) {
                data[i][0] = " " + i;
                data[i][1] = "Lol" + i;
                data[i][2] = "sshhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" + i;
                data[i][3] = "" + 6 + i;
                data[i][4] = "dds" + i;
                //Данные из бд
            };
            JTable MyOrderTable = new JTable(data, columnNames);
            //JScrollPane scrollPane = new JScrollPane(MyOrderTable);

            MyOrderTable.setBounds(200, 100, 375, 338);
            MyOrderTable.setBackground(Color.WHITE);

            add(MyOrderTable);
        });

    }

    private void SomeButton() {
        removeAll();
        repaint();

        JLabel NameUser = new JLabel(UserName); //данные с бд      
        JButton UnLoginButton = new JButton("Выйти");
        JButton HomeButton = new JButton("Главная");

        NameUser.setFont(new Font("Serif", Font.ITALIC, 15));
        UnLoginButton.setBackground(Color.WHITE);
        HomeButton.setBackground(Color.WHITE);

        NameUser.setBounds(10, 520, 100, 30);
        UnLoginButton.setBounds(690, 5, 80, 30);
        HomeButton.setBounds(10, 5, 90, 30);

        add(NameUser);
        add(UnLoginButton);
        add(HomeButton);

        UnLoginButton.addActionListener((ActionEvent e) -> {
            removeAll();
            repaint();
            LoginPanel();
        });

        HomeButton.addActionListener((ActionEvent e) -> {
            removeAll();
            repaint();
            if (user) {
                Developer();
            } else {
                Customer();
            }
        });
    }
}
