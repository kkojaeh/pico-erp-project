package pico.erp.project.data;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.data.CompanyId;
import pico.erp.item.data.ItemId;
import pico.erp.shared.data.Auditor;
import pico.erp.user.data.UserId;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectView {

  ProjectId id;

  String name;

  CompanyId customerId;

  String customerName;

  UserId managerId;

  String managerName;

  String customerManagerName;

  Auditor createdBy;

  OffsetDateTime createdDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String name;

    CompanyId customerId;

    UserId managerId;

    String customerManagerName;

    ItemId itemId;

  }

}
