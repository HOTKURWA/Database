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
        LoginPanel panel = new LoginPanel();
        add(panel);
        panel.setLayout(null);
    }
}

class LoginPanel extends JPanel
{
    public LoginPanel()
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
        
        LoginButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                String StringLogin = (LoginField.getText());
                String StringPassword = (PasswordField.getText());
                       
                if((StringLogin.equals("a")&StringPassword.equals("a"))||(StringLogin.equals("b")&StringPassword.equals("b")))
                {
                    
                    removeAll();
                    validate();
                    repaint();
                    
                    if(StringLogin.equals("a"))
                    {
                        Developer();      
                    }else
                    {
                        Customer();     
                    }
                }
            }
        });
    }
    
    private void Developer() 
    {        
        System.out.println("Hi Developer");
    }
    private void Customer() 
    {
        JButton Button = new JButton("Button");
        Button.setBackground(Color.WHITE);
        Button.setBounds(325, 110, 150, 30);
        add(Button);
        System.out.println("Hi Customer");                 
    }
}