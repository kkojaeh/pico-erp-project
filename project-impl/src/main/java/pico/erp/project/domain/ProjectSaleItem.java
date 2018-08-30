package pico.erp.project.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.item.data.ItemData;
import pico.erp.project.data.ProjectSaleItemId;
import pico.erp.project.domain.ProjectSaleItemMessages.CreateRequest;
import pico.erp.project.domain.ProjectSaleItemMessages.CreateResponse;
import pico.erp.project.domain.ProjectSaleItemMessages.DeleteResponse;
import pico.erp.project.domain.ProjectSaleItemMessages.ExpireResponse;
import pico.erp.project.domain.ProjectSaleItemMessages.UpdateRequest;
import pico.erp.project.domain.ProjectSaleItemMessages.UpdateResponse;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Audit(alias = "project-sale-item")
public class ProjectSaleItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProjectSaleItemId id;

  Project project;

  BigDecimal orderedQuantity;

  ItemData itemData;

  BigDecimal unitPrice;

  BigDecimal deliveredQuantity;

  BigDecimal chargedQuantity;

  BigDecimal paidQuantity;

  OffsetDateTime expirationDate;

  OffsetDateTime expiredDate;

  OffsetDateTime createdDate;

  boolean expired;

  public ProjectSaleItem() {
    orderedQuantity = BigDecimal.ZERO;
    deliveredQuantity = BigDecimal.ZERO;
    chargedQuantity = BigDecimal.ZERO;
    paidQuantity = BigDecimal.ZERO;
    expired = false;
  }

  public ProjectSaleItemMessages.CreateResponse apply(CreateRequest request) {
    this.id = request.getId();
    this.project = request.getProject();
    this.itemData = request.getItemData();
    this.unitPrice = request.getUnitPrice();
    this.expirationDate = request.getExpirationDate();
    return new CreateResponse(Collections.emptyList());
  }

  public ProjectSaleItemMessages.UpdateResponse apply(UpdateRequest request) {
    this.unitPrice = request.getUnitPrice();
    return new UpdateResponse(Collections.emptyList());
  }

  public ProjectSaleItemMessages.DeleteResponse apply(
    ProjectSaleItemMessages.DeleteRequest request) {
    return new DeleteResponse(Collections.emptyList());
  }

  public ProjectSaleItemMessages.ExpireResponse apply(
    ProjectSaleItemMessages.ExpireRequest request) {
    this.expired = true;
    this.expiredDate = OffsetDateTime.now();
    return new ExpireResponse(Collections.emptyList());
  }


}
