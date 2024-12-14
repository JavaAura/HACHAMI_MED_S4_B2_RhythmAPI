package com.yc.Rhythm.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoProdConfig {
   // @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(
            new SimpleMongoClientDatabaseFactory("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.3.4")
        );
    }   
}
