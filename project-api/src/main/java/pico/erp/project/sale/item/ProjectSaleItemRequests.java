package pico.erp.project.sale.item;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.item.data.ItemId;
import pico.erp.project.data.ProjectId;
import pico.erp.project.sale.item.data.ProjectSaleItemId;

public interface ProjectSaleItemRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProjectId projectId;

    @Valid
    @NotNull
    ProjectSaleItemId id;

    @Valid
    @NotNull
    ItemId itemId;

    @NotNull
    BigDecimal unitPrice;

    OffsetDateTime expirationDate;

  }

  @Data
  class UpdateRequest {

    @Valid
    @NotNull
    ProjectSaleItemId id;

    @NotNull
    BigDecimal unitPrice;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    ProjectSaleItemId id;

  }
}
