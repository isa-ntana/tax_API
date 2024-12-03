package com.zup.br.taxes_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.br.taxes_management.controllers.TaxCalculationController;
import com.zup.br.taxes_management.controllers.dtos.TaxCalculationDTO;
import com.zup.br.taxes_management.controllers.dtos.TaxCalculationResponseDTO;
import com.zup.br.taxes_management.models.Tax;
import com.zup.br.taxes_management.services.TaxCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TaxCalculationController.class)
public class TaxCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaxCalculationService taxCalculationService;

    Tax tax;
    TaxCalculationDTO taxCalculationDTO;
    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {

        this.tax = new Tax();
        this.taxCalculationDTO = new TaxCalculationDTO();
        this.mapper = new ObjectMapper();

        tax.setId(3L);
        tax.setName("IOF");
        tax.setDescription("Tax on financial transactions");
        tax.setAliquot(0.38);

        taxCalculationDTO.setBaseValue(3000.0);
    }

    @Test
    public void calculationIsValid() throws Exception {

        TaxCalculationResponseDTO expectedResponse = new TaxCalculationResponseDTO(
                tax.getName(),
                taxCalculationDTO.getBaseValue(),
                tax.getAliquot(),
                11.4
        );

        Mockito.when(taxCalculationService.findTaxType(tax.getId())).thenReturn(tax);
        taxCalculationDTO.setId(tax.getId());
        Mockito.when(taxCalculationService.calculateTaxValue(taxCalculationDTO.getId(), taxCalculationDTO.getBaseValue()))
                .thenReturn(expectedResponse);

        String json = mapper.writeValueAsString(taxCalculationDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/calculation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taxType").value("IOF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.baseValue").value(3000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aliquot").value(0.38))
                .andExpect(MockMvcResultMatchers.jsonPath("$.taxValue").value(11.4));
    }

}
