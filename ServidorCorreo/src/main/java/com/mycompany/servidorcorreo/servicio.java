/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servidorcorreo;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author josedanielcardenasrincon
 */
@Path("/correo")
public class servicio {
    
    @POST
    public void add() {
        System.out.println("Se recibe el correo");
    }
}
