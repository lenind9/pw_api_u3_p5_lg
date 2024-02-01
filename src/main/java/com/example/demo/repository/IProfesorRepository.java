package com.example.demo.repository;

import java.util.List;

import com.example.demo.repository.modelo.Profesor;

public interface IProfesorRepository {
	
	public void insertar(Profesor profesor);
	public Profesor seleccionar(Integer id);
	public void actualizar(Profesor profesor);
	public void actualizarParcial(Integer id, String nombre, String apellido);
	public void eliminar(Integer id);

	public List<Profesor> consultarGenero(String genero);
}
