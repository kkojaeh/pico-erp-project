package pico.erp.project.sale.item;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.item.ItemId;
import pico.erp.project.ProjectId;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional
@Validated
public class ProjectSaleItemServiceLogic implements ProjectSaleItemService {

  @Autowired
  private ProjectSaleItemRepository projectSaleItemRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProjectSaleItemMapper mapper;

  @Override
  public ProjectSaleItemData create(ProjectSaleItemRequests.CreateRequest request) {
    val saleItem = new ProjectSaleItem();
    val response = saleItem.apply(mapper.map(request));
    if (projectSaleItemRepository.exists(saleItem.getId())) {
      throw new ProjectSaleItemExceptions.AlreadyExistsException();
    }
    val created = projectSaleItemRepository.create(saleItem);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(ProjectSaleItemRequests.DeleteRequest request) {
    val saleItem = projectSaleItemRepository.findBy(request.getId())
      .orElseThrow(ProjectSaleItemExceptions.NotFoundException::new);
    val response = saleItem.apply(mapper.map(request));
    projectSaleItemRepository.deleteBy(saleItem.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(ProjectSaleItemId id) {
    return projectSaleItemRepository.exists(id);
  }

  @Override
  public boolean exists(ProjectId id, ItemId itemId) {
    return projectSaleItemRepository.exists(id, itemId);
  }

  @Override
  public ProjectSaleItemData get(ProjectSaleItemId id) {
    return projectSaleItemRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProjectSaleItemExceptions.NotFoundException::new);
  }

  @Override
  public ProjectSaleItemData get(ProjectId id, ItemId itemId) {
    return projectSaleItemRepository.findBy(id, itemId)
      .map(mapper::map)
      .orElseThrow(ProjectSaleItemExceptions.NotFoundException::new);
  }

  @Override
  public List<ProjectSaleItemData> getAll(ProjectId projectId) {
    return projectSaleItemRepository.findAllBy(projectId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(ProjectSaleItemRequests.UpdateRequest request) {
    val saleItem = projectSaleItemRepository.findBy(request.getId())
      .orElseThrow(ProjectSaleItemExceptions.NotFoundException::new);
    val response = saleItem.apply(mapper.map(request));
    projectSaleItemRepository.update(saleItem);
    eventPublisher.publishEvents(response.getEvents());
  }
}
