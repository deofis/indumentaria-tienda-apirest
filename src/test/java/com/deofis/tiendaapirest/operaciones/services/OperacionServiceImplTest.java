package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.autenticacion.dto.IniciarSesionRequest;
import com.deofis.tiendaapirest.autenticacion.services.AutenticacionService;
import com.deofis.tiendaapirest.clientes.repositories.ClienteRepository;
import com.deofis.tiendaapirest.operaciones.domain.DetalleOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import com.deofis.tiendaapirest.pagos.repositories.FormaPagoRepository;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OperacionServiceImplTest {

    @Autowired
    OperacionService operacionService;

    @Autowired
    AutenticacionService autenticacionService;

    @Autowired
    OperacionRepository operacionRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FormaPagoRepository formaPagoRepository;

    Operacion operacion;

    @BeforeEach
    void setUp() {

        IniciarSesionRequest usuario = new IniciarSesionRequest();
        usuario.setEmail("ezegavilan95@gmail.com");
        usuario.setPassword("12345");

        autenticacionService.iniciarSesion(usuario);

        DetalleOperacion detalleOperacion = DetalleOperacion.builder()
                .cantidad(5)
                .producto(productoRepository.getOne(1L))
                .build();

        List<DetalleOperacion> items = new ArrayList<>();
        items.add(detalleOperacion);

        operacion = Operacion.builder().cliente(clienteRepository.getOne(52L)).formaPago(formaPagoRepository.getOne(1L)).items(items).build();

    }

    @Transactional
    @Test
    void registrarEnvio() {
        Operacion operacionGuardada = operacionService.registrarNuevaOperacion(operacion);
        System.out.println(operacionGuardada);
        System.out.println(operacionGuardada.getEstado());

        operacionService.registrarEnvio(operacionGuardada.getNroOperacion());
        Operacion operacionEnviada = operacionRepository.getOne(operacionGuardada.getNroOperacion());
        System.out.println(operacionEnviada);
        System.out.println(operacionEnviada.getEstado());

        operacionService.registrarRecibo(operacionEnviada.getNroOperacion());
        Operacion operacionRecibida = operacionRepository.getOne(operacionEnviada.getNroOperacion());
        System.out.println(operacionRecibida);
        System.out.println(operacionRecibida.getEstado());
    }
}