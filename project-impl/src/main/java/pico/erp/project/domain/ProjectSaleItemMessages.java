package pico.erp.project.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.item.data.ItemData;
import pico.erp.project.data.ProjectSaleItemId;
import pico.erp.shared.event.Event;

public interface ProjectSaleItemMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    ProjectSaleItemId id;

    @NotNull
    Project project;

    @NotNull
    ItemData itemData;

    @NotNull
    BigDecimal unitPrice;

    OffsetDateTime expirationDate;

  }

  @Data
  class UpdateRequest {

    @NotNull
    BigDecimal unitPrice;

  }

  @Data
  class DeleteRequest {

  }

  @Data
  class ExpireRequest {

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

  @Value
  class ExpireResponse {

    Collection<Event> events;

  }
}
