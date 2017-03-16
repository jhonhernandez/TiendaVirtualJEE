/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import auditoria.CreacionOrdenInterceptor;
import entidades.Comprador;
import entidades.InformacionEnvio;
import entidades.InformacionFactura;
import entidades.Orden;
import entidades.Producto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

/**
 *
 * @author Estudiante
 */
@Stateful
public class AdministracionOrden implements AdministracionOrdenLocal {
    
    private List<Producto> productos;
    private Comprador comprador;
    private InformacionFactura informacionFactura;
    private InformacionEnvio informacionEnvio;
    
    @EJB
    AdministracionPersistenciaJPALocal administracionPersistencia;
    
    public AdministracionOrden() {
        productos = new ArrayList<Producto>();
    }
    
    @Override
    public void adicionarComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    @Override
    public void adicionarInformacionFactura(InformacionFactura informacionFactura) {
        this.informacionFactura = informacionFactura;
    }

    @Override
    public void adicionarInformacionEnvio(InformacionEnvio informacionEnvio) {
        this.informacionEnvio = informacionEnvio;
    }

    @Override
    @Remove
    @Interceptors(CreacionOrdenInterceptor.class)
    public Integer crearOrdenCompra() {
        /*informacionEnvio.setId(administracionPersistencia.crearInformacionEnvio(informacionEnvio));
        informacionFactura.setId(administracionPersistencia.crearInformacionFactura(informacionFactura));*/
        administracionPersistencia.crearInformacionEnvio(informacionEnvio);
        administracionPersistencia.crearInformacionFactura(informacionFactura);
        
        Orden orden = new Orden();
        orden.setComprador(comprador);
        orden.setFecha(new Date());
        orden.setInformacionEnvio(informacionEnvio);
        orden.setInformacionFactura(informacionFactura);
        
        /*orden.setId(administracionPersistencia.crearOrden(orden));
        
        administracionPersistencia.modificarProductos(productos, orden);*/
        
        administracionPersistencia.crearOrden(orden);
        administracionPersistencia.modificarProductos(productos, orden);
        
        return orden.getId();
    }

    @Override
    @Remove
    public void cancelarOrdenCompra() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adicionarProducto(Producto producto) {
        productos.add(producto);
    }

    @Override
    public Comprador getComprador() {
        return comprador;
    }

    @Override
    public List consultarCarroCompras() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}