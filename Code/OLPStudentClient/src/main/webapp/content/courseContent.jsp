<%-- 
    Document   : template
    Created on : Apr 6, 2016, 2:34:17 PM
    Author     : asawe
--%>

<%@page import="se.wegelius.olp.model.LectureDeserializer"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="se.wegelius.olp.client.LectureClient"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="se.wegelius.olp.model.Lecture"%>
<%@page import="se.wegelius.olp.client.CourseClient"%>
<%@page import="se.wegelius.olp.model.Course"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="java.util.List"%>
<%@page import="se.wegelius.olp.model.Playlist"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="se.wegelius.olp.client.PlaylistClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object userId = session.getAttribute("userId");
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Lecture.class, new LectureDeserializer());
    Object lectId = session.getAttribute("lectureId");
    Gson gson = builder.create();
    int courseId = -1;
    int lectureId;
    Course course = null;
    LectureClient lClient = new LectureClient();
    Lecture[] lectureArray = new Lecture[0];
    if (!request.getParameter("courseId").isEmpty()) {
        courseId = Integer.parseInt(request.getParameter("courseId"));
        CourseClient cClient = new CourseClient();
        String jsonCourse = cClient.getJson(courseId).getEntity(String.class);
        course = new Gson().fromJson(jsonCourse, Course.class);
        String jsonLectures = lClient.getJsonCourse(courseId).getEntity(String.class);
        lectureArray = (Lecture[]) gson.fromJson(jsonLectures, Lecture[].class);
    }
    if (lectId != null) {
        lectureId = (int)lectId;
    } else {
        lectureId = 1;
    }
    boolean isInPlaylist = false;
    Playlist current = null;
    PlaylistClient pClient = new PlaylistClient();
    String jsonPlaylist = pClient.getJsonByUser((int) userId).getEntity(String.class);
    List<Playlist> playlists = new Gson().fromJson(jsonPlaylist, new TypeToken<List<Playlist>>() {
    }.getType());

    for (Playlist p : playlists) {
        if (p.getCourseId() == courseId) {
            isInPlaylist = true;
            current = p;
            break;
        }
    }
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
                            <%
                                for (Lecture l : lectureArray) {
                                    out.write("<li><a>" + l.getLectureName() + "</a></li>");
                                }
                            %>
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
                <%                    if (userId != null) {
                        if (!isInPlaylist) {
                            out.write("<form action='playlist' method='post' id='followForm'>");
                            out.write("<input type='hidden' name='userid' value='" + userId.toString() + "' />");
                            // Replace course id by the real one when actual courses will be displayed
                            out.write("<input type='hidden' name='courseid' value='" + courseId + "' />");
                            out.write("<button type='submit' id='follow_btn' class='btn btn-block bt-login' data-loading-text='Following...'>Follow</button>");
                            out.write("</form>");
                        } else {
                            out.write("<form action='playlist' method='get' id='followForm'>");
                            out.write("<input type='hidden' name='action' value='delete' />");
                            out.write("<input type='hidden' name='playlistid' value='" + current.getPlaylistId() + "' />");
                            out.write("<button type='submit' id='follow_btn' class='btn btn-block bt-login' data-loading-text='Unfollowing...'>Unfollow</button>");
                            out.write("</form>");
                        }
                    } else {
                        out.write("<a href='#' style='text-decoration:none' data-toggle='modal' data-target='#loginModal'><button id='follow_btn' class='btn btn-block bt-login' data-loading-text='Following...'>Follow</button></a>");
                    }
                %>
            </div>

        </div>

        <div class="col-md-9 course-content">
            <!--Body content    /-->        
            <p> <h3><% out.write(course.getCourseName());%></h3></p>

            <p> <h4><% out.write(lectureArray[lectureId].getLectureName());%></h4></p>
            <div style="position: relative; width: 640px;">
                <video id=0 controls width=640 height=360>
                    <% out.write("<source src='assets/img/videos/" + lectureArray[lectureId].getVideo() + ".ogv' type='video/ogg;codecs=\"theora, vorbis\"'>");%>
                    <% out.write("<source src='assets/img/videos/" + lectureArray[lectureId].getVideo() + ".webm' type='video/webm'>");%>
                    <% out.write("<source src='assets/img/videos/" + lectureArray[lectureId].getVideo() + ".mp4' type='video/mp4'>");%>
                    <p>Video is not visible, most likely your browser does not support HTML5 video</p>
                </video>
            </div>
        </div>
    </div>
</div>    
<!-- End container -->