package com.server.core;

import com.server.core.pojo.Entity;
import com.server.core.pojo.Mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {
    List<Entity> entities = null;
    List<Mapping> mappings = null;

    //key --> servlet-name value --> servlet-class
    private Map<String,String> entityMap = new HashMap<>();

    // key --> url-pattern value -->servlet-name
    private Map<String,String> mappingMap = new HashMap<>();



    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;

        for(Entity entity:entities){
//            System.out.println("entity: " + entities);
            entityMap.put(entity.getName(),entity.getClz());
        }

        for (Mapping mapping:mappings){
            for(String pattern:mapping.getPatterns()){
//                System.out.println("pattern: "+pattern );
                mappingMap.put(pattern,mapping.getName());
            }
        }
    }

    /**
     * get the corresponding class name by url(pattern)
     * @param pattern
     * @return class name
     */
    public String getClz(String pattern){
//        System.out.println("pattern:---> "+pattern);
        String name = mappingMap.get(pattern);
        return entityMap.get(name);
    }
}
