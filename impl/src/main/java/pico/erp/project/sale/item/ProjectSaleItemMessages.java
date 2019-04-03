package pico.erp.project.sale.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.item.ItemData;
import pico.erp.project.Project;
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

    LocalDateTime expirationDate;

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
