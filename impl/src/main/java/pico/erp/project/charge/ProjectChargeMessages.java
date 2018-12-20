package pico.erp.project.charge;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.project.Project;
import pico.erp.shared.event.Event;

public interface ProjectChargeMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    ProjectChargeId id;

    Project project;

    @NotNull
    String name;

    @NotNull
    BigDecimal unitPrice;

    @NotNull
    BigDecimal quantity;

  }

  @Data
  class UpdateRequest {

    @NotNull
    String name;

    @NotNull
    BigDecimal unitPrice;

    @NotNull
    BigDecimal quantity;

  }

  @Data
  class DeleteRequest {

  }

  @Value
  class CreateResponse {

    Collection<Event> events;

  }

  @Value
  class UpdateResponse {

    Collection<Event> events;

  }

  @Value
  class DeleteResponse {

    Collection<Event> events;

  }
}
