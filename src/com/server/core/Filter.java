package com.server.core;

import com.server.core.pojo.Request;
import com.server.core.pojo.Session;

public class Filter {

    public static Boolean isValid(Request request){
        String sessionId = request.getSessionId();
        if( sessionId== null){
            return false;
        }


        // has sessionId
        if(sessionId != null){

            Session session = StudentInfoHandler.getInstance().getSessionById(sessionId);
            // has session
            if(session != null){
                if(!session.isExpired()){
                    return true;
                }
            }
        }

        return false;



    }
}
