package org.crud.basico.estudante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

public class GerenciarEstudante {

    private final EntityManager entityManager;

    public GerenciarEstudante(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Estudante estudante) {
        entityManager.getTransaction().begin();
        entityManager.persist(estudante);
        entityManager.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public List<Estudante> list() {
        String sqlOOList = "from Estudante";
        Query hqlList = entityManager.createQuery(sqlOOList);

        return hqlList.getResultList();
    }
}
