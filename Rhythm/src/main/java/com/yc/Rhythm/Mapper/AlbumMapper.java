package com.yc.Rhythm.Mapper;





import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.yc.Rhythm.dto.req.AlbumRequest;
import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.entity.Album;

@Mapper(componentModel = "spring",uses = {SongMapper.class})
public interface AlbumMapper {

    @Mapping(target = "songs", ignore = true) 
    Album toEntity(AlbumRequest request);

    @Mapping(target = "songs", source = "songs") 
    AlbumResponse toResponse(Album album);

    
}       