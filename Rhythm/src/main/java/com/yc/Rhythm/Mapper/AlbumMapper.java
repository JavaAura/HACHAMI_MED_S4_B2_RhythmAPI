package com.yc.Rhythm.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.yc.Rhythm.dto.req.AlbumRequest;
import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.entity.Album;
import com.yc.Rhythm.entity.Category;

@Mapper(componentModel = "spring", uses = {SongMapper.class, CategoryMapper.class})
public interface AlbumMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "songs", ignore = true)
    @Mapping(target = "coverImageId", ignore = true)
    @Mapping(target = "category", ignore = true)
    Album toEntity(AlbumRequest request);

    @Mapping(target = "songs", ignore = true)
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    AlbumResponse toResponse(Album album);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "songs", ignore = true)
    @Mapping(target = "coverImageId", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntityFromRequest(AlbumRequest request, @MappingTarget Album album);

    @AfterMapping
    default void mapSongs(Album album, @MappingTarget AlbumResponse response) {
        if (album.getSongs() != null) {
            List<SongResponse> songResponses = album.getSongs().stream()
                .map(song -> new SongResponse(song.getId(), song.getTitle()))
                .collect(Collectors.toList());
            response.setSongs(songResponses);
        }
    }

    @AfterMapping
    default void mapCategory(Album album, @MappingTarget AlbumResponse response) {
        Category category = album.getCategory();
        if (category != null) {
            response.setCategoryId(category.getId());
            response.setCategoryName(category.getName());
        }
    }
}

