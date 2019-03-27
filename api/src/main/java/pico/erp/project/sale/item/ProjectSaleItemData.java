package pico.erp.project.sale.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.item.ItemId;
import pico.erp.project.ProjectId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class ProjectSaleItemData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProjectSaleItemId id;

  ProjectId projectId;

  ItemId itemId;

  BigDecimal unitPrice;

  BigDecimal orderedQuantity;

  BigDecimal deliveredQuantity;

  BigDecimal chargedQuantity;

  BigDecimal paidQuantity;

  LocalDateTime expirationDate;

  LocalDateTime expiredDate;

  boolean expired;

  LocalDateTime createdDate;

}
