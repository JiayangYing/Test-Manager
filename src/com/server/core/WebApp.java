package com.server.core;

import com.server.core.pojo.Entity;
import com.server.core.pojo.Mapping;
import com.service.servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class WebApp {
    private static WebContext webContext;

    static {
        try {
            // SAX(Simple API for XML) parse
            //1 get the parsing factory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            //2. get the parser
            SAXParser parser = factory.newSAXParser();

            //3. load the Document processor

            //4. writer handlers
            WebHandler webHandler = new WebHandler();

            //5. parse
            parser.parse(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("web.xml")
                    , webHandler);

            List<Entity> entities = webHandler.getEntities();
            List<Mapping> mappings = webHandler.getMappings();

            webContext = new WebContext(entities, mappings);

        } catch (Exception e) {
            System.out.println("Error parsing the configuration file");
        }
    }

    /**
     * get corresponding servlet by url
     *
     * @param url
     * @return
     */
    public static Servlet getServletByUrl(String url) {

//        System.out.println("url = " + url);
//        System.out.println(webContext);



//        System.out.println(className);
        try {
            String className = webContext.getClz("/" + url);
            Class<?> clz = Class.forName(className);

            Servlet servlet = (Servlet) clz.getConstructor().newInstance();

            System.out.println(servlet);
            return servlet;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}
