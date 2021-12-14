package com.sistecredito.cursos.tutorials.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistecredito.cursos.tutorials.models.Tutorials;

public interface TutorialsRepository extends JpaRepository<Tutorials, Integer> {

}
