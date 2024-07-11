package com.jdeleonc.foro.forohub;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroTopic(
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
