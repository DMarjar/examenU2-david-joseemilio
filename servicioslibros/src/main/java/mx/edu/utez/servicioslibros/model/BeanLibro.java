package mx.edu.utez.servicioslibros.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Table
@Entity
@NoArgsConstructor
public class BeanLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private LocalDate fechaPublicacion;
    private String portada;
}
