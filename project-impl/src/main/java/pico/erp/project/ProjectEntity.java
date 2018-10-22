package pico.erp.project;


import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pico.erp.attachment.AttachmentId;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.company.CompanyId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;
import pico.erp.shared.data.Contact;
import pico.erp.user.UserId;

@Entity(name = "Project")
@Table(name = "PJT_PROJECT")
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ProjectId id;

  @Column(length = TypeDefinitions.NAME_LENGTH)
  String name;

  @Lob
  @Column(length = TypeDefinitions.CLOB_LENGTH)
  String description;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "CUSTOMER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CompanyId customerId;

  @Column(name = "CUSTOMER_NAME", length = TypeDefinitions.NAME_LENGTH)
  String customerName;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "MANAGER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  UserId managerId;

  @Column(name = "MANAGER_NAME", length = TypeDefinitions.NAME_LENGTH)
  String managerName;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "name", column = @Column(name = "CUSTOMER_MANAGER_CONTACT_NAME", length = TypeDefinitions.NAME_LENGTH)),
    @AttributeOverride(name = "email", column = @Column(name = "CUSTOMER_MANAGER_CONTACT_EMAIL", length =
      TypeDefinitions.EMAIL_LENGTH * 2)),
    @AttributeOverride(name = "telephoneNumber", column = @Column(name = "CUSTOMER_MANAGER_CONTACT_TELEPHONE_NUMBER", length =
      TypeDefinitions.PHONE_NUMBER_LENGTH * 2)),
    @AttributeOverride(name = "mobilePhoneNumber", column = @Column(name = "CUSTOMER_MANAGER_CONTACT_MOBILE_PHONE_NUMBER", length =
      TypeDefinitions.PHONE_NUMBER_LENGTH * 2)),
    @AttributeOverride(name = "faxNumber", column = @Column(name = "CUSTOMER_MANAGER_CONTACT_FAX_NUMBER", length =
      TypeDefinitions.PHONE_NUMBER_LENGTH * 2))
  })
  @NotNull
  Contact customerManagerContact;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "COMMENT_SUBJECT_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CommentSubjectId commentSubjectId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "CREATED_BY_ID", updatable = false, length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "CREATED_BY_NAME", updatable = false, length = TypeDefinitions.NAME_LENGTH))
  })
  @CreatedBy
  Auditor createdBy;

  @CreatedDate
  @Column(updatable = false)
  OffsetDateTime createdDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "LAST_MODIFIED_BY_ID", length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "LAST_MODIFIED_BY_NAME", length = TypeDefinitions.NAME_LENGTH))
  })
  @LastModifiedBy
  Auditor lastModifiedBy;

  @LastModifiedDate
  OffsetDateTime lastModifiedDate;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ATTACHMENT_ID", length = TypeDefinitions.ID_LENGTH))
  })
  AttachmentId attachmentId;

  /*
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "PROJECT_ID")
  @OrderBy("createdDate DESC")
  List<ProjectChargeEntity> charges;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "PROJECT_ID")
  @OrderBy("createdDate DESC")
  List<ProjectSaleItemEntity> saleItems;
  */

}
