package com.sistecredito.cursos.tutorials.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.sistecredito.cursos.tutorials.models.Tutorials;
import com.sistecredito.cursos.tutorials.repositories.TutorialsRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialsController {
	@Autowired
	TutorialsRepository tutorialsRepository;
	// http://localhost:8080/api/tutorials

	@PostMapping("/tutorials")
	public ResponseEntity<Tutorials> createTutorial(@RequestBody Tutorials tutorialsBody) {

		try {
			Tutorials _tutorials = tutorialsRepository
					.save(new Tutorials(tutorialsBody.getTitle(), tutorialsBody.getDescription(), false));
			return new ResponseEntity<>(_tutorials, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorials>> getAllTutorials() {
		try {
			List<Tutorials> _tutorials = tutorialsRepository.findAll();
			if (_tutorials.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorials> getTutorialById(@PathVariable("id") int id) {
		try {
			Optional<Tutorials> _tutorials = tutorialsRepository.findById(id);
			if (!_tutorials.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity(_tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorials> updateTutorial(@PathVariable("id") int id, @RequestBody Tutorials tutorialsBody) {
		try {

			Optional<Tutorials> _tutorials = tutorialsRepository.findById(id);
			if (_tutorials.isPresent()) {
				Tutorials tutorials = _tutorials.get();
				tutorials.setTitle(tutorialsBody.getTitle());
				tutorials.setDescription(tutorialsBody.getDescription());
				tutorials.setPublished(tutorialsBody.isPublished());
				tutorialsRepository.save(tutorials);
				return new ResponseEntity(_tutorials, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<String> deleteTutorial(@PathVariable("id") int id) {
		try {
			Optional<Tutorials> _tutorials = tutorialsRepository.findById(id);
			if (_tutorials.isPresent()) {
				tutorialsRepository.delete(_tutorials.get());
				return new ResponseEntity<String>(String.format("El tutorial %d ha sido eliminado", id), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/tutorials/{id}")
	public ResponseEntity<Tutorials> updatePartialTutorial(@PathVariable("id") int id,
			@RequestParam(name = "title", required = false, defaultValue = "por defecto") String title,
			@RequestParam(name = "description", required = true) String description,
			@RequestParam(name = "published", required = false) Boolean published) {
		try {
			Optional<Tutorials> _tutorials = tutorialsRepository.findById(id);
			if (_tutorials.isPresent()) {
				if (title != null) {
					_tutorials.get().setTitle(title);
				}
				if (description != null) {
					_tutorials.get().setDescription(description);
				}
				if (published != null) {
					_tutorials.get().setPublished(published);
				}

				tutorialsRepository.save(_tutorials.get());
				return new ResponseEntity<Tutorials>(_tutorials.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PostMapping("/tutorials/updatePublished/{id}")
	public ResponseEntity<String> updatePublishedTutorial(@PathVariable("id") int id) {
		try {
			Optional<Tutorials> _tutorials = tutorialsRepository.findById(id);
			if (_tutorials.isPresent()) {
				Tutorials tutorials = _tutorials.get();
				tutorials.setPublished(true);
				tutorialsRepository.save(_tutorials.get());
				return new ResponseEntity(tutorials, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
