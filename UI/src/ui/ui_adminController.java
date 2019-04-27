package ui;

import buvette.*;
import client.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Black_Shadow
 */
public class ui_adminController implements Initializable {
    
    @FXML
    private Label label;
    
   /* @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }*/
    
     @FXML private TableView table_client, table_recette;
     @FXML private TableColumn col_id, col_pro, col_recette;
     @FXML private TableColumn col_nom;
     @FXML private TableColumn col_email;
     @FXML private TableColumn col_solde;
     /* @FXML private TableColumn colP;
     @FXML private Label labP;*/
     @FXML private JFXTextField tf_nom_c, tf_email_c, tf_solde_min, tf_solde_max;
     @FXML
     private ImageView btn_menu, btn_cmd, btn_client,btn_compta,btn_hide,btn_retour,btn_back,btn_down;
     @FXML private TextField adNom,modNom,newNom,newEmail,newSolde,email,solde_inc;
     @FXML private JFXPasswordField pass,pass_c;
     @FXML private ComboBox adCat,modCat;
     @FXML private CheckBox adDis,modDis;
     @FXML private Spinner<Double> adPrix,modPrix;
     @FXML private VBox v_commande;
     @FXML private DatePicker pickDate;
     @FXML private DatePicker datePi;
     @FXML private Label l_total_compta, l_total_solde;
    
     @FXML
     private JFXButton btn_add,btn_hist, adVal, adAn;
     //@FXML private Button adAn,;
     @FXML private VBox v_boissons, v_pizzas, v_sandwichs, v_plats, v_plats_marocains, v_patisseries, v_hist;
      @FXML
     private AnchorPane h_menu, h_cmd, h_client,h_compta,topbar,h_ajout,h_hist, h_nouveau, h_modPass;
     
     private ClientManager clmanager;
     private CommandesManager comanager;
     private ProduitsManager promanager;
     private ToggleGroup toggleGroup = new ToggleGroup();
     private int idModAct;
     private SpinnerValueFactory<Double> valueFactory1;
     private ScheduledService<ArrayList<Commande>> svc ;
     private ScheduledService<String> svc2 ;
     

 
//************************************Handlers*************************************************     
     @FXML
     private void handleButtonAction(MouseEvent event) throws IOException{
         if(event.getTarget()==btn_menu){
             h_menu.setVisible(true);
             h_cmd.setVisible(false);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
             h_hist.setVisible(false);
            btn_hide.setVisible(true);
            btn_down.setVisible(false);
         }
         else
             if(event.getTarget()==btn_cmd){
                 h_menu.setVisible(false);
             h_cmd.setVisible(true);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
             h_hist.setVisible(false);
                          btn_hide.setVisible(true);
            btn_down.setVisible(false);
             }
         else
             if(event.getTarget()==btn_client){
                try {
                    clmanager.getClients();
                } catch (IOException ex) {
                    Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(clmanager.clients);
                table_client.getItems().remove(0,table_client.getItems().size());
                table_client.getItems().addAll(clmanager.clients);
                h_menu.setVisible(false);
             h_cmd.setVisible(false);
             h_client.setVisible(true);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
             h_hist.setVisible(false);
                          btn_hide.setVisible(true);
            btn_down.setVisible(false);
             }
         else
             if(event.getTarget()==btn_compta){
                 h_menu.setVisible(false);
             h_cmd.setVisible(false);
             h_client.setVisible(false);
             h_compta.setVisible(true);
             h_ajout.setVisible(false);
             h_hist.setVisible(false);
                          btn_hide.setVisible(true);
            btn_down.setVisible(false);
             }
         else
             if(event.getTarget()==btn_hide){
                 h_menu.setVisible(false);
             h_cmd.setVisible(false);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
            h_hist.setVisible(false);
            btn_down.setVisible(true);
            btn_hide.setVisible(false);
             }
         else
             if(event.getTarget()==btn_add){
                 h_menu.setVisible(false);
             h_cmd.setVisible(false);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(true);
            h_hist.setVisible(false);
                         btn_hide.setVisible(true);
            btn_down.setVisible(false);
             }
         else
             if(event.getTarget() == btn_retour){
                 h_menu.setVisible(true);
                 placeProd();
             h_cmd.setVisible(false);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
            h_hist.setVisible(false);
                         btn_hide.setVisible(true);
            btn_down.setVisible(false);
             }
         else
             if(event.getTarget() == btn_back){
                 h_menu.setVisible(false);
             h_cmd.setVisible(true);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
             h_hist.setVisible(false);
                          btn_hide.setVisible(true);
            btn_down.setVisible(false);
            
             }
         else
             if(event.getTarget() == btn_hist){
                 h_menu.setVisible(false);
             h_cmd.setVisible(false);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
             h_hist.setVisible(true);
             btn_hide.setVisible(true);
            btn_down.setVisible(false);
             }
         else
             if(event.getTarget() == btn_down){
                 h_menu.setVisible(true);
             h_cmd.setVisible(false);
             h_client.setVisible(false);
             h_compta.setVisible(false);
             h_ajout.setVisible(false);
             h_hist.setVisible(false);
             btn_hide.setVisible(true);
            btn_down.setVisible(false);
             }
      //  /* else
        /* else
             if(event.getTarget() == adVal){
            Produit p = new Produit(adNom.getText(),Float.parseFloat(adPrix.getValue().toString()),
                    adDis.isSelected(),adCat.getSelectionModel().getSelectedItem().toString(),2);
            System.out.println(p);
             }*/
     }
    @FXML
    private void handleModOk(MouseEvent event) throws IOException {
       Produit p = promanager.produits.get(idModAct);
       if(modCat.getSelectionModel().getSelectedItem()!=null)
           p.setCategorie(modCat.getSelectionModel().getSelectedItem().toString());
       
       p.setDisponibilite(modDis.isSelected());
       p.setNom(modNom.getText());
       p.setPrix(modPrix.getValue());
         
       promanager.modifierProduit(p);
        
    }
    @FXML
    private void handleModSup(MouseEvent event) {
        /*try {
        Produit p = promanager.produits.get(idModAct);
        promanager.supprimerProduit(p);
        } catch (IOException ex) {
        Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    
    @FXML
    private void handleClose(MouseEvent event) {
        try {
            for(Object obj: v_commande.getChildren())
            {
                HBox hb= (HBox)obj;
                Commande co = (Commande)hb.getUserData();
                comanager.resetEtat(co);
            }
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   @FXML
    private void handleAdAn(MouseEvent event){
        adNom.setText("");
        h_ajout.setVisible(false);
        h_menu.setVisible(true);
    }
     @FXML
    private void handleAdVal(MouseEvent event) throws IOException{
    //    //String nom, float prix, boolean disponibilite, String categorie, int delais
      Produit p = new Produit(adNom.getText(),adPrix.getValue(),adDis.isSelected(),adCat.getSelectionModel().getSelectedItem().toString(),2);
      System.out.println(p);
      promanager.addNewProduit(p);
      promanager.getProduits();
      
    }
    @FXML
     private void handleNouveau(MouseEvent event) throws IOException{
          h_client.setVisible(false);
          h_nouveau.setVisible(true);
    
    }
     @FXML
    private void handleCmodifier(MouseEvent event) throws IOException{
          h_client.setVisible(false);
          h_modPass.setVisible(true);
   
    }
    @FXML
    private void handleNewVal(MouseEvent event) throws IOException{
        if(newNom.getText()!=null && newEmail.getText()!=null && newSolde.getText()!=null && pass_c.getText()!=null ){
            
            try {
                Double s= Double.parseDouble(newSolde.getText());
                //Client(String name, String email, String password, Double solde)
                Client c= new Client(newNom.getText(),newEmail.getText(),pass_c.getText(),s);
                clmanager.addNewClient(c);
            }
           catch (IOException | NumberFormatException e){
                System.out.println("flop");
            }
        }
   
        h_client.setVisible(true);
        h_nouveau.setVisible(false);
    }
    @FXML
    private void handleNewAn(MouseEvent event) throws IOException{
          h_client.setVisible(true);
          h_nouveau.setVisible(false);
   
    }
    @FXML
    private void handleChangVal(MouseEvent event) throws IOException{
        
        if(email.getText()!=null && email.getText().length()!=0 && pass.getText()!=null && pass.getText().length()!=0)
        {
            clmanager.setPass(email.getText(), pass.getText());
            h_client.setVisible(true);
            h_modPass.setVisible(false);
        }
        else{
            // les champs ne sont pas rempli
        }
       
    } 
    @FXML
    private void handleChangAn(MouseEvent event) throws IOException{
        h_client.setVisible(true);
        h_modPass.setVisible(false);
   
    }
    @FXML
    private void handleRechercherC(MouseEvent event) throws IOException {
        
        String nom = tf_nom_c.getText();
       // nom= nom=="Nom"?"":nom;
        String email_rech = tf_email_c.getText();
       // email= email=="Email"?"":email;
        Double solde_min;
        Double solde_max;
     //   System.out.println(tf_nom_c.getText().length()+ " "+ tf_email_c.getText().length()+" "+tf_solde_min.getText().length()+" "+ tf_solde_max.getText().length());
        try {
             solde_min= Double.parseDouble(tf_solde_min.getText());
        }
        catch (NumberFormatException e) {
            solde_min =Double.NEGATIVE_INFINITY;
        }
        try {
             solde_max= Double.parseDouble(tf_solde_max.getText());
             
        }
        catch (NumberFormatException e) {
            solde_max = Double.MAX_VALUE;
        }
        
        table_client.getItems().remove(0,table_client.getItems().size());
        table_client.getItems().addAll(clmanager.filtre(nom, email_rech, solde_min.doubleValue(), solde_max.doubleValue()));
    }
    @FXML
     private void handleRecharge(MouseEvent event) throws IOException {
        
        Double solde_plus=0.;
        
        if(email.getText()!=null &&email.getText().length()!=0)
        {
        try {
             solde_plus= Double.parseDouble(solde_inc.getText());
             clmanager.incrementerSolde(email.getText(), solde_plus);
             h_client.setVisible(true);
             h_modPass.setVisible(false);
             
        }
        catch (NumberFormatException e) {
           // solde_min =Double.NEGATIVE_INFINITY;
        }
      
        } 
        else {
            // vous devez entrez un email
        }
       
    }
     private void handle_change_etat(Button btn){
         
         ComboBox c = (ComboBox) btn.getUserData();
         int etat = c.getSelectionModel().getSelectedIndex() ;
         HBox row = (HBox) c.getUserData();
         Commande com = (Commande)row.getUserData();
        
         
        try {
            if(etat==2){
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(
                        getClass().getResource("ui_refus.fxml"));
                System.out.println(fxmlLoader);
                Parent parent = (Parent) fxmlLoader.load();

                ui_refusController childController = fxmlLoader.getController();
                 
                
                /*childController.currentInputProperty().addListener((obs, oldInput, newInput) -> {
                com.setObservations(newInput);
                });*/

                Scene scene = new Scene(parent);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parent.getScene().getWindow());
                stage.setScene(scene);
                stage.resizableProperty().setValue(false);
                
                childController.getBtn_ok().setOnAction(new EventHandler() {
              
                    @Override
                    public void handle(Event event) {
                        //childController.handleOK();
                        com.setObservations(childController.getInput().getText());
                        stage.close();
                        
                    }
                });
                
                stage.showAndWait();
            }
            if(etat==2 || etat==4){
                com.setEtat(etat);
                comanager.formatComm(com, promanager);
                v_commande.getChildren().remove(row);
                
            }
            comanager.setEtat(com, etat);
        } catch (IOException | org.json.simple.parser.ParseException ex) {
            Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
         
     }
     
     @FXML
     private void handleGo(MouseEvent event) throws IOException {
        
        LocalDate value = pickDate.getValue();
        if(value!=null){
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = parser.parse(value.toString());
                placeHisto(date);
            } catch (ParseException ex) {
                Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            placeHisto(null);
        }
        System.out.println(value);
       
    }
     
    @FXML
    private void handle_recette(MouseEvent event) throws IOException{
        
       // System.out.println(datePi);
        LocalDate value = datePi.getValue();
        if(value!=null){
   
            table_recette.getItems().remove(0,table_recette.getItems().size());
            table_recette.getItems().addAll(comanager.comptaParProduit(value.toString()));
            l_total_compta.setText(String.valueOf(comanager.comptaTotal(value.toString())));
           
        }
        else{
            
        }
        
      
   
    }
    
    @FXML
    private void handle_soldeC(MouseEvent event) throws IOException{
      
        l_total_solde.setText(String.valueOf(clmanager.getTotalSolde()));
    }
     
     
    
  //***************************************End_Handlers**********************************************************
     
    private void placeHisto(Date d){
        try {
            File file = new File("commandes.txt");
            if (!file.exists()) {
                        file.createNewFile();
                }
           // getResourceAsFile("client/notifications"+ClientTransfert.get().getId()+".txt")
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String c_line = br.readLine();
            String r_line;
           // ArrayList<HistLine> resul = new ArrayList();
            int i;
            boolean toAdd=false;
            v_hist.getChildren().remove(0, v_hist.getChildren().size());
            for(i =0;c_line!=null ; i++)
            {
                HBox hComm = new HBox(10);
            //  hComm.setUserData(comm);
                hComm.setPadding(new Insets(5,15,0,0));
                hComm.setMinHeight(Control.USE_PREF_SIZE);
                hComm.setPrefHeight(120);
                
                r_line=c_line.replace("@#%", "\n");
                String[] sep = r_line.split("\\|");
                System.out.println(r_line);
                System.out.println(sep[0]);
                System.out.println(sep[4]);
                System.out.println(sep.length);
                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                Date date = parser.parse(sep[0]);
                if(d !=null && (date.toString().equals(d.toString()))){
                  System.out.println("matched");
                 // HistLine hl= new HistLine(sep[1],sep[4],Double.parseDouble(sep[2]),sep[3]=="2"?"Non":"Oui");
                 // resul.add(hl);
                 toAdd=true;
                    
                }
                else if(d==null){
                  //  HistLine hl= new HistLine(sep[1],sep[4],Double.parseDouble(sep[2]),sep[3]=="2"?"Non":"Oui");
                   // resul.add(hl);
                   toAdd=true;
                }
              
               if(toAdd){
               
                    JFXTextArea tdetails = new JFXTextArea(sep[4]);
                    tdetails.setPrefWidth(180);
                    tdetails.setMaxWidth(Control.USE_PREF_SIZE);

                    Label lCode = new Label(sep[2]);
                    Label lTotal = new Label(sep[1]);
                    Label lDelivree = new Label(sep[3]=="2"?"Non":"Oui");
                    lCode.setMinWidth(170);
                    lTotal.setMinWidth(122);
                    
                    hComm.getChildren().addAll(tdetails, lTotal,lCode, lDelivree);
                   
                    HBox.setMargin(tdetails, new Insets(19,0,0,0));
                    HBox.setMargin(lCode, new Insets(19,0,0,0));
                    HBox.setMargin(lTotal, new Insets(19,0,0,0));
                    HBox.setMargin(lDelivree, new Insets(19,0,0,0));
                
                    v_hist.getChildren().add(hComm);
               }
               
               c_line=br.readLine();
                
            }
       
        br.close();
        } catch (IOException ex) {
            Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    
    private void placeNewCommandes (ArrayList<Commande> comms) {
        try {
           
            
            for(Commande comm: comms){
                HBox hComm = new HBox(13);
                hComm.setUserData(comm);
                hComm.setPadding(new Insets(5,15,0,0));
                hComm.setMinHeight(Control.USE_PREF_SIZE);
                hComm.setPrefHeight(120);
                
                String item_lines = "";

                for(Item it: comm.getProduits()) {

                    item_lines+="+"+promanager.getProduitById(it.getProduit_id()).getNom()+
                                ": "+it.getQuantite()+" "+it.getRemarq()+"\n";
                 }
                
                TextArea tdetails = new TextArea(item_lines);
                tdetails.autosize();
               // tdetails.setPadding(new Insets(5,10,5,5));
               //reusable
                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = parser.parse(comm.getDate());
                Date today = new Date();
                long t_restant =comm.getDeadline()-((today.getTime()/60000) - (date.getTime()/60000));
                t_restant = t_restant<0?0:t_restant;
                Label ltr = new Label(String.valueOf(t_restant)); //setText...
               //***************************************************
               
              
               
                Label lCode = new Label(comm.getCode());
                Label lTotal = new Label(String.valueOf(comm.getTotal()));
                ObservableList<String> options = 
                FXCollections.observableArrayList(
                    "En attente",
                    "Acceptée",
                    "Refusée",
                    "Prêt",
                    "Servi"
                );
                ComboBox cetat = new ComboBox(options);
                cetat.setUserData(hComm);
                cetat.getSelectionModel().select(comm.getEtat());
                
               
                tdetails.setPrefWidth(240);
                tdetails.setMaxWidth(Control.USE_PREF_SIZE);
                
                
                lCode.setMinWidth(80);
                lTotal.setMinWidth(122);
                ltr.setMinWidth(90);
                
                VBox cont = new VBox(20);
                Button changer = new Button("Changer");
                changer.setUserData(cetat);
                cont.getChildren().addAll(changer, cetat);
                
                hComm.getChildren().addAll(tdetails, lTotal, ltr,lCode, cont);
                HBox.setMargin(cont, new Insets(15,0,0,0));
                HBox.setMargin(tdetails, new Insets(19,0,0,0));
                HBox.setMargin(lCode, new Insets(19,0,0,0));
                HBox.setMargin(lTotal, new Insets(19,0,0,0));
                HBox.setMargin(ltr, new Insets(19,0,0,0));
                
                v_commande.getChildren().add(hComm);
                
                changer.setOnAction(new EventHandler() {
              
                    @Override
                    public void handle(Event event) {
                        handle_change_etat((Button)event.getSource());
                    }
                });
                
                
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
     
    private void displayMod(Integer n){
        
        idModAct=n;
        Produit p = promanager.produits.get(n);
        modNom.setText(p.getNom());
        valueFactory1.setValue(p.getPrix());
        
        modDis.setSelected(p.getDisponibilite());
        
        
    }
  
    
    private void connect_tab_c(){
        col_id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        col_email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        col_solde.setCellValueFactory(new PropertyValueFactory<Client, Double>("solde"));
    }
    private void connect_tab_r(){
        col_pro.setCellValueFactory(new PropertyValueFactory<ProRecettes, String>("nom"));
        col_recette.setCellValueFactory(new PropertyValueFactory<ProRecettes, Double>("total"));
        
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
                HBox hProd = new HBox(25);
               
                p=promanager.produits.get(i);
                RadioButton rb = new RadioButton();
                Label labelProd =new Label(p.getNom()+" ");
                labelProd.autosize();
                rb.setUserData(Integer.toString(i));
                hProd.getChildren().addAll(labelProd, rb);
               // hProd.getChildren().add(rb);
                
                switch(p.getCategorie()){
              
                    case "pizzas":
                        
                        v_pizzas.getChildren().add(hProd);
                        rb.setToggleGroup(toggleGroup);
                        //lbl.setVisible(true);
                        break;
                    case "boissons":
                        v_boissons.getChildren().add(hProd);
                        rb.setToggleGroup(toggleGroup);
                        break;
                    case "sandwichs":
                        v_sandwichs.getChildren().add(hProd);
                        rb.setToggleGroup(toggleGroup);
                        break;
                    case "plats":
                        v_plats.getChildren().add(hProd);
                        rb.setToggleGroup(toggleGroup);
                        break;
                    case "plats_marocains":
                        v_plats_marocains.getChildren().add(hProd);
                        rb.setToggleGroup(toggleGroup);
                        break;
                    case "patisseries":
                        v_patisseries.getChildren().add(hProd);
                        rb.setToggleGroup(toggleGroup);
                        break;                        
                }
            }
            
            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                 if (toggleGroup.getSelectedToggle() != null) {

                     System.out.println(toggleGroup.getSelectedToggle().getUserData().toString());
                     displayMod(Integer.parseInt(toggleGroup.getSelectedToggle().getUserData().toString()));


                 }

             } 
            });
        } catch (IOException ex) {
            Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void decrement() {
        
        for (Object obj:v_commande.getChildren()){
            HBox hb =(HBox)obj;
            Label lb = (Label)hb.getChildren().get(2);
            Integer d = Integer.parseInt(lb.getText()) -1;
            lb.setText(String.valueOf(d>0?d:0));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList categ = FXCollections.observableArrayList(
            "pizzas", "boissons", "sandwichs", "plats", "plats_marocains", "patisseries");
        adCat.setItems(categ);
        ObservableList categ1 = FXCollections.observableArrayList(
            "pizzas", "boissons", "sandwichs", "plats", "plats_marocains", "patisseries");
   
        modCat.setItems(categ1);
        
        SpinnerValueFactory<Double> valueFactory = 
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 70, 3, 0.5);
        adPrix.setValueFactory(valueFactory);
        valueFactory1 = 
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 70, 0, 0.5);
        modPrix.setValueFactory(valueFactory1);
        clmanager = new ClientManager();
        promanager = new ProduitsManager();
        comanager = new CommandesManager();
        
        placeProd();
        connect_tab_c();
        connect_tab_r();
        
         svc = new ScheduledService<ArrayList<Commande>>() {
        protected Task<ArrayList<Commande>> createTask() {
            return new Task<ArrayList<Commande>>() {
                protected ArrayList<Commande> call() {
                   
                   
                        try {
                            comanager.getNewComm();
                        } catch (IOException ex) {
                        Logger.getLogger(ui_adminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                    return comanager.commandes;
                }
            };
            }   
        };
        svc.setOnSucceeded(event -> placeNewCommandes(svc.getValue()));
        svc.setPeriod(Duration.seconds(5));
        svc.start();
        
        svc2 = new ScheduledService<String>() {
        protected Task<String> createTask() {
            return new Task<String>() {
                protected String call() {
                   
                        
                   
                    return "";
                }
            };
            }   
        };
        svc2.setOnSucceeded(event -> decrement());
        svc2.setPeriod(Duration.seconds(60));
        svc2.setDelay(Duration.seconds(5));
        svc2.start();
        
    }      
    
}
