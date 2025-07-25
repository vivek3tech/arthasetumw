package com.fincodefusion.arthasetu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAI {
    String action;
    String voicedata;
    String amount;
    String contact;
}
