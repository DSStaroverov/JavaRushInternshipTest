package ru.staroverov.internship.test.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.staroverov.internship.test.Entity.Part;
import ru.staroverov.internship.test.Repository.PartRepository;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

@Service("partService")
@Repository
@Transactional
public class PartServiceImpl implements PartService{

    @Autowired
    private PartRepository partRepository;

    @Override
    @Transactional(readOnly=true)
    public List<Part> findAll() {

        return Lists.newArrayList(partRepository.findAll());
    }

    @Override
    @Transactional(readOnly=true)
    public Part findById(Long id) {

        return partRepository.findById(id).get();
    }

    @Override
    public Part save(Part part) {
        return partRepository.save(part);
    }

    @Override
    public void delete(Part part) {
        partRepository.delete(part);
    }

    @Override
    public Page<Part> findAllByPage(Pageable pageable) {
        return partRepository.findAll(pageable);
    }

    @Override
    public Page<Part> search(String title,Pageable pageable) {
        return partRepository.findBySearchParams(title,pageable);
    }

    @Override
    public Page<Part> search(String title,boolean necessary, Pageable pageable) {
        return partRepository.findBySearchParams(title,necessary,pageable);
    }

    @Override
    public Part update(Part part, Long id) throws IOException {
        Part entity = partRepository.findById(id).get();

        if (part.getTitle() != null) entity.setTitle(part.getTitle());
        if(part.getCounts()>=0){
            entity.setCounts(part.getCounts());
        }else {
            entity.setCounts(0);
        }
        entity.setNecessary(part.isNecessary());

        return partRepository.save(entity);
    }
}
