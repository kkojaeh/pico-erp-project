package pico.erp.project.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.project.ProjectId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class ProjectChargeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProjectChargeId id;

  ProjectId projectId;

  String name;

  BigDecimal unitPrice;

  BigDecimal quantity;

  boolean charged;

  LocalDateTime chargedDate;

  boolean paid;

  LocalDateTime paidDate;

  LocalDateTime createdDate;

}
