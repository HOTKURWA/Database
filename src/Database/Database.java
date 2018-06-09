package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
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
    String NameTable[], Namefolder = "Project", Nameline;

    public BasePanel() {
        LoginPanel();
    }

    @SuppressWarnings("empty-statement")

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
            } catch (SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            JTable MyOrderTable = new JTable(date, date[0]);
            MyOrderTable.setBounds(20, 100, 750, 400);
            add(MyOrderTable);
        });

    }

    private void Developer() {

        SomeButton();
        JButton ReWriteButton = new JButton("Просмотр БД");
        JButton SearchButton = new JButton("Поиск по БД");
        JButton DeliteButton = new JButton("Удаление из БД");

        ReWriteButton.setBounds(325, 110, 150, 30);
        SearchButton.setBounds(325, 160, 150, 30);
        DeliteButton.setBounds(325, 210, 150, 30);

        ReWriteButton.setBackground(Color.WHITE);
        SearchButton.setBackground(Color.WHITE);
        DeliteButton.setBackground(Color.WHITE);

        add(ReWriteButton);
        add(SearchButton);
        add(DeliteButton);

        ReWriteButton.addActionListener((ActionEvent e) -> {
            SomeButton();
            VoiceTable(1);
        });

        SearchButton.addActionListener((ActionEvent e) -> {
            SomeButton();
            VoiceTable(0);

        });
        DeliteButton.addActionListener((ActionEvent e) -> {
            SomeButton();
            VoiceTable(2);
        });

    }

    private void VoiceTable(int Search) {
        String[] items = {
            "1. Проект",
            "2. Работа",
            "3. Сдано",
            "4. Тип"};
        JComboBox comboBox = new JComboBox(items);
        comboBox.setBounds(40, 50, 200, 30);
        comboBox.setBackground(Color.WHITE);

        ActionListener actionListener = (ActionEvent e1) -> {
            switch (comboBox.getSelectedIndex()) {

                case 0: {
                    Namefolder = "Project";
                    NameTable = new String[]{
                        "Код проекта",
                        "Название проекта",
                        "Дата заказа проекта",
                        "Стоимость проекта",
                        "Трудоемкость проекта",
                        "Код заказчика",
                        "Код исполнитея"};
                    break;
                }
                case 1: {
                    Namefolder = "Job";
                    NameTable = new String[]{
                        "Код работы",
                        "Описание работы",
                        "Трудоемкость работы",
                        "Дата начала работы",
                        "Дата окончания работы",
                        "Код проекта",
                        "Код вида работы"};
                    break;
                }
                case 2: {
                    Namefolder = "Commissioned";
                    NameTable = new String[]{
                        "Код сдачи",
                        "Дата сдачи заказчику",
                        "Код работы",
                        "Оценка сдачи"};
                    break;
                }
                case 3: {
                    Namefolder = "Type of work";
                    NameTable = new String[]{
                        "Код вида работы",
                        "Номер этапа разаботки",
                        "Трудоемкость вида работы",
                        "Наименование вида работы",};
                    break;
                }
            }
            SomeButton();
            add(comboBox);
            if (Search == 0) {
                VoiceNameline();
                SearchBox();
            } else {
                WriteTable(null, Search);

            }
        };
        add(comboBox);
        comboBox.addActionListener(actionListener);
    }

    private void VoiceNameline() {

        JComboBox Column = new JComboBox(NameTable);
        Column.setBounds(280, 50, 200, 30);
        Column.setBackground(Color.WHITE);
        Nameline = NameTable[0];

        ActionListener actionListener = (ActionEvent e1) -> {
            switch (Column.getSelectedIndex()) {
                case 0: {
                    Nameline = NameTable[0];
                    break;
                }
                case 1: {
                    Nameline = NameTable[1];
                    break;
                }
                case 2: {
                    Nameline = NameTable[2];
                    break;
                }
                case 3: {
                    Nameline = NameTable[3];
                    break;
                }
                case 4: {
                    Nameline = NameTable[4];
                    break;
                }
                case 5: {
                    Nameline = NameTable[5];
                    break;
                }
                case 6: {
                    Nameline = NameTable[6];
                    break;
                }
                case 7: {
                    Nameline = NameTable[7];
                    break;
                }
            }
        };
        add(Column);
        Column.addActionListener(actionListener);
    }

    private void SearchBox() {

        JTextField SearchField = new JTextField(10);
        JButton SearchButton = new JButton("Искать");

        SearchField.setBounds(520, 50, 200, 30);
        SearchButton.setBounds(350, 100, 100, 20);

        SearchButton.setBackground(Color.WHITE);

        add(SearchField);
        add(SearchButton);

        SearchButton.addActionListener((ActionEvent e) -> {

            String SearchString = (SearchField.getText());
            SearchString = SearchString.trim();
            WriteTable(SearchString, 0);
        });

    }

    private void WriteTable(String SearchString, int Delite) {
        try {
            int n = 1;
            int Stringlength = NameTable.length;
            String data[][] = new String[30][Stringlength];
            Connection con = DriverManager.getConnection(url, login, password);

            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();

            ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"" + Namefolder + "\"");

            while (Custrs.next()) {
                if (SearchString != null) {
                    String strSeach = Custrs.getString(Nameline);
                    if (strSeach.equals(SearchString)) {
                        for (int i = 0; i < Stringlength; i++) {
                            data[n][i] = Custrs.getString(NameTable[i]);
                        }
                        n++;
                    }
                } else {
                    for (int i = 0; i < Stringlength; i++) {
                        data[n][i] = Custrs.getString(NameTable[i]);
                    }
                    n++;
                }
                for (int i = 0; i < Stringlength; i++) {
                    data[0][i] = NameTable[i];
                }
            }

            JTable SearchTable = new JTable(data, data[0]);
            if (SearchString != null) {
                SearchTable.setBounds(20, 150, 750, 350);
            } else {
                if (Delite == 2) {
                    String items[] = new String[n];
               
                    for (int i = 1; i <= n; i++) {
                        items[i-1] = data[i][0];
                    }
                    JComboBox comboBox = new JComboBox(items);
                    JButton DeliteButton = new JButton("Удалить");

                    comboBox.setBounds(550, 100, 200, 30);
                    DeliteButton.setBounds(600, 150, 100, 20);

                    comboBox.setBackground(Color.WHITE);
                    DeliteButton.setBackground(Color.WHITE);

                    add(comboBox);
                    add(DeliteButton);

                    DeliteButton.addActionListener((ActionEvent e) -> {
                        try {
                            ResultSet CustrsD = stmt2.executeQuery("DELETE FROM \"public\".\"" + Namefolder + "\" WHERE (\"" + data[0][0] + "\") = '" + data[comboBox.getSelectedIndex()+1][0] + "' ");
                        } catch (SQLException ex) {
                            Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                }
                SearchTable.setBounds(20, 100, 525, 430);
            }

            SearchTable.setPreferredScrollableViewportSize(SearchTable.getPreferredSize());
            add(SearchTable);

            repaint();

        } catch (SQLException ex) {
            Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
