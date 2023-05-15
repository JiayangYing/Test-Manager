package com.service.servlet.impl;

import com.server.core.Filter;
import com.server.core.StudentInfoHandler;
import com.server.core.pojo.Question;
import com.server.core.pojo.Request;
import com.server.core.pojo.Response;
import com.server.core.pojo.Student;
import com.service.servlet.Servlet;

import java.io.IOException;

public class TestServlet implements Servlet {
    @Override
    public void service(Request request, Response response) throws Exception {
        if(!Filter.isValid(request))
        {
            response.pushToBrowser(302,"http://localhost:8080/homepage");
        }else {

            System.out.println("TestServlet");
            String requestMethod = request.getRequestMethod();
            System.out.println("requestMethod--> " + requestMethod);
            if (requestMethod.equals("GET")) {
                doGet(request, response);
            } else if (requestMethod.equals("POST")) {
                doPost(request, response);
            }
        }
    }

    private void doGet(Request request, Response response) throws IOException {
        // get or set questions
        String sessionId = request.getSessionId();
        Student student = StudentInfoHandler.getInstance().getStudentBySessionId(sessionId);
        Question[] questions = student.getQuestions();
        if(questions[0] == null){
            for (int i = 0; i < questions.length - 1; i++) {
                questions[i] = new Question("multiple", "JAVA",
                        "which  is your favourite programming language?",
                        new String[]{"Python", "Java", "c"});
            }
            questions[questions.length-1] = new Question("challenge", "C", "Please print \"Hello C\"");
            student.setQuestions(questions);
        }

        // Start building the HTML page
        response.print("<!DOCTYPE html>");
        response.print("<html>");
        response.print("<head>");
        response.print("<title>Question Page</title>");
        response.print("<style>");
        response.print(".question { display: none; }");
        response.print(".question.active { display: block; }");
        response.print("</style>");
        response.print("</head>");
        response.print("<body>");
        response.print("<form id=\"questionForm\" method=\"POST\" action=\"http://localhost:8080/test\">");

// Add questions and choices
        for (int qNum = 0; qNum < questions.length; qNum++) {

            Question question = questions[qNum];
            response.print("<div class=\"question" + (qNum == 0 ? " active" : "") + "\">");
            response.print("<h1>Q" + (qNum + 1) + "</h1>");
            response.print("<h2>" + question.getContent() + "</h2>");
            response.print("<input type=\"hidden\" name=\"questionNumber\" value=\"" + qNum + "\">");

            if(question.getType().equals("multiple")){
                String[] choices = question.getChoice();
                for (int i = 0; i < choices.length; i++) {
                    String choice = choices[i];
                    response.print("<input type=\"radio\" name=\"answer" + qNum + "\" value=\"" + i + "\" id=\"choice-" + qNum + "-" + i + "\">");
                    response.print("<label for=\"choice-" + qNum + "-" + i + "\">" + choice + "</label><br>");
                }
            }else {
                //<textarea id="answer" name="answer" required></textarea>
                response.print("<textarea name=\"answer" + qNum + "\" style=\"width: 100%; height: 100px;\">" + "</textarea>");
            }


            response.print("</div>");
        }

// Add navigation buttons
        response.print("<button type=\"button\" onclick=\"showQuestion(currentIndex - 1)\" id=\"previous\" disabled>Previous</button>");
        response.print("<button type=\"button\" onclick=\"showQuestion(currentIndex + 1)\" id=\"next\">Next</button>");
        response.print("<button type=\"submit\" formaction=\"/test\" id=\"submit\" style=\"display:none;\">Submit</button>");
        response.print("<button type=\"submit\" formaction=\"/homepage\" id=\"saveAndGoHome\">Save and Return to Home</button>");
        response.print("</form>");

// JavaScript for handling navigation and showing the right buttons
        response.print("<script>");
        response.print("var currentIndex = 0;");
        response.print("var totalQuestions = " + questions.length + ";");
        response.print("function showQuestion(index) {");
        response.print("  if (index < 0 || index >= totalQuestions) return;");
        response.print("  currentIndex = index;");
        response.print("  document.getElementById('previous').disabled = (currentIndex === 0);");
        response.print("  document.getElementById('next').disabled = (currentIndex === totalQuestions - 1);");
        response.print("  document.getElementById('submit').style.display = (currentIndex === totalQuestions - 1) ? 'inline' : 'none';");
        response.print("  var questions = document.getElementsByClassName('question');");
        response.print("  for (var i = 0; i < questions.length; i++) {");
        response.print("    questions[i].classList.remove('active');");
        response.print("  }");
        response.print("  questions[index].classList.add('active');");
        response.print("}");
        response.print("</script>");
        response.print("</body>");
        response.print("</html>");

        response.pushToBrowser(200);

    }

    private void doPost(Request request, Response response) throws IOException {
        response.pushToBrowser(302,"http://localhost:8080/homepage");
        System.out.println(request.getQueryStr());

    }


}
