package subscriptor;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriptor implements MqttCallback{
	
	     public Subscriptor() {
	     }

	     public static void main(String[] args){
	         try {
	             MqttClient client=new MqttClient("tcp://172.24.42.86:8083",MqttClient.generateClientId());
	             client.setCallback(new Subscriptor());
	             client.connect();
	             client.subscribe("protegemos/apt515/hub/cerradura1/alerta");
	             Scanner sc = new Scanner(System.in);
	             while(true) {
	            	 if(sc.hasNext()) {
	            		 MqttMessage message = new MqttMessage();
	    	             message.setPayload(sc.nextLine().getBytes());
	    	             client.publish("protegemos/apt515/hub/cerradura1/key", message);
	            	 }
	             }
	             
	         } catch (MqttException ex) {
	             //Logger.getLogger(Subscriptor.class.getName()).log(Level.SEVERE, null, ex);
	             ex.printStackTrace();
	         }
	         
	     } 

	     @Override
	     public void connectionLost(Throwable thrwbl) {
	         System.out.println("Se perdio la conexion al broker MQTT");//To change body of generated methods, choose Tools | Templates.
	     }

	     @Override
	     public void messageArrived(String string, MqttMessage mm) throws Exception {
	         System.out.println(mm.toString()); //To change body of generated methods, choose Tools | Templates.
	     }

	     @Override
	     public void deliveryComplete(IMqttDeliveryToken imdt) {
	         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	     }

}