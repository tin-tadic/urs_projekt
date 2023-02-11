package com.example.urs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitPresenceDTO {
    private String username;

    private String password;

    private String code;
}
