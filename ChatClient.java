
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class ChatClient extends JFrame implements ActionListener,Runnable{

  JTextField t1;
  JTextArea ta1;
  JButton b1,b2;
  JPanel p1,centerPanel,southPanel;
  
  String name;
  
  //ServerSocket ss;
  Socket s;//for storing client socket object
  
  Thread th1;
  
  BufferedReader br;
  BufferedWriter bw;
  
  
  
  public ChatClient()
  {
    this.setVisible(true);
    this.setSize(400,300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    p1 = new JPanel();
    
    centerPanel = new JPanel();
    southPanel = new JPanel();
    t1 = new JTextField(20);
    ta1 = new JTextArea(10,20);
    JScrollPane js = new JScrollPane(ta1);
    b1 = new JButton("Send");
    b2 = new JButton("Cancel");
    p1.setLayout(new BorderLayout());
    centerPanel.setLayout(new BorderLayout());
    centerPanel.add(js,"Center");
    centerPanel.add(t1,"South");
    p1.add(centerPanel,"Center");
    southPanel.add(b1);
    southPanel.add(b2);
    p1.add(southPanel,"South");
    this.add(p1);
    p1.setBorder(BorderFactory.createTitledBorder("Chat Client"));
    b1.addActionListener(this);
    b2.addActionListener(this);
    name= JOptionPane.showInputDialog(null,"Enter Name ?");
    this.setTitle("User Name :"+name);
    
  try {
    //ss = new ServerSocket(8888);
    //s = ss.accept();//
    
    s = new Socket("localhost",8888);
    
    br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    bw.write("Hello");
    bw.newLine();
    bw.flush();
    th1 = new Thread(this);
    th1.start();
        
    
  }
  catch(Exception ex)
  {
    
  }
    
  }
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==b1)
    {
      try {
      String msg =name+" says-->"+t1.getText();
      bw.write(msg);
      bw.newLine();
      bw.flush();
      ta1.append(msg+"\n");
      t1.setText("");
      }
      catch(Exception ex)
      {
        
      }
    }
    else
    {
      System.exit(0);
    }
  }
  public void run()
  {
    for(;;)
    {
      try
      {
        ta1.append(br.readLine()+"\n");
      }
      catch(Exception ex)
      {
        
      }
    }
  }
  public static void main(String args[])
  {
    new ChatClient();
  }
}