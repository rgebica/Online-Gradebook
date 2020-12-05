package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBehaviourResponse {
    private long behaviourId;
    private String responseContent;
    private String subject;
}
