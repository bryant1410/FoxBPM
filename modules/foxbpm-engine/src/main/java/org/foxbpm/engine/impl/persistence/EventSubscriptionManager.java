/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 * @author kenshin
 */
package org.foxbpm.engine.impl.persistence;

import java.util.List;

import com.founder.fix.fixflow.core.impl.Page;
import com.founder.fix.fixflow.core.impl.subscription.EventSubscriptionEntity;
import com.founder.fix.fixflow.core.impl.subscription.EventSubscriptionQueryImpl;

public class EventSubscriptionManager extends AbstractManager {
	
	public void saveEventSubscriptionEntity(EventSubscriptionEntity eventSubscriptionEntity){
		getDbSqlSession().save("saveEventSubscriptionEntity", eventSubscriptionEntity);
	}
	
	
	public void deleteEventSubscriptionEntity(String subscriptionId){
		getDbSqlSession().delete("deleteEventSubscriptionEntity", subscriptionId);
	}
	
	@SuppressWarnings("unchecked")
	public List<EventSubscriptionEntity> findEventSubscriptionByQueryCriteria(EventSubscriptionQueryImpl eventSubscriptionQueryImpl,Page page){
		return getDbSqlSession().selectList("findEventSubscriptionByQueryCriteria", eventSubscriptionQueryImpl,page);
	}
	
	
	
}