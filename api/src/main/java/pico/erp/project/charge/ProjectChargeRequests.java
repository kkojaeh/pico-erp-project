package pico.erp.project.charge;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.project.ProjectId;

public interface ProjectChargeRequests {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProjectChargeId id;

    @Valid
    @NotNull
    ProjectId projectId;

    @Valid
    @NotNull
    String name;

    @Valid
    @NotNull
    BigDecimal unitPrice;

    @Valid
    @NotNull
    BigDecimal quantity;

  }

  @Data
  class UpdateRequest {

    @Valid
    @NotNull
    ProjectChargeId id;

    @Valid
    @NotNull
    String name;

    @Valid
    @NotNull
    BigDecimal unitPrice;

    @Valid
    @NotNull
    BigDecimal quantity;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    ProjectChargeId id;

  }
}
