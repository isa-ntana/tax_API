package com.zup.br.taxes_management.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.br.taxes_management.controllers.dtos.TaxTypeRegisterDTO;
import com.zup.br.taxes_management.models.Tax;
import com.zup.br.taxes_management.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxService {

    @Autowired
    private TaxRepository taxRepository;

    ObjectMapper mapper = new ObjectMapper();

    public Tax registerTaxType(TaxTypeRegisterDTO taxTypeRegisterDTO) {
        return taxRepository.save(mapper.convertValue(taxTypeRegisterDTO, Tax.class));
    }

    public List<Tax> displayAllTaxTypes() {
        return taxRepository.findAll();
    }

    public Tax displayTaxTypeById(Long id) {
        return taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax type not found"));
    }

    public boolean deleteTaxTypeById(Long id) {
        if (taxRepository.existsById(id)) {
            taxRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
