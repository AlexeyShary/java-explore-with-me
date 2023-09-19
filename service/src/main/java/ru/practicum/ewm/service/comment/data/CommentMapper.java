package ru.practicum.ewm.service.comment.data;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "userName", source = "entity.user.name")
    CommentDto toDto(Comment entity);
}
