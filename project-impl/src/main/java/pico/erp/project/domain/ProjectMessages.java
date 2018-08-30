package pico.erp.project.domain;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.attachment.data.AttachmentId;
import pico.erp.company.data.CompanyData;
import pico.erp.project.data.ProjectId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Contact;
import pico.erp.shared.event.Event;
import pico.erp.user.data.UserData;

public interface ProjectMessages {

  @Data
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
    CompanyData customerData;

    @Valid
    @NotNull
    UserData managerData;

    @Valid
    @NotNull
    Contact customerManagerContact;

    @Valid
    AttachmentId attachmentId;

  }

  @Data
  class UpdateRequest {

    @Size(min = 3, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    String description;

    @Valid
    @NotNull
    CompanyData customerData;

    @Valid
    @NotNull
    UserData managerData;

    @Valid
    @NotNull
    Contact customerManagerContact;

    @Valid
    AttachmentId attachmentId;

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
