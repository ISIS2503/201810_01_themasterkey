/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josedanielcardenasrincon
 */
@XmlRootElement
public class AlertaDTO {
    
    private String id;
    
    private String tipo;
    
    private String idCerradura;
    
    private String idHub;
    
    private String idInmueble;
    
    private String idConjunto;
    
    private String idPropietario;
    
    private String idAdministrador;  
    
    private Date fecha;

    public AlertaDTO() {
    }

    public AlertaDTO(String id, String tipo, String idCerradura, String idHub, String idInmueble, String idConjunto, String idPropietario, String idAdministrador, Date fecha) {
        this.id = id;
        this.tipo = tipo;
        this.idCerradura = idCerradura;
        this.idHub = idHub;
        this.idInmueble = idInmueble;
        this.idConjunto = idConjunto;
        this.idPropietario = idPropietario;
        this.idAdministrador = idAdministrador;
        this.fecha = fecha;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdCerradura() {
        return idCerradura;
    }

    public void setIdCerradura(String idCerradura) {
        this.idCerradura = idCerradura;
    }

    public String getIdHub() {
        return idHub;
    }

    public void setIdHub(String idHub) {
        this.idHub = idHub;
    }

    public String getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(String idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getIdConjunto() {
        return idConjunto;
    }

    public void setIdConjunto(String idConjunto) {
        this.idConjunto = idConjunto;
    }

    public String getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(String idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }   
    
}
