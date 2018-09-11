package pico.erp.project.jpa;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.company.CompanyService;
import pico.erp.company.data.CompanyData;
import pico.erp.company.data.CompanyId;
import pico.erp.item.ItemService;
import pico.erp.item.data.ItemData;
import pico.erp.item.data.ItemId;
import pico.erp.project.Project;
import pico.erp.project.charge.ProjectCharge;
import pico.erp.project.sale.item.ProjectSaleItem;
import pico.erp.user.UserService;
import pico.erp.user.data.UserData;
import pico.erp.user.data.UserId;

@Mapper
public abstract class ProjectJpaMapper {

  @Lazy
  @Autowired
  private CompanyService companyService;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private ItemService itemService;

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

  protected Project map(ProjectEntity entity) {
    return Project.builder()
      .id(entity.getId())
      .name(entity.getName())
      .description(entity.getDescription())
      .customerData(map(entity.getCustomerId()))
      .managerData(map(entity.getManagerId()))
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

  protected ProjectCharge map(ProjectChargeEntity entity) {
    return ProjectCharge.builder()
      .id(entity.getId())
      .project(map(entity.getProject()))
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

  protected ProjectSaleItem map(ProjectSaleItemEntity entity) {
    return ProjectSaleItem.builder()
      .id(entity.getId())
      .project(map(entity.getProject()))
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
    @Mapping(target = "itemId", source = "itemData.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true)
  })
  protected abstract ProjectSaleItemEntity map(ProjectSaleItem item);

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true)
  })
  protected abstract ProjectChargeEntity map(ProjectCharge charge);


  @Mappings({
    @Mapping(target = "customerId", source = "customerData.id"),
    @Mapping(target = "customerName", source = "customerData.name"),
    @Mapping(target = "managerId", source = "managerData.id"),
    @Mapping(target = "managerName", source = "managerData.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProjectEntity map(Project project);

  public abstract void pass(ProjectEntity from, @MappingTarget ProjectEntity to);

  public abstract void pass(ProjectChargeEntity from, @MappingTarget ProjectChargeEntity to);

  public abstract void pass(ProjectSaleItemEntity from, @MappingTarget ProjectSaleItemEntity to);

}
