package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database 
{
    public static void main(String []args)
    {
        ButtonFrame frame= new ButtonFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class ButtonFrame extends JFrame
{
    public ButtonFrame()
    {
        setSize(800,600);
        setTitle("Database");
        BasePanel panel = new BasePanel();
        panel.setLayout(null);
        add(panel);
    }
}

class BasePanel extends JPanel
{
    private boolean user;
    private String UserName;
    public BasePanel()
    { 
        LoginPanel();
    }
    
    private void LoginPanel()
    {
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
        
        LoginButton.addActionListener(new java.awt.event.ActionListener() { 
            @SuppressWarnings("empty-statement")
            public void actionPerformed(ActionEvent e) 
            { 
                        try { 
                            String StringLogin = (LoginField.getText());
                            String StringPassword = (PasswordField.getText());
                            
                            
                            Class.forName("org.postgresql.Driver");
                            String url = "jdbc:postgresql://localhost:5432/DataBase";
                            String login = "postgres";
                            String password = "14589";
                            Connection con = DriverManager.getConnection(url, login, password);
                            try {
                                Statement stmt1 = con.createStatement();
                                Statement stmt2 = con.createStatement();
                                
                                ResultSet Custrs = stmt1.executeQuery("SELECT * FROM \"public\".\"Customer\"");
                                ResultSet Execurs = stmt2.executeQuery("SELECT * FROM \"public\".\"Executor\"");
                                
                                while (Custrs.next()) {
                                    
                                    String strLogin = Custrs.getString("Логин заказчика");
                                    String strPassword = Custrs.getString("Пароль заказчика");
                                                                        
                                    if(StringLogin.equals(strLogin)&StringPassword.equals(strPassword))
                                    {
                                        UserName = Custrs.getString("Ф.И.О. заказчика");
                                        user=true;
                                        Developer();
                                    } 
                                }
                                
                                stmt1.close();
                                Custrs.close();
                                
                                while (Execurs.next()) {
                                    
                                    String strLogin = Execurs.getString("Логин исполнителя");
                                    String strPassword = Execurs.getString("Пароль исполнителя");                                  
                                   
                                    if(StringLogin.equals(strLogin)&StringPassword.equals(strPassword))
                                    {
                                        UserName = Execurs.getString("Ф.И.О. Исполнителя");
                                        user=false;
                                        Customer();
                                    } 
                                }
                                
                                stmt2.close();
                                Execurs.close();

                            } finally {
                                con.close();
                            }
                        }   catch (ClassNotFoundException ex) {
                    Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BasePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });  
    }
    
    private void Developer() 
    {        
        SomeButton();
        System.out.println("Hi Developer");
    }
    private void Customer() 
    {
        SomeButton();
        
        JButton AddOrderButton = new JButton("Сделать заказ");
        JButton MyOrderButton = new JButton("Мои заказы");
        
        AddOrderButton.setBounds(325, 110, 150, 30);
        MyOrderButton.setBounds(325, 160, 150, 30);
        
        AddOrderButton.setBackground(Color.WHITE);
        MyOrderButton.setBackground(Color.WHITE);
        
        add(AddOrderButton);
        add(MyOrderButton);  
        
       AddOrderButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                SomeButton();
                
                int NumberOrder = 1;
                JLabel textOrdering = new JLabel("Оформление заказа № "+ NumberOrder);
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
                
                SendButton.addActionListener(new java.awt.event.ActionListener() 
                { 
                    public void actionPerformed(ActionEvent e)         
                    {
                        String SrtingFieldOrdering;
                        SrtingFieldOrdering = TextFieldOrdering.getText();
                        
                        Customer();
                        //Занести в бд
                    }
                }); 
            }
       });
       
       
        MyOrderButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            {                 
                SomeButton();

                String[] columnNames = {
                    "Код проекта",
                    "Название проекта",
                    "Дата заказа",
                    "Стоимость проекта",
                    "Этап разработки"
                }; 
                
                String[][] data = new String[21][5];
                data[0][0]="Код проекта";
                data[0][1]="Название проекта";
                data[0][2]="Дата заказа";
                data[0][3]="Стоимость проекта";
                data[0][4]="Этап разработки";
                
                for(int i=1; i<21;i++)
                {
                    data[i][0] = " " + i;
                    data[i][1] = "Lol" + i;
                    data[i][2] = "sshhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" + i;
                    data[i][3] = "" + 6 + i;
                    data[i][4] = "dds" + i;
                    //Данные из бд
                };
                JTable MyOrderTable = new JTable(data,columnNames);
                //JScrollPane scrollPane = new JScrollPane(MyOrderTable);
                
                MyOrderTable.setBounds(200, 100, 375, 338);
                MyOrderTable.setBackground(Color.WHITE);
                
                add(MyOrderTable);                
                
            }
       });

    }
        private void SomeButton()
    {
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
        
        UnLoginButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                removeAll();
                repaint();
                LoginPanel();
            }
       });
        
        HomeButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                removeAll();
                repaint();
                if(user)
                {
                    Developer();
                }else
                {
                    Customer();
                }
            }
       });
    }
}
