package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
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

    int CodeTypeWork, UserID, TimeInt = 0;
    String NameTable[], Namefolder = "Project", Nameline, ProjectCodeTime;

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
        JButton ChangeButton = new JButton("Редактирование проектов");
        JButton InquiryButton = new JButton("Запросы");

        ReWriteButton.setBounds(285, 110, 200, 30);
        SearchButton.setBounds(285, 150, 200, 30);
        DeliteButton.setBounds(285, 190, 200, 30);
        ChangeButton.setBounds(285, 230, 200, 30);
        InquiryButton.setBounds(285, 270, 200, 30);

        ReWriteButton.setBackground(Color.WHITE);
        SearchButton.setBackground(Color.WHITE);
        DeliteButton.setBackground(Color.WHITE);
        ChangeButton.setBackground(Color.WHITE);
        InquiryButton.setBackground(Color.WHITE);

        add(ReWriteButton);
        add(SearchButton);
        add(DeliteButton);
        add(ChangeButton);
        add(InquiryButton);

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
        ChangeButton.addActionListener((ActionEvent e) -> {
            SomeButton();
            Change();
        });
        InquiryButton.addActionListener((ActionEvent e) -> {
            SomeButton();
            Inquiry();
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
                SearchTable.setBounds(100, 125, 525, 400);
            } else {
                if (Delite == 2) {
                    String items[] = new String[n];

                    for (int i = 1; i <= n; i++) {
                        items[i - 1] = data[i][0];
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
                            ResultSet CustrsD = stmt2.executeQuery("DELETE FROM \"public\".\"" + Namefolder + "\" WHERE (\"" + data[0][0] + "\") = '" + data[comboBox.getSelectedIndex() + 1][0] + "' ");
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

    private void Change() {
        try {
            SomeButton();
            Connection con = DriverManager.getConnection(url, login, password);

            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();

            ResultSet Custrs_Project = stmt1.executeQuery("SELECT * FROM \"public\".\"Project\"");
            ResultSet Custrs_Job = stmt2.executeQuery("SELECT * FROM \"public\".\"Job\"");

            JLabel ProjectLabel = new JLabel("Проект:");
            JTextField ProjectField = new JTextField(100);
            JButton SearchProjectButton = new JButton("Искать");
            JLabel NameLabel = new JLabel("Название:");
            JTextField NameField = new JTextField(100);
            JLabel DateLabel = new JLabel("Дата заказа:");
            JTextField DateField = new JTextField(100);
            JLabel CashLabel = new JLabel("Стоимость:");
            JTextField CashField = new JTextField(100);
            JLabel HardLabel = new JLabel("Трудоемкость:");
            JTextField HardField = new JTextField(100);
            JLabel CodeLabel = new JLabel("Код исполнитея:");
            JTextField CodeField = new JTextField(100);
            JLabel DescriptionLabel = new JLabel("Описание работы:");
            JTextField DescriptionField = new JTextField(100);
            JLabel StartDateLabel = new JLabel("Дата начала работы:");
            JTextField StartDateField = new JTextField(100);
            JLabel EndDateLabel = new JLabel("Дата окончания работы:");
            JTextField EndDateField = new JTextField(100);
            JLabel CodeTOWLabel = new JLabel("Код вида работы:");
            JTextField CodeTOWField = new JTextField(100);
            //UtilDateModel model = new UtilDateModel();

            JLabel Error = new JLabel("Введите число!");

            ProjectField.setBounds(20, 75, 100, 30);
            ProjectLabel.setBounds(20, 50, 100, 30);
            SearchProjectButton.setBounds(150, 75, 100, 30);
            NameLabel.setBounds(20, 100, 150, 30);
            NameField.setBounds(20, 125, 230, 30);
            DateLabel.setBounds(20, 150, 150, 30);
            DateField.setBounds(20, 175, 150, 30);
            CashLabel.setBounds(20, 200, 150, 30);
            CashField.setBounds(20, 225, 150, 30);
            HardLabel.setBounds(20, 250, 150, 30);
            HardField.setBounds(20, 275, 150, 30);
            CodeLabel.setBounds(20, 300, 150, 30);
            CodeField.setBounds(20, 325, 150, 30);
            DescriptionLabel.setBounds(20, 350, 230, 30);
            DescriptionField.setBounds(20, 375, 150, 30);
            StartDateLabel.setBounds(300, 100, 150, 30);
            StartDateField.setBounds(300, 125, 150, 30);
            EndDateLabel.setBounds(300, 150, 150, 30);
            EndDateField.setBounds(300, 175, 150, 30);
            CodeTOWLabel.setBounds(300, 200, 150, 30);
            CodeTOWField.setBounds(300, 225, 100, 30);

            SearchProjectButton.setBackground(Color.WHITE);

            add(ProjectField);
            add(ProjectLabel);
            add(SearchProjectButton);
            add(Error);

            Error.setVisible(false);

            SearchProjectButton.addActionListener((ActionEvent e) -> {

                if (ProjectField.getText().matches("-?\\d+(\\.\\d+)?")) {
                    try {
                        Error.setVisible(false);
                        NameLabel.setVisible(true);
                        NameField.setVisible(true);
                        DateField.setVisible(true);
                        DateLabel.setVisible(true);
                        CashField.setVisible(true);
                        CashLabel.setVisible(true);
                        HardField.setVisible(true);
                        HardLabel.setVisible(true);
                        CodeField.setVisible(true);
                        CodeLabel.setVisible(true);
                        DescriptionLabel.setVisible(true);
                        DescriptionField.setVisible(true);
                        StartDateLabel.setVisible(true);
                        StartDateField.setVisible(true);
                        EndDateLabel.setVisible(true);
                        EndDateField.setVisible(true);
                        CodeTOWLabel.setVisible(true);
                        CodeTOWField.setVisible(true);

                        add(NameField);
                        add(NameLabel);
                        add(DateField);
                        add(DateLabel);
                        add(CashField);
                        add(CashLabel);
                        add(HardField);
                        add(HardLabel);
                        add(CodeField);
                        add(CodeLabel);
                        add(DescriptionLabel);
                        add(DescriptionField);
                        add(StartDateLabel);
                        add(StartDateField);
                        add(EndDateLabel);
                        add(EndDateField);
                        add(CodeTOWLabel);
                        add(CodeTOWField);

                        repaint();

                        while (Custrs_Project.next()) {
                            String ProjectCode = Custrs_Project.getString("Код проекта");
                            if (ProjectField.getText().equals(ProjectCode)) {
                                NameField.setText(Custrs_Project.getString("Название проекта"));
                                DateField.setText(Custrs_Project.getString("Дата заказа проекта"));
                                CashField.setText(Custrs_Project.getString("Стоимость проекта"));
                                HardField.setText(Custrs_Project.getString("Трудоемкость проекта"));
                                CodeField.setText(Custrs_Project.getString("Код исполнитея"));
                                ProjectCodeTime = ProjectCode;
                            }
                        }
                        while (Custrs_Job.next()) {
                            String ProjectCode = Custrs_Job.getString("Код проекта");
                            if (ProjectCode.equals(ProjectCodeTime)) {
                                DescriptionField.setText(Custrs_Job.getString("Описание работы"));
                                StartDateField.setText(Custrs_Job.getString("Дата начала работы"));
                                EndDateField.setText(Custrs_Job.getString("Дата окончания работы"));
                                CodeTOWField.setText(Custrs_Job.getString("Код вида работы"));
                            }
                        }

                        JButton ChangeButton = new JButton("Редактировать");
                        ChangeButton.setBounds(600, 525, 150, 30);
                        ChangeButton.setBackground(Color.WHITE);
                        add(ChangeButton);

                        ChangeButton.addActionListener((ActionEvent el) -> {
//                            try {
////                                PreparedStatement pst1 = con.prepareStatement("UPDATE \"Project\" SET (\"Название проекта\" = ? , \"Стоимость проекта\"= ?, \"Трудоемкость проекта\"= ?, \"Код исполнитея\"= ?)  WHERE \"Код проекта\" = '" + ProjectCodeTime + "'");
////                                
////                                pst1.setString(1, NameField.getText());
////                                pst1.setInt(2, Integer.parseInt(CashField.getText()));
////                                pst1.setInt(3, Integer.parseInt(HardField.getText()));
////                                pst1.setInt(4, Integer.parseInt(CodeField.getText()));
////                                pst1.executeUpdate();
//                                
//                            } catch (SQLException ex) {
//                                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                        });

                    } catch (SQLException ex) {
                        Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Error.setVisible(true);
                    NameLabel.setVisible(false);
                    NameField.setVisible(false);
                    DateField.setVisible(false);
                    DateLabel.setVisible(false);
                    CashField.setVisible(false);
                    CashLabel.setVisible(false);
                    HardField.setVisible(false);
                    HardLabel.setVisible(false);
                    CodeField.setVisible(false);
                    CodeLabel.setVisible(false);
                    DescriptionLabel.setVisible(false);
                    DescriptionField.setVisible(false);
                    StartDateLabel.setVisible(false);
                    StartDateField.setVisible(false);
                    EndDateLabel.setVisible(false);
                    EndDateField.setVisible(false);
                    CodeTOWLabel.setVisible(false);
                    CodeTOWField.setVisible(false);
                    repaint();

                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Inquiry() {

        JButton RunButton1 = new JButton("Выполнить");
        JButton RunButton2 = new JButton("Выполнить");
        JButton RunButton3 = new JButton("Выполнить");
        JButton RunButton4 = new JButton("Выполнить");
        JButton RunButton5 = new JButton("Выполнить");

        JLabel RunLabel1 = new JLabel("Выдать клиентов, с кодом больше                и телефоном, начинающимся на");
        JLabel RunLabel2 = new JLabel("Выдать всех заказчиков с фамилией заканчивающийся на         , сгруппировать и отсортировать их");
        JLabel RunLabel3 = new JLabel("Вывести ФИО заказчика, заканчивающийся на         , чтобы в названии его проекта было             ,");
        JLabel RunLabel31 = new JLabel("стоимость проекта превышала                 и трудоемкость проекта была равна");
        JLabel RunLabel4 = new JLabel("Вывести все описания работы, даты начала и окончания работы, ФИО заказчика и ФИО исполнителя");
        JLabel RunLabel5 = new JLabel("Вывести максимальную, минимальную и среднее значение суммы оплаты, которые имеют форму оплаты");

        JTextField RunField1 = new JTextField("891");
        JTextField RunField12 = new JTextField("3");

        JTextField RunField2 = new JTextField("ов");

        JTextField RunField3 = new JTextField("ов");
        JTextField RunField32 = new JTextField("ПО");
        JTextField RunField33 = new JTextField("10000");
        String[] FOrCB3 = new String[]{"1", "2", "3"};
        JComboBox RunCB3 = new JComboBox(FOrCB3);
        String[] FOrCB5 = new String[]{"PayPal", "СбербанкОнлайн"};
        JComboBox RunCB5 = new JComboBox(FOrCB5);

        RunButton1.setBounds(10, 155, 100, 20);
        RunButton2.setBounds(10, 205, 100, 20);
        RunButton3.setBounds(10, 255, 100, 20);
        RunButton4.setBounds(10, 305, 100, 20);
        RunButton5.setBounds(10, 355, 100, 20);

        RunLabel1.setBounds(130, 150, 500, 30);
        RunLabel2.setBounds(130, 200, 600, 30);
        RunLabel3.setBounds(130, 240, 600, 30);
        RunLabel31.setBounds(130, 260, 600, 30);
        RunLabel4.setBounds(130, 300, 600, 30);
        RunLabel5.setBounds(130, 350, 700, 30);

        RunField1.setBounds(580, 155, 35, 20);
        RunField12.setBounds(340, 155, 35, 20);

        RunField2.setBounds(480, 205, 30, 20);

        RunField3.setBounds(410, 245, 20, 20);
        RunField32.setBounds(650, 245, 30, 20);
        RunField33.setBounds(315, 265, 40, 20);
        RunCB3.setBounds(580, 265, 40, 20);

        RunCB5.setBounds(620, 380, 120, 20);

        RunButton1.setBackground(Color.WHITE);
        RunButton2.setBackground(Color.WHITE);
        RunButton3.setBackground(Color.WHITE);
        RunButton4.setBackground(Color.WHITE);
        RunButton5.setBackground(Color.WHITE);
        RunCB5.setBackground(Color.WHITE);
        RunCB3.setBackground(Color.WHITE);

        add(RunButton1);
        add(RunButton2);
        add(RunButton3);
        add(RunButton4);
        add(RunButton5);

        add(RunLabel1);
        add(RunLabel2);
        add(RunLabel3);
        add(RunLabel31);
        add(RunLabel4);
        add(RunLabel5);

        add(RunField1);
        add(RunField12);

        add(RunField2);

        add(RunField3);
        add(RunField32);
        add(RunField33);
        add(RunCB3);

        add(RunCB5);

        RunButton1.addActionListener((ActionEvent e) -> {
            try {
                TimeInt = 0;
                Connection con = DriverManager.getConnection(url, login, password);
                int PhoneN = Integer.parseInt(RunField1.getText());
                int Code = Integer.parseInt(RunField12.getText());
                String data[][] = new String[100][2];
                PreparedStatement statement = con.prepareStatement("select * from \"public\".\"Customer\" where \"Код заказчика\" > " + Code + " AND \"Телефон\" LIKE '" + PhoneN + "%'");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    data[TimeInt][0] = resultSet.getString("Ф.И.О. заказчика");
                    data[TimeInt][1] = resultSet.getString("Телефон");
                    TimeInt++;
                }

                SomeButton();
                String[] cho = new String[]{"", ""};
                JTable MyOrderTable = new JTable(data, cho);
                JLabel RunLabelOP = new JLabel("Выдать клиентов, с кодом больше  " + Code + "  и телефоном, начинающимся на  " + PhoneN);
                RunLabelOP.setBounds(50, 50, 800, 30);
                add(RunLabelOP);
                MyOrderTable.setBounds(50, 100, 150, 200);
                add(MyOrderTable);

            } catch (SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        RunButton2.addActionListener((ActionEvent e) -> {
            try {
                TimeInt = 0;
                Connection con = DriverManager.getConnection(url, login, password);
                String okon = RunField2.getText();
                String data[][] = new String[100][1];
                PreparedStatement statement = con.prepareStatement("select * from \"public\".\"Customer\" WHERE \"Ф.И.О. заказчика\" LIKE '%" + okon + "%' ORDER BY \"Ф.И.О. заказчика\"");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    data[TimeInt][0] = resultSet.getString("Ф.И.О. заказчика");
                    TimeInt++;
                }

                SomeButton();
                JLabel RunLabelOP = new JLabel("Выдать всех заказчиков с фамилией заканчивающийся на \"" + okon + "\", сгруппировать и отсортировать их");
                RunLabelOP.setBounds(50, 50, 800, 30);
                add(RunLabelOP);
                String[] cho = new String[]{""};
                JTable MyOrderTable = new JTable(data, cho);
                MyOrderTable.setBounds(50, 100, 75, 400);
                add(MyOrderTable);

            } catch (SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        RunButton3.addActionListener((ActionEvent e) -> {
            try {
                TimeInt = 0;
                Connection con = DriverManager.getConnection(url, login, password);
                String okon = RunField3.getText();
                String Slovo = RunField32.getText();
                String Stoimost = RunField33.getText();
                int Trudoyom = RunCB3.getSelectedIndex() + 1;

                String data[][] = new String[100][4];
                PreparedStatement statement = con.prepareStatement("SELECT f.\"Ф.И.О. заказчика\", p.\"Название проекта\", p.\"Стоимость проекта\", p.\"Трудоемкость проекта\" FROM \"Customer\" f LEFT OUTER JOIN \"Project\" p ON f.\"Код заказчика\" = p.\"Код заказчика\" WHERE f.\"Ф.И.О. заказчика\" LIKE '%" + okon + "%' AND p.\"Название проекта\" LIKE '%" + Slovo + "%' AND p.\"Стоимость проекта\" >" + Stoimost + " AND p.\"Трудоемкость проекта\" = " + Trudoyom + "");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    data[TimeInt][0] = resultSet.getString("Ф.И.О. заказчика");
                    data[TimeInt][1] = resultSet.getString("Название проекта");
                    data[TimeInt][2] = resultSet.getString("Стоимость проекта");
                    data[TimeInt][3] = resultSet.getString("Трудоемкость проекта");
                    TimeInt++;
                }

                SomeButton();
                JLabel RunLabelOP = new JLabel("Вывести ФИО заказчика, заканчивающийся на\"" + okon + "\" , чтобы в названии его проекта было   " + Slovo);
                JLabel RunLabelOP2 = new JLabel("стоимость проекта превышала " + Stoimost + " и трудоемкость проекта была равна  " + (Trudoyom));

                RunLabelOP.setBounds(50, 50, 800, 30);
                RunLabelOP2.setBounds(50, 65, 800, 30);

                add(RunLabelOP);
                add(RunLabelOP2);
                String[] cho = new String[]{"", "", "", "", ""};
                JTable MyOrderTable = new JTable(data, cho);
                MyOrderTable.setBounds(50, 100, 300, 400);
                add(MyOrderTable);

            } catch (SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        RunButton4.addActionListener((ActionEvent e) -> {
            try {
                TimeInt = 0;
                Connection con = DriverManager.getConnection(url, login, password);

                String data[][] = new String[100][6];
                PreparedStatement statement = con.prepareStatement("SELECT j.\"Описание работы\", j.\"Дата начала работы\", j.\"Дата окончания работы\",t.\"Наименование вида работы\",	c.\"Ф.И.О. заказчика\",e.\"Ф.И.О. Исполнителя\"FROM \"Job\" j LEFT OUTER JOIN \"Type of work\" t ON j.\"Код вида работы\" = t.\"Код вида работы\" LEFT OUTER JOIN \"Project\" p ON j.\"Код проекта\" = p.\"Код проекта\" LEFT OUTER JOIN \"Customer\" c ON p.\"Код заказчика\" = c.\"Код заказчика\" LEFT OUTER JOIN \"Executor\" e ON p.\"Код исполнитея\" = e.\"Код исполнителя\"");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    data[TimeInt][0] = resultSet.getString("Описание работы");
                    data[TimeInt][1] = resultSet.getString("Дата начала работы");
                    data[TimeInt][2] = resultSet.getString("Дата окончания работы");
                    data[TimeInt][3] = resultSet.getString("Наименование вида работы");
                    data[TimeInt][4] = resultSet.getString("Ф.И.О. заказчика");
                    data[TimeInt][5] = resultSet.getString("Ф.И.О. Исполнителя");
                    TimeInt++;
                }

                SomeButton();

                JLabel RunLabelOP = new JLabel("Вывести все описания работы, даты начала и окончания работы, ФИО заказчика и ФИО исполнителя");
                RunLabelOP.setBounds(50, 50, 800, 30);
                add(RunLabelOP);

                String[] cho = new String[]{"", "", "", "", "", ""};
                JTable MyOrderTable = new JTable(data, cho);
                MyOrderTable.setBounds(50, 100, 450, 400);
                add(MyOrderTable);

            } catch (SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        RunButton5.addActionListener((ActionEvent e) -> {
            try {
                TimeInt = 0;
                Connection con = DriverManager.getConnection(url, login, password);
                String PayRoad = null;
                switch (RunCB3.getSelectedIndex()) {
                    case 0:
                        PayRoad = "PayPal";
                        break;
                    case 1:
                        PayRoad = "СбербанкОнлайн";
                        break;
                }
                String data[][] = new String[1][3];
                PreparedStatement statement = con.prepareStatement("SELECT MIN(f.\"Сумма оплаты\") AS \"MinPrise\", \n"
                    + "MAX(f.\"Сумма оплаты\") AS \"MaxPrise\", \n"
                    + "AVG(f.\"Сумма оплаты\") AS \"AVG prise\" \n"
                    + "FROM \"Payment\" f\n"
                    + "GROUP BY f.\"Форма оплаты\"\n"
                    + "HAVING f.\"Форма оплаты\" LIKE '%" + PayRoad + "%'");

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    data[TimeInt][0] = resultSet.getString("MinPrise");
                    data[TimeInt][1] = resultSet.getString("MaxPrise");
                    data[TimeInt][2] = resultSet.getString("AVG prise");
                    TimeInt++;
                }

                SomeButton();

                JLabel RunLabelOP = new JLabel("Вывести максимальную, минимальную и среднее значение суммы оплаты, которые имеют форму оплаты " + PayRoad);
                RunLabelOP.setBounds(50, 50, 800, 30);
                add(RunLabelOP);

                String[] cho = new String[]{"", "", ""};
                JTable MyOrderTable = new JTable(data, cho);
                MyOrderTable.setBounds(50, 100, 225, 17);
                add(MyOrderTable);

            } catch (SQLException ex) {
                Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
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
