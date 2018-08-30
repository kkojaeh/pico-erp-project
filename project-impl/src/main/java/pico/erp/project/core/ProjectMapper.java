package pico.erp.project.core;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.company.CompanyService;
import pico.erp.company.data.CompanyData;
import pico.erp.company.data.CompanyId;
import pico.erp.item.ItemService;
import pico.erp.item.data.ItemData;
import pico.erp.item.data.ItemId;
import pico.erp.project.ProjectChargeRequests;
import pico.erp.project.ProjectExceptions.NotFoundException;
import pico.erp.project.ProjectRequests.CreateRequest;
import pico.erp.project.ProjectRequests.DeleteRequest;
import pico.erp.project.ProjectRequests.UpdateRequest;
import pico.erp.project.ProjectSaleItemRequests;
import pico.erp.project.data.ProjectChargeData;
import pico.erp.project.data.ProjectData;
import pico.erp.project.data.ProjectId;
import pico.erp.project.data.ProjectSaleItemData;
import pico.erp.project.domain.Project;
import pico.erp.project.domain.ProjectCharge;
import pico.erp.project.domain.ProjectChargeMessages;
import pico.erp.project.domain.ProjectMessages;
import pico.erp.project.domain.ProjectSaleItem;
import pico.erp.project.domain.ProjectSaleItemMessages;
import pico.erp.user.UserService;
import pico.erp.user.data.UserData;
import pico.erp.user.data.UserId;

@Mapper
public abstract class ProjectMapper {

  @Lazy
  @Autowired
  private CompanyService companyService;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private ItemService itemService;

  @Autowired
  private ProjectRepository projectRepository;

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  protected CompanyData map(CompanyId companyId) {
    return Optional.ofNullable(companyId)
      .map(companyService::get)
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  protected Project map(ProjectId projectId) {
    return projectRepository.findBy(projectId)
      .orElseThrow(NotFoundException::new);
  }

  @Mappings({
    @Mapping(target = "customerId", source = "customerData.id"),
    @Mapping(target = "managerId", source = "managerData.id")
  })
  public abstract ProjectData map(Project project);

  @Mappings({
    @Mapping(target = "customerData", source = "customerId"),
    @Mapping(target = "managerData", source = "managerId")
  })
  public abstract ProjectMessages.CreateRequest map(CreateRequest request);

  @Mappings({
    @Mapping(target = "customerData", source = "customerId"),
    @Mapping(target = "managerData", source = "managerId")
  })
  public abstract ProjectMessages.UpdateRequest map(UpdateRequest request);

  public abstract ProjectMessages.DeleteRequest map(DeleteRequest request);

  @Mappings({
    @Mapping(target = "project", source = "projectId")
  })
  public abstract ProjectChargeMessages.CreateRequest map(
    ProjectChargeRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "project", source = "projectId"),
    @Mapping(target = "itemData", source = "itemId")
  })
  public abstract ProjectSaleItemMessages.CreateRequest map(
    ProjectSaleItemRequests.CreateRequest request);

  public abstract ProjectChargeMessages.UpdateRequest map(
    ProjectChargeRequests.UpdateRequest request);

  public abstract ProjectSaleItemMessages.UpdateRequest map(
    ProjectSaleItemRequests.UpdateRequest request);

  public abstract ProjectChargeMessages.DeleteRequest map(
    ProjectChargeRequests.DeleteRequest request);

  public abstract ProjectSaleItemMessages.DeleteRequest map(
    ProjectSaleItemRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "projectId", source = "project.id")
  })
  public abstract ProjectChargeData map(ProjectCharge data);

  @Mappings({
    @Mapping(target = "projectId", source = "project.id"),
    @Mapping(target = "itemId", source = "itemData.id")
  })
  public abstract ProjectSaleItemData map(ProjectSaleItem data);

}
