package pico.erp.project.charge;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.project.Project;
import pico.erp.project.ProjectEntity;
import pico.erp.project.ProjectId;
import pico.erp.project.ProjectMapper;
import pico.erp.project.sale.item.ProjectSaleItemMessages;
import pico.erp.project.sale.item.ProjectSaleItemRequests;

@Mapper
public abstract class ProjectChargeMapper {

  @Lazy
  @Autowired
  protected ProjectMapper projectMapper;

  public ProjectCharge domain(ProjectChargeEntity entity) {
    return ProjectCharge.builder()
      .id(entity.getId())
      .project(projectMapper.domain(entity.getProject()))
      .name(entity.getName())
      .unitPrice(entity.getUnitPrice())
      .quantity(entity.getQuantity())
      .charged(entity.isCharged())
      .chargedDate(entity.getChargedDate())
      .paid(entity.isPaid())
      .paidDate(entity.getPaidDate())
      .createdDate(entity.getCreatedDate())
      .build();
  }

  @Mappings({
    @Mapping(target = "project", source = "project.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true)
  })
  public abstract ProjectChargeEntity entity(ProjectCharge charge);

  protected ProjectEntity entity(ProjectId projectId) {
    return projectMapper.entity(projectId);
  }

  @Mappings({
    @Mapping(target = "project", source = "projectId")
  })
  public abstract ProjectChargeMessages.CreateRequest map(
    ProjectChargeRequests.CreateRequest request);

  public abstract ProjectChargeMessages.UpdateRequest map(
    ProjectChargeRequests.UpdateRequest request);

  public abstract ProjectSaleItemMessages.UpdateRequest map(
    ProjectSaleItemRequests.UpdateRequest request);

  public abstract ProjectChargeMessages.DeleteRequest map(
    ProjectChargeRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "projectId", source = "project.id")
  })
  public abstract ProjectChargeData map(ProjectCharge data);

  protected Project map(ProjectId projectId) {
    return projectMapper.map(projectId);
  }

  public abstract void pass(ProjectChargeEntity from, @MappingTarget ProjectChargeEntity to);

}
