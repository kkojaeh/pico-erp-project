package pico.erp.project.sale.item;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.project.Project;
import pico.erp.project.ProjectEntity;
import pico.erp.project.ProjectId;
import pico.erp.project.ProjectMapper;

@Mapper
public abstract class ProjectSaleItemMapper {

  @Lazy
  @Autowired
  protected ProjectMapper projectMapper;

  @Lazy
  @Autowired
  private ItemService itemService;

  public ProjectSaleItem domain(ProjectSaleItemEntity entity) {
    return ProjectSaleItem.builder()
      .id(entity.getId())
      .project(projectMapper.domain(entity.getProject()))
      .itemData(map(entity.getItemId()))
      .unitPrice(entity.getUnitPrice())
      .orderedQuantity(entity.getOrderedQuantity())
      .deliveredQuantity(entity.getDeliveredQuantity())
      .chargedQuantity(entity.getChargedQuantity())
      .paidQuantity(entity.getPaidQuantity())
      .expirationDate(entity.getExpirationDate())
      .expired(entity.isExpired())
      .expiredDate(entity.getExpiredDate())
      .createdDate(entity.getCreatedDate())
      .build();
  }

  @Mappings({
    @Mapping(target = "project", source = "project.id"),
    @Mapping(target = "itemId", source = "itemData.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true)
  })
  public abstract ProjectSaleItemEntity entity(ProjectSaleItem item);

  protected ProjectEntity entity(ProjectId projectId) {
    return projectMapper.entity(projectId);
  }

  @Mappings({
    @Mapping(target = "project", source = "projectId"),
    @Mapping(target = "itemData", source = "itemId")
  })
  public abstract ProjectSaleItemMessages.CreateRequest map(
    ProjectSaleItemRequests.CreateRequest request);

  public abstract ProjectSaleItemMessages.UpdateRequest map(
    ProjectSaleItemRequests.UpdateRequest request);

  public abstract ProjectSaleItemMessages.DeleteRequest map(
    ProjectSaleItemRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "projectId", source = "project.id"),
    @Mapping(target = "itemId", source = "itemData.id")
  })
  public abstract ProjectSaleItemData map(ProjectSaleItem data);

  protected Project map(ProjectId projectId) {
    return projectMapper.map(projectId);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  public abstract void pass(ProjectSaleItemEntity from, @MappingTarget ProjectSaleItemEntity to);


}
