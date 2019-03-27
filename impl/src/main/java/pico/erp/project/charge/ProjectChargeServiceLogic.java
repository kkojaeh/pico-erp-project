package pico.erp.project.charge;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.project.ProjectId;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProjectChargeServiceLogic implements ProjectChargeService {

  @Autowired
  private ProjectChargeRepository projectChargeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProjectChargeMapper mapper;

  @Override
  public ProjectChargeData create(ProjectChargeRequests.CreateRequest request) {
    val charge = new ProjectCharge();
    val response = charge.apply(mapper.map(request));
    if (projectChargeRepository.exists(charge.getId())) {
      throw new ProjectChargeExceptions.AlreadyExistsException();
    }
    val created = projectChargeRepository.create(charge);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(ProjectChargeRequests.DeleteRequest request) {
    val charge = projectChargeRepository.findBy(request.getId())
      .orElseThrow(ProjectChargeExceptions.NotFoundException::new);
    val response = charge.apply(mapper.map(request));
    projectChargeRepository.deleteBy(charge.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(ProjectChargeId id) {
    return projectChargeRepository.exists(id);
  }

  @Override
  public ProjectChargeData get(ProjectChargeId id) {
    return projectChargeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProjectChargeExceptions.NotFoundException::new);
  }

  @Override
  public List<ProjectChargeData> getAll(ProjectId projectId) {
    return projectChargeRepository.findAllBy(projectId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(ProjectChargeRequests.UpdateRequest request) {
    val charge = projectChargeRepository.findBy(request.getId())
      .orElseThrow(ProjectChargeExceptions.NotFoundException::new);
    val response = charge.apply(mapper.map(request));
    projectChargeRepository.update(charge);
    eventPublisher.publishEvents(response.getEvents());
  }
}
