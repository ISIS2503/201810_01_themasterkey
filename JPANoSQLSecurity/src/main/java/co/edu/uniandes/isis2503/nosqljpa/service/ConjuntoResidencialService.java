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
import co.edu.uniandes.isis2503.nosqljpa.logic.ConjuntoResidencialLogic;
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
@Path("/conjuntos")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class ConjuntoResidencialService {
    
    private final ConjuntoResidencialLogic conjuntoResidencialLogic;

    public ConjuntoResidencialService() {
        this.conjuntoResidencialLogic = new ConjuntoResidencialLogic();
    }

    @POST
    @Secured({Role.yale, Role.admin})
    public ConjuntoResidencialDTO add(ConjuntoResidencialDTO dto) {
        return conjuntoResidencialLogic.add(dto);
    }

    @PUT
    @Secured({Role.yale, Role.admin})
    public ConjuntoResidencialDTO update(ConjuntoResidencialDTO dto) {
        return conjuntoResidencialLogic.update(dto);
    }

    @GET
    @Secured({Role.yale, Role.segutidadPrivada, Role.admin, Role.propietario})
    @Path("/{id}")
    public ConjuntoResidencialDTO find(@PathParam("id") String id) {
        return conjuntoResidencialLogic.find(id);
    }

    @GET
    @Secured({Role.yale})
    public List<ConjuntoResidencialDTO> all() {
        return conjuntoResidencialLogic.all();
    }

    @DELETE
    @Path("/{id}")
    @Secured({Role.yale})
    public Response delete(@PathParam("id") String id) {
        try {
            conjuntoResidencialLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Floor was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ConjuntoResidencialService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
