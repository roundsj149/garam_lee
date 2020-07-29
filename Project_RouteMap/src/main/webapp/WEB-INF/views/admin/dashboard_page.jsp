<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/dashboard_page.css">
	<!-- ChartJS -->
	<script src="${pageContext.request.contextPath}/resources/js/admin/Chart.min.js"></script>
	<!-- GoogleCharts -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/admin/dashboard_page.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/admin/jquery.flot.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/admin/jquery.flot.resize.min.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">대쉬보드</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">대쉬보드</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <div class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-lg-6">
            <div class="card">
              <div class="card-header">
                  <h3 class="card-title">게시글 차트</h3>
                  <div class="card-tools">
	                  <a href="javascript:void(0);" class="btn btn-tool btn-sm" onclick="viewDonut2()">
	                   	어제
	                  </a>
	                  <a href="javascript:void(0);" class="btn btn-tool btn-sm" onclick="viewDonut1()">
	                   	오늘
	                  </a>
                  </div>
              </div>
              <div class="card-body" id="donutid">
                <div class="d-flex">
                  <p class="d-flex flex-column">
                    <span class="text-bold text-lg" id="totalCount"></span>
                    <span class="text-bold text-lg" id="totalCount2" style="display:none;"></span>
                    <span>총 게시글</span>
                  </p>
                  <p class="ml-auto d-flex flex-column text-right">
                    <span class="text-success" id="testtest">
                    </span>
                    <span class="text-success" id="testtest2" style="display:none;">
                    </span>
                    <span class="text-muted">전 일 대비</span>
                  </p>
                </div>
                <!-- /.d-flex -->

                <div class="position-relative mb-4 chartjs-render-monitor">
                   <canvas id="donutChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                   <canvas id="donutChart2" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%; display:none;"></canvas>
                </div>
              </div>
            </div>
            <!-- /.card -->

            <div class="card">
              <div class="card-header">
                <h3 class="card-title">인기 게시글</h3>
                <div class="card-tools">
                  <a href="javascript:void(0);" class="btn btn-tool btn-sm" onclick="hideRTChart()">
                   	여행지/맛집
                  </a>
                  <a href="javascript:void(0);" class="btn btn-tool btn-sm" onclick="hideLcChart()">
                                             루트공유
                  </a>
                </div>
              </div>
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                    <th>순위</th>
                    <th>제목</th>
                    <th>좋아요</th>
                    <th>링크</th>
                  </tr>
                  </thead>
                  <tbody id="likeBoard">
                  </tbody>
                  <tbody id="likeBoard2" style="display:none;">
                  </tbody>
                </table>
              </div>
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col-md-6 -->
          <div class="col-lg-6">
            <div class="card">
              <div class="card-header">
                  <h3 class="card-title">회원 유입 수</h3>
				  <div class="card-tools">
	                <a href="javascript:void(0);" class="btn btn-tool btn-sm" onclick="LastStack()">
	                                             전 주
	                </a>
                    <a href="javascript:void(0);" class="btn btn-tool btn-sm" onclick="thisStack()">
                     	이번 주
                    </a>
                  </div>
              </div>
              <div class="card-body" style="padding:2rem;">
                <div class="d-flex">
                  <p class="d-flex flex-column">
                    <span class="text-bold text-lg" id="thisLoginTotalCount"></span>
                    <span class="text-bold text-lg" id="lastLoginTotalCount" style ="display:none;"></span>
                    <span>총 유입수</span>
                  </p>
                  
                  <p class="ml-auto d-flex flex-column text-right">
                    <span class="text-success" id="thisStackInc">
                    </span>
                    <span class="text-success" id="lastStackInc" style="display:none;">
                    </span>
                    <span class="text-muted">전 주 대비</span>
                  </p>
                </div>
                <canvas id="stackedBarChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                <canvas id="stackedBarChart2" style="display:none; min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                <!-- /.d-flex -->
              </div>
            </div>
            <div class="card card-outline">
              <div class="card-header" style="margin-top:3px;">
                <h3 class="card-title">
                  	실시간 회원 유입 수
                </h3>
              </div>
              <div class="card-body" style="padding:0px;">
                <div id="chart" class="chart" style="width: 800px; height: 343px; max-height: 343px; max-width: 100%;"></div>
              </div>
              <!-- /.card-body-->
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col-md-6 -->
        </div>
        <!-- /.row -->
      </div>
      <!-- /.container-fluid -->
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->