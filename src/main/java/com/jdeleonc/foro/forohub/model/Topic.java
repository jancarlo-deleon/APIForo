package com.jdeleonc.foro.forohub.model;

import com.jdeleonc.foro.forohub.DatosRegistroTopic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topics")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String titulo;

    @NotBlank
    @Column(unique = true)
    private String mensaje;

    private LocalDateTime fechaDeCreacion;

    private String status;

    @NotBlank
    private String autor;

    @NotBlank
    private String curso;

    public Topic(DatosRegistroTopic datos){

        this.titulo = datos.titulo();
        this.mensaje= datos.mensaje();
        this.autor = datos.autor();
        this.curso = datos.curso();

    }

    @PrePersist
    public void prePersist() {
        this.fechaDeCreacion = LocalDateTime.now();
        this.status = "Creado";
    }


}
