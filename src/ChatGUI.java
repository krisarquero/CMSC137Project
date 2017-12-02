import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ChatGUI extends JPanel implements Runnable{

  public static Socket clientSocket;
  public static int portNumber = 12345;
    public String clientName = "Werpa Pu";
    public String inetAddress = "localhost";
    public BufferedWriter outputStream;
    public BufferedReader inputStream;
    public JTextField textField;
    public JTextArea textArea;

    public ChatGUI(String clientName, String inetAddress){
        this.clientName = clientName;   
        this.inetAddress = inetAddress;

        // User interface
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JButton sendButton = new JButton("Send");
        sendButton.setBackground(Color.black);
        sendButton.setForeground(Color.white);

        textArea = new JTextArea();
        textField = new JTextField();
        textArea.append("[BATTLESPLIX CHAT BOX]\n");
        textArea.append("");

        panel1.setLayout(new BorderLayout());
        panel1.add(textField, BorderLayout.CENTER);
        panel1.add(sendButton, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());        
        this.add(textArea, BorderLayout.CENTER);
        this.add(panel1, BorderLayout.SOUTH);

        try {
          System.out.println("Client connected through inetAddress: " + inetAddress + "...");
          clientSocket = new Socket(inetAddress, portNumber);
          outputStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
          inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));         
        } catch(Exception e){
          e.printStackTrace();
        }
    
    
        sendButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                String s=clientName + " : "+textField.getText();  
                textField.setText("");
                try{
                    outputStream.write(s);
                    outputStream.write("\r\n");
                    outputStream.flush(); 
                    }catch(Exception e){e.printStackTrace();}
            }
          }
        );  
    }

    public void run(){
        try {
            String message = inputStream.readLine();
            while(message != null){
                System.out.println(message);
                textArea.append(message+"\n");
                message = inputStream.readLine(); 
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}