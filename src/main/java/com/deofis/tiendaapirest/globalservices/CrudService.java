package com.deofis.tiendaapirest.globalservices;

public interface CrudService<T, ID> {

    T save(T object);

}
