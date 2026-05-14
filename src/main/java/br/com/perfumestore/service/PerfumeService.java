package br.com.perfumestore.service;

import br.com.perfumestore.dto.PerfumeDTO;

import java.util.List;

public interface PerfumeService {

    List<PerfumeDTO> findAll();

    void save(PerfumeDTO perfumeDTO);

    void deleteById(String id);

    void update(String id, PerfumeDTO perfumeDTO);

    PerfumeDTO findById(String id);
}

