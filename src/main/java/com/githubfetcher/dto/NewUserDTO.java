package com.githubfetcher.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Data
public class NewUserDTO {

    @NotNull
    private String login;
    
    @Length(min = 3)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    private HashMap<String, Object> params;
}
