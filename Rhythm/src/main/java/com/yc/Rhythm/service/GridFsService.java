package com.yc.Rhythm.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class GridFsService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public String storeFile(MultipartFile file) throws IOException {
        return gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        ).toString();
    }


    public GridFSFile getFile(String id) {
        return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    }

    public void deleteFile(String id) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }
}

