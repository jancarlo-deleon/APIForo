package com.jdeleonc.foro.forohub.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizacionTopic(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String autor,
        @NotBlank
        String curso

) {
}
