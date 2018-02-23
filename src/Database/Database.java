package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Database 
{
    public static void main(String []args)
    {
        ButtonFrame frame= new ButtonFrame();//создаем фрейм
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//операция отвечающая за окончание программы после закрытия фрейма
        frame.setVisible(true);//делаем фрейм видимым
        
    }
}

class ButtonFrame extends JFrame
{//создаем класс отвечающий за фрейм
    public ButtonFrame()
    {//конструктор данного класа
        setSize(230,400);//размеры фрейма
        setTitle("Database");//название фрейма
        LoginPanel panel = new LoginPanel();//создаем панель
        add(panel);//добавляем панель на фрейм
    }
}

class LoginPanel extends JPanel
{//класс отвечающий за фрейм
    public LoginPanel()
    { //конструктор панели
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
        
        LoginButton.addActionListener(new java.awt.event.ActionListener() { //привязываем слушатель
            public void actionPerformed(ActionEvent e) 
            { 
                String StringLogin = (LoginField.getText());
                String StringPassword = (PasswordField.getText());
                       
                if(StringLogin.equals("a")&StringPassword.equals("a"))
                {
                   System.out.println("Успешный вход");     
                   System.exit(0);
                }
            }
        });
    }
}