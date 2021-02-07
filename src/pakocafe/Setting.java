package pakocafe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


class Setting extends JFrame {
    private JPanel        contentpane;
    private JLabel        drawpane;
    private JLabel        CreditButton;
    private JTable        listname;
    private MyImageIcon BGsettingImg,CreditImg;
    
    
    private int frameWidth     =  650,  frameHeight  = 750;
    private int buttonWidth    =  260,  buttonHeight =  83;
    
    final private String BGSettingfile = "resources/BG/BGSetting.jpg", Creditfile = "resources/Button/credit.png";//,Volumefile="resources/Button/volume.png";
    final private String[]   header = {"ID","Firstname","Surname"};
    final private String[][] data   = { { "6113055", "Kesini", "Thawisaeng"       },
                                        { "6113064", "Suchawadee", "Phinngam"     },
                                        { "6113065", "Apichaya", "Lawthienchai"   },
                                        { "6113296", "Puntara", "Arunratanadilok" }, };
 
    public Setting(){
        //---- Initialize base property
	setTitle("Setting");
        setBounds(50, 50, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //---- set contentpane
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        BGsettingImg = new MyImageIcon (BGSettingfile).resize(frameWidth,frameHeight);
        
      
        drawpane = new JLabel();
        drawpane.setIcon(BGsettingImg);
        drawpane.setLayout(null);
        
        
        //----(1) create label picture
        CreditImg = new MyImageIcon(Creditfile).resize(buttonWidth, buttonHeight);
        CreditButton = new JLabel();
        CreditButton.setBounds(190,40,buttonWidth, buttonHeight);
        CreditButton.setIcon(CreditImg);
     
        
        //----(2) create Table
        listname = new JTable(data,header);
        listname.setBounds(135, 130, 350, 140);
        listname.setRowHeight(35);
        listname.setFont(new Font("SanSerif", Font.PLAIN, 15));
        listname.setEnabled(false);
        
        //---- add components & update screen
        drawpane.add(CreditButton);
        drawpane.add(listname);
        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }
    
    public static void main(String[] args) 
    {
	new Setting();
    }
    
}
