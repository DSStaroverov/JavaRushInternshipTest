package ru.staroverov.internship.test.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.staroverov.internship.test.Entity.Part;
import ru.staroverov.internship.test.Service.PartService;

import java.io.IOException;

@Controller
@RequestMapping(path = "/parts")
public class PartController {

    protected int page;

    @Autowired
    private PartService partService;

    @RequestMapping(path = "")
    public String viewPartsList(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ask") String order,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String necessary,
            Model uiModel
    ) {
        Sort sort;
        if (order.equals("desc")) sort = new Sort(Sort.Direction.DESC, sortBy);
        else sort = new Sort(Sort.Direction.ASC, sortBy);

        //Нумерация страниц для Spring Data JPA начинается с 0
        Integer pageNumber = (page > 0) ? page - 1 : 0;

        PageRequest pageRequest = PageRequest.of(pageNumber, 10, sort);
        this.page=pageRequest.getPageNumber();
        //Page<Part> parts = partService.findAllByPage(pageRequest);
        Page<Part> parts;
        if (!necessary.equals("") && (necessary.equals("true") || necessary.equals("false"))) {

            parts = partService.search(title, Boolean.parseBoolean(necessary), pageRequest);

        }
        else {

            parts = partService.search(title, pageRequest);

        }


        int minComp=-1;
        for(Part part:partService.findAll()){
            if(part.isNecessary()) {
                if (minComp <0) {
                    minComp=part.getCounts();
                }
                if(minComp>part.getCounts()){
                    minComp=part.getCounts();
                }
            }
        }

        uiModel.addAttribute("minComp",minComp);
        uiModel.addAttribute("parts", parts);
        uiModel.addAttribute("title",title);
        uiModel.addAttribute("necessary",necessary);


        return "parts/list";
    }

    @GetMapping(path = "/{id}")
    public String editPart (@PathVariable Long id, Model uiModel){
        Part editPart = partService.findById(id);

        uiModel.addAttribute("editPart", editPart);

        return "parts/editPart";
    }


    @PostMapping(path = "delete/{id}")
    public String deletePart(@PathVariable Long id){
        Part part = partService.findById(id);
        partService.delete(part);

        return "redirect:/parts/";
    }

    @PostMapping(path = "/edition/{id}")
    public String editionSubmit(
            @ModelAttribute Part editPart,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        partService.update(editPart, id);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/parts/";
    }

    @GetMapping(path = "/add")
    public String addPart(Model uiModel){
        uiModel.addAttribute("newPart", new Part());
        return "parts/addPart";
    }


    @PostMapping(path = "/add")
    public String addSubmit(
            @ModelAttribute Part newPart
            //RedirectAttributes redirectAttributes

    ) throws IOException {

        //redirectAttributes.addAttribute(newPart);
        partService.save(newPart);

        return "redirect:/parts";
    }
}