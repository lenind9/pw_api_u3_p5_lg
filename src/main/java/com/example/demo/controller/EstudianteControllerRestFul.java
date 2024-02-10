package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.modelo.Estudiante;
import com.example.demo.service.IEstudianteService;
import com.example.demo.service.IMateriaService;
import com.example.demo.service.to.EstudianteTO;
import com.example.demo.service.to.MateriaTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// API: determinada por el proyecto java
// Servicio -> Controller: clase Controller

@RestController // Servicio
@RequestMapping(path = "/estudiantes")
public class EstudianteControllerRestFul {

	@Autowired
	private IEstudianteService estudianteService;

	@Autowired
	private IMateriaService materiaService;

	// Metodos: Capacidades

	// Path Variable
	// Verbo: GET
	// http://localhost:8080/API/v1.0/Matricula/estudiantes/{cedula} GET
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EstudianteTO> buscar(@PathVariable Integer id) {
		EstudianteTO estu = this.estudianteService.buscarTO(id);
		Link link = linkTo(methodOn(EstudianteControllerRestFul.class).consultarMateriasPorId(estu.getId()))
				.withRel("materias");
		Link link2 = linkTo(methodOn(EstudianteControllerRestFul.class).buscar(estu.getId()))
				.withSelfRel();
		estu.add(link);
		estu.add(link2);
		return ResponseEntity.status(HttpStatus.OK).body(estu);
	}
	// http://localhost:8080/API/v1.0/Matricula/estudiantes/buscar

	@PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
	public void guardar(@RequestBody Estudiante estudiante) {
		this.estudianteService.guardar(estudiante);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void actualizar(@RequestBody Estudiante estudiante, @PathVariable Integer id) {
		estudiante.setId(id);
		this.estudianteService.actualizar(estudiante);
	}

	@PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void actualizarParcial(@RequestBody Estudiante estudiante, @PathVariable Integer id) {
		this.estudianteService.actualizarParcial(estudiante.getNombre(), estudiante.getApellido(), id);
	}

	@DeleteMapping(path = "/{id}")
	public void borrar(@PathVariable Integer id) {
		this.estudianteService.borrar(id);
	}

	// Consultar todos los estudiantes (retorna una lista)
	// http://localhost:8080/API/v1.0/Matricula/estudiantes/consultarTodos?genero=M
	@GetMapping(path = "/tmp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Estudiante>> consultarTodos(
			@RequestParam(required = false, defaultValue = "M") String genero) {
		List<Estudiante> ls = this.estudianteService.buscarTodos(genero);
		HttpHeaders cabeceras = new HttpHeaders();
		cabeceras.add("mensaje_242", "Lista consultada de manera satisfactoria");
		return new ResponseEntity<>(ls, cabeceras, 242);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EstudianteTO>> consultarTodosHateoas() {
		List<EstudianteTO> ls = this.estudianteService.buscarTodosTO();
		for (EstudianteTO est : ls) {
			Link link = linkTo(methodOn(EstudianteControllerRestFul.class).consultarMateriasPorId(est.getId()))
					.withRel("materias");
			est.add(link);
		}
		return ResponseEntity.status(HttpStatus.OK).body(ls);
	}

	// http://localhost:8080/API/v1.0/Matricula/estudiantes/1/materias GET
	@GetMapping(path = "/{id}/materias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MateriaTO>> consultarMateriasPorId(@PathVariable Integer id) {
		List<MateriaTO> ls = this.materiaService.buscarPorIdEstudiante(id);
		return ResponseEntity.status(HttpStatus.OK).body(ls);
	}
}
