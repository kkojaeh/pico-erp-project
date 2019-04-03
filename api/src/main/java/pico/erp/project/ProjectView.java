package pico.erp.project;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.shared.data.Auditor;
import pico.erp.shared.data.Contact;
import pico.erp.user.UserId;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectView {

  ProjectId id;

  String name;

  CompanyId customerId;

  UserId managerId;

  Contact customerManagerContact;

  Auditor createdBy;

  LocalDateTime createdDate;

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
