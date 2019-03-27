package pico.erp.project;

import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProjectServiceLogic implements ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProjectMapper mapper;

  @Override
  public ProjectData create(ProjectRequests.CreateRequest request) {
    val project = new Project();
    val response = project.apply(mapper.map(request));
    if (projectRepository.exists(project.getId())) {
      throw new ProjectExceptions.AlreadyExistsException();
    }
    val created = projectRepository.create(project);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(ProjectRequests.DeleteRequest request) {
    val project = projectRepository.findBy(request.getId())
      .orElseThrow(ProjectExceptions.NotFoundException::new);
    val response = project.apply(mapper.map(request));
    projectRepository.deleteBy(project.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(ProjectId id) {
    return projectRepository.exists(id);
  }

  @Override
  public ProjectData get(ProjectId id) {
    return projectRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProjectExceptions.NotFoundException::new);
  }


  @Override
  public void update(ProjectRequests.UpdateRequest request) {
    val project = projectRepository.findBy(request.getId())
      .orElseThrow(ProjectExceptions.NotFoundException::new);
    val response = project.apply(mapper.map(request));
    projectRepository.update(project);
    eventPublisher.publishEvents(response.getEvents());
  }
}
