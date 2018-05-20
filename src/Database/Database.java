package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.LineBorder;

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

    String NameTable[];

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
        JButton ReWriteButton = new JButton("Просмотр БД");
        JButton SearchButton = new JButton("Поиск по БД");

        ReWriteButton.setBounds(325, 110, 150, 30);
        SearchButton.setBounds(325, 160, 150, 30);

        ReWriteButton.setBackground(Color.WHITE);
        SearchButton.setBackground(Color.WHITE);

        add(ReWriteButton);
        add(SearchButton);

        ReWriteButton.addActionListener((ActionEvent e) -> {

            SomeButton();
            String[] items = {
                "1. Проект",
                "2. Работа",
                "3. Сдано",
                "4. Тип"
            };
            JComboBox comboBox = new JComboBox(items);
            comboBox.setBounds(20, 50, 200, 30);
            comboBox.setBackground(Color.WHITE);

            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int TimeInt, i;

                    switch (comboBox.getSelectedIndex()) {

                        case 0: {
                            try {
                                String data[][] = new String[30][7];
                                i = 1;
                                Connection con = DriverManager.getConnection(url, login, password);
                                Statement stmt1 = con.createStatement();
                                ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"Project\"");

                                while (Custrs.next()) {

                                    TimeInt = Custrs.getInt("Код проекта");
                                    data[i][0] = String.valueOf(TimeInt);
                                    data[i][1] = Custrs.getString("Название проекта");
                                    data[i][2] = Custrs.getString("Дата заказа проекта");
                                    data[i][3] = Custrs.getString("Стоимость проекта");
                                    data[i][4] = Custrs.getString("Трудоемкость проекта");
                                    TimeInt = Custrs.getInt("Код заказчика");
                                    data[i][5] = String.valueOf(TimeInt);
                                    TimeInt = Custrs.getInt("Код исполнитея");
                                    data[i][6] = String.valueOf(TimeInt);
                                    i++;

                                }

                                data[0][0] = "Код проекта";
                                data[0][1] = "Название проекта";
                                data[0][2] = "Дата заказа проекта";
                                data[0][3] = "Стоимость проекта";
                                data[0][4] = "Трудоемкость проекта";
                                data[0][5] = "Код заказчика";
                                data[0][6] = "Код исполнитея";

                                JTable MyOrderTable = new JTable(data, data[0]);
                                MyOrderTable.setBounds(20, 100, 750, 432);

                                SomeButton();
                                add(comboBox);
                                add(MyOrderTable);
                                con.close();
                                break;
                            } catch (SQLException ex) {
                                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        case 1: {

                            try {
                                String data[][] = new String[30][7];
                                i = 1;
                                Connection con = DriverManager.getConnection(url, login, password);
                                Statement stmt1 = con.createStatement();
                                ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"Job\"");

                                while (Custrs.next()) {

                                    TimeInt = Custrs.getInt("Код работы");
                                    data[i][0] = String.valueOf(TimeInt);
                                    data[i][1] = Custrs.getString("Описание работы");
                                    data[i][2] = Custrs.getString("Трудоемкость работы");
                                    data[i][3] = Custrs.getString("Дата начала работы");
                                    data[i][4] = Custrs.getString("Дата окончания работы");
                                    TimeInt = Custrs.getInt("Код проекта");
                                    data[i][5] = String.valueOf(TimeInt);
                                    TimeInt = Custrs.getInt("Код вида работы");
                                    data[i][6] = String.valueOf(TimeInt);
                                    i++;

                                }

                                data[0][0] = "Код работы";
                                data[0][1] = "Описание работы";
                                data[0][2] = "Трудоемкость работы";
                                data[0][3] = "Дата начала работы";
                                data[0][4] = "Трудоемкость проекта";
                                data[0][5] = "Код проекта";
                                data[0][6] = "Код вида работы";

                                JTable MyOrderTable = new JTable(data, data[0]);
                                MyOrderTable.setBounds(20, 100, 750, 432);

                                SomeButton();
                                add(comboBox);
                                add(MyOrderTable);
                                con.close();
                                break;
                            } catch (SQLException ex) {
                                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        case 2: {

                            try {
                                String data[][] = new String[30][4];
                                i = 1;
                                Connection con = DriverManager.getConnection(url, login, password);
                                Statement stmt1 = con.createStatement();
                                ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"Commissioned\"");

                                while (Custrs.next()) {

                                    TimeInt = Custrs.getInt("Оценка сдачи");
                                    data[i][0] = String.valueOf(TimeInt);
                                    data[i][1] = Custrs.getString("Дата сдачи заказчику");
                                    TimeInt = Custrs.getInt("Код работы");
                                    data[i][2] = String.valueOf(TimeInt);
                                    TimeInt = Custrs.getInt("Код сдачи");
                                    data[i][3] = String.valueOf(TimeInt);
                                    i++;
                                }

                                data[0][0] = "Оценка сдачи";
                                data[0][1] = "Дата сдачи заказчику";
                                data[0][2] = "Код работы";
                                data[0][3] = "Код сдачи";

                                JTable MyOrderTable = new JTable(data, data[0]);
                                MyOrderTable.setBounds(20, 100, 750, 400);

                                SomeButton();
                                add(comboBox);
                                add(MyOrderTable);
                                con.close();
                                break;
                            } catch (SQLException ex) {
                                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        case 3: {
                            try {
                                String data[][] = new String[30][6];
                                i = 1;
                                Connection con = DriverManager.getConnection(url, login, password);
                                Statement stmt1 = con.createStatement();
                                Statement stmt2 = con.createStatement();
                                ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"Type of work\"");
                                ResultSet Custrs2 = stmt2.executeQuery("SELECT * FROM \"public\".\"Stage of development\"");
                                while (Custrs2.next()) {
                                    data[i][2] = Custrs2.getString("Наименование этапа");
                                    i++;
                                }
                                i = 1;
                                while (Custrs.next()) {

                                    TimeInt = Custrs.getInt("Код вида работы");
                                    data[i][0] = String.valueOf(TimeInt);
                                    data[i][1] = Custrs.getString("Наименование вида работы");
                                    TimeInt = Custrs.getInt("Номер этапа разаботки");
                                    data[i][3] = String.valueOf(TimeInt);
                                    TimeInt = Custrs.getInt("Трудоемкость вида работы");
                                    data[i][4] = String.valueOf(TimeInt);
                                    data[i][5] = Custrs.getString("Наименование вида работы");
                                    i++;

                                }

                                data[0][0] = "Код вида работы";
                                data[0][1] = "Наименование этапа";
                                data[0][2] = "Наименование вида работы";
                                data[0][3] = "Номер этапа разработки";
                                data[0][4] = "Трудоемкость вида работы";
                                data[0][5] = "Наименование вида работы";

                                JTable MyOrderTable = new JTable(data, data[0]);
                                MyOrderTable.setBounds(20, 100, 750, 400);

                                SomeButton();
                                add(comboBox);
                                add(MyOrderTable);
                                con.close();
                                break;
                            } catch (SQLException ex) {
                                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        default: {
                            validate();
                            break;
                        }

                    }
                    validate();

                    repaint();
                }

            };
            add(comboBox);
            comboBox.addActionListener(actionListener);
        });

        SearchButton.addActionListener((ActionEvent e) -> {
            SomeButton();
            String[] items = {
                "1. Проект",
                "2. Работа",
                "3. Сдано",
                "4. Тип"};
            JComboBox comboBox = new JComboBox(items);
            comboBox.setBounds(20, 50, 200, 30);
            comboBox.setBackground(Color.WHITE);
            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (comboBox.getSelectedIndex()) {

                        case 0: {
                            NameTable = new String[]{
                                "Project",
                                "Код работы",
                                "Описание работы",
                                "Трудоемкость работы",
                                "Дата начала работы",
                                "Дата окончания работы",
                                "Код проекта",
                                "Код вида работы"};
                            SomeButton();
                            add(comboBox);

                            JComboBox Column = new JComboBox(NameTable);
                            Column.setBounds(20, 100, 200, 30);
                            Column.setBackground(Color.WHITE);
                            add(Column);

                            SearchBox();
                            break;
                        }
                        case 1: {
                            NameTable = new String[]{
                                "Job",
                                "Оценка сдачи",
                                "Дата сдачи заказчику",
                                "Код работы",
                                "Код сдачи"};
                            SomeButton();
                            JComboBox Column = new JComboBox(NameTable);
                            Column.setBounds(20, 100, 200, 30);
                            Column.setBackground(Color.WHITE);
                            add(Column);
                            add(comboBox);
                            SearchBox();
                            break;
                        }
                        case 2: {
                            NameTable = new String[]{
                                "Commissioned",
                                "Дата сдачи заказчику",
                                "Код работы",
                                "Код сдачи",
                                "Стоимость проекта",
                                "Трудоемкость проекта",
                                "Код заказчика",
                                "Код исполнитея"};
                            SomeButton();
                            JComboBox Column = new JComboBox(NameTable);
                            Column.setBounds(20, 100, 200, 30);
                            Column.setBackground(Color.WHITE);
                            add(Column);
                            add(comboBox);
                            SearchBox();
                            break;
                        }
                        case 3: {
                            NameTable = new String[]{
                                "Type of work",
                                "Код вида работы",
                                "Номер этапа разаботки",
                                "Трудоемкость вида работы",
                                "Наименование вида работы",};
                            SomeButton();
                            JComboBox Column = new JComboBox(NameTable);
                            Column.setBounds(20, 100, 200, 30);
                            Column.setBackground(Color.WHITE);
                            add(Column);
                            add(comboBox);
                            SearchBox();
                            break;
                        }
                    }
                }
            };

            add(comboBox);
            comboBox.addActionListener(actionListener);
        });

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
                stmt1.close();
                con.close();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(BasePanel.class
                    .getName()).log(Level.SEVERE, null, ex);
            }

            JLabel textOrdering = new JLabel("Оформление заказа № " + (CodeTypeWork + 1));
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
                    CodeTypeWork++;

                    String SrtingFieldOrdering;
                    SrtingFieldOrdering = TextFieldOrdering.getText();

                    Connection con = DriverManager.getConnection(url, login, password);
                    String query1 = "INSERT INTO \"Job\"(\"Код работы\", \"Описание работы\", \"Код проекта\",\"Трудоемкость работы\", \"Код вида работы\") VALUES(?, ?, ?, 1, 5)";
                    String query2 = "INSERT INTO \"Project\"(\"Код проекта\", \"Код заказчика\",\"Название проекта\",\"Стоимость проекта\",\"Код исполнитея\",\"Трудоемкость проекта\") VALUES(?, ?, ?, 1000, 1, 1)";
                    String query3 = "INSERT INTO \"Type of work\"(\"Номер этапа разаботки\") VALUES(1)";

                    PreparedStatement pst1 = con.prepareStatement(query1);
                    PreparedStatement pst2 = con.prepareStatement(query2);
                    PreparedStatement pst3 = con.prepareStatement(query3);

                    pst1.setInt(1, CodeTypeWork);
                    pst1.setString(2, SrtingFieldOrdering);

                    pst2.setInt(1, CodeTypeWork);
                    pst2.setInt(2, UserID);
                    pst2.setString(3, SrtingFieldOrdering);
                    pst2.executeUpdate();

                    pst1.setInt(3, CodeTypeWork);
                    pst1.executeUpdate();

                    con.close();

                    Customer();

                } catch (SQLException ex) {
                    Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });

        MyOrderButton.addActionListener((ActionEvent e) -> {
            SomeButton();
            int i = 0;
            String[][] date = new String[20][5];
            Integer ToDevelop[] = new Integer[100];
            try {
                int IdCustomer;
                Connection con = DriverManager.getConnection(url, login, password);
                Statement stmt1 = con.createStatement();
                Statement stmt2 = con.createStatement();
                Statement stmt3 = con.createStatement();
                Statement stmt4 = con.createStatement();

                date[0][0] = "Код";
                date[0][1] = "Название";
                date[0][2] = "Дата заказа";
                date[0][3] = "Стоимость";
                date[0][4] = "Этап";

                ResultSet Custrs_Project = stmt1.executeQuery("SELECT * FROM \"public\".\"Project\"");
                ResultSet Custrs_Job = stmt2.executeQuery("SELECT * FROM \"public\".\"Job\"");
                ResultSet Custrs_TypeOfWork = stmt3.executeQuery("SELECT * FROM \"public\".\"Type of work\"");
                ResultSet Custrs_StageOfDevelopment = stmt4.executeQuery("SELECT * FROM \"public\".\"Stage of development\"");

                while (Custrs_Project.next()) {
                    IdCustomer = Custrs_Project.getInt("Код заказчика");
                    if (IdCustomer == UserID) {
                        i++;
                        ToDevelop[0] = Custrs_Project.getInt("Код проекта");
                        date[i][0] = String.valueOf(ToDevelop[0]);
                        date[i][1] = Custrs_Project.getString("Название проекта");
                        date[i][2] = Custrs_Project.getString("Дата заказа проекта");
                        date[i][3] = Custrs_Project.getString("Стоимость проекта");

                        while (Custrs_Job.next()) {

                            ToDevelop[1] = Custrs_Job.getInt("Код проекта");

                            if (Objects.equals(ToDevelop[0], ToDevelop[1])) {

                                ToDevelop[2] = Custrs_Job.getInt("Код вида работы");
                                while (Custrs_TypeOfWork.next()) {

                                    ToDevelop[3] = Custrs_TypeOfWork.getInt("Код вида работы");
                                    if (Objects.equals(ToDevelop[3], ToDevelop[2])) {

                                        ToDevelop[4] = Custrs_TypeOfWork.getInt("Номер этапа разаботки");

                                        while (Custrs_StageOfDevelopment.next()) {

                                            ToDevelop[5] = Custrs_StageOfDevelopment.getInt("Номер этапа разработки");
                                            if (Objects.equals(ToDevelop[5], ToDevelop[4])) {

                                                date[i][4] = Custrs_StageOfDevelopment.getString("Наименование этапа");

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException ex) {Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);}
            
            JTable MyOrderTable = new JTable(date, date[0]);
            MyOrderTable.setBounds(20, 100, 750, 400);
            add(MyOrderTable);
        });

    }

    private void SearchBox() {
    removeAll();
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

        NameUser.setBounds(10, 530, 100, 30);
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
