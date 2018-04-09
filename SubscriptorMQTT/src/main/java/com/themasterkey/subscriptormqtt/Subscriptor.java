/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.themasterkey.subscriptormqtt;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;



/**
 *
 * @author jd.cardenas10
 */
public class Subscriptor implements MqttCallback{

    public Subscriptor() {
    }

    public static void main(String[] args){
        try {
            MqttClient client=new MqttClient("tcp://localhost:8083",MqttClient.generateClientId());
            client.setCallback(new Subscriptor());
            client.connect();
            client.subscribe("protegemos/apt515/hub/cerradura1/alerta");
            
        } catch (MqttException ex) {
            Logger.getLogger(Subscriptor.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
        }
        
    } 

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Se perdio la conexion al broker MQTT");//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {    
        String cadena=mm.toString();
        System.out.println(cadena);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
