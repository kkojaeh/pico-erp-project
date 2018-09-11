package pico.erp.project.charge.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.project.data.ProjectId;

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

  OffsetDateTime chargedDate;

  boolean paid;

  OffsetDateTime paidDate;

  OffsetDateTime createdDate;

}
