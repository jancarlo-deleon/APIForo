package com.jdeleonc.foro.forohub.controller;

import com.jdeleonc.foro.forohub.DatosRegistroTopic;
import com.jdeleonc.foro.forohub.dto.topic.DatosRespuestaTopic;
import com.jdeleonc.foro.forohub.model.Topic;
import com.jdeleonc.foro.forohub.repository.TopicRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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


}
