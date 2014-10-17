/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author yangguangftlp
 */
package org.foxbpm.bpmn.converter.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Element;
import org.dom4j.Node;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.model.Connector;
import org.foxbpm.model.InputParam;
import org.foxbpm.model.OutputParam;

/**
 * 
 * BpmnXML工具类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class BpmnXMLUtil implements BpmnXMLConstants {
	
	/**
	 * 处理针对element节点限定名称(xx:aa)的本地部分aa
	 * 
	 * @param nodeName
	 *            element节点名称
	 * @return 名称
	 */
	public static String getEleLoclaName(String nodeName) {
		int index = nodeName.indexOf(':');
		if (index > 0) {
			return nodeName.substring(index + 1);
		}
		return nodeName;
	}
	/**
	 * 表达式解析
	 * 
	 * @param element
	 *            表达式节点
	 * @return 返回表达式
	 */
	@SuppressWarnings("rawtypes")
	public static String parseExpression(Element element) {
		Node node = null;
		for (Iterator iterator = element.nodeIterator(); iterator.hasNext();) {
			node = (Node) iterator.next();
			if (Element.CDATA_SECTION_NODE == node.getNodeType()) {
				return node.getText();
			}
		}
		return null;
	}
	/**
	 * 连接器解析
	 * 
	 * @param element
	 *            连接器节点connectorInstanceElements
	 * @return 返回连接器实例对象
	 */
	@SuppressWarnings("rawtypes")
	public static Connector parserConnectorElement(Element element) {
		Element elem = null;
		Connector connector = new Connector();
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			parserElementConnector(connector, elem);
		}
		return connector;
	}
	/**
	 * 设置连接器属性
	 * 
	 * @param connector
	 *            连接器对象
	 * @param element
	 *            连接器节点或子节点
	 * 
	 * @return 返回连接器实例对象
	 */
	@SuppressWarnings("rawtypes")
	public static void parserElementConnector(Connector connector, Element element) {
		Element elem = null;
		String parentNodeName = element.getName();
		String nodeName = null;
		String expression = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_CONNECTORINSTANCE.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_INPUTS.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_TIMEEXPRESSION.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_SKIPCOMMENT.equals(nodeName)) {
				parserElementConnector(connector, elem);
				// 继续下一个
				continue;
			}
			if (BpmnXMLConstants.ELEMENT_EXPRESSION.equalsIgnoreCase(nodeName)) {
				// 表达式解析
				expression = parseExpression(elem);
			} else if (BpmnXMLConstants.ELEMENT_DOCUMENTATION.equalsIgnoreCase(nodeName)) {
				connector.setDocumentation(elem.getText());
			}
			/** 连接输入参数 */
			if (BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_INPUTS.equalsIgnoreCase(parentNodeName)) {
				
				if (null == connector.getInputsParam()) {
					connector.setInputsParam(new ArrayList<InputParam>());
				}
				InputParam inputParam = new InputParam();
				inputParam.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
				inputParam.setName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_NAME));
				inputParam.setDataType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_DATATYPE));
				inputParam.setExecute(Boolean.valueOf(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ISEXECUTE)));
				inputParam.setExpression(expression);
				connector.getInputsParam().add(inputParam);
			} else if (BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_OUTPUTS.equalsIgnoreCase(parentNodeName)) {
				if (null == connector.getOutputsParam()) {
					connector.setOutputsParam(new ArrayList<OutputParam>());
				}
				OutputParam outputParam = new OutputParam();
				outputParam.setId(elem.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
				outputParam.setVariableTarget(elem.attributeValue(BpmnXMLConstants.ATTRIBUTE_VARIABLETARGET));
				outputParam.setDocumentation("");
				connector.getOutputsParam().add(outputParam);
				
			} else if (BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_OUTPUTSDEF.equalsIgnoreCase(parentNodeName)) {
				
			} else /**  */
			/**  */
			if (BpmnXMLConstants.ELEMENT_TIMEEXPRESSION.equalsIgnoreCase(parentNodeName)) {
				connector.setTimerEventDefinition(null);
			} else /**  */
			if (BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION.equalsIgnoreCase(parentNodeName)) {
				// connector.setSkipExpression(expression);
			} else /**  */
			if (BpmnXMLConstants.ELEMENT_SKIPCOMMENT.equalsIgnoreCase(parentNodeName)) {
				// connector.setInputsParam(inputsParam);
			}
		}
		
		/** 处理connectorInstance节点基本属性 */
		if (BpmnXMLConstants.ELEMENT_CONNECTORINSTANCE.equalsIgnoreCase(parentNodeName)) {
			connector.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CONNECTORID));
			connector.setPackageName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_PACKAGENAME));
			connector.setClassName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CLASSNAME));
			connector.setConnectorInstanceId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_ID));
			connector.setConnectorInstanceName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_NAME));
			connector.setEventType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_EVENTTYPE));
			connector.setErrorCode(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ERRORCODE));
			connector.setErrorHandling(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ERRORHANDLING));
			connector.setIsTimeExecute(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ISTIMEEXECUTE));
		}
	}
}