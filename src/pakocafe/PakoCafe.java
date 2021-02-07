package pakocafe;

import pakocafe.Kitchen;
import pakocafe.Shopping;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PakoCafe extends JFrame {


    private JPanel contentpane, OwnerBar;
    private JLabel drawpane;
    private MyImageIcon BGHomeImage, NewGameImage, StartImage, BGCafeImage ;
    private MyButton                 NewGameButton, StartButton, CookingButton, MallButton, SettingButton, PromoteButton;
    private JTextField NameField, PointField;
    private JProgressBar PromoteProgress;

    private Table[]    allTable    = new Table[4]; // Label Tale
    private Order[]    allOrder    = new Order[4]; // Label Speech Frame
    private Customer[] allCustomer = new Customer[4]; // Label Customer
    private JLabel[]   allFood     = new JLabel[4]; // Label food on table
    private Coin[]     allCoin     = new Coin[4]; //Label Coin 

    PakoCafe myFrame; // Pointer for this frame
    Kitchen kitchenFrame; 
    Shopping shoppingFrame;
    Setting settingFrame;

 
    private String username; // name from get Caret
    private int    promote = 0; //value in JProgessbar (PromoteProgess)
    private Owner  myOwner; // Player's Account
    private CustomerThread[]    AllCustomerThread = new CustomerThread[4]; // Thread of Customer
    private MySoundEffect       themeSound; 
    private int frameWidth = 1024, frameHeight = 768; 
    
    
    private  String   BGHomeFile      = "resources/BG/BGhome.png",
                      BGCafeFile      = "resources/BG/BGcafe.png",
                      PromoteFile     = "resources/Button/promote.png";
    private  String[] StartButtonFile = {"resources/Button/start.png", "resources/Button/startcolor.png"},
                      NewGameFile     = {"resources/Button/newgame.png","resources/Button/newgamecolor.png"},
                      CookingFile     = {"resources/Button/cooking.png","resources/Button/cookingcolor.png"},
                      SettingFile     = {"resources/Button/setting.png","resources/Button/settingcolor.png"},
                      MallFile        = {"resources/Button/mall.png","resources/Button/mallcolor.png"},
                      TableFile       = {"resources/Table/1.png","resources/Table/2.png","resources/Table/3.png","resources/Table/4.png","resources/Table/5.png","resources/Table/6.png"},
                      OwnerFile       = {"resources/Owner/down.png","resources/Owner/up.png"};
    public static void main(String[] args) {
        new PakoCafe();
    }

    public PakoCafe() {
        myFrame = this;
        // -----(1) set sound for motion game music
        themeSound = new MySoundEffect("resources/Audio/themesound.wav");
        themeSound.playLoop();
        
        // -----(2) initialize base property
        setTitle("Pako Cafe");
        setBounds(1, 1, frameWidth, frameHeight); //Set Where frame open & size
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
      
        HomeMode_and_Validate();
    }
    
    
    // ----(A) section1 #similar to addcomponent() section1#
    public void HomeMode_and_Validate() {  
        
        
        BGHomeImage = new MyImageIcon(BGHomeFile).resize(contentpane.getWidth(), contentpane.getHeight());
        BGCafeImage = new MyImageIcon(BGCafeFile).resize(contentpane.getWidth(), contentpane.getHeight());

        drawpane = new JLabel();
        drawpane.setIcon(BGHomeImage);
        drawpane.setLayout(null);
        
        // -----(3)create textfield & handler event for user registor
        NameField = new JTextField();
        NameField.setBounds(615, 240, 200, 50);
        NameField.setFont(new Font("SanSerif", Font.BOLD, 15));
        NameField.setVisible(false);
        NameField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                username = NameField.getText();
            }
        });
        // -----(4) create start button & handler listener
        StartButton = new MyButton(StartButtonFile[0], 628, 295, StartButtonFile[1]);
        StartButton.setVisible(false);
        StartButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // If NameFirld Not Blank only
                if (username != null && !username.isEmpty()&&username.trim().length() > 0) {
                    int ch = JOptionPane.showConfirmDialog(new JFrame(), "Name : " + username, "Are you sure?", JOptionPane.YES_NO_OPTION);
                    if (ch == JOptionPane.YES_OPTION) {
                        NameField.setVisible(false);
                        StartButton.setVisible(false);
                        CafeMode(); // Main Application
                    } else;
                }
            }

            public void mouseEntered(MouseEvent e) {
                StartButton.setLocation(StartButton.getX() + 2, StartButton.getY() + 2);
                StartButton.setButtonColor(true);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                StartButton.setLocation(StartButton.getX() - 2, StartButton.getY() - 2);
                StartButton.setButtonColor(false);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // -----(5) create newbutton & handler listener
        NewGameButton = new MyButton(NewGameFile[0], 625, 270, NewGameFile[1]);
        NewGameButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                    NameField.setVisible(true);
                    StartButton.setVisible(true);
                    NewGameButton.setVisible(false);
                }
            }

            public void mouseEntered(MouseEvent e) {
                NewGameButton.setLocation(NewGameButton.getX() + 2, NewGameButton.getY() + 2);
                NewGameButton.setButtonColor(true);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                NewGameButton.setLocation(NewGameButton.getX() - 2, NewGameButton.getY() - 2);
                NewGameButton.setButtonColor(false);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // -----add components & update screen
        drawpane.add(NewGameButton);
        drawpane.add(NameField);
        drawpane.add(StartButton);
        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }
    // ----(B) section1 #similar to addcomponent() section2#
    public void CafeMode() {
      
        drawpane.setIcon(BGCafeImage);
        // -----(6) handler listener of frame before closed 
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(new JFrame(), "Your Point : " + Integer.toString(myOwner.getPoint()), "Thank you & Good Bye " + myOwner.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // -----(7) create textfeild for illustate allocated point
        PointField = new JTextField("0");
        PointField.setEditable(false);
        PointField.setSize(150, 26);
        PointField.setFont(new Font("SanSerif", Font.BOLD, 15));
        PointField.setHorizontalAlignment(SwingConstants.RIGHT);
        // -----(8) create object for allocate inform of user
        myOwner = new Owner(username, PointField,OwnerFile);
        myOwner.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e) {myOwner.setOwnerIcon(1); }
            public void mouseExited(MouseEvent e)  {myOwner.setOwnerIcon(-1);}});
        drawpane.add(myOwner);
        // -----(9) create kitchenButton & handler listener for link to Kitchen frame 
        CookingButton = new MyButton(CookingFile[0], 800, 20, CookingFile[1]);
        CookingButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    kitchenFrame = new Kitchen(myFrame);
                }
            }
            public void mouseEntered(MouseEvent e) {
                CookingButton.setLocation(CookingButton.getX() + 2, CookingButton.getY() + 2);
                CookingButton.setButtonColor(true);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e)  {
                CookingButton.setLocation(CookingButton.getX() - 2, CookingButton.getY() - 2);
                CookingButton.setButtonColor(false);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // -----(10) create kitchenButton & handler listener for link to Setting frame 
        SettingButton = new MyButton(SettingFile[0], 400, 20, SettingFile[1]);
        SettingButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    settingFrame = new Setting();
                }
            }
            public void mouseEntered(MouseEvent e) {
                SettingButton.setLocation(SettingButton.getX() + 2, SettingButton.getY() + 2);
                SettingButton.setButtonColor(true);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                SettingButton.setLocation(SettingButton.getX() - 2, SettingButton.getY() - 2);
                SettingButton.setButtonColor(false);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        // -----(11) create kitchenButton & handler listener for link to Shopping frame 
        MallButton = new MyButton(MallFile[0], 600, 20, MallFile[1]);
        MallButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                   shoppingFrame = new Shopping(myFrame); 
                }
            }
            public void mouseEntered(MouseEvent e) {
                MallButton.setLocation(MallButton.getX() + 2, MallButton.getY() + 2);
                MallButton.setButtonColor(true);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                MallButton.setLocation(MallButton.getX() - 2, MallButton.getY() - 2);
                MallButton.setButtonColor(false);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // -----(12) create PromoteButton & handler event for update progressbar
        PromoteButton = new MyButton(PromoteFile, 20, 20, 100, 100);
        PromoteButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    promote++;
                    if (promote >= 10) {
                        setCustomer(); // Generate Customer
                        promote = promote - 10;
                    }
                    PromoteProgress.setValue(promote * 10);
                }
            }
            public void mouseEntered(MouseEvent e) {
                PromoteButton.setLocation(PromoteButton.getX() + 2, PromoteButton.getY() + 2);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                PromoteButton.setLocation(PromoteButton.getX() - 2, PromoteButton.getY() - 2);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // -----(13) create progressbar
        PromoteProgress = new JProgressBar();
        PromoteProgress.setSize(150, 26);
        PromoteProgress.setStringPainted(true);
        // -----(14) create table & create food
        allTable[0] = new Table(510, 400, TableFile[0]);
        allTable[1] = new Table(750, 400, TableFile[0]);
        allTable[2] = new Table(510, 600, TableFile[0]);
        allTable[3] = new Table(750, 600, TableFile[0]);
        for (int i = 0; i < allFood.length; i++) {
            allFood[i] = new JLabel();
            allFood[i].setSize(80, 59);

            allTable[i].add(allFood[i]);
            allFood[i].setLocation(30, 15);
            drawpane.add(allTable[i]);
        }
        // -----(14) create label for group component that have nearly region
        OwnerBar = new JPanel();
        OwnerBar.setBounds(150, 20, 230, 100);
        // OwnerBar.setBackground(new Color(255,255,255));
        OwnerBar.setLayout(new BoxLayout(OwnerBar, BoxLayout.Y_AXIS));
            OwnerBar.add(new JLabel("Promote Progress"));
            OwnerBar.add(PromoteProgress);
            OwnerBar.add(new JLabel("Your Point : "));
            OwnerBar.add(PointField);
            drawpane.add(OwnerBar);
        
        drawpane.add(CookingButton);
        drawpane.add(MallButton);
        drawpane.add(SettingButton);
        drawpane.add(PromoteButton);

    }

    public void setCustomer() {

        for (int i = 0; i < allTable.length; i++) {
            if (allTable[i].IsTableBusy() == false) {
                allTable[i].reserveTable();
                allCustomer[i] = new Customer();
                repaint(); //Customer จะได้ไม่แหว่ง
                allOrder[i] = new Order(allTable[i].getX() - 75, allTable[i].getY() - 60);
                allCoin[i] = new Coin();
                drawpane.add(allOrder[i]);
                drawpane.add(allCustomer[i]);
                drawpane.add(allCoin[i]);
                AllCustomerThread[i] = new CustomerThread(allCustomer[i], allTable[i], allOrder[i], myOwner, allFood[i], allCoin[i]); //เริ่มเธรด
                break; //ถ้าได้generate จะออกลูป
            }
        }
    }

    public Owner getMyOwner(){return myOwner;}
    
    public void setTable(int type,int number ){
        MyImageIcon TableNew = new MyImageIcon(TableFile[type-1]).resize(140, 126);
            allTable[number-1].setIcon(TableNew);
    }

}//end class
/////////////////////////////////////Class ImageIcon///////////////////////////////////

class MyImageIcon extends ImageIcon{ 
    public MyImageIcon(String fname) {   super(fname);   }
    public MyImageIcon(Image image)  {   super(image);   }
    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
};
/////////////////////////////////////Class Table ///////////////////////////////////////

class Table extends JLabel {

    private int TableWidth = 140, TableHeight = 126;
    private int curX, curY;
    private MyImageIcon TableImage;
    private boolean busy; //Condition for Generate CustomerThreads (busy=true -> not generate)

    public Table(int x, int y, String ImgFile) {
        curX = x;
        curY = y;
        busy = false;
        TableImage = new MyImageIcon(ImgFile).resize(TableWidth, TableHeight);
        setIcon(TableImage);
        setBounds(curX, curY, TableWidth, TableHeight);
    }

    public int getTableWidth() {
        return TableWidth;
    }

    public int getTableHeight() {
        return TableHeight;
    }

    public boolean IsTableBusy() {
        return busy;
    }

    public void reserveTable() {
        busy = true;
    }

    public void emptyTable() {
        busy = false;
    }
};
////////////////////////////////////////// Class Customer //////////////////////////////////

class Customer extends JLabel {

    private int CustomerWidth = 150, CustomerHeight = 148;
    private int curX = 525, curY = 249;
    private MyImageIcon CustomerImage;
    private boolean move = false;

    public Customer() {
        Random random    = new Random();
        int number       = random.nextInt(7) + 1; //Random Customer 
        String ImageFile = "resources/Customer/" + number + ".png";
        CustomerImage    = new MyImageIcon(ImageFile).resize(CustomerWidth, CustomerHeight);
        setIcon(CustomerImage);
        setBounds(curX, curY, CustomerWidth, CustomerHeight);
    }

    public boolean getCustomerMove() {
        return move;
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }

    public void setCurX(int x) {
        curX = x;
    }

    public void setCurY(int y) {
        curY = y;
    }

    public void setCustomerMove(boolean s) {
        move = s;
    }
};
/////////////////////////////////////////Class Order////////////////////////////////

class Order extends JLabel {
    //กรอบคำพูดของลูกค้า
    private int OrderWidth = 100, OrderHeight = 82;
    private int OrderX, OrderY;
    private MyImageIcon OrderImage;
    private int typeOfOrder; // type of food from order (Customer)
    private boolean IsServe = false; // Condition check "Is food served?" (true = order will unvisible and make foodLabel visible)

    public Order(int x, int y) {
        Random random    = new Random();
        int number       = random.nextInt(6) + 1; //Random menu
        String ImageFile = "resources/Order/" + number + ".png";
        OrderImage       = new MyImageIcon(ImageFile).resize(OrderWidth, OrderHeight);
        typeOfOrder      = number;
        OrderX = x;
        OrderY = y;
        setIcon(OrderImage);
        setBounds(OrderX, OrderY, OrderWidth, OrderHeight);
        setVisible(false);
    }

    public int getTypeOrder() {
        return typeOfOrder;
    }

    public boolean getIsServe() {
        return IsServe;
    }

    public void setIsServe(boolean s) {
        IsServe = s;
    }
};
/////////////////////////////////////////Class Owner ///////////////////////////////////////

class Owner extends JLabel {

    private int point = 0; // Earn from sell food
    private int currentFood = 0; //Type of Food that we make from Kitchen Frame (0 mean NoFood , 1-6 mean Food1,2,3,4,5,6)
    private int OwnerWidth =345, OwnerHeight=347;
    private String username; // get from Caret Listener
    private JTextField PointBar; // Update Point's TextField (Update Easier)
    private MyImageIcon Down,Up; // (Image of Normal Owner, Owner raise a cup)

    public Owner(String n, JTextField p,String[] a) {
        username = n;
        PointBar = p;
        PointBar.setText(Integer.toString(point));
        Down = new MyImageIcon(a[0]).resize(OwnerWidth, OwnerHeight);
        Up = new MyImageIcon(a[1]).resize(OwnerWidth, OwnerHeight);
        this.setBounds(49, 282, OwnerWidth, OwnerHeight);
        this.setIcon(Down);
        
    }
    synchronized public boolean CheckedCurrentFood(int order) {
      // if Thread that use function has type of order = type of food that Owner has -> return Is Serve = true and Make currentFood = 0 (NoFood)
        
        if (currentFood == order) {
            currentFood = 0;
            return true;

        } else {
            return false;
        }
    }
    synchronized public void changePoint(int a) {
        // increase & decrease point of Owner
        point = point + a;
        PointBar.setText(Integer.toString(point));
    }
    synchronized public int getPoint() {
        return point;
    }
    synchronized public void setFood(int food) {
        //Get food that we make in Kitchen Frame to myOwner
        currentFood = food;
      
    }
    
    public void setOwnerIcon(int x){
        if(x<0)setIcon(Down);
        else setIcon(Up);
    }
    public String getName() {
        return username;
    }
 
};
/////////////////////////////////////////Class MyButton (Customize) ///////////////////////////////////

class MyButton extends JButton {

    private int ButtonWidth = 177, ButtonHeight = 90;
    private MyImageIcon ButtonImg, ClickImg;

    public MyButton(String filename, int x, int y, String Clickfile) {
        ButtonImg = new MyImageIcon(filename).resize(ButtonWidth, ButtonHeight);
        ClickImg  = new MyImageIcon(Clickfile).resize(ButtonWidth, ButtonHeight);
        //------------ สำหรับปุ่มล่องหน -----------------
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        //------------------------------------------
        setBounds(x, y, ButtonWidth, ButtonHeight);
        setIcon(ButtonImg);

    }

    public MyButton(String filename, int x, int y, int w, int h) {
        ButtonWidth  = w;
        ButtonHeight = h;
        ButtonImg    = new MyImageIcon(filename).resize(ButtonWidth, ButtonHeight);
        //------------ สำหรับปุ่มล่องหน -----------------
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        //------------------------------------------
        setBounds(x, y, ButtonWidth, ButtonHeight);
        setIcon(ButtonImg);
    }

    public void setButtonColor(boolean c) {
        if (c) {  setIcon(ClickImg);   } 
        else   {  setIcon(ButtonImg);  }
    }
};

/////////////////////////////////////Class Coin //////////////////////////////////////
class Coin extends JLabel {
    
    private int CoinWidth = 50, CoinHeight = 50;
    private int curX = 0, curY = 0;
    private MyImageIcon CoinImage;
    private int value; // value of coin
    private boolean IsHarvest, move = false; //IsHarvest mean "Is coin keep?" (true -> coin is keep , earn income ,make coinLabel unvisible)

    public Coin() {
        Random random    =  new Random();
        int    number    =  random.nextInt(3) + 1; //Random Coin (we have 3 type -> 100,200,300)
        String ImageFile =  "resources/Coin/" + number + ".png";
        CoinImage        =  new MyImageIcon(ImageFile).resize(CoinWidth, CoinHeight);
        value = number * 100;
        setIcon(CoinImage);
        setBounds(curX, curY, CoinWidth, CoinHeight);
        setVisible(false);
    }

    public int getValue() {
        return value;
    }

    public boolean getIsHarvest() {
        return IsHarvest;
    }

    public void setIsHarvest(boolean s) {
        IsHarvest = s;
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }

    public void setCurX(int a) {
        curX = a;
    }

    public void setCurY(int a) {
        curY = a;
    }

    public void setMove(boolean s) {
        move = s;
    }

    public boolean getMove() {
        return move;
    }
};
/////////////-///////////////////////////Class Thread  ///////////////////////////////

class CustomerThread extends Thread {
    // 4 Threads for 4 Tables,Customers
    private Customer myCustomer; 
    private Table myTable;
    private Order myOrder;
    private Owner sharedOwner; //Shared all Thread
    private JLabel myFood;
    private Coin myCoin;

    public CustomerThread(Customer temp, Table t, Order o, Owner ow, JLabel f, Coin c) {
        myCustomer  = temp;
        myTable     = t;
        myOrder     = o;
        sharedOwner = ow;
        myFood = f;
        myCoin = c;
        this.start();
    }

    public void run() {
        CustomerWait(1000); //ThreadSleep
        CustomerWalk(1); 
        CustomerWait(3000);
        CustomerOrder();
        CustomerWait(9000);
        CustomerPay();
        CustomerWalk(-1);
        myTable.emptyTable();
    }
    public void CustomerWait(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   // value io -> (positive = move to Table / negative = move to Door)
    public void CustomerWalk(int io) {
        myCustomer.setCustomerMove(true);
        int targetX, targetY;
        if (io > 0){
            targetX = myTable.getX() - 5;
            targetY = myTable.getY() - 90;
        } 
        else{
            targetX = 525;
            targetY = 249;
        }
        do {

                if (myCustomer.getCurX() < targetX) {
                    myCustomer.setCurX(myCustomer.getCurX() + 1);
                }
                if (myCustomer.getCurX() > targetX) {
                    myCustomer.setCurX(myCustomer.getCurX() - 1);
                }
                if (myCustomer.getCurY() < targetY) {
                    myCustomer.setCurY(myCustomer.getCurY() + 1);
                }
                if (myCustomer.getCurY() > targetY) {
                    myCustomer.setCurY(myCustomer.getCurY() - 1);
                }
                if (myCustomer.getCurX() == targetX && myCustomer.getCurY() == targetY) {
                    myCustomer.setCustomerMove(false);
                }
            myCustomer.setLocation(myCustomer.getCurX(), myCustomer.getCurY());
            myCustomer.repaint(); //ป้องกันการกระตุกของการวาด
            try { Thread.sleep(10); } catch (InterruptedException e) {e.printStackTrace(); }

            } while (myCustomer.getCustomerMove());
        if (io < 0) {
            myCustomer.setVisible(false); //ลูกค้าออกจากร้าน
        }
    }
    
    
    public void CustomerOrder() {
        myOrder.setVisible(true); //Customer Order
        while (myOrder.getIsServe() == false) {
            CustomerWait(2000); //หาจังหวะให้เธรดทำงานเสดได้ไปต่อ 
            boolean x = sharedOwner.CheckedCurrentFood(myOrder.getTypeOrder()); //look more from class Customer (true mean serve food)
            myOrder.setIsServe(x);
        }
        myOrder.setVisible(false);
        MyImageIcon foodimg = new MyImageIcon("resources/Sushi/" + myOrder.getTypeOrder() + ".png").resize(80, 59); //add image to food on table
        myFood.setIcon(foodimg);
        myFood.setVisible(true);
    }

    public void CustomerPay() {
        myFood.setVisible(false);
        myCoin.setCurX(myTable.getX());
        myCoin.setCurY(myTable.getY());
        myCoin.setVisible(true);
        CoinFly(); // For make coin move
        myCoin.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                sharedOwner.changePoint(myCoin.getValue());
                myCoin.setIsHarvest(true);
                myCoin.setVisible(false);
            }
            public void mouseEntered(MouseEvent e) {
                myCoin.getParent().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                myCoin.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

    }
    // make coin move
    public void CoinFly() {
        myCoin.setMove(true);
        myCoin.setLocation(myTable.getX(), myTable.getY());
        int targetX = myTable.getX() - 40, targetY = myTable.getY();
        do {

            if ( myCoin.getCurX() > targetX ){
                 myCoin.setCurX(myCoin.getCurX() - 1);
                 myCoin.setCurY(myCoin.getCurY() - 1);
            }
            if ( myCoin.getCurX() <= targetX ){
                if ( myCoin.getCurY() != targetY) {
                     myCoin.setCurY(myCoin.getCurY() + 1);
                     myCoin.setCurX(myCoin.getCurX() - 1);
                } 
                else{
                     myCoin.setMove(false);
                }
            }

            myCoin.setLocation(myCoin.getCurX(), myCoin.getCurY());
            myCoin.repaint(); //ป้องกันการกระตุกของการวาด
            try {  Thread.sleep(7); } catch (InterruptedException e) {  e.printStackTrace();  }

        } while (myCoin.getMove());
    }
};

class MySoundEffect {
    private java.applet.AudioClip audio;
    public MySoundEffect(String filename) {
        try {
            java.io.File file = new java.io.File(filename);
            audio = java.applet.Applet.newAudioClip(file.toURL());
        } 
        catch (Exception e) {
                                e.printStackTrace();
                            }
    }
    public void playOnce() {
        audio.play();
    }
    public void playLoop() {
        audio.loop();
    }
    public void stop() {
        audio.stop();
    }
 
};
