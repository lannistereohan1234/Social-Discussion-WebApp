package com.example.redit.mapper;

import java.util.List;

import com.example.redit.dto.SubreditDto;
import com.example.redit.model.Post;
import com.example.redit.model.Subredit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.redit.dto.SubreditDto;
import com.example.redit.model.Post;
import com.example.redit.model.Subredit;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubreditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subredit.getPosts()))")
    SubreditDto mapSubreditToDto(Subredit subredit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subredit mapDtoToSubredit(SubreditDto subreditDto);
}