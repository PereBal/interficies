<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <!--[if IE]>
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
            <![endif]-->
        <title>Bootstrap Registration Page</title>
        <!-- Bootstrap Core CSS -->
        <link href="bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

        <!-- Custom CSS -->
        <link href="css/dashboard.css" rel="stylesheet" type="text/css"/>

        <!-- Custom Fonts -->
        <link href="bootstrap-3.3.6-dist/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
            <!-- GOOGLE FONT -->
            <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

    </head>
    <body>
        <div class="container">
            <div class="row text-center pad-top ">
                <div class="col-md-12">
                    <h2>Bootstrap Registration Page</h2>
                </div>
            </div>
            <div class="row  pad-top">

                <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <strong>   Register Yourself </strong>  
                        </div>
                        <div class="panel-body">
                            <form role="form" action="Signup" method="post">
                                <br/>
                                <div class="form-group input-group">
                                    <span class="input-group-addon"><i class="fa fa-circle-o-notch"  ></i></span>
                                    <input type="text" name="userName" class="form-control" placeholder="Your Name" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                    <input type="text" class="form-control" placeholder="Desired Username" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon">@</span>
                                    <input type="text" class="form-control" placeholder="Your Email" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                                    <input type="password" name="userPassword" class="form-control" placeholder="Enter Password" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                                    <input type="password" class="form-control" placeholder="Retype Password" />
                                </div>

                                <input type="submit" value="Register Me" class="btn btn-success">
                                    <hr />
                                    Already Registered ?  <a href="#" >Login here</a>
                            </form>
                        </div>

                    </div>
                </div>


            </div>
        </div>


        <!-- JAVASCRIPT FILES PLACED AT THE BOTTOM TO REDUCE THE LOADING TIME  -->
        <!-- jQuery -->
        <script src="jquery/jquery.js" type="text/javascript"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="bootstrap-3.3.6-dist/js/bootstrap.min.js" type="text/javascript"></script>

        <!-- Custom Theme JavaScript -->
        <script src="js/dashboard.js"></script>

    </body>
</html>
