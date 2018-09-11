package pico.erp.project.charge;

import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.project.ProjectMapper;
import pico.erp.project.charge.data.ProjectChargeData;
import pico.erp.project.charge.data.ProjectChargeId;
import pico.erp.project.data.ProjectId;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class ProjectChargeServiceLogic implements ProjectChargeService {

  @Autowired
  private ProjectChargeRepository projectChargeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProjectMapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;

  @Override
  public ProjectChargeData create(ProjectChargeRequests.CreateRequest request) {
    val charge = new ProjectCharge();
    val response = charge.apply(mapper.map(request));
    if (projectChargeRepository.exists(charge.getId())) {
      throw new ProjectChargeExceptions.AlreadyExistsException();
    }
    val created = projectChargeRepository.create(charge);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(ProjectChargeRequests.DeleteRequest request) {
    val charge = projectChargeRepository.findBy(request.getId())
      .orElseThrow(ProjectChargeExceptions.NotFoundException::new);
    val response = charge.apply(mapper.map(request));
    projectChargeRepository.deleteBy(charge.getId());
    auditService.delete(charge);
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
    auditService.commit(charge);
    eventPublisher.publishEvents(response.getEvents());
  }
}
