<%-- 
    Document   : template
    Created on : Apr 6, 2016, 2:34:17 PM
    Author     : asawe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
Object userId= session.getAttribute("userId");
%>
<script type="text/javascript">
<!--
    var d = document.getElementById("browse_courses");
    d.className += "";
//-->
</script>
<!-- Begin container -->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-3">
            <!--Sidebar content-->
            <div class="navbar course-tabs">                          
                <ul class="nav nav-tabs tabbable">
                    <li class="active"><a href="#one" data-toggle="tab">Lectures</a></li>
                    <li><a href="#two" data-toggle="tab">Exercises</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="one">
                        <ul>
                            <li><a>Lecture 1</a></li>
                            <li><a>Lecture 2</a></li>
                            <li><a>Lecture 3</a></li>
                            <li><a>Lecture 4</a></li>
                            <li><a>Lecture 5</a></li>
                        </ul>
                    </div>
                    <div class="tab-pane" id="two">
                        <ul>
                            <li><a>Exercise 1</a></li>
                            <li><a>Exercise 2</a></li>
                            <li><a>Exercise 3</a></li>
                            <li><a>Exercise 4</a></li>
                            <li><a>Exercise 5</a></li>
                        </ul>
                    </div>

                </div>
                <%
                    if (userId!=null){
                         out.write("<form action='playlist' method='post' id='followForm'>");
                            out.write("<input type='hidden' name='userid' value='"+userId.toString()+"' />");
                            // Replace course id by the real one when actual courses will be displayed
                            out.write("<input type='hidden' name='courseid' value='1' />");
                            out.write("<button type='submit' id='follow_btn' class='btn btn-block bt-login' data-loading-text='Following...'>Follow</button>");
                        out.write("</form>");
                    }
                    else {
                        out.write("<a href='#' style='text-decoration:none' data-toggle='modal' data-target='#loginModal'><button id='follow_btn' class='btn btn-block bt-login' data-loading-text='Following...'>Follow</button></a>");
                    }
                %>
            </div>
            
        </div>
        
        <div class="col-md-9 course-content">
            <!--Body content-->
            <p> <h3>Awesom course!</h3></p>
            <video width="534" height="300" controls poster="assets/img/MMHO.jpg"  >

                <source src="assets/img/videos/testLecture.mp4" type="video/mp4" />

                <em>Sorry, your browser doesn't support HTML5 video.</em>

            </video>
        
        </div>
    </div>
</div>    
<!-- End container -->