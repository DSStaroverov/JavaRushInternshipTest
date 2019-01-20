package ru.staroverov.internship.test.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.staroverov.internship.test.Entity.Part;

import java.io.IOException;
import java.util.List;

public interface PartService {
    List<Part> findAll();
    Part findById(Long id);
    Part save(Part part);
    void delete(Part part);
    Page<Part> findAllByPage(Pageable pageable);

    Page<Part> search(String title,Pageable pageable);
    Page<Part> search(String title,boolean necessary, Pageable pageable);
    Part update(Part part, Long id) throws IOException;
}
