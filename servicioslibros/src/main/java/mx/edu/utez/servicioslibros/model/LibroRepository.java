package mx.edu.utez.servicioslibros.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<BeanLibro, Long>{
    Page<BeanLibro> findAll(Pageable pageable);
}
