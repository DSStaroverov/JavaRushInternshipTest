package ru.staroverov.internship.test.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.staroverov.internship.test.Entity.Part;

public interface PartRepository extends PagingAndSortingRepository<Part,Long> {

    //допилить параметр страницы

    @Query("SELECT t FROM Part t WHERE " +
            "(LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            " t.necessary = :necessary")
    Page<Part> findBySearchParams(@Param("title") String title,@Param("necessary") boolean necessary,Pageable pageable);



    @Query("SELECT t FROM Part t where (LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<Part> findBySearchParams(@Param("title") String title, Pageable pageable);




}
