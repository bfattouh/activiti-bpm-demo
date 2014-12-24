package com.appnovation.activiti.test;



import org.activiti.engine.repository.Deployment;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import java.util.List;

/**
 * 
 * @author bfattouh
 *
 */
public class AccountCreationTest {
	
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
		TaskService taskService = processEngine.getTaskService(); 
		Deployment deployment = repositoryService.createDeployment()
			.addClasspathResource("accountcreation.bpmn20.xml")
			.name("Account Creation")
			.deploy();
		
		System.out.println("deployment name : " + deployment.getName());
		System.out.println("deployment id : " + deployment.getId());
		System.out.println("deployment tenant id : " + deployment.getTenantId());
		System.out.println("deployment time : " + deployment.getDeploymentTime());
		
		//deployment name : Account Creation
		//deployment id : 20001
		//deployment tenant id : 
		//deployment time : Wed Dec 24 12:48:16 EST 2014
				
		// remove tasks already present
		List<Task> availableTaskList = taskService.createTaskQuery().taskName("Work on account creation").list();
		for (Task task : availableTaskList) {
			taskService.complete(task.getId());
		}	

	}
}
