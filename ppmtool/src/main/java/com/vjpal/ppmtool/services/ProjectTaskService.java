package com.vjpal.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vjpal.ppmtool.domain.Backlog;
import com.vjpal.ppmtool.domain.ProjectTask;
import com.vjpal.ppmtool.repositories.BacklogRepository;
import com.vjpal.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired 
	private BacklogRepository backlogRepository;
	
	@Autowired 
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		//Exceptions: Project not found
		
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
	}

	public Iterable<ProjectTask> findBacklogById(String id) {
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

}
