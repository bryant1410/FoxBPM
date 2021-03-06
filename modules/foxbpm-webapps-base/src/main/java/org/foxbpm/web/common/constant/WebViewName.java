package org.foxbpm.web.common.constant;

/**
 * 表示层的名称定义，用于映射/jsp/目录定义的jsp文件, 目前表示层支持.jsp格式文件
 * 
 * @author MEL
 * @date 2014-06-06
 */
public final class WebViewName {
	public final static String START_PROCESS_VIEWNAME = "startProcess";
	// 错误视图
	public final static String ERROR_VIEWNAME = "error";
	/*************************** 任务中心 ***********************************/
	// 查询代办任务视图
	public final static String QUERY_QUERYTODOTASK_VIEWNAME = "queryTodoTask";
	// 查询流程定义视图
	public final static String QUERY_QUERYALLPROCESSDEF_VIEWNAME = "queryProcessDef";
	// 查询流程实例视图
	public final static String QUERY_QUERYALLPROCESSINST_VIEWNAME = "queryProcessInst";
	// 查询任务详细信息视图
	public final static String QUERY_TASKDETAILINFOR_ACTION = "queryTaskDetailInfor";
	// 开启一个任务视图
	public final static String START_TASK_VIEWNAME = "startTask";
	// 处理任务视图
	public final static String DO_TASK_VIEWNAME = "doTask";
	public final static String SET_DELEGATION = "setDelegation";
	/*************************** 管控中心 ***********************************/
	// 流程定义视图
	public final static String PROCESSDEF_VIEWNAME = "processDef";
	// 流程管理视图
	public final static String PROCESSINSTMANAGE_VIEWNAME = "processInstManage";
	// 流程变量视图
	public final static String PROCESSVARIABLEMANAGE_VIEWNAME = "processVariableManage";

	// 流程定义视图
	public final static String SELECT_PROCESSDEF = "selectProcessDefList";
	public final static String SELECT_USER = "selectUserList";
	// 成功视图
	public final static String RESULT = "common/result";
}
