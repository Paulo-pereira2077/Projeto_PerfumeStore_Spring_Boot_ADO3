package br.com.perfumestore.controller;

import br.com.perfumestore.dto.PerfumeDTO;
import br.com.perfumestore.service.PerfumeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PerfumeServiceImpl service;

    public HomeController(PerfumeServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("perfumeDTO", new PerfumeDTO());
        return "redirect:/public/perfumes";
    }

    @GetMapping("/public/perfumes")
    public String getPerfumes(Model model) {
        List<PerfumeDTO> allPerfumes = service.findAll();
        model.addAttribute("perfumes", allPerfumes);
        return "public/dashboard";
    }
}
