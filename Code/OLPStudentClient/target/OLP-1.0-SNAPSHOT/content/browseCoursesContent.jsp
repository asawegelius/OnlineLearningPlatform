<%-- 
    Document   : browseCoursesContent
    Created on : Apr 6, 2016, 2:38:32 PM
    Author     : asawe
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.util.Scanner"%>
<%@page import="se.wegelius.olp.client.CourseClient"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="se.wegelius.olp.model.CourseBranch"%>
<%@page import="se.wegelius.olp.model.Course"%>
<%@page import="se.wegelius.olp.model.Course"%>
<%@page import="se.wegelius.olp.client.CourseBranchClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CourseBranchClient branchClient = new CourseBranchClient();
    String jsonBranches = branchClient.getJson().getEntity(String.class);
    List<CourseBranch> branches = new Gson().fromJson(jsonBranches, new TypeToken<List<CourseBranch>>() {
            }.getType());
    CourseClient courseClient = new CourseClient();
    String jsonCourses = courseClient.getJson().getEntity(String.class);
    List<Course> courses = new Gson().fromJson(jsonCourses, new TypeToken<List<Course>>() {
            }.getType());

%>
<script type="text/javascript">
<!--
    var d = document.getElementById("browse_courses");
    d.className += "active";
//-->
</script>
<!-- Begin container -->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2 left">
            <!--Sidebar content-->
            <div class="navbar navbar-fixed-left">
                <a class="navbar-brand" href="#">Topics</a>
                <ul class="nav navbar-nav">
                    <% for (CourseBranch branch : branches) {
                    out.write("<li class='dropdown'><a href='#' class='dropdown-toggle' data-toggle='dropdown'>"+branch.getCourseBranchName() +"<span class='caret'></span></a>");
                        out.write("<ul class='dropdown-menu' role='menu'>");
                            for(Course c: courses){
        System.out.println(c.getCourseName() + " branchId " + c.getCourseBranch().getCourseBranchId() + " branch branchid " + branch.getCourseBranchId());
                                if(c.getCourseBranch().getCourseBranchId() == branch.getCourseBranchId()){
                                    out.write("<li><a href='course.jsp'>" + c.getCourseName() + "</a></li>");
                                }
                            }
                        out.write("</ul>");
                    out.write("</li>");
                     }%>

                </ul>
            </div>
        </div>
        <div class="span10 content">
            <!--Body content-->
        </div>
    </div>
</div>

<!-- End container -->