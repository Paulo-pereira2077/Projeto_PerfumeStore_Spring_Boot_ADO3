package br.com.perfumestore.controller;

import br.com.perfumestore.dto.PerfumeDTO;
import br.com.perfumestore.service.PerfumeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private final PerfumeServiceImpl service;

    public AdminController(PerfumeServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("perfumeDTO", new PerfumeDTO());
        return "/admin/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/perfumes")
    public String createPerfume(@ModelAttribute PerfumeDTO perfumeDTO, BindingResult result) {
        if (perfumeDTO.getId() != null && !perfumeDTO.getId().isEmpty()) {
            service.update(perfumeDTO.getId(), perfumeDTO);
        } else {
            service.save(perfumeDTO);
        }
        return "redirect:/admin/perfumes";
    }

    @GetMapping("/admin/perfumes")
    public String getPerfumes(Model model) {
        List<PerfumeDTO> allPerfumes = service.findAll();
        model.addAttribute("perfumes", allPerfumes);
        return "/admin/dashboard";
    }

    @GetMapping("/admin/perfumes/edit")
    public String editPerfume(@RequestParam("id") String id, Model model) {

        PerfumeDTO perfume = service.findById(id);

        if (perfume == null) {
            return "redirect:/admin/perfumes";
        }
        model.addAttribute("perfumeDTO", perfume);

        return "admin/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/perfumes/delete")
    public String deletePerfume(@RequestParam("id") String id, Model model) {

        service.deleteById(id);

        List<PerfumeDTO> perfumes = service.findAll();
        model.addAttribute("perfumes", perfumes);

        return "redirect:/admin/perfumes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/stats")
    public ResponseEntity<?> stats() { return ResponseEntity.ok().build(); }

}
