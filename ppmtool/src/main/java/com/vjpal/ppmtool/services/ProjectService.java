package com.vjpal.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vjpal.ppmtool.domain.Backlog;
import com.vjpal.ppmtool.domain.Project;
import com.vjpal.ppmtool.exceptions.ProjectIdException;
import com.vjpal.ppmtool.repositories.BacklogRepository;
import com.vjpal.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if(project.getId()!= null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);
			
		} catch(Exception e) {
			throw new ProjectIdException("Project Id: '" + project.getProjectIdentifier().toUpperCase() + "' already exists!");
		}
	}
	
	public Project findProjectByIdentifier(String projectId) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project Id: '" + projectId + "' does not exists!");
		}
		
		return project;
	}
	
	public Iterable<Project> findAllProjects() {
		
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Cannot find Project with ID '" + projectId + "'. This Project does not exists.");
		}
		
		projectRepository.delete(project);
	}
	
}
