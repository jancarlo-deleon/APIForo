package com.jdeleonc.foro.forohub.dto.topic;

import com.jdeleonc.foro.forohub.DatosRegistroTopic;
import com.jdeleonc.foro.forohub.model.Topic;

import java.time.LocalDateTime;

public record DatosRespuestaTopic(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        String estado,
        String autor,
        String curso
) {

    public DatosRespuestaTopic(Topic topic){
        this(topic.getId(), topic.getTitulo(), topic.getMensaje(),topic.getFechaDeCreacion(), topic.getStatus(), topic.getAutor(), topic.getCurso());
    }

}
