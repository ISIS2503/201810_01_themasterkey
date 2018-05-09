package subscriptormqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.util.Date;
import java.util.Scanner;

public class Subscriptor implements MqttCallback {

    static final String ip = "tcp://192.168.1.6:8083";

    Date horaInicial = new Date(2018, 5, 9, 8, 0);
    Date horaFinal = new Date(2018, 5, 9, 10, 0);
    //Date horaInicial = new Date(2018, 5, 9, 8, 0);
    //Date horaFinal = new Date(2018, 5, 9, 10, 0);

    public Subscriptor() {
    }

    public static void main(String[] args) {
        try {
            MqttClient client = new MqttClient(ip, MqttClient.generateClientId());
            client.setCallback(new Subscriptor());
            client.connect();
            client.subscribe("protegemos/apt515/hub/cerradura1/alerta");
            Scanner sc = new Scanner(System.in);
            while (true) {
                if (sc.hasNext()) {
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
        if (mm.toString().contains("5")) {
            System.out.println("Health Check recibido, enviando health check al sistema central de Yale...");
        } else if (mm.toString().contains("10")) {
            Date horaActual = new Date();
            MqttClient client = new MqttClient(ip, MqttClient.generateClientId());
            client.setCallback(new Subscriptor());
            client.connect();
            MqttMessage message = new MqttMessage();
            if (horaActual.after(horaInicial) && horaActual.before(horaFinal)){
                message.setPayload("11".getBytes());
            } else {
                message.setPayload("13".getBytes());
            }
            client.publish("protegemos/apt515/hub/cerradura1/key", message);
        } else
            System.out.println(mm.toString()); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}