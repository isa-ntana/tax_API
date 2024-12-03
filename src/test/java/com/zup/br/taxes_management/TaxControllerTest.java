package com.zup.br.taxes_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.br.taxes_management.controllers.TaxTypeController;
import com.zup.br.taxes_management.controllers.dtos.TaxTypeRegisterDTO;
import com.zup.br.taxes_management.models.Tax;
import com.zup.br.taxes_management.services.TaxService;
import org.hamcrest.CoreMatchers;
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

import java.util.List;

@WebMvcTest(TaxTypeController.class)
public class TaxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaxService taxService;

    private ObjectMapper mapper;
    private Tax tax;
    private TaxTypeRegisterDTO taxTypeRegisterDTO;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();

        this.tax = new Tax();
        tax.setId(1L);
        tax.setName("ICMS");
        tax.setDescription("Tax on circulation of goods and services");
        tax.setAliquot(18.0);

        this.taxTypeRegisterDTO = new TaxTypeRegisterDTO();
        taxTypeRegisterDTO.setName("IPI");
        taxTypeRegisterDTO.setDescription("Service tax");
        taxTypeRegisterDTO.setAliquot(12.0);
    }

    @Test
    public void returnAllTaxTypes() throws Exception {
        Tax tax2 = new Tax();
        tax2.setId(2L);
        tax2.setName("IPI");
        tax2.setDescription("Service tax");
        tax2.setAliquot(12.0);

        Mockito.when(taxService.displayAllTaxTypes()).thenReturn(List.of(tax, tax2));

        List<Tax> taxList = taxService.displayAllTaxTypes();

        String json = mapper.writeValueAsString(taxList);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/types")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is("ICMS")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", CoreMatchers.is("Tax on circulation of goods and services")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].aliquot", CoreMatchers.is(18.0)))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", CoreMatchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", CoreMatchers.is("IPI")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", CoreMatchers.is("Service tax")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].aliquot", CoreMatchers.is(12.0))
                );
    }

    @Test
    public void taxTypeHasFoundById() throws Exception {
        String json = mapper.writeValueAsString(tax);

        Mockito.when(taxService.displayTaxTypeById(Mockito.anyLong())).thenReturn(tax);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/types/{id}", tax.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("ICMS")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is("Tax on circulation of goods and services")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aliquot", CoreMatchers.is(18.0))
                );
    }

    @Test
    public void testWhenDeleteTaxTypeReturnsNoContent() throws Exception {
        Mockito.when(taxService.deleteTaxTypeById(tax.getId())).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/types/{id}", tax.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
