package pico.erp.project;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.AttachmentId;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.company.CompanyData;
import pico.erp.project.ProjectEvents.CreatedEvent;
import pico.erp.project.ProjectEvents.DeletedEvent;
import pico.erp.project.ProjectEvents.UpdatedEvent;
import pico.erp.project.ProjectMessages.CreateResponse;
import pico.erp.project.ProjectMessages.DeleteResponse;
import pico.erp.project.ProjectMessages.UpdateResponse;
import pico.erp.shared.data.Contact;
import pico.erp.user.UserData;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Project implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProjectId id;

  String name;

  CompanyData customer;

  UserData manager;

  Contact customerManagerContact;

  String description;

  CommentSubjectId commentSubjectId;

  AttachmentId attachmentId;

  public Project() {
  }

  public CreateResponse apply(ProjectMessages.CreateRequest request) {
    id = request.getId();
    name = request.getName();
    description = request.getDescription();
    customer = request.getCustomer();
    manager = request.getManager();
    customerManagerContact = request.getCustomerManagerContact();
    commentSubjectId = CommentSubjectId.from(id.getValue().toString());
    attachmentId = request.getAttachmentId();
    return new CreateResponse(Arrays.asList(new CreatedEvent(this.id)));
  }

  public UpdateResponse apply(ProjectMessages.UpdateRequest request) {
    name = request.getName();
    description = request.getDescription();
    customer = request.getCustomer();
    manager = request.getManager();
    customerManagerContact = request.getCustomerManagerContact();
    attachmentId = request.getAttachmentId();
    return new UpdateResponse(Arrays.asList(new UpdatedEvent(this.id)));
  }

  public DeleteResponse apply(ProjectMessages.DeleteRequest request) {
    return new DeleteResponse(Arrays.asList(new DeletedEvent(this.id)));
  }


}
