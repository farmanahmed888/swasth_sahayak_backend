package com.example.demo.DTO.Questionnare;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class savemultiplequestiondto {
    private String type;
    private List<String> options;
    private String ques_text;

}
