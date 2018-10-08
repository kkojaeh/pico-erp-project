package pico.erp.project;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.AttachmentId;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.company.CompanyId;
import pico.erp.shared.data.Contact;
import pico.erp.user.UserId;

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
