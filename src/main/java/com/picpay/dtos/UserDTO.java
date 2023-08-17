package com.picpay.dtos;

import com.picpay.domain.user.UserType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String password;
    private BigDecimal balance;
    private UserType userType;
}
