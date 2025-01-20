package com.yc.Rhythm.Mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.entity.Song;

@Mapper(componentModel = "spring", uses = {})
public interface SongMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "audioFileId", ignore = true)
    @Mapping(target = "imageId", ignore = true)
    Song toEntity(SongRequest request);

    @Mapping(target = "albumId", source = "album.id")
    @Mapping(target = "albumTitle", source = "album.title")
    SongResponse toResponse(Song song);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "audioFileId", ignore = true)
    @Mapping(target = "imageId", ignore = true)
    void updateEntityFromRequest(SongRequest request, @MappingTarget Song song);
}

