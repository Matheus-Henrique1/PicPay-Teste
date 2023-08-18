package com.picpay.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionDTO {

    private BigDecimal value;
    private Long senderId;
    private Long receiveId;

}
