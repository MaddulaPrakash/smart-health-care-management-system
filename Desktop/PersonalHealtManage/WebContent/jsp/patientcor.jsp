<!DOCTYPE html>
<html lang="en" class="no-js">
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title>Pateint Cordinator</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport"/>
        <meta content="" name="description"/>
        <meta content="" name="author"/>

        <!-- GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Hind:300,400,500,600,700" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath()%>/vendor/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

        <!-- PAGE LEVEL PLUGIN STYLES -->
        <link href="<%=request.getContextPath()%>/css/vendor/animate.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/vendor/swiper/css/swiper.min.css" rel="stylesheet" type="text/css"/>

        <!-- THEME STYLES -->
        <link href="<%=request.getContextPath()%>/css/vendor/layout.min.css" rel="stylesheet" type="text/css"/>

        <!-- Favicon -->
        <link rel="shortcut icon" href="favicon.ico"/>
		<script type="text/javascript">
			function preventBack(){window.history.forward();}
			setTimeout("preventBack()", 0);
			window.onunload=function(){null};
		</script>
    </head>
    <!-- END HEAD -->

    <!-- BODY -->
    <body id="body" data-spy="scroll" data-target=".header">

        <!--========== HEADER ==========-->
        <header class="header navbar-fixed-top">
            <!-- Navbar -->
            <nav class="navbar" role="navigation">
                <div class="container">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="menu-container js_nav-item">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="toggle-icon"></span>
                        </button>

                        <!-- Logo -->
                        <div class="logo">
                            <a class="logo-wrap" href="#body">
                                <img class="logo-img logo-img-main" src="<%=request.getContextPath()%>/img/logo.jpg" alt="Asentus Logo">
                                <img class="logo-img logo-img-active" src="<%=request.getContextPath()%>/img/logo-dark.png" alt="Asentus Logo">
                            </a>
                        </div>
                        <!-- End Logo -->
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse nav-collapse">
                        <div class="menu-container">
                            <ul class="nav navbar-nav navbar-nav-right">
                                <li class="js_nav-item nav-item"><a class="nav-item-child nav-item-hover" href="<%=request.getContextPath()%>/jsp/patientcor.jsp">Home</a></li>
                                <li class="js_nav-item nav-item"><a class="nav-item-child nav-item-hover" href="<%=request.getContextPath()%>/jsp/viewappointmentspc.jsp">View Appointments</a></li>
								<li class="js_nav-item nav-item"><a class="nav-item-child nav-item-hover" href="#work">Work</a></li>
                                <li class="js_nav-item nav-item"><a class="nav-item-child nav-item-hover" href="<%=request.getContextPath()%>/dia/logout.do">Logout</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- End Navbar Collapse -->
                </div>
            </nav>
            <!-- Navbar -->
        </header>
        <!--========== END HEADER ==========-->

        <!--========== SLIDER ==========-->
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
            <div class="container">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                </ol>
            </div>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item active">
                    <img class="img-responsive" src="<%=request.getContextPath()%>/img/1920x1080/01.jpg" alt="Slider Image">
                    <div class="container">
                        <div class="carousel-centered">
                            <div class="margin-b-40">
                                <h1 class="carousel-title">Cordinator</h1>
                                <p class="color-white">Avoid Caffeine, Alcohol, and Nicotine<br/>Caffeine and nicotine are stimulants and so will increase your level of stress rather than reduce it.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <img class="img-responsive" src="<%=request.getContextPath()%>/img/1920x1080/02.jpg" alt="Slider Image">
                    <div class="container">
                        <div class="carousel-centered">
                            <div class="margin-b-40">
                                <h2 class="carousel-title">Cordinator</h2>
                                <p class="color-white">You should also aim to avoid or<br/>reduce your intake of refined sugars</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--========== SLIDER ==========-->

        <!-- Work -->
        <div id="work">
            <div class="section-seperator">
                <div class="content-md container">
                    <div class="row margin-b-40">
                        <div class="col-sm-6">
                            <h2>Work</h2>
                            <p>Run Actively for kms. Walk Along the Path</p>
                        </div>
                    </div>
                    <!--// end row -->

                    <!-- Masonry Grid -->
                    <div class="masonry-grid row">
                        <div class="masonry-grid-sizer col-xs-6 col-sm-6 col-md-1"></div>
                        <div class="masonry-grid-item col-xs-12 col-sm-6 col-md-8 margin-b-30">
                            <!-- Work -->
                            <div class="work work-popup-trigger">
                                <div class="work-overlay">
                                    <img class="full-width img-responsive" src="<%=request.getContextPath()%>/img/800x400/01.jpg" alt="Portfolio Image">
                                </div>
                                <div class="work-popup-overlay">
                                    <div class="work-popup-content">
                                        <a href="javascript:void(0);" class="work-popup-close">Hide</a>
                                        <div class="margin-b-30">
                                            <h3 class="margin-b-5">Art Of Coding</h3>
                                            <span>Clean &amp; Minimalistic Design</span>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-8 work-popup-content-divider sm-margin-b-20">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p>Stress Management</p>
                                                    <ul class="list-inline work-popup-tag">
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Design,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Coding,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Portfolio</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p class="margin-b-5"><strong>Project Leader:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Designer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Developer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Customer:</strong> Icare Team</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End Work -->
                        </div>
                        <div class="masonry-grid-item col-xs-12 col-sm-6 col-md-4 margin-b-30">
                            <!-- Work -->
                            <div class="work work-popup-trigger">
                                <div class="work-overlay">
                                    <img class="full-width img-responsive" src="<%=request.getContextPath()%>/img/397x415/01.jpg" alt="Portfolio Image">
                                </div>
                                <div class="work-popup-overlay">
                                    <div class="work-popup-content">
                                        <a href="javascript:void(0);" class="work-popup-close">Hide</a>
                                        <div class="margin-b-30">
                                            <h3 class="margin-b-5">Art Of Coding</h3>
                                            <span>Clean &amp; Minimalistic Design</span>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-8 work-popup-content-divider sm-margin-b-20">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p>Stress Management Icare</p>
                                                    <ul class="list-inline work-popup-tag">
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Design,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Coding,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Portfolio</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p class="margin-b-5"><strong>Project Leader:</strong>Icare Team</p>
                                                    <p class="margin-b-5"><strong>Designer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Developer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Customer:</strong> Icare Team</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End Work -->
                        </div>
                        <div class="masonry-grid-item col-xs-12 col-sm-6 col-md-4 md-margin-b-30">
                            <!-- Work -->
                            <div class="work work-popup-trigger">
                                <div class="work-overlay">
                                    <img class="full-width img-responsive" src="<%=request.getContextPath()%>/img/397x300/01.jpg" alt="Portfolio Image">
                                </div>
                                <div class="work-popup-overlay">
                                    <div class="work-popup-content">
                                        <a href="javascript:void(0);" class="work-popup-close">Hide</a>
                                        <div class="margin-b-30">
                                            <h3 class="margin-b-5">Art Of Coding</h3>
                                            <span>Clean &amp; Minimalistic Design</span>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-8 work-popup-content-divider sm-margin-b-20">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p>Icare Stress Management Application</p>
                                                    <ul class="list-inline work-popup-tag">
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Design,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Coding,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Portfolio</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p class="margin-b-5"><strong>Project Leader:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Designer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Developer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Customer:</strong> Icare Team</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End Work -->
                        </div>
                        <div class="masonry-grid-item col-xs-12 col-sm-6 col-md-4 md-margin-b-30">
                            <!-- Work -->
                            <div class="work work-popup-trigger">
                                <div class="work-overlay">
                                    <img class="full-width img-responsive" src="<%=request.getContextPath()%>/img/397x300/02.jpg" alt="Portfolio Image">
                                </div>
                                <div class="work-popup-overlay">
                                    <div class="work-popup-content">
                                        <a href="javascript:void(0);" class="work-popup-close">Hide</a>
                                        <div class="margin-b-30">
                                            <h3 class="margin-b-5">Art Of Coding</h3>
                                            <span>Clean &amp; Minimalistic Design</span>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-8 work-popup-content-divider sm-margin-b-20">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p>Icare Stress Management Application</p>
                                                    <ul class="list-inline work-popup-tag">
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Design,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Coding,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Portfolio</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p class="margin-b-5"><strong>Project Leader:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Designer:</strong>Icare Team</p>
                                                    <p class="margin-b-5"><strong>Developer:</strong>Icare Team</p>
                                                    <p class="margin-b-5"><strong>Customer:</strong> Icare Team</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End Work -->
                        </div>
                        <div class="masonry-grid-item col-xs-12 col-sm-6 col-md-4">
                            <!-- Work -->
                            <div class="work work-popup-trigger">
                                <div class="work-overlay">
                                    <img class="full-width img-responsive" src="<%=request.getContextPath()%>/img/397x300/03.jpg" alt="Portfolio Image">
                                </div>
                                <div class="work-popup-overlay">
                                    <div class="work-popup-content">
                                        <a href="javascript:void(0);" class="work-popup-close">Hide</a>
                                        <div class="margin-b-30">
                                            <h3 class="margin-b-5">Art Of Coding</h3>
                                            <span>Clean &amp; Minimalistic Design</span>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-8 work-popup-content-divider sm-margin-b-20">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                  <p>Icare Stress Management Application</p>
                                                    <ul class="list-inline work-popup-tag">
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Design,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Coding,</a></li>
                                                        <li class="work-popup-tag-item"><a class="work-popup-tag-link" href="#">Portfolio</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="margin-t-10 sm-margin-t-0">
                                                    <p class="margin-b-5"><strong>Project Leader:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Designer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Developer:</strong> Icare Team</p>
                                                    <p class="margin-b-5"><strong>Customer:</strong> Icare Team</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End Work -->
                        </div>
                    </div>
                    <!-- End Masonry Grid -->
                </div>
            </div>
            
            <!-- Clients -->
            <div class="content-lg container">
                <!-- Swiper Clients -->
                <div class="swiper-slider swiper-clients">
                    <!-- Swiper Wrapper -->
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <img class="swiper-clients-img" src="<%=request.getContextPath()%>/img/clients/01.png" alt="Clients Logo">
                        </div>
                        <div class="swiper-slide">
                            <img class="swiper-clients-img" src="<%=request.getContextPath()%>/img/clients/02.png" alt="Clients Logo">
                        </div>
                        <div class="swiper-slide">
                            <img class="swiper-clients-img" src="<%=request.getContextPath()%>/img/clients/03.png" alt="Clients Logo">
                        </div>
                        <div class="swiper-slide">
                            <img class="swiper-clients-img" src="<%=request.getContextPath()%>/img/clients/04.png" alt="Clients Logo">
                        </div>
                        <div class="swiper-slide">
                            <img class="swiper-clients-img" src="<%=request.getContextPath()%>/img/clients/05.png" alt="Clients Logo">
                        </div>
                        <div class="swiper-slide">
                            <img class="swiper-clients-img" src="<%=request.getContextPath()%>/img/clients/06.png" alt="Clients Logo">
                        </div>
                    </div>
                    <!-- End Swiper Wrapper -->
                </div>
                <!-- End Swiper Clients -->
            </div>
            <!-- End Clients -->
        </div>
        <!-- End Work -->

            
             

            
        
        <!-- Back To Top -->
        <a href="javascript:void(0);" class="js-back-to-top back-to-top">Top</a>

        <!-- JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
        <!-- CORE PLUGINS -->
        <script src="<%=request.getContextPath()%>/vendor/jquery.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/jquery-migrate.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

        <!-- PAGE LEVEL PLUGINS -->
        <script src="<%=request.getContextPath()%>/vendor/jquery.easing.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/jquery.back-to-top.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/jquery.smooth-scroll.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/jquery.wow.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/swiper/js/swiper.jquery.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/masonry/jquery.masonry.pkgd.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/vendor/masonry/imagesloaded.pkgd.min.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBsXUGTFS09pLVdsYEE9YrO2y4IAncAO2U"></script>

        <!-- PAGE LEVEL SCRIPTS -->
        <script src="<%=request.getContextPath()%>/js/layout.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/components/wow.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/components/swiper.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/components/masonry.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/components/google-map.min.js" type="text/javascript"></script>

    </body>
    <!-- END BODY -->
</html>