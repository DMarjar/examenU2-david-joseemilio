package mx.edu.utez.servicioslibros.controller;

import mx.edu.utez.servicioslibros.model.BeanLibro;
import mx.edu.utez.servicioslibros.model.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/libro")
public class LibroController {
    @Autowired
    private LibroRepository libroRepository;

    @PostMapping("/")
    Page<BeanLibro> getAll (Pageable pageable) {
        return libroRepository.findAll(pageable);
    }


    @PostMapping("/save")
    BeanLibro save (BeanLibro beanLibro) {
        return libroRepository.saveAndFlush(beanLibro);
    }

    @PostMapping("/delete")
    boolean delete (BeanLibro beanLibro) {
        libroRepository.delete(beanLibro);
        return true;
    }

    @PostMapping("/getById")
    BeanLibro getById (BeanLibro beanLibro) {
        return libroRepository.findById(beanLibro.getId()).orElse(null);
    }



}
