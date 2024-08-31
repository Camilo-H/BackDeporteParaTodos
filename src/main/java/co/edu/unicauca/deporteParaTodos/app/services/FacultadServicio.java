package co.edu.unicauca.deporteParaTodos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.deporteParaTodos.core.domain.models.FacultadEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IFacultadServicio;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database.IFacultadRepositorio;

@Service
public class FacultadServicio implements IFacultadServicio{

    @Autowired
    private IFacultadRepositorio repoFacultad;

    @Override
    @Transactional(readOnly = true)
    public Iterable<FacultadEntidad> obtenerFacultades(){
        Iterable<FacultadEntidad> facultades = repoFacultad.findAll();
        /*List<FacultadEntidad> list = new ArrayList<FacultadEntidad>();
        facultades.forEach(list::add);
        System.out.println("este es tamaño: "+list.size());*/
        return facultades;
    }
}