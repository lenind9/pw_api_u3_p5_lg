package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.IEstudianteRepository;
import com.example.demo.repository.modelo.Estudiante;
import com.example.demo.service.to.EstudianteLigeroTO;
import com.example.demo.service.to.EstudianteTO;

@Service
public class EstudianteServiceImpl implements IEstudianteService {
	
	@Autowired
	private IEstudianteRepository estudianteRepository;

	@Override
	public void guardar(Estudiante estudiante) {
		// TODO Auto-generated method stub
		this.estudianteRepository.insertar(estudiante);
	}

	@Override
	public void actualizar(Estudiante estudiante) {
		// TODO Auto-generated method stub
		this.estudianteRepository.actualizar(estudiante);
	}

	@Override
	public void actualizarParcial(String nombre, String apellido, Integer id) {
		// TODO Auto-generated method stub
		this.estudianteRepository.actualizarParcial(nombre, apellido, id);
	}

	@Override
	public Estudiante buscar(Integer id) {
		// TODO Auto-generated method stub
		return this.estudianteRepository.seleccionar(id);
	}

	@Override
	public void borrar(Integer id) {
		// TODO Auto-generated method stub
		this.estudianteRepository.eliminar(id);
	}

	@Override
	public List<Estudiante> buscarTodos(String genero) {
		// TODO Auto-generated method stub
		return this.estudianteRepository.consultarTodos(genero);
	}

	private EstudianteTO convertir(Estudiante estu) {
		EstudianteTO estuTO = new EstudianteTO();
		estuTO.setApellido(estu.getApellido());
		estuTO.setFechaNacimiento(estu.getFechaNacimiento());
		estuTO.setGenero(estu.getGenero());
		estuTO.setId(estu.getId());
		estuTO.setNombre(estu.getNombre());
		estuTO.setDireccion(estu.getDireccion());
		estuTO.setCorreoElectronico(estu.getCorreoElectronico());
		estuTO.setEdad(estu.getEdad());
		estuTO.setTelefono(estu.getTelefono());
		estuTO.setCarrera(estu.getCarrera());
		return estuTO;
	}
	
	private EstudianteLigeroTO convertirLigero(EstudianteTO estuTO) {
		EstudianteLigeroTO estuLTO = new EstudianteLigeroTO();
		estuLTO.setId(estuTO.getId());
		estuLTO.setNombre(estuTO.getNombre());
		return estuLTO;
	}
	
	@Override
	public List<EstudianteTO> buscarTodosTO() {
		// TODO Auto-generated method stub
		List<Estudiante> ls = this.estudianteRepository.consultarTodos("M");
		List<EstudianteTO> lsFinal = new ArrayList<>();
		for(Estudiante estu : ls) {
			lsFinal.add(this.convertir(estu));
		}
		return lsFinal;
	}

	@Override
	public EstudianteTO buscarTO(Integer id) {
		// TODO Auto-generated method stub
		return this.convertir(this.estudianteRepository.seleccionar(id));
	}

	@Override
	public List<EstudianteLigeroTO> buscarTodosLigeroTO() {
		// TODO Auto-generated method stub
		List<Estudiante> ls = this.estudianteRepository.consultarTodos("M");
		List<EstudianteLigeroTO> lsFinal = new ArrayList<>();
		for(Estudiante estu : ls) {
			lsFinal.add(this.convertirLigero(this.convertir(estu)));
		}
		return lsFinal;
	}

	@Override
	public EstudianteLigeroTO buscarLigeroTO(Integer id) {
		// TODO Auto-generated method stub
		return this.convertirLigero(this.convertir(this.estudianteRepository.seleccionar(id)));
	}

}
