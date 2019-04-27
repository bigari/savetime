/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;



/**
 *
 * @author Black_Shadow
 */
public class ClientTransfert {
    private static Client cl;
    private static boolean placed;
    
    public static  void set(Client cli){
        cl=cli;
        placed=true;
    }
    public static Client get(){
        if(placed)
        return cl;
        else{
            return new Client("","","",0.0);
            
        }
    }
    
}
