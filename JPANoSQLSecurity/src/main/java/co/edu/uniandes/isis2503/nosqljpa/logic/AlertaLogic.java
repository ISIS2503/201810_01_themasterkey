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
package co.edu.uniandes.isis2503.nosqljpa.logic;

import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.AlertaConverter.CONVERTER;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlertaDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.AlertaPersistence;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author josedanielcardenasrincon
 */
public class AlertaLogic {
    
    private final AlertaPersistence persistence;

    public AlertaLogic() {
        this.persistence = new AlertaPersistence();
    }
    
    public AlertaDTO add(AlertaDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        if (dto.getFecha()==null){
            dto.setFecha(new Date());
        }
        AlertaDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    public AlertaDTO update(AlertaDTO dto) {
        AlertaDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    public AlertaDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }
    
    public List<AlertaDTO> findByAdministradorId(String id) {
        return CONVERTER.listEntitiesToListDTOs(persistence.findByAdministradorId(id));
    }
    
    public List<AlertaDTO> findByPropietarioId(String id) {
        return CONVERTER.listEntitiesToListDTOs(persistence.findByPropietarioId(id));
    }

    public List<AlertaDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    public Boolean delete(String id) {
        return persistence.delete(id);
    }
}
