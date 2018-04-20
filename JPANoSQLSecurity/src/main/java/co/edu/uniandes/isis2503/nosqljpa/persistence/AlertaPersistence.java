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
package co.edu.uniandes.isis2503.nosqljpa.persistence;

import co.edu.uniandes.isis2503.nosqljpa.model.entity.AlertaEntity;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author josedanielcardenasrincon
 */
public class AlertaPersistence extends Persistencer<AlertaEntity, String>{

    public AlertaPersistence() {
        this.entityClass=AlertaEntity.class;
    }
    
    public List<AlertaEntity> findByPropietarioId(String id) {
        List<AlertaEntity> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idPropietario = :id1";
        Query query = entityManager.createQuery(queryString).setParameter("id1", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<AlertaEntity> findByAdministradorId(String id) {
        List<AlertaEntity> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idAdministrador = :id1";
        Query query = entityManager.createQuery(queryString).setParameter("id1", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
}
