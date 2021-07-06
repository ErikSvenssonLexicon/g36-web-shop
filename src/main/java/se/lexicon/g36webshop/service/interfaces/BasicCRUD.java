package se.lexicon.g36webshop.service.interfaces;

import java.util.Collection;

public interface BasicCRUD <T, DTO>{

    T create(DTO dto);
    T findById(String id);
    Collection<T> findAll();
    T update(String id, DTO dto);
    void delete(String id);

}
