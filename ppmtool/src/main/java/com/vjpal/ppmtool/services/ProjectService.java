package com.vjpal.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vjpal.ppmtool.domain.Project;
import com.vjpal.ppmtool.exceptions.ProjectIdException;
import com.vjpal.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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
	
}
