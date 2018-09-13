package pico.erp.project.data;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.data.AttachmentId;
import pico.erp.comment.subject.data.CommentSubjectId;
import pico.erp.company.data.CompanyId;
import pico.erp.shared.data.Contact;
import pico.erp.user.data.UserId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class ProjectData implements Serializable {

  private static final long serialVersionUID = 1L;

  ProjectId id;

  String name;

  String description;

  CompanyId customerId;

  UserId managerId;

  Contact customerManagerContact;

  CommentSubjectId commentSubjectId;

  AttachmentId attachmentId;

}
