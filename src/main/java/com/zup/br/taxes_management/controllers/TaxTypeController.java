package com.zup.br.taxes_management.controllers;

import com.zup.br.taxes_management.controllers.dtos.TaxTypeRegisterDTO;
import com.zup.br.taxes_management.models.Tax;
import com.zup.br.taxes_management.services.TaxService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/types")
public class TaxTypeController {
    @Autowired
    private TaxService taxService;

    @GetMapping
    public ResponseEntity<?> getAllTaxTypes() {
        return ResponseEntity.status(200).body(taxService.displayAllTaxTypes());
    }

    @PostMapping
    public ResponseEntity<?> createTaxType(@RequestBody @Valid TaxTypeRegisterDTO taxTypeRegisterDTO) {
        Tax tax = taxService.registerTaxType(taxTypeRegisterDTO);
        return ResponseEntity.status(201).body(tax);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaxTypeById(@PathVariable Long id) {
        try {
            Tax tax = taxService.displayTaxTypeById(id);
            return ResponseEntity.status(200).body(tax);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Tax type not found by id");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaxTypeById(@PathVariable Long id) {
        try {
            taxService.deleteTaxTypeById(id);
            return ResponseEntity.status(204).body("Tax type deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Tax type not found");
        }
    }
}
