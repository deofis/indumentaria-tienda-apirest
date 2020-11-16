package com.deofis.tiendaapirest.clientes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "states")
@AllArgsConstructor
@NoArgsConstructor
public class Estado implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    private Long id;
    private String nombre;
}
