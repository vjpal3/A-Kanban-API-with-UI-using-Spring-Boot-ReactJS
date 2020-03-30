package com.vjpal.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vjpal.ppmtool.domain.Backlog;
import com.vjpal.ppmtool.domain.Project;
import com.vjpal.ppmtool.domain.ProjectTask;
import com.vjpal.ppmtool.exceptions.ProjectNotFoundException;
import com.vjpal.ppmtool.repositories.BacklogRepository;
import com.vjpal.ppmtool.repositories.ProjectRepository;
import com.vjpal.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired 
	private BacklogRepository backlogRepository;
	
	@Autowired 
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		//Exceptions: Project not found
		try {

			//PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			//set the Bl to PT
			projectTask.setBacklog(backlog);
			
			//Our project sequence should be: IDPRO-1, IDPRO-2
			Integer BacklogSequence = backlog.getPTSequence();
			
			// Update the BL Sequence
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			
			//Add sequence to Project Task;
			projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//INITIAL priority when priority null
			//if(projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
			if(projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}
			
			//INITIAL Status when status is null
			if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);
			
		} catch(Exception e) {
			throw new ProjectNotFoundException("Project Not found");
		}
		
	}

	public Iterable<ProjectTask> findBacklogById(String id) {
		
		Project project = projectRepository.findByProjectIdentifier(id);
		
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID: '" + id + "' does not exist.");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		
		//make sure we are searching on an existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist.");
		}
		
		// make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task: '" + pt_id + "' not found.");
		}
		
		//make sure that the backlog/project id in the path corresponds to the right project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in project '" + backlog_id);
		}
		
		return projectTask;
	}
	
	//update project task
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		
		//find existing project task
		//replace it with updated task
		//save update
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		projectTask = updatedTask;
		return projectTaskRepository.save(projectTask);
	}
	
}
