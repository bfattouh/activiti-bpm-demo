package com.appnovation.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
//import org.mule.module.client.MuleClient;

/**
 * 
 * @author bfattouh
 *
 */
public class MuleListener {

	public void sendMessage(MuleClient muleClient, String url, Object payload,
			String resultVariable, DelegateExecution de) throws MuleException {

		Map<String, Object> properties = new HashMap<String, Object>();
		setProperty(properties, "activityId",
				de.getCurrentActivityId());
		setProperty(properties, "activityName",
				de.getCurrentActivityName());
		setProperty(properties, "eventName", de.getEventName());
		setProperty(properties, "id", de.getId());
		setProperty(properties, "parentId", de.getParentId());
		setProperty(properties, "processBusinessKey",
				de.getProcessBusinessKey());
		setProperty(properties, "processDefinitionId",
				de.getProcessDefinitionId());
		setProperty(properties, "processVariablesMap",
				de.getVariables());
		setProperty(properties, "processInstanceId",
				de.getProcessInstanceId());

		MuleMessage muleMessage = muleClient.send(url, payload, properties);

		if (resultVariable != null) {
			de.setVariable(resultVariable, muleMessage.getPayload());
		}
	}

	private void setProperty(Map<String, Object> map, String key, Object value) {			
		if (value != null) {
			map.put(key, value);
		}
	}

}
