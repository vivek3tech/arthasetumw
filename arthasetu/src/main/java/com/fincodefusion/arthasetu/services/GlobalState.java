package com.fincodefusion.arthasetu.services;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Data
@RequestScope
public class GlobalState {

    private String globalAction = "voice";
    private String amount;
    private String contact;

}
