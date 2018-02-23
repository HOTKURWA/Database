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
        setSize(230,400);
        setTitle("Database");
        LoginPanel panel = new LoginPanel();
        add(panel);
    }
}

class LoginPanel extends JPanel
{
    public LoginPanel()
    { 
        String s; 
        JTextField LoginField = new JTextField(10);
        JTextField PasswordField = new JTextField(10);
        JButton LoginButton = new JButton("Login");
        LoginButton.setBackground(Color.WHITE);
        LoginButton.setBounds(54, 54, 845, 300);
        
        add(new JLabel("Login:", SwingConstants.RIGHT));
        add(LoginField);
        
        add(new JLabel("Password:", SwingConstants.RIGHT));
        add(PasswordField);

        add(LoginButton);
        
        LoginButton.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                String StringLogin = (LoginField.getText());
                String StringPassword = (PasswordField.getText());
                       
                if(StringLogin.equals("a")&StringPassword.equals("a"))
                {
                   System.out.println("???????? ????");     
                   System.exit(0);
                }
            }
        });
    }
}