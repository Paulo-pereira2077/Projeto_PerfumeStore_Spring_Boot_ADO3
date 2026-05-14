package br.com.perfumestore.dto;

import java.util.List;

public class PerfumeResponseBody {

    private List<PerfumeDTO> perfumes;

    public PerfumeResponseBody(List<PerfumeDTO> allPerfumes) {
        this.perfumes = allPerfumes;
    }

    public List<PerfumeDTO> getPerfumes() {
        return perfumes;
    }

    public void setPerfumes(List<PerfumeDTO> perfumes) {
        this.perfumes = perfumes;
    }
}
