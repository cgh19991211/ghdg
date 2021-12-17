package com.gh.filecenter.service;

import com.gh.filecenter.entities.File;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {
    @Autowired
    private GridFSBucket gridFSBucket;
    
    @Autowired
    private GridFsTemplate gridFsTemplate;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public File getFileById(String id){
        return mongoTemplate.findById(id,File.class);
    }
    
    @Transactional
    public File saveFile(File file){
        return mongoTemplate.save(file);
    }
}
