package io.github.LucasMS115.spring_sales.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class CredentialsDTO {
    private String username;
    private String password;
}
