package com.picpay.dtos;

import com.picpay.domain.user.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReturnTransactionDTO {

    private Long id;
    private User sender;
    private User receiver;
    private BigDecimal amount;
    private LocalDateTime timesTamp;

}
