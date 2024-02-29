package mx.edu.utez.servicioslibros.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface LibroRepository extends JpaRepository<BeanLibro, Long>{
    Page<BeanLibro> findAll(Pageable pageable);
    Page<BeanLibro> findByOrderByAutorDesc (Pageable pageable);
    Page<BeanLibro> findByOrderByFechaPublicacionDesc (Pageable pageable);
    Page<BeanLibro> findByPortadaIsNotNull(Pageable pageable);
}
