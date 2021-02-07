package pakocafe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


class Shopping extends JFrame {
   
    private  JPanel           contentpane;
    private  JLabel           drawpane,headerLabel;
    private  MyImageIcon      backgroundImg;
    private  JRadioButton[]   shopRadio;
    private  ButtonGroup      radiogroup;

    PakoCafe PakoFrame;
    private TableShopping shop; //for keep type of table , number of table that we buy
    private int           point;
    private String        Table ;
    private int           numberTable;
    
    
    private int frameWidth     =  650, frameHeight = 750;
    private String[] BGfile    =  {"resources/BG/rockpink.jpg"};
    private String[] Deskfile  =  {"resources/Table/1.png" , "resources/Table/2.png",
                                   "resources/Table/3.png" , "resources/Table/4.png",
                                   "resources/Table/5.png" , "resources/Table/6.png", };
    private String[] DeskName  =  {"Table Red","Table Gray", "Table Green","Table Wood","Table Blue","Table Pink"};
    private int[] DeskWidth    =  { 220, 250, 240, 200, 265, 265  };
    private int[] DeskHeight   =  { 150, 170, 170, 150, 190, 190  };
    private int[] DeskCurX     =  {  40, 340,  40, 363,  35, 343  };
    private int[] DeskCurY     =  {  70,  62, 270, 280, 450, 450  };
    
    private int[] CheckWidth   =  { 150,152,152,152,152,152 };
    private int[] CheckHeight  =  {  40, 40, 40, 40, 40, 40 };
    private int[] CheckCurX    =  {  55,355, 50,355, 50,363 };
    private int[] CheckCurY    =  { 210,207,420,420,620,620 };
    
    public Shopping(PakoCafe p){
        //---- initialize base property
        PakoFrame = p;
	setTitle("Shopping");
        setBounds(50, 50, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        AddComponents();
    }
    public static void main(String[] args) 
    {
	new Shopping(new PakoCafe());
    }
    public void AddComponents(){
        
        backgroundImg = new MyImageIcon(BGfile[0]).resize(frameWidth, frameHeight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        
        
        //-----(0) create Label header
        headerLabel = new JLabel("SHOPPING");
        headerLabel.setFont(new Font("Monospaced", Font.BOLD+Font.PLAIN,50));
        headerLabel.setLayout(null);
        headerLabel.setBounds(200,5,400,80);
        drawpane.add(headerLabel);
        //-----(1) create picture for selling
        JLabel DeskLabel[] = new JLabel[6];
        MyImageIcon DeskImg[] = new MyImageIcon[6];
        for (int j = 0; j < DeskImg.length; j++) {
            DeskImg[j]   = new MyImageIcon(Deskfile[j]).resize(DeskWidth[j], DeskHeight[j]);
            DeskLabel[j] = new JLabel(DeskImg[j]);
            DeskLabel[j].setBounds(DeskCurX[j], DeskCurY[j], DeskWidth[j], DeskHeight[j]);
            drawpane.add(DeskLabel[j]);
            repaint();
        }
        //-----(2) create Jbutton for sending order request
        JButton OrderButton = new JButton("Order");
        OrderButton.setFont(new Font("SanSerif", Font.BOLD,25));
        OrderButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        OrderButton.setLayout(null);
        OrderButton.setBounds(250,650,115,50);
        OrderButton.setBackground(new Color(100,255,150));
        //----- create object for keep value from lambda expression
        shop = new TableShopping();
        //----------2.1) handler Listener of Jbutton  
        OrderButton.addActionListener(e -> {
            String component = e.getActionCommand();
            if (component.equals("Order")) {

                Table = shop.getName();
                numberTable = shop.getNumber();
                int choice = JOptionPane.showConfirmDialog(null, ("Your order is : " + Table + " = " + numberTable), "This is a Confirm Dialog",
                             JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                
                
                if (choice == 0 && numberTable != 0 && PakoFrame.getMyOwner().getPoint()>=300) { // choice = 0 is Yes Option
                            JOptionPane.showMessageDialog(null,
                            ("        " + Table + " at Table No. " + numberTable + "\n Thank you for your purchase"), "Pako Cafe",
                            JOptionPane.INFORMATION_MESSAGE);
                            
                            
                            PakoFrame.getMyOwner().changePoint(-300);
                            PakoFrame.setTable(shop.changeTotype(), shop.getNumber());
                            
                            
                } 
                else { JOptionPane.showMessageDialog(null, ("hope to see you next time"), "Pako Cafe", JOptionPane.INFORMATION_MESSAGE ); }
              
            }//end if

        });
        //-----(3) create radiobutton & handler Listerner for choose type of food table
        JToggleButton[] shopRadio = new JToggleButton[DeskImg.length];
        radiogroup = new ButtonGroup();
        for (int i=0; i < DeskImg.length; i++){
             shopRadio[i] = new JRadioButton(DeskName[i],false);     
             shopRadio[i].setFont(new Font("SanSerif", Font.BOLD,17));
             shopRadio[i].setBounds(CheckCurX[i],CheckCurY[i],CheckWidth[i],CheckHeight[i]);
             shopRadio[i].setOpaque(false);
             radiogroup.add(shopRadio[i]);
             shopRadio[i].addItemListener(e ->{ 
                                                JRadioButton tempCheck = (JRadioButton)e.getItem(); 
                                                    if (tempCheck.isSelected())   {
                                                        String nameTable = tempCheck.getText();
                                                        shop.setName(nameTable);  }
		                             }
                                        );	
                  drawpane.add(shopRadio[i]);
        }
        //-----(4) create spinner & handler Listerner  for select number of order  
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
 
               spinner.setBounds(250,610,115,30);
               spinner.addChangeListener(e->{
                   JSpinner tempSpin = (JSpinner)e.getSource();
                   String x = tempSpin.getValue().toString();
                   int value = Integer.parseInt(x);  
                   shop.setNumber(value);
                  
               });
               drawpane.add(spinner); 
        //-----(5) add components  & update screen
        contentpane.add(OrderButton);
        contentpane.add(drawpane, BorderLayout.CENTER);
             validate();
             
    }//end constructor
    
    
    public int getCurrentPoint()       { return point; }
    
    
}//end Shopping class


/////////////////////////////////////////////////////////////////////////////////
class TableShopping{
    // for keep type,number of table that we buy
    private String name;
    private int    number;

    public void   setName(String n) { name = n;     }
    public void   setNumber(int v)  { number = v;   }
    public String getName()         { return name;  }
    public int    getNumber()       { return number;}
    public        TableShopping()   { super();      }  
    public int changeTotype(){ 
        // because we keep name of type after we keep integer 
        int type; 
        if( name.compareToIgnoreCase("Table Red")==0 )         type = 1;
        else if ( name.compareToIgnoreCase("Table Gray")==0  ) type = 2;
        else if ( name.compareToIgnoreCase("Table Green")==0 ) type = 3;
        else if ( name.compareToIgnoreCase("Table Wood")==0  ) type = 4;
        else if ( name.compareToIgnoreCase("Table Blue")==0  ) type = 5;
        else type = 6;
    
    return type;  }
}

