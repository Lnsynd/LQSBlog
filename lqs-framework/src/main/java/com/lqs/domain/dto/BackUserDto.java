package com.lqs.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackUserDto {
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    private String userName;
    private String sex;
    private String status;
    private String email;
}
