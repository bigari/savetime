/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui_user;

/**
 *
 * @author Black_Shadow
 */
import buvette.*;
import client.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static java.lang.Math.toIntExact;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.json.simple.parser.ParseException;



public class ui_clientController implements Initializable, ControlledScreen {
     ScreensController myController;
    
     
     private ClientManager clmanager;
     private CommandesManager comanager;
     private ProduitsManager promanager;
     private ToggleGroup toggleGroup = new ToggleGroup();
     private int idModAct;
     private SpinnerValueFactory<Double> valueFactory1;
     private java.util.Timer timer;
     private int last_notif;
     private final int nbNotifs =10;
     private boolean init;
     private EventHandler eventHand = new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            handleInit();
                        };
                    };
     private ScheduledService<ArrayList> svc ;
             
    @FXML
    private Label total_lbl, solde_lbl;
    @FXML private ImageView logout,im_commande,im_notif,im_exit; 
    @FXML private AnchorPane h_menu,h_notif,h_client, window;
    

    
    @Override
    public void setScreenParent(ScreensController screenParent){
        myController=screenParent;
    }
   @FXML private VBox v_boissons, v_pizzas, v_sandwichs, v_plats, v_plats_marocains, v_patisseries,v_panier;
   @FXML private VBox v_notif;
   @FXML private Button btn_validerCo;
   @FXML private JFXTimePicker time_co;
   @FXML
   private void handleCommande(MouseEvent event) throws IOException{
        int delay=0;
        Produit p;
       
        for(int i=2;i<v_panier.getChildren().size();i++){
            p=promanager.getProduitById((int)v_panier.getChildren().get(i).getUserData());
            if(delay<p.getDelais())
                delay=p.getDelais();
        }
        
        
        //T t = new T();
        LocalTime timeS =  time_co.getValue();
        LocalTime now = LocalTime.now();
         
      //  java.sql.Time gettedTimePickerTime = java.sql.Time.valueOf(time_co.getValue());

       // java.util.Date today = new java.util.Date();
        int deadl = (timeS.getHour() - now.getHour())*60 + (timeS.getMinute()-now.getMinute()); 
         //long tim=  (today.getTime()-gettedTimePickerTime.getTime())/60000;
        //  long deadl = ecart;
        System.out.println(deadl);
        if(Double.parseDouble(total_lbl.getText())<=Double.parseDouble(solde_lbl.getText()) ){

        if(deadl>=delay){
            System.out.println("good dlay");
            Commande c;
            ArrayList<Item> itlist= new ArrayList();
            HBox hb1;
            for(int i=2;i<v_panier.getChildren().size();i++){
                p=promanager.getProduitById((int)v_panier.getChildren().get(i).getUserData());
                hb1=(HBox)(v_panier.getChildren().get(i));
                Spinner lqte=(Spinner)hb1.getChildren().get(2);
                int qte=(int)lqte.getValue();
                JFXTextField txt_rmq=(JFXTextField)hb1.getChildren().get(3);
                String rmq=txt_rmq.getText();

               Item it= new Item(p.getId(),rmq,qte);
                itlist.add(it);
            }
            double tot;
            tot= Double.parseDouble(total_lbl.getText());
            c= new Commande(itlist,ClientTransfert.get().getId(),tot,deadl);
            ClientTransfert.get().commander(c);
            solde_lbl.setText(String.valueOf(ClientTransfert.get().getSolde()));
            v_panier.getChildren().remove(2, v_panier.getChildren().size());
            total_lbl.setText("0");

        }
        else
            System.out.println("Délais insuffisant veuillez changer le temps choisi ");

        } 
        else
        {
          System.out.println("Désolé vous ne pouvez passez cette commande votre solde est insuffisant");
        }
   
   }
    @FXML
   private void handleInit(){
        window.removeEventHandler(MouseEvent.MOUSE_MOVED, 
                   eventHand );
        init=true;
        placeNotifs(last_notif, last_notif+ nbNotifs);
        svc.start();
   }
 
    
     @FXML
     private void handleButtonAction(MouseEvent event) throws IOException{
         if(event.getTarget()==im_commande){
             placeProd();
             h_menu.setVisible(true);
             h_notif.setVisible(false);
         }
         else
             if(event.getTarget()==im_notif){
                h_menu.setVisible(false);
             h_notif.setVisible(true);
             }
         else
             if(event.getTarget()==im_exit){
                 System.exit(0);
             }
         else
             if(event.getTarget()==logout){
                myController.setScreen(UI_user.ui_userID);
                
             }
     }
     public void handleQuantite(Spinner sp,Integer oldValue, Integer newValue){
         
         if(newValue!=oldValue)
         {
            HBox hb = (HBox)sp.getParent();
           Label lprix=(Label)hb.getChildren().get(1);
           Double prix=Double.parseDouble(lprix.getText());
           Double total = Double.parseDouble(total_lbl.getText());
           total=total+(newValue-oldValue)*prix;
           total_lbl.setText(total.toString());
         
         }
        /* else{
             VBox vb = (VBox)hb.getParent();
             vb.getChildren().remove(hb);
         }*/
     }
     public void handleChoix (MouseEvent event) throws IOException{
         Double total = Double.parseDouble(total_lbl.getText());
         
         JFXButton b= (JFXButton)event.getTarget();
          Integer ind = Integer.parseInt(b.getUserData().toString());
                   Produit p = promanager.produits.get(ind.intValue());
                   for(int i=2; i<v_panier.getChildren().size(); i++)
                   {
                       if(p.getId()==(int)v_panier.getChildren().get(i).getUserData())
                       {
                          
                           HBox hb=(HBox)v_panier.getChildren().get(i);
                          // System.out.println(hb.getChildren().get(0).getClass());
                          Spinner squant= (Spinner) hb.getChildren().get(2);
                          Integer quant = (Integer)squant.getValue();
                          total-=p.getPrix()*quant;
                           total_lbl.setText(total.toString());
                            v_panier.getChildren().remove(i);
                           return;
                       }
                   }
                   HBox hPanier = new HBox(10);
                   
                   
                   hPanier.setUserData(p.getId());
                   Label lDes = new Label(p.getNom());
                   lDes.setMinSize(136, 28);
                   Label lPrix = new Label(String.valueOf  ((p.getPrix())));
                   lPrix.setMinSize(60, 30);
                   
                   Spinner<Integer> qte = new Spinner();
                   
                           SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1,1);
                qte.setValueFactory(valueFactory);
                qte.setMinSize(87, 29);
                qte.valueProperty().addListener((obs, oldValue, newValue) -> 
                        handleQuantite(qte,oldValue,newValue)
                );
                
                JFXTextField txt_observations = new JFXTextField();
                txt_observations.setMinSize(126, 28);
                
                hPanier.getChildren().addAll(lDes,lPrix,qte,txt_observations);
                v_panier.getChildren().add(hPanier);
                total+=p.getPrix();
                total_lbl.setText(total.toString());
  
         
     }
      public void handlePlus(){
          placeNotifs(last_notif, last_notif+ nbNotifs);
      }
    
      private void placeProd(){
        try {
            
            promanager.getProduits();
            System.out.println(promanager.produits);
            Produit p;
            v_pizzas.getChildren().remove(0,v_pizzas.getChildren().size() );
             v_boissons.getChildren().remove(0,v_boissons.getChildren().size() );
              v_sandwichs.getChildren().remove(0,v_sandwichs.getChildren().size() );
               v_plats.getChildren().remove(0,v_plats.getChildren().size() );
                v_plats_marocains.getChildren().remove(0,v_plats_marocains.getChildren().size() );
                 v_patisseries.getChildren().remove(0,v_patisseries.getChildren().size() );
                 
            VBox boxProd = new VBox();
            for (int i=0; i<promanager.produits.size();i++){
                p=promanager.produits.get(i);
                if(p.getDisponibilite())
                {
                HBox hProd = new HBox(25);
               
                
                //JFXButton rb = new JFXButton("+|-");
                JFXButton prod =new JFXButton("    "+p.getNom());
                prod.autosize();
                prod.setUserData(Integer.toString(i));
                prod.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            handleChoix(event);
                        } catch (IOException ex) {
                            Logger.getLogger(ui_clientController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                hProd.getChildren().add(prod);
               // hProd.getChildren().add(rb);
                
                switch(p.getCategorie()){
              
                    case "pizzas":
                        
                        v_pizzas.getChildren().add(hProd);
                        //rb.setToggleGroup(toggleGroup);
                        //lbl.setVisible(true);
                        break;
                    case "boissons":
                        v_boissons.getChildren().add(hProd);
                       // rb.setToggleGroup(toggleGroup);
                        break;
                    case "sandwichs":
                        v_sandwichs.getChildren().add(hProd);
                       // rb.setToggleGroup(toggleGroup);
                        break;
                    case "plats":
                        v_plats.getChildren().add(hProd);
                      //  rb.setToggleGroup(toggleGroup);
                        break;
                    case "plats_marocains":
                        v_plats_marocains.getChildren().add(hProd);
                    //    rb.setToggleGroup(toggleGroup);
                        break;
                    case "patisseries":
                        v_patisseries.getChildren().add(hProd);
                      //  rb.setToggleGroup(toggleGroup);
                        break;                        
                }
            }
            }

        } catch (IOException ex) {
            Logger.getLogger(ui_clientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private ArrayList notifyClient(Commande c){
        if(c==null)
            return null;
        String notif;
        Boolean fromUser = false;
        ArrayList ret = new ArrayList();
        
      //  for (Commande _comm_in: list_comm) {
            
      //      if(c.getEtat()<4){
            fromUser=false;
            switch (c.getEtat()) {
                    case 0:
                        fromUser=true;
                        notif = "Votre commande "+c.getCode()+" a eté enregistrée\nTotal: "+c.getTotal()+"\n\n"+c.getDate();
                        break;
                    case 1:
                        notif = "Votre commande "+c.getCode()+" a eté acceptée\nTotal: "+c.getTotal()+"\n\n"+c.getDate();
                        break;
                    case 2:
                        notif = "Votre commande "+c.getCode()+" a eté refusée\nRaison: "+c.getObservations()+"\n\n"+c.getDate();
                        break;
                    case 3:
                        notif = "Votre commande "+ c.getCode()+" est prête\nTotal: "+ c.getTotal()+"\n\n"+ c.getDate();
                        break;
                    default:
                        notif="";
                        break;
            }
            
            TextArea txt = new TextArea(notif);
            txt.setEditable(false);
            txt.setMinHeight(Control.USE_PREF_SIZE);
            txt.setPrefHeight(100);
            txt.setFont(Font.font ("Verdana", 13));
            txt.setStyle("-fx-border-color:   #330000;"+
                // "-fx-background-color:   #330000;"+ 
                 "-fx-border-radius: 2em");
            System.out.println("tryna notify "+ v_notif);
      
       //     }
        //}
      //  System.out.println(last_notif);
      ret.add(fromUser);
      ret.add(txt);
      return ret;
    }
   
    
    private void placeNotifs(int from, int to){
        try {
            File file = new File("notifications"+ClientTransfert.get().getId()+".txt");
            if (!file.exists()) {
                        file.createNewFile();
                }
           // getResourceAsFile("client/notifications"+ClientTransfert.get().getId()+".txt")
            ReversedLinesFileReader fr = new ReversedLinesFileReader(file);
            String notif_line = fr.readLine();
            String notif;
            boolean fromUser; 
            int i;
            for(i =0;notif_line!=null && i<to; i++)
            {
                
                fromUser=false;
                if(i>=from ){
                    String[] sep = notif_line.split("\\|");
                    if("0".equals(sep[3]) || "1".equals(sep[3]) || "2".equals(sep[3]) || "3".equals(sep[3])){
                    /*
                    0->date  1->code  2->total  3->etat  4->obsverationsGerant
                    */
                    switch (sep[3]) {
                        case "0":
                            fromUser=true;
                            notif = "Votre commande "+sep[1]+" a eté enregistrée\nTotal: "+sep[2]+"\n\n"+sep[0];
                            break;
                        case "1":
                            notif = "Votre commande "+sep[1]+" a eté acceptée\nTotal: "+sep[2]+"\n\n"+sep[0];
                            break;
                        case "2":
                            notif = "Votre commande "+sep[1]+" a eté refusée\nRaison: "+
                                    (sep.length==5?sep[4]:" ")+"\n\n"+sep[0];
                            break;
                        case "3":
                            notif = "Votre commande "+sep[1]+" est prête\nTotal: "+sep[2]+"\n\n"+sep[0];
                            break;
                        default:
                            notif="";
                            break;
                    }

                TextArea txt = new TextArea(notif);
                txt.setEditable(false);
                txt.setMinHeight(Control.USE_PREF_SIZE);
                txt.setPrefHeight(100);
                txt.setFont(Font.font ("Verdana", 13));
                txt.setStyle("-fx-border-color:   #330000;"+
                    // "-fx-background-color:   #330000;"+ 
                     "-fx-border-radius: 2em");
                System.out.println("placed in"+ v_notif);
                v_notif.getChildren().add(0,txt);
                if(fromUser){
                    txt.setPadding(new Insets(5,10,5,5));
                    txt.setStyle( "-fx-background-color: #709faf;");
                    VBox.setMargin(txt, new Insets(0,0,0,100));
                }
                else{
                    txt.setStyle( "-fx-background-color: #330000;");
                    txt.setPadding(new Insets(5,5,5,10));
                    VBox.setMargin(txt, new Insets(0,100,0,0));   
                }
                    
                }
                }

                notif_line=fr.readLine();
            }
        last_notif=i;
        System.out.println(last_notif);
        fr.close();
        } catch (IOException ex) {
            Logger.getLogger(ui_clientController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public void place_fetched_notif(ArrayList nl){
     // for(Object txt:nl){
            
            if (nl==null || nl.size()==0)
                return;
            TextArea txt = (TextArea)nl.get(1);  
            this.v_notif.getChildren().add(v_notif.getChildren().size()-1,txt); 
            System.out.println("notified");
            Boolean fromUser = (Boolean)nl.get(0);
            if(fromUser){
                txt.setPadding(new Insets(5,10,5,5));
                txt.setStyle( "-fx-background-color: #709faf;");
                VBox.setMargin(txt, new Insets(0,0,0,100));
            }
            else{
                txt.setStyle( "-fx-background-color: #330000;");
                txt.setPadding(new Insets(5,5,5,10));
                VBox.setMargin(txt, new Insets(0,100,0,0));   
            }
          
            last_notif++;
     // }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //clmanager = new ClientManager();
        init=false;
        last_notif = 0;
        window.addEventHandler(MouseEvent.MOUSE_MOVED, 
                   eventHand );
        svc = new ScheduledService<ArrayList>() {
        protected Task<ArrayList> createTask() {
            return new Task<ArrayList>() {
                protected ArrayList call() {
                   ArrayList notifs_list = new ArrayList();
                   if (init){
                        try {
                        notifs_list=notifyClient(ClientTransfert.get().getNotification());
                        } catch (IOException | ParseException ex) {
                        Logger.getLogger(ui_clientController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    return notifs_list;
                }
            };
            }   
        };
        svc.setOnSucceeded(event -> place_fetched_notif(svc.getValue()));
        svc.setPeriod(Duration.seconds(5));
        //svc.start();
        
        time_co.setIs24HourView(true);
        promanager = new ProduitsManager();
        comanager = new CommandesManager();
        placeProd();
        System.out.println("yoo");
        
    }
    
}
