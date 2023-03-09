package com.lqs.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "标签列表DTO")
public class TagListDto {

    @ApiModelProperty(notes = "名称")
    private String name;

    @ApiModelProperty(notes = "备注")
    private String remark;
}
