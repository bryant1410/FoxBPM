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
 * @author ych
 */
package org.foxbpm.model;

/**
 * 边界事件
 * 
 * @author ych
 * 
 */
public class BoundaryEvent extends CatchEvent {
	
	private static final long serialVersionUID = 1L;
	
	private String attachedToRef;
	/** 默认值true */
	private boolean isCancelActivity = true;
	
	public boolean isCancelActivity() {
		return isCancelActivity;
	}
	
	public void setCancelActivity(boolean isCancelActivity) {
		this.isCancelActivity = isCancelActivity;
	}
	
	public void setAttachedToRef(String attachedToRef) {
		this.attachedToRef = attachedToRef;
	}
	
	public String getAttachedToRef() {
		return attachedToRef;
	}
	
}
