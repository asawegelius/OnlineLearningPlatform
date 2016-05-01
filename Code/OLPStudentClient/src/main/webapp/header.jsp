<%-- 
    Document   : header
    Created on : Apr 6, 2016, 2:24:20 PM
    Author     : asawe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="assets/img/icons/favicon.ico">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<title>OLP Online Learning Platform</title>

<script>
    $('#loginModal').on('hidden.bs.modal', function () {
        $('.modal-body').find('lable,input,textarea').val('');

    });
</script>

<link href="assets/css/main.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="assets/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

</head>

<body>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">OLP</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li id="browse_courses"><a href="browseCourses.jsp">Browse Courses</a></li>
                    <li id="about"><a href="about.jsp">About</a></li>
                    <li id="contact"><a href="contact.jsp">Contact</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <%
                        String user = null;
                        //check if no on has logged in:
                        if(session.getAttribute("user") == null){
                            // check if there is a message to display
                            if(session.getAttribute("msg")!= null){
                                 out.write("<li><p class=\"navbar-text\"> " + (String) session.getAttribute("msg")+ "</p></li>");
                            }
                            out.write("<li><a class=\"navlink\" href=\"#\" data-toggle=\"modal\" data-target=\"#loginModal\">Login/Sign Up</a></li>");
                        }
                    %>
                    
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>


    <%@ include file="content/loginRegisterContent.jsp" %>