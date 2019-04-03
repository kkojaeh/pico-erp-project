package pico.erp.project;

import java.util.Optional;
import kkojaeh.spring.boot.component.ComponentAutowired;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.company.CompanyData;
import pico.erp.company.CompanyId;
import pico.erp.company.CompanyService;
import pico.erp.project.ProjectExceptions.NotFoundException;
import pico.erp.project.ProjectRequests.CreateRequest;
import pico.erp.project.ProjectRequests.DeleteRequest;
import pico.erp.project.ProjectRequests.UpdateRequest;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;

@Mapper
public abstract class ProjectMapper {

  @ComponentAutowired
  private CompanyService companyService;

  @ComponentAutowired
  private UserService userService;

  @Lazy
  @Autowired
  private ProjectRepository projectRepository;

  public Project domain(ProjectEntity entity) {
    return Project.builder()
      .id(entity.getId())
      .name(entity.getName())
      .description(entity.getDescription())
      .customer(map(entity.getCustomerId()))
      .manager(map(entity.getManagerId()))
      .customerManagerContact(entity.getCustomerManagerContact())
      .commentSubjectId(entity.getCommentSubjectId())
      .attachmentId(entity.getAttachmentId())
      /*
      .charges(
        entity.getCharges().stream().map(this::map).collect(Collectors.toList())
      )
      .saleItems(
        entity.getSaleItems().stream().map(this::map).collect(Collectors.toList())
      )
      */
      .build();
  }

  @Mappings({
    @Mapping(target = "customerId", source = "customer.id"),
    @Mapping(target = "managerId", source = "manager.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProjectEntity entity(Project project);

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

  @Mappings({
    @Mapping(target = "customer", source = "customerId"),
    @Mapping(target = "manager", source = "managerId")
  })
  public abstract ProjectMessages.CreateRequest map(CreateRequest request);

  @Mappings({
    @Mapping(target = "customer", source = "customerId"),
    @Mapping(target = "manager", source = "managerId")
  })
  public abstract ProjectMessages.UpdateRequest map(UpdateRequest request);

  public abstract ProjectMessages.DeleteRequest map(DeleteRequest request);


  @Mappings({
    @Mapping(target = "customerId", source = "customer.id"),
    @Mapping(target = "managerId", source = "manager.id")
  })
  public abstract ProjectData map(Project project);

  public Project map(ProjectId projectId) {
    return projectRepository.findBy(projectId)
      .orElseThrow(NotFoundException::new);
  }

  public abstract void pass(ProjectEntity from, @MappingTarget ProjectEntity to);

}
