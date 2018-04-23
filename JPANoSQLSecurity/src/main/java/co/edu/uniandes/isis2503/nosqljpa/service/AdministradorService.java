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
import co.edu.uniandes.isis2503.nosqljpa.logic.AdministradorLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.ConjuntoResidencialLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AdministradorDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.ConjuntoResidencialDTO;
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
@Path("/administradores")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class AdministradorService {
    
    private final AdministradorLogic administradorLogic;
    private final ConjuntoResidencialLogic conjuntoLogic;

    public AdministradorService() {
        this.administradorLogic = new AdministradorLogic();
        this.conjuntoLogic = new ConjuntoResidencialLogic();
    }

    @POST
    public AdministradorDTO add(AdministradorDTO dto) {
        return administradorLogic.add(dto);
    }

    @POST
    @Path("{id}/conjunto")
    public ConjuntoResidencialDTO addConjunto(@PathParam("id") String id, ConjuntoResidencialDTO dto) {
        AdministradorDTO admin = administradorLogic.find(id);
        ConjuntoResidencialDTO result = conjuntoLogic.add(dto);
        admin.setConjunto(dto.getId());
        administradorLogic.update(admin);
        return result;
    }

    @PUT
    public AdministradorDTO update(AdministradorDTO dto) {
        return administradorLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public AdministradorDTO find(@PathParam("id") String id) {
        return administradorLogic.find(id);
    }

    @GET
    public List<AdministradorDTO> all() {
        return administradorLogic.all();
    }

    @DELETE
    @Secured({Role.yale})
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            administradorLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Floor was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(AdministradorService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
