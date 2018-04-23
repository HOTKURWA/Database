package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
    public BasePanel()
    { 
        LoginPanel();
    }
    
    private void LoginPanel()
    {
             
        
        JTextField LoginField = new JTextField(10);
        JTextField PasswordField = new JTextField(10);
        JButton LoginButton = new JButton("Login");
        LoginButton.setBackground(Color.WHITE);
        
        LoginField.setBounds(325, 110, 150, 30);
        PasswordField.setBounds(325, 150, 150, 30);
        LoginButton.setBounds(350, 200, 100, 30);

        add(LoginField);
        add(PasswordField);
        add(LoginButton);
        
        //LoginButton.addActionListener(new java.awt.event.ActionListener() { 
        //    public void actionPerformed(ActionEvent e) 
        //    { 
                String StringLogin = (LoginField.getText());
                String StringPassword = (PasswordField.getText());
                       
                //if((StringLogin.equals("a")&StringPassword.equals("a"))||(StringLogin.equals("b")&StringPassword.equals("b")))
                //{
                    
                    removeAll();
                    repaint();
                    
                    if(StringLogin.equals("a"))
                    {
                        Developer();      
                    }else
                    {
                        Customer();     
                    //}
                    }
            //}
        //});  
    }
    
    private void Developer() 
    {        
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
                
                JLabel textOrdering = new JLabel("Оформление заказа");
                JLabel textdescription = new JLabel("Напишите, какую программу вы хотите:");
                JTextArea TextFieldOrdering = new JTextArea();
                
                textOrdering.setFont(new Font("Serif", Font.BOLD, 20));
                TextFieldOrdering.setLineWrap(true);
                TextFieldOrdering.setWrapStyleWord(true);
                TextFieldOrdering.setFont(new Font("Serif", Font.ITALIC, 16));
                                                
                textOrdering.setBounds(320, 25, 300, 30);
                textdescription.setBounds(10, 70, 300, 30);
                TextFieldOrdering.setBounds(10, 100, 760, 400);
                
                add(textOrdering);
                add(textdescription);
                add(TextFieldOrdering);
                
                repaint();
                
            }
       });
       
       
        AddOrderButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                SomeButton();
            }
       });

    }
        private void SomeButton()
    {
        removeAll();
        repaint();
        
        JLabel NameUser = new JLabel("Пупченко А. В."); //данные с бд      
        JButton UnLoginButton = new JButton("Выйти");
        
        NameUser.setFont(new Font("Serif", Font.ITALIC, 15));
        UnLoginButton.setBackground(Color.RED);
        
                
        NameUser.setBounds(10, 2, 100, 30);
        UnLoginButton.setBounds(690, 5, 80, 30);
                
        add(NameUser);
        add(UnLoginButton);
        
        UnLoginButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                LoginPanel();
            }
       });

               
    }
}
