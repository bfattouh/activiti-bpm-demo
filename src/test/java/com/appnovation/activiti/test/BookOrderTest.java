package com.appnovation.activiti.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import java.util.Map;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import java.util.List;


public class BookOrderTest {
	
	@Test
	public void startBookOrder() {
		
		
		ProcessEngine processEngine = ProcessEngineConfiguration
		        .createStandaloneProcessEngineConfiguration()
		        .setJdbcUrl("jdbc:mysql://localhost:3306/activiti?autoReconnect=true") 
		        .setJdbcDriver("com.mysql.jdbc.Driver")
		        .setJdbcUsername("root")
		        .setJdbcPassword("")
		        .setJobExecutorActivate(true)
		        .buildProcessEngine();
	
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.deleteDeployment("2501");
		//RuntimeService runtimeService = processEngine.getRuntimeService();
		//IdentityService identityService = processEngine.getIdentityService();
		TaskService taskService = processEngine.getTaskService(); 
		Deployment deployment = repositoryService.createDeployment()
			.addClasspathResource("bookorder.bpmn20.xml")
			.deploy();
		
		System.out.println("dployment name : " + deployment.getName());
		System.out.println("dployment id : " + deployment.getId());
		System.out.println("dployment tenant id : " + deployment.getTenantId());
		System.out.println("dployment time : " + deployment.getDeploymentTime());
		
		
		// remove tasks already present
		List<Task> availableTaskList = taskService.createTaskQuery().taskName("Work on order").list();
		for (Task task : availableTaskList) {
			taskService.complete(task.getId());
		}	
		
//		Map<String, Object> variableMap = new HashMap<String, Object>();
//		variableMap.put("isbn", "123456");
//		identityService.setAuthenticatedUserId("kermit");
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
//				"bookorder", variableMap);
//		assertNotNull(processInstance.getId());
//		List<Task> taskList = taskService.createTaskQuery().taskName("Work on order").list();
//		assertEquals(1, taskList.size());
//		System.out.println("found task " + taskList.get(0).getName());
	}
}
