package com.jdeleonc.foro.forohub.controller;

import com.jdeleonc.foro.forohub.DatosRegistroTopic;
import com.jdeleonc.foro.forohub.dto.topic.DatosActualizacionTopic;
import com.jdeleonc.foro.forohub.dto.topic.DatosRespuestaTopic;
import com.jdeleonc.foro.forohub.model.Topic;
import com.jdeleonc.foro.forohub.repository.TopicRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopic datos, UriComponentsBuilder uriBuilder){

        Topic topic = new Topic(datos);

        topicRepository.save(topic);

        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosRespuestaTopic(topic));

    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var topic = topicRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaTopic(topic));
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopic>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = topicRepository.findAll(paginacion).map(DatosRespuestaTopic::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopic datos) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);

        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();

            boolean cambiosRealizados = false;

            if (!topic.getTitulo().equals(datos.titulo())) {
                topic.setTitulo(datos.titulo());
                cambiosRealizados = true;
            }
            if (!topic.getMensaje().equals(datos.mensaje())) {
                topic.setMensaje(datos.mensaje());
                cambiosRealizados = true;
            }
            if (!topic.getAutor().equals(datos.autor())) {
                topic.setAutor(datos.autor());
                cambiosRealizados = true;
            }
            if (!topic.getCurso().equals(datos.curso())) {
                topic.setCurso(datos.curso());
                cambiosRealizados = true;
            }

            if (cambiosRealizados) {
                return ResponseEntity.ok(new DatosRespuestaTopic(topic));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
