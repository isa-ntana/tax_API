package com.zup.br.taxes_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.br.taxes_management.controllers.dtos.TaxCalculationDTO;
import com.zup.br.taxes_management.controllers.dtos.TaxCalculationResponseDTO;
import com.zup.br.taxes_management.controllers.dtos.TaxTypeRegisterDTO;
import com.zup.br.taxes_management.models.Tax;
import com.zup.br.taxes_management.repositories.TaxRepository;
import com.zup.br.taxes_management.services.TaxCalculationService;
import com.zup.br.taxes_management.services.TaxService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.bean.override.mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaxServiceTest {
    @Autowired
    private TaxCalculationService taxCalculationService;

    @MockitoBean
    private TaxRepository taxRepository;

    private TaxCalculationDTO taxCalculation;

    @Autowired
    private TaxService taxtypeService;

    private Tax tax;

    private Tax taxTest = new Tax();
    private TaxTypeRegisterDTO taxTypeRegisterDTO;
    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new ObjectMapper();
        this.tax = new Tax();
        this.taxCalculation = new TaxCalculationDTO();

        taxCalculation.setBaseValue(1230.0);
        tax.setId(1L);
        tax.setName("ICMS");
        tax.setDescription("Tax on circulation of goods and services");
        tax.setAliquot(18.0);

        taxTest.setName("IOF");
        taxTest.setDescription("Tax on financial transactions");
        taxTest.setAliquot(0.38);

        this.taxTypeRegisterDTO = new TaxTypeRegisterDTO();
        taxTypeRegisterDTO.setName("IOF");
        taxTypeRegisterDTO.setDescription("Tax on financial transactions");
        taxTypeRegisterDTO.setAliquot(0.38);
    }

    @Test
    public void registerTaxType() {
        Tax taxRegistered = taxtypeService.registerTaxType(taxTypeRegisterDTO);
        Mockito.verify(taxRepository, Mockito.times(1)).save(taxTest);
    }

    @Test
    public void listAllTaxTypes() {
        Tax tax2 = new Tax();
        tax2.setId(2L);
        tax2.setName("ISS");
        tax2.setDescription("Service tax");
        tax2.setAliquot(5.0);

        Mockito.when(taxRepository.findAll()).thenReturn(List.of(tax, tax2));

        List<Tax> taxList = taxRepository.findAll();

        assertEquals(2, taxList.size());
        assertEquals("ISS", tax2.getName());
        assertEquals("ICMS", tax.getName());

        Mockito.verify(taxRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findTaxTypeById() {
        Mockito.when(taxRepository.findById(tax.getId())).thenReturn(Optional.of(tax));
        Mockito.when(taxRepository.save(tax)).thenReturn(tax);

        Tax taxTest = taxRepository.findById(tax.getId()).get();

        Mockito.verify(taxRepository, Mockito.times(1)).findById(tax.getId());
        assertEquals("ICMS", tax.getName());
    }

    @Test
    public void taxTypeFound() {
        Mockito.when(taxRepository.findById(tax.getId())).thenReturn(Optional.of(tax));

        TaxCalculationResponseDTO taxCalculationResponseDTO =
                taxCalculationService.calculateTaxValue(tax.getId(), taxCalculation.getBaseValue());
        assertEquals("ICMS", taxCalculationResponseDTO.getTaxType());
        assertEquals(1230.0, taxCalculationResponseDTO.getBaseValue());
        assertEquals(18.0, taxCalculationResponseDTO.getAliquot());
        assertEquals(221.4, taxCalculationResponseDTO.getTaxValue());
    }
}
