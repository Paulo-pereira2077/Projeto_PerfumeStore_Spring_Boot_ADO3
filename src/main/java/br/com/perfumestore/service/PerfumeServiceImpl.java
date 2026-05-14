package br.com.perfumestore.service;

import br.com.perfumestore.dao.PerfumeDao;
import br.com.perfumestore.dto.PerfumeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PerfumeServiceImpl implements PerfumeService {

    private final PerfumeDao perfumeDao;

    public PerfumeServiceImpl(PerfumeDao perfumeDao) {
        this.perfumeDao = perfumeDao;
    }

    @Override
    public List<PerfumeDTO> findAll() {
        return perfumeDao.findAll();
    }

    @Override
    public void save(PerfumeDTO perfumeDTO) {

        if (perfumeDTO.getId() == null) {
            UUID uuid = UUID.randomUUID();
            perfumeDTO.setId(uuid.toString());
        }

        perfumeDao.save(perfumeDTO);
    }

    @Override
    public void deleteById(String id) {
        perfumeDao.deleteById(id);
    }

    @Override
    public void update(String id, PerfumeDTO perfumeDTO) {
        perfumeDao.update(id, perfumeDTO);
    }

    @Override
    public PerfumeDTO findById(String id) {
        return perfumeDao.findById(id);
    }

}