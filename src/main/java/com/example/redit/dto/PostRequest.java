package com.example.redit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
	private Long postId;
	private String subreditName;
	private String postName;
	private String url;
	private String description;
	
}
