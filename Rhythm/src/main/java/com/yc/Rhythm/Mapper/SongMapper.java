package com.yc.Rhythm.Mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.dto.res.SongSimpleResponse;
import com.yc.Rhythm.entity.Song;


@Mapper(componentModel = "spring")
public interface SongMapper {
    
    @Mapping(target = "album.id", source = "albumId")
    Song toEntity(SongRequest request);
    
    @Mapping(target = "album", source = "album")
    SongResponse toResponse(Song song);

    @Named("toSimpleResponseList")
    List<SongSimpleResponse> toSimpleResponseList(List<Song> songs);


}