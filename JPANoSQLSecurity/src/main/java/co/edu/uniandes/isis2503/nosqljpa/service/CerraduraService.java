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
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter.Role;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.logic.CerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CerraduraDTO;
import com.sun.istack.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author josedanielcardenasrincon
 */
@Path("/cerraduras")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class CerraduraService {
    private final CerraduraLogic cerraduraLogic;

    public CerraduraService() {
        this.cerraduraLogic = new CerraduraLogic();
    }

    @POST
    @Secured({Role.propietario})
    public CerraduraDTO add(CerraduraDTO dto) {
        return cerraduraLogic.add(dto);
    }

    //Servicio para modificar los horarios de apertura de la puerta.
    @PUT
    @Secured({Role.propietario})
    public CerraduraDTO update(CerraduraDTO dto) {
        return cerraduraLogic.update(dto);
    }

    @GET
    @Secured({Role.propietario})
    @Path("/{id}")
    public CerraduraDTO find(@PathParam("id") String id) {
        return cerraduraLogic.find(id);
    }

    @GET
    @Secured({Role.yale})
    public List<CerraduraDTO> all() {
        return cerraduraLogic.all();
    }

    @DELETE
    @Secured({Role.yale})
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            cerraduraLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Floor was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(CerraduraService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
