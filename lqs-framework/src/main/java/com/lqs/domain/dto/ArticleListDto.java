package com.lqs.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    @ApiModelProperty(notes = "标题")
    private String title;

    @ApiModelProperty(notes = "摘要")
    private String summary;
}
