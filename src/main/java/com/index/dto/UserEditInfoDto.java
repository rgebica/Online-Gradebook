package com.index.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEditInfoDto {
    String email;
    String username;
}
