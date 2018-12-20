package pico.erp.project.charge;

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
import pico.erp.project.Project;
import pico.erp.project.charge.ProjectChargeMessages.CreateResponse;
import pico.erp.project.charge.ProjectChargeMessages.DeleteResponse;
import pico.erp.project.charge.ProjectChargeMessages.UpdateResponse;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Audit(alias = "project-charge")
public class ProjectCharge implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProjectChargeId id;

  Project project;

  String name;

  BigDecimal unitPrice;

  BigDecimal quantity;

  boolean charged;

  OffsetDateTime chargedDate;

  boolean paid;

  OffsetDateTime paidDate;

  OffsetDateTime createdDate;

  public ProjectCharge() {
    unitPrice = BigDecimal.ZERO;
    quantity = BigDecimal.ZERO;
    charged = false;
    paid = false;
  }

  public CreateResponse apply(ProjectChargeMessages.CreateRequest request) {
    this.id = request.getId();
    this.project = request.getProject();
    this.name = request.getName();
    this.unitPrice = request.getUnitPrice();
    this.quantity = request.getQuantity();
    return new CreateResponse(Collections.emptyList());
  }

  public UpdateResponse apply(ProjectChargeMessages.UpdateRequest request) {
    this.name = request.getName();
    this.unitPrice = request.getUnitPrice();
    this.quantity = request.getQuantity();
    return new UpdateResponse(Collections.emptyList());
  }

  public DeleteResponse apply(ProjectChargeMessages.DeleteRequest request) {
    return new DeleteResponse(Collections.emptyList());
  }


}
