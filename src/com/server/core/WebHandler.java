package com.server.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.server.core.pojo.Entity;
import com.server.core.pojo.Mapping;

import java.util.ArrayList;
import java.util.List;

public class WebHandler extends DefaultHandler{

    private List<Entity> entities;
    private  List<Mapping> mappings;

    private Entity entity;
    private Mapping mapping;
    private String tag;
    private boolean isMapping = false;

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    @Override
    public void startDocument() throws SAXException {
        entities =  new ArrayList<Entity>();
        mappings = new ArrayList<Mapping>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.println(qName+"--->start parsing  ");
        if(null != qName){
            tag = qName;
            if(tag.equals("servlet")){
                entity = new Entity();
                isMapping = false;
            }else if(tag.equals("servlet-mapping")){
                mapping = new Mapping();
                isMapping = true;
            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();

        if(tag != null){
            if(isMapping){
                if(tag.equals("servlet-name")){
                    if(contents.length() >0){
                        mapping.setName(contents);
                    }
                }else if(tag.equals("url-pattern")){
                    if(contents.length() >0){
                        mapping.addPattern(contents);
                    }
                }
            }else {
                if(tag.equals("servlet-name")){
                    if(contents.length() >0){
                        entity.setName(contents);
                    }
                }else if(tag.equals("servlet-class")){
                    if(contents.length() >0){
                        entity.setClz(contents);
                    }
                }
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println(qName+"--->end parsing  ");
        if(qName.equals("servlet")){
            entities.add(entity);
        }else if(qName.equals("servlet-mapping")){
            mappings.add(mapping);
        }
        tag = null;
    }
}

