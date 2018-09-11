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
import pico.erp.attachment.data.AttachmentId;
import pico.erp.audit.annotation.Audit;
import pico.erp.comment.data.CommentSubjectId;
import pico.erp.company.data.CompanyData;
import pico.erp.project.ProjectEvents.CreatedEvent;
import pico.erp.project.ProjectEvents.DeletedEvent;
import pico.erp.project.ProjectEvents.UpdatedEvent;
import pico.erp.project.ProjectMessages.CreateResponse;
import pico.erp.project.ProjectMessages.DeleteResponse;
import pico.erp.project.ProjectMessages.UpdateResponse;
import pico.erp.project.data.ProjectId;
import pico.erp.shared.data.Contact;
import pico.erp.user.data.UserData;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Audit(alias = "project")
public class Project implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProjectId id;

  String name;

  CompanyData customerData;

  UserData managerData;

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
    customerData = request.getCustomerData();
    managerData = request.getManagerData();
    customerManagerContact = request.getCustomerManagerContact();
    commentSubjectId = CommentSubjectId.from(id.getValue());
    attachmentId = request.getAttachmentId();
    return new CreateResponse(Arrays.asList(new CreatedEvent(this.id)));
  }

  public UpdateResponse apply(ProjectMessages.UpdateRequest request) {
    name = request.getName();
    description = request.getDescription();
    customerData = request.getCustomerData();
    managerData = request.getManagerData();
    customerManagerContact = request.getCustomerManagerContact();
    attachmentId = request.getAttachmentId();
    return new UpdateResponse(Arrays.asList(new UpdatedEvent(this.id)));
  }

  public DeleteResponse apply(ProjectMessages.DeleteRequest request) {
    return new DeleteResponse(Arrays.asList(new DeletedEvent(this.id)));
  }


}
