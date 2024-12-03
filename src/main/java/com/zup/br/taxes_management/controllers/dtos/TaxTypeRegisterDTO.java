package com.zup.br.taxes_management.controllers.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TaxTypeRegisterDTO {

    @NotBlank(message = "name can't be blank")
    private String name;

    @NotBlank(message = "description can't be blank")
    private String description;

    @NotNull(message = "aliquot can't be null")
    private Double aliquot;
}
