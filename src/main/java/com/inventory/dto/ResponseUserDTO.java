package com.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {
    private String name;
    private String email;
    private String address;
    private String gender;
    private String phone;
}
