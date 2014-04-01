package org.foxbpm.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelBaseElementImpl;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

public class ProcessDefinitionBuilder {

	protected KernelProcessDefinitionImpl processDefinition;
	protected Stack<KernelFlowElementsContainerImpl> containerStack = new Stack<KernelFlowElementsContainerImpl>();
	protected KernelBaseElementImpl processElement = processDefinition;
	protected KernelSequenceFlowImpl sequenceFlow;
	protected List<Object[]> unresolvedSequenceFlows = new ArrayList<Object[]>();

	public ProcessDefinitionBuilder() {
		this(null);
	}

	public ProcessDefinitionBuilder(String processDefinitionId) {
		processDefinition = new KernelProcessDefinitionImpl(processDefinitionId);
		containerStack.push(processDefinition);
	}

	public ProcessDefinitionBuilder createFlowNode(String id) {
		KernelFlowNodeImpl flowNode = containerStack.peek().createFlowNode(id);
		containerStack.push(flowNode);
		processElement = flowNode;

		sequenceFlow = null;

		return this;
	}

	public ProcessDefinitionBuilder endFlowNode() {
		containerStack.pop();
		processElement = containerStack.peek();

		sequenceFlow = null;

		return this;
	}

	public ProcessDefinitionBuilder initial() {
		processDefinition.setInitial(getFlowNode());
		return this;
	}

	public ProcessDefinitionBuilder startSequenceFlow(String destinationFlowNodeId) {
		return startSequenceFlow(destinationFlowNodeId, null);
	}

	public ProcessDefinitionBuilder startSequenceFlow(String targetFlowNodeId, String sequenceFlowId) {
		if (targetFlowNodeId == null) {
			throw new KernelException("targetFlowNodeId is null");
		}
		KernelFlowNodeImpl flowNode = getFlowNode();
		sequenceFlow = flowNode.createOutgoingSequenceFlow(sequenceFlowId);
		unresolvedSequenceFlows.add(new Object[] { sequenceFlow, targetFlowNodeId });
		processElement = sequenceFlow;
		return this;
	}

	public ProcessDefinitionBuilder endSequenceFlow() {
		processElement = containerStack.peek();
		sequenceFlow = null;
		return this;
	}

	public ProcessDefinitionBuilder sequenceFlow(String targetFlowNodeId) {
		return sequenceFlow(targetFlowNodeId, null);
	}

	public ProcessDefinitionBuilder sequenceFlow(String targetFlowNodeId, String sequenceFlowId) {
		startSequenceFlow(targetFlowNodeId, sequenceFlowId);
		endSequenceFlow();
		return this;
	}

	public ProcessDefinitionBuilder behavior(KernelFlowNodeBehavior flowNodeBehaviour) {
		getFlowNode().setFlowNodeBehavior(flowNodeBehaviour);
		return this;
	}

	public ProcessDefinitionBuilder property(String name, Object value) {
		processElement.setProperty(name, value);
		return this;
	}

	public KernelProcessDefinition buildProcessDefinition() {
		for (Object[] unresolvedSequenceFlow : unresolvedSequenceFlows) {
			KernelSequenceFlowImpl sequenceFlow = (KernelSequenceFlowImpl) unresolvedSequenceFlow[0];
			String targetFlowNodeName = (String) unresolvedSequenceFlow[1];
			KernelFlowNodeImpl destination = processDefinition.findFlowNode(targetFlowNodeName);
			if (destination == null) {
				throw new RuntimeException("target '" + targetFlowNodeName + "' not found.  (referenced from sequenceFlow in '"
						+ sequenceFlow.getSourceRef().getId() + "')");
			}
			sequenceFlow.setTargetRef(destination);
		}
		return processDefinition;
	}

	protected KernelFlowNodeImpl getFlowNode() {
		return (KernelFlowNodeImpl) containerStack.peek();
	}

	public ProcessDefinitionBuilder scope() {
		getFlowNode().setScope(true);
		return this;
	}

	/*
	public ProcessDefinitionBuilder executionListener(ExecutionListener executionListener) {
		if (transition != null) {
			transition.addExecutionListener(executionListener);
		} else {
			throw new PvmException("not in a transition scope");
		}
		return this;
	}

	public ProcessDefinitionBuilder executionListener(String eventName, ExecutionListener executionListener) {
		if (transition == null) {
			scopeStack.peek().addExecutionListener(eventName, executionListener);
		} else {
			throw new PvmException("not in an activity- or process definition scope. (but in a transition scope)");
		}
		return this;
	}
	*/
}