package ru.staroverov.internship.test.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.staroverov.internship.test.Entity.Part;
import ru.staroverov.internship.test.Service.PartService;

import java.util.List;


@Controller
@RequestMapping(path = "/parts/api")
public class PartRestController {
    final Logger logger = LoggerFactory.getLogger(PartRestController.class);


    @Autowired
    private PartService partService;

    @GetMapping(path="/all")
    public @ResponseBody List<Part> getAllParts() {

        return partService.findAll();
    }

    @GetMapping(path = "")
    public @ResponseBody
    Page<Part> getPageParts(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ask") String order
    ) {
        Sort sort;
        if (order.equals("desc")) sort = new Sort(Sort.Direction.DESC, sortBy);
        else sort = new Sort(Sort.Direction.ASC,sortBy);

        //Нумерация страниц для Spring Data JPA начинается с 0
        Integer pageNumber = (page > 0) ? page-1 : 0;
        //PageRequest pageRequest = PageRequestBuilder(pageNumber, 10, sort);
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, sort);
        return partService.findAllByPage(pageRequest);
    }



    @GetMapping(path = "/{id}")
    public @ResponseBody
    Part findBookById(@PathVariable Long id) {
        return partService.findById(id);
    }

    @PostMapping(value = "")
    public @ResponseBody
    Part create(@RequestBody Part part){
        logger.info("Creating part: " + part);
        partService.save(part);
        logger.info("Part created successfully with info: " + part);

        return part;
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody
    void update(@RequestBody Part part, @PathVariable Long id){
        logger.info("Updating part: " + part);
        partService.save(part);
        logger.info("Part update successfully with info: " + part);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    void delete(@PathVariable Long id){
        logger.info("Deleting Part with id: " + id);
        Part part = partService.findById(id);
        partService.delete(part);
        logger.info("Part deleted successfully");
    }


    @GetMapping(path = "/search")
    public @ResponseBody
    Page<Part> search(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ask") String order,
            @RequestParam(required = false, defaultValue = "") String title

    ){
        Sort sort;
        if (order.equals("desc")) sort = new Sort(Sort.Direction.DESC, sortBy);
        else sort = new Sort(Sort.Direction.ASC,sortBy);

        //Нумерация страниц для Spring Data JPA начинается с 0
        Integer pageNumber = (page > 0) ? page-1 : 0;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, sort);


        return partService.search(title, pageRequest);
    }


}
