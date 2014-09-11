var pagefunction = function() {
	var runningTrackTable = $('#datatable_col_reorder')
			.DataTable(
					{
						columns : [
								{
									data : 'initiator'
								}, 
								{
									data : 'subject'
								},
								{
									data : 'startTime'
								},
								{
									"orderable" : false,
									"data" : 'instanceStatus',
									"defaultContent" : "-"
								},
								{
									data : 'initiator'
								},
								{
									"orderable" : false,
									"data" : 'processLocation',
									"defaultContent" : "-"
								},
								{
									"orderable" : false,
									"data" : null,
									"width":"15%",
									"defaultContent" : ""//"<a href='javascript:void(0);' class='btn btn-danger'>查看</a> "
								} ],
						columnDefs : [ 
								{
									"targets" : [ 0 ],
									"orderable" : false,
									"width":20,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
								
										$(td).html("<img width='20' height='20' class='online' src='/foxbpm-webapps-common/service/identity/users/"+cellData+"/picture'/>");
										
									}
								},
								{
									"targets" : [ 3 ],
									"orderable" : true,
									"createdCell" : function(td, cellData,
											rowData, row, col) {
										if (rowData.endTime == null) {
											$(td)
													.html(
															"<span class='label label-default'>激活</span>");
											if (rowData.isSuspended == 'true') {
												$(td)
														.html(
																"<span class='label label-default'>暂停</span>");
											}
											if (rowData.assignee == null) {
												$(td)
														.html(
																"<span class='label label-default'>未领取</span>");
											}

										} else {
											$(td)
													.html(
															"<span class='label label-default'>完成</span>");
										}

									}
								} ],

						"processing" : true,
						"orderable" : true,
						"serverSide" : true,
						"ajax" : "/foxbpm-webapps-common/service/runtime/process-instances",
						"sDom" : "<'dt-toolbar'<'col-sm-6 col-xs-12 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'>C>"
								+ "t"
								+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p l>>",
						"autoWidth" : true,
						"colVis" : {
							"buttonText" : "选择展示的列",
							"restore" : "恢复默认展示",
							"showAll" : "展示所有列",
							"showNone" : "不展示列"
						},
						"oLanguage" : {
							"sProcessing" : "正在加载中......",
							"sZeroRecords" : "对不起，查询不到相关数据！",
							"sEmptyTable" : "表中无数据存在！",
							"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
							"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
							"sSearch" : "搜索",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "上一页",
								"sNext" : "下一页",
								"sLast" : "末页"
							},
							"sSearch": "_INPUT_ "+
                    		"<select id='TASKSTATE_SEARCH' class='form-control'><option value='0'>未领取</option><option value='1'>领取</option><option value='2' selected>所有</option></select> " +
                    		"&nbsp;&nbsp;<input placeholder='创建起始' id='createtime_start_dateselect_filter' type='text'  class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:15%'>" +
                    		"<label for='createtime_start_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title='' style='left:-50%;top:-5px'></label>" +
                    		" - " +
                    		"<input placeholder='创建终止' id='createtime_end_dateselect_filter' type='text' value='' class='form-control datepicker' data-dateformat='yy-mm-dd' style='width:15%'>"+
                    		"<label for='createtime_end_dateselect_filter' class='glyphicon glyphicon-calendar no-margin padding-top-15' rel='tooltip' title=''  style='left:-32%;top:-5px'></label> " +
                    		"&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' onclick='searchTodoTask();' href='javascript:void(0);'>搜索</a>&nbsp;&nbsp;<a class='btn btn-primary' style='height: 30px; disabled: true;' href='javascript:void(0);' onclick='clearCondition();'>重置</a></form>"
						},
						"bStateSave" : false,
						"bAutoWidth" : true,
						"sPaginationType" : 'full_numbers',
						"drawCallback":function(){
							//调整页面布局
							$("#datatable_col_reorder_length").css("padding-right","10px");
							$(".dt-toolbar").css("padding-bottom","6px");
							$(".dt-toolbar").css("padding-right","32px");
							$("#datatable_col_reorder_filter").css("width","900px");
							$("[type='search']").attr("placeholder","主题/发起人");
							 
							pageSetUp();		
						}

					});
	runningTrackTable.column(1).order( 'asc' );
	clearCondition = function (){
		$("[type='search']").val("");
		$("#INITIATOR_SEARCH").val("");
		$("#TASKSTATE_SEARCH").val("2");
		$("#createtime_end_dateselect_filter").val("");
		$("#createtime_start_dateselect_filter").val("");
		var baseUrl = "/foxbpm-webapps-common/service/runtime/process-instances?participate=participate&";
		runningTrackTable.ajax.url(baseUrl).load();
	};
     searchTodoTask = function() {
    	var baseUrl = "/foxbpm-webapps-common/service/runtime/process-instances?participate=participate&";
    	var assigneed = $("#TASKSTATE_SEARCH").val();
    	var subjectLike =  $("[type='search']").val();
    	baseUrl = baseUrl + "assigneed="+assigneed;
    	if(subjectLike != ""){
    		baseUrl = baseUrl + "&subjectLike="+ subjectLike;
    	}
     	var initiator = "";//$("#INITIATOR_SEARCH").val();
     	if(initiator != ""){
     		baseUrl = baseUrl + "&initiator="+ initiator;
     	}
    	 
	    //创建
	    var  createTimeB =  $("#createtime_start_dateselect_filter").val();
	    if(createTimeB != ""){
	    	baseUrl = baseUrl + "&createTimeB="+ createTimeB;
	    }
	    var createTimeE = $("#createtime_end_dateselect_filter").val();
	    if(createTimeE != "")
	    {
	    	baseUrl = baseUrl + "&createTimeE="+ createTimeE;
	    }
     	
	    runningTrackTable.ajax.url(baseUrl).load();
     };
};