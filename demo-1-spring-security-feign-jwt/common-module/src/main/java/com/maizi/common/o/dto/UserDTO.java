package com.maizi.common.o.dto;

import lombok.*;

/**
 * @author maizi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private String username;
    private String password;

}
