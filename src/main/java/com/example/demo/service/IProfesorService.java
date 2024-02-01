package com.example.demo.service;

import java.util.List;

import com.example.demo.repository.modelo.Profesor;

public interface IProfesorService {
	
	public void guardar(Profesor profesor);
	public Profesor buscar(Integer id);
	public void actualizar(Profesor profesor);
	public void actualizarParcial(Integer id, String nombre, String apellido);
	public void borrar(Integer id);
	
	public List<Profesor> buscarPorGenero(String genero);

}
