package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return projectMapper.convertToDto(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> list = projectRepository.findAll();
        return list.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {

        dto.setProjectStatus(Status.OPEN);

        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {

        //Find current project based on project_code
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        //Map updated project dto to entity object
        Project convertedProject = projectMapper.convertToEntity(dto);

        //set Id  to converted object
        convertedProject.setId(project.getId());

        // Converted the project and set the project status what we capture in the project  from DB
        convertedProject.setProjectStatus(project.getProjectStatus());

        //save updated project
        projectRepository.save(convertedProject);

    }

    @Override
    public void delete(String code) {

        Project project = projectRepository.findByProjectCode(code);
        project.setDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void complete(String ProjectCode) {

        Project project = projectRepository.findByProjectCode(ProjectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }
}
