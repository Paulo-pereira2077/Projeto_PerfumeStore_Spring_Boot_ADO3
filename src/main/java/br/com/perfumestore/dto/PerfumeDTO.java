package br.com.perfumestore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PerfumeDTO {

    private String id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String name;

    @NotBlank(message = "A marca é obrigatória.")
    private String brand;

    @NotBlank(message = "A família olfativa é obrigatória.")
    private String fragranceFamily; // Família Olfativa (Amadeirado, Floral, etc.)

    @NotBlank(message = "O volume é obrigatório.")
    private String volume;          // Tamanho (ex: 100ml)

    private Double price;           // Preço

    // Construtor Vazio
    public PerfumeDTO() {
    }

    // Construtor com campos principais
    public PerfumeDTO(String name, String brand, String fragranceFamily, String volume, Double price) {
        this.name = name;
        this.brand = brand;
        this.fragranceFamily = fragranceFamily;
        this.volume = volume;
        this.price = price;
    }

    // --- GETTERS E SETTERS ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getFragranceFamily() { return fragranceFamily; }
    public void setFragranceFamily(String fragranceFamily) { this.fragranceFamily = fragranceFamily; }
    
    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

}