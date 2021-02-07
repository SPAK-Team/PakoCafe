package pakocafe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Kitchen extends JFrame {
    
    PakoCafe PakoFrame;

    private JPanel      contentpane;
    private JLabel      drawpane;
    private JComboBox   ListCombo;
    private MyImageIcon backgroundImg, MenuImg, TableImg ,FoodImg,CorrectImg;
    private JButton     ConfirmButton;
    
    private JLabel MenuLabel, TableLabel,FoodLabel,CorrectLabel;

final private int frameWidth   = 1366,  frameHeight   = 768;
      private int MenuCurX     = 1140,  MenuCurY      =  10;
      private int MenuWidth    =  230,  MenuHeight    = 260;
final private int TableCurX    =  490,  TableCurY     = 460;
      private int TableWidth   =  301,  TableHeight   = 170;
      private int FoodCurX     =  550,  FoodCurY      = 470;
      private int FoodWidth    =  150,  FoodHeight    = 110; 
      private int CorrectWidth =  300,  CorrectHeight = 300;


      private String[] MenuList     = {"Salmon sushi","Otoro sushi","Tamagoyaki sushi", "Ebi sushi",
                                       "Onigiri","Ikura sushi"};
      private String[] IngredName   = {"TamagoyakiLabel","IkuraLabel","salmonLabel","OtoroLabel","SeaweedLabel","EbiLabel"};
      private String[] SideDishName = {"RiceLabel","ShoyuLabel","WasabiLabel"};
    
      private String[] BGfile       = {"resources/BG/Kitchen.jpg"};
      private String[] Ingredfile   = {"resources/Ingrediant/Tamago.png", "resources/Ingrediant/SalmonRoe.png",
                                       "resources/Ingrediant/Salmon.png", "resources/Ingrediant/Otello.png",
                                       "resources/Ingrediant/Nori.png", "resources/Ingrediant/Ebi.png"};
      private String[] SideDishfile = {"resources/Ingrediant/Rice.png", "resources/Ingrediant/Shoyu.png","resources/Ingrediant/Wasabi.png"};
      private String[] Foodfile     = {"resources/Sushi/1.png","resources/Sushi/2.png","resources/Sushi/3.png","resources/Sushi/4.png","resources/Sushi/5.png","resources/Sushi/6.png"};
      private String[] Correctfile  = {"resources/Correct/1.png", "resources/Correct/2.png"};
    
      private int[] IngredWidth     = {170, 127, 172, 172, 145, 172};
      private int[] IngredHeight    = {82, 82, 82, 82, 82, 82};
      private int[] IngredCurX      = {133, 340, 503, 686, 885, 1048};
      private int[] IngredCurY      = {340, 340, 340, 340, 340, 340};

      private int[] SideDishWidth   = {207, 90, 139};
      private int[] SideDishHeight  = {100, 145, 90};
      private int[] SideDishCurX    = {220, 830, 900};
      private int[] SideDishCurY    = {500, 490, 460};

    
    private int ToCafe = 0;
    public Kitchen(PakoCafe p) {
        //----- initialize base component
        PakoFrame = p;
        setTitle("Kitchen zone");
        setBounds(50, 50, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        AddComponents();

    }

    public static void main(String[] args) {
        new Kitchen(new PakoCafe());
    }

    public void AddComponents() {
        backgroundImg = new MyImageIcon(BGfile[0]).resize(frameWidth, frameHeight);
        MenuImg       = new MyImageIcon("resources/ListMenu/Menu1.png").resize(MenuWidth, MenuHeight);
        TableImg      = new MyImageIcon("resources/Ingrediant/Plain.png").resize(TableWidth, TableHeight);

        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        //-----(1) create result picture & handler listener for tell result of draging ingrediant
        CorrectLabel = new JLabel();
        CorrectLabel.setBounds(425,100,CorrectWidth, CorrectHeight);
        CorrectLabel.setVisible(false);
        CorrectLabel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    CorrectLabel.setVisible(false);
                    FoodLabel.setVisible(false);
                }
            }}
        );
        
        MyImageIcon CorrectImg[] = new MyImageIcon [2];
        for(int j=0 ; j<Correctfile.length;j++){  CorrectImg[j] = new MyImageIcon(Correctfile[j]).resize(CorrectWidth, CorrectHeight);}
        drawpane.add(CorrectLabel);
        
        // -----(2)  create menu picture  for Guide ingrediant
        MenuLabel = new JLabel(MenuImg);
        MenuLabel.setBounds(MenuCurX, MenuCurY, MenuWidth, MenuHeight);
        drawpane.add(MenuLabel);
        
        // -----(3) create ingrediant & handler listener 
        MyImageIcon IngredImg[] = new MyImageIcon[6];
        MyLabel   IngredLabel[] = new MyLabel[6];
        for (int j = 0; j < IngredImg.length; j++) {
            IngredImg[j]   = new MyImageIcon(Ingredfile[j]).resize(IngredWidth[j], IngredHeight[j]);
            IngredLabel[j] = new MyLabel(IngredImg[j]);
            IngredLabel[j].setName(IngredName[j]);
            IngredLabel[j].setConditions(IngredCurX[j], IngredCurY[j], IngredWidth[j], IngredHeight[j]);
            drawpane.add(IngredLabel[j]);
            repaint();
        }
        // -----(4) create SideDish  
        MyImageIcon SideDishImg[] = new MyImageIcon[3];
        MyLabel SideDishLabel[]   = new MyLabel[3];
        for (int j = 1; j < SideDishImg.length; j++) {
            SideDishImg[j] = new MyImageIcon(SideDishfile[j]).resize(SideDishWidth[j], SideDishHeight[j]);
            SideDishLabel[j] = new MyLabel(SideDishImg[j]);
            SideDishLabel[j].setName(SideDishName[j]);
            SideDishLabel[j].setConditions(SideDishCurX[j], SideDishCurY[j], SideDishWidth[j], SideDishHeight[j]);
            drawpane.add(SideDishLabel[j]);

        }
            MyLabel RiceLabel;
            SideDishImg[0] = new MyImageIcon(SideDishfile[0]).resize(SideDishWidth[0], SideDishHeight[0]);
            RiceLabel = new MyLabel(SideDishImg[0]);
            RiceLabel.setConditions(SideDishCurX[0], SideDishCurY[0], SideDishWidth[0], SideDishHeight[0]);
            drawpane.add(RiceLabel);
            
        // -----(5) create success corect food
        FoodLabel = new JLabel();
        FoodLabel.setBounds(FoodCurX, FoodCurY, FoodWidth, FoodHeight);
        MyImageIcon FoodImg[] = new MyImageIcon[6]; 
            for(int j=0;j<Foodfile.length;j++)   { FoodImg[j] = new MyImageIcon(Foodfile[j]).resize(FoodWidth, FoodHeight);   }
            FoodLabel.setVisible(false);
            drawpane.add(FoodLabel);
            
        // -----(6) create butcher for put ingrediant
        TableLabel = new JLabel(TableImg);
        TableLabel.setBounds(TableCurX, TableCurY, TableWidth, TableHeight);
        drawpane.add(TableLabel);
        
        // -----(7) create list menu & handler listener
        ListCombo = new JComboBox(MenuList);
        ListCombo.setFont(new Font("SanSerif", Font.BOLD + Font.ITALIC, 20));
        ListCombo.setBackground(new Color(200, 220, 250, 250));
        ListCombo.setPreferredSize(new Dimension(190, 40));
        ListCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    int index = ListCombo.getSelectedIndex();
                    MenuImg = new MyImageIcon("resources/ListMenu/Menu" + (index + 1) + ".png").resize(MenuWidth, MenuHeight);
                    MenuLabel.setIcon(MenuImg);
                    repaint();
                } else {
                }
            }
        });
       // -----(8) create button & handler listener for confirm button 
       ConfirmButton = new JButton("☺ Confirm ☺");
       ConfirmButton.setLayout(null);
       ConfirmButton.setBounds(555,205,150,50);
       ConfirmButton.setFont(new Font("Dialog", Font.BOLD,17));
       ConfirmButton.setBackground(new Color(163,254,35));
       ConfirmButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int temp = ToCafe;
            // Condition find type of food (Max : 3 ingreadiants) (temp -> 0 mean no food , 1-6 mean food type1,2,3,4,5,6)
            if (TableLabel.getBounds().intersects(RiceLabel.getBounds())&&TableLabel.getBounds().intersects(IngredLabel[4].getBounds())) {
                    if(TableLabel.getBounds().intersects(IngredLabel[0].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[1].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[2].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[3].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[5].getBounds())){ /*System.out.println("Tamako Sushi");*/temp=3; }
                    else if  (TableLabel.getBounds().intersects(IngredLabel[1].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[0].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[2].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[3].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[5].getBounds())){/*System.out.println("Igura Sushi");*/temp=6;}
                    else if  (TableLabel.getBounds().intersects(IngredLabel[2].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[0].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[1].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[3].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[5].getBounds())){/*System.out.println("Salmon Sushi"); */temp=1;}
                    else if  (TableLabel.getBounds().intersects(IngredLabel[3].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[0].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[1].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[2].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[5].getBounds())){/*System.out.println("Otoro Sushi");*/temp=2;}
                    else if  (TableLabel.getBounds().intersects(IngredLabel[5].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[0].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[1].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[2].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[3].getBounds())){/*System.out.println("Ebi Sushi");*/temp=4;}
                    else if  (!TableLabel.getBounds().intersects(IngredLabel[0].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[1].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[2].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[3].getBounds())&&!TableLabel.getBounds().intersects(IngredLabel[5].getBounds())){/*System.out.println("Onigiri");*/temp=5;}
                    else     {/* System.out.println("Incorrect");*/temp=0; }
            }
            else { /*System.out.println("Incorrect");*/temp=0; }
            
            //if we can make food exactly
            if(temp!=0){
                    
                    FoodLabel.setIcon(FoodImg[temp-1]);
                    FoodLabel.setVisible(true);
                    CorrectLabel.setIcon(CorrectImg[0]);
                     
                    ToCafe = temp;
                    PakoFrame.getMyOwner().setFood(ToCafe);
                     
                     for (int j = 0 ; j < IngredLabel.length;   j++) {   IngredLabel[j].setLocation(IngredCurX[j], IngredCurY[j]); }
                     for (int j = 1 ; j < SideDishLabel.length; j++) {   SideDishLabel[j].setLocation(SideDishCurX[j], SideDishCurY[j]); }
                     RiceLabel.setLocation(SideDishCurX[0], SideDishCurY[0]);
            }
            else{CorrectLabel.setIcon(CorrectImg[1]);}
           
                                                             CorrectLabel.setVisible(true);                    
        }
    });
       drawpane.add(ConfirmButton);
        
        // -----(9)add components & update screen
        JPanel control = new JPanel();
        control.setBounds(0, 0, 500, 100);
        control.setBackground(new Color(250, 200, 200));
        control.add(new JLabel("                                                                                                                                                                                                                                                                     "));
        control.add(ListCombo);
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }
    
    public int getFood(){return ToCafe;}
    
    
}
////////////////////////////////////////////////////////////////////////////////////

class MyLabel extends JLabel implements MouseListener, MouseMotionListener {

    private int CurX, CurY;
    private int Width, Height;
    private boolean drag;
    private int count = 0;
    private MySoundEffect dragSound; 
    
    public void setConditions(int curx, int cury, int width, int height) {
        CurX   = curx;
        CurY   = cury;
        Width  = width;
        Height = height;
        this.setBounds(CurX, CurY, Width, Height);
    }
    public MyLabel(MyImageIcon LabelImg) {
        this.setIcon(LabelImg);
        drag = false;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        dragSound = new MySoundEffect("resources/Audio/dragsound.wav");
    }

    public void mouseClicked(MouseEvent e) {
        count++;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (count >= 2) {
            JOptionPane.showMessageDialog(null, "Drag me to accurate position", "Warnning",
                     JOptionPane.INFORMATION_MESSAGE);
        }
        drag = true;
        dragSound.playOnce();
    }
    public void mouseDragged(MouseEvent e) {
        if ( drag == true ) {
            CurX = CurX + e.getX();
            CurY = CurY + e.getY();

            Container p = getParent(); 
            if (CurX < 0) { CurX = 0; }
            if (CurY < 0) { CurY = 0; }
            if (CurX + Width > p.getWidth()) {CurX = p.getWidth() - Width;}
            if (CurY + Height > p.getHeight()) {CurY = p.getHeight() - Height;}
             this.setLocation(CurX, CurY);
        }
        else {}
    }

    public void mousePressed (MouseEvent e)  { }
    public void mouseReleased(MouseEvent e)  { }
    public void mouseEntered (MouseEvent e)  { }
    public void mouseExited  (MouseEvent e)  { }
    public void mouseMoved   (MouseEvent e)  { }

};

