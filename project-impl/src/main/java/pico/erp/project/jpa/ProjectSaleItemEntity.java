package pico.erp.project.jpa;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pico.erp.item.data.ItemId;
import pico.erp.project.sale.item.data.ProjectSaleItemId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;

@Data
@Entity(name = "ProjectSaleItem")
@Table(name = "PJT_PROJECT_SALE_ITEM", indexes = {
  @Index(name = "PJT_PROJECT_SALE_ITEM_ITEM_ID_IDX", columnList = "PROJECT_ID, ITEM_ID", unique = true)
})
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectSaleItemEntity {

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.ID_LENGTH))
  })
  ProjectSaleItemId id;

  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  ProjectEntity project;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ITEM_ID", length = TypeDefinitions.ID_LENGTH))
  })
  ItemId itemId;

  @Column(precision = 19, scale = 2)
  BigDecimal orderedQuantity;

  @Column(precision = 19, scale = 2)
  BigDecimal deliveredQuantity;

  @Column(precision = 19, scale = 2)
  BigDecimal chargedQuantity;

  @Column(precision = 19, scale = 2)
  BigDecimal paidQuantity;

  @Column(scale = 2)
  BigDecimal unitPrice;

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

  OffsetDateTime expirationDate;

  OffsetDateTime expiredDate;

  boolean expired;

}
