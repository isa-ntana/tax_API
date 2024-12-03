package com.zup.br.taxes_management.services;

import com.zup.br.taxes_management.controllers.dtos.TaxCalculationResponseDTO;
import com.zup.br.taxes_management.models.Tax;
import com.zup.br.taxes_management.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculationService {
    @Autowired
    private TaxRepository taxRepository;

    public Tax findTaxType(Long id) {
        return taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id not found"));
    }

    public TaxCalculationResponseDTO calculateTaxValue(Long id, Double baseValue) {
        Tax tax = findTaxType(id);
        Double taxValue = baseValue * tax.getAliquot() / 100;
        return new TaxCalculationResponseDTO(tax.getName(), baseValue, tax.getAliquot(), taxValue);
    }


}
