package br.com.perfumestore.controller;

import br.com.perfumestore.dto.PerfumeDTO;
import br.com.perfumestore.dto.PerfumeResponseBody;
import br.com.perfumestore.service.PerfumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final PerfumeService perfumeService;

    public RestController(PerfumeService service) {
        this.perfumeService = service;
    }

    @GetMapping("/api/perfumes")
    public ResponseEntity<PerfumeResponseBody> home() {
        List<PerfumeDTO> allPerfumes = perfumeService.findAll();
        PerfumeResponseBody responseBody = new PerfumeResponseBody(allPerfumes);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/perfumes")
    public ResponseEntity<PerfumeDTO> createPerfume(@RequestBody PerfumeDTO perfume) {
        this.perfumeService.save(perfume);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/api/perfumes/{id}")
    public ResponseEntity<PerfumeDTO> deletePerfume(@PathVariable String id) {
        this.perfumeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/perfumes/{id}")
    public ResponseEntity<PerfumeDTO> updatePerfume(@PathVariable String id, @RequestBody PerfumeDTO perfumeDTO) {
        this.perfumeService.update(id, perfumeDTO);
        return ResponseEntity.ok(perfumeDTO);
    }
}