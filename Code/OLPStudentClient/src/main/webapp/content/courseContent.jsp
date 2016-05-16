<%-- 
    Document   : template
    Created on : Apr 6, 2016, 2:34:17 PM
    Author     : asawe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            </div>
        </div>
        <div class="col-md-9 course-content">
            <!--Body content-->
            <ul>
                <li>                    
                    <p> <h3>awesome course!</h3></p>
                </li>
                <li>    
                    <!--
                    <video width="534" height="300" controls poster="assets/img/MMHO.jpg"  >

                        <source src="assets/img/videos/testLecture.mp4" type="video/mp4" />

                        <em>Sorry, your browser doesn't support HTML5 video.</em>

                    </video>
                     -->
                    <div style="position: relative; width: 640px;">
                        <video id=0 controls width=640 height=360>
                            <source src="assets/img/videos/test.ogv" type='video/ogg; codecs="theora, vorbis"'/>
                            <source src="assets/img/videos/test.webm" type='video/webm' >
                            <source src="assets/img/videos/test.mp4" type='video/mp4'>
                            <p>Video is not visible, most likely your browser does not support HTML5 video</p>
                        </video>
                    </div>
                </li>
                <li>
                    <a href="/sign_in?action_name=collect&amp;resource_id=490139" data-view="modalAjax" rel="nofollow" title="Add to Collection"><span>Add to Collection</span></a>
                </li>
            </ul>
        </div>
    </div>
</div>    
<!-- End container -->