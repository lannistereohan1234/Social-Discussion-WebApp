package com.example.redit.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubreditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
