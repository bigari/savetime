/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;
import userpkg.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Black_Shadow
 */
public abstract class Manager extends User {
   // private String token;
   // private final String email="savetime@gmail.com";
  //  private final String password="**savetimeisthebest**";
  //  protected Web w;

    public Manager() {
        super();
        super.setEmail("savetime@gmail.com");
        super.setPassword("**savetimeisthebest**");
        try {
            super.login();
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
  
}
