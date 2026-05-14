package br.com.perfumestore.controller;

import br.com.perfumestore.dto.PerfumeDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PerfumeController {

    @GetMapping("/index")
    public String exibirFormulario(Model model) {
        model.addAttribute("perfumeDTO", new PerfumeDTO());
        return "index";
    }

    @PostMapping("/perfumes")
    public String salvarPerfume(@Valid PerfumeDTO perfumeDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "index";
        }

        System.out.println("Perfume validado com sucesso: " + perfumeDTO.getName() + ", " + perfumeDTO.getBrand());

        return "redirect:/sucesso";
    }

    @GetMapping("/sucesso")
    public String sucesso() {
        return "sucesso";
    }

}