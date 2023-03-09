package com.lqs.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加分类dto")
public class AddCategoryDto {

    private String name;
    private String description;
    private String status;
}
