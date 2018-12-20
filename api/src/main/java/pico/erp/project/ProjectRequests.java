package pico.erp.project;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.attachment.AttachmentId;
import pico.erp.company.CompanyId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Contact;
import pico.erp.user.UserId;

public interface ProjectRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProjectId id;

    @Size(min = 3, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    String description;

    @Valid
    @NotNull
    CompanyId customerId;

    @Valid
    @NotNull
    UserId managerId;

    @Valid
    @NotNull
    Contact customerManagerContact;

    @Valid
    AttachmentId attachmentId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    ProjectId id;

    @Size(min = 3, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    String description;

    @Valid
    @NotNull
    CompanyId customerId;

    @NotNull
    @Valid
    UserId managerId;

    @Valid
    @NotNull
    Contact customerManagerContact;

    @Valid
    AttachmentId attachmentId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    ProjectId id;

  }
}
