package pico.erp.project;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.project.sale.item.QProjectSaleItemEntity;
import pico.erp.shared.LabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.QExtendedLabeledValue;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class ProjectQueryJpa implements ProjectQuery {

  private final QProjectEntity project = QProjectEntity.projectEntity;

  private final QProjectSaleItemEntity saleItem = QProjectSaleItemEntity.projectSaleItemEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;


  @Override
  public List<? extends LabeledValuable> asLabels(String keyword, long limit) {
    val query = new JPAQuery<LabeledValue>(entityManager);
    val select = new QExtendedLabeledValue(
      project.id.value.as("value"),
      project.name.as("label"),
      project.customerName.as("subLabel"),
      project.customerManagerContact.name.as("stamp")
    );
    query.select(select);
    query.from(project);
    query.where(project.name
      .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", keyword, "%")));
    query.orderBy(project.name.asc());
    query.limit(limit);
    return query.fetch();
  }

  @Override
  public Page<ProjectView> retrieve(ProjectView.Filter filter, Pageable pageable) {
    val query = new JPAQuery<ProjectView>(entityManager);
    val select = Projections.bean(ProjectView.class,
      project.id,
      project.name,
      project.customerId,
      project.customerName,
      project.managerId,
      project.managerName,
      project.customerManagerContact.name.as("customerManagerName"),
      project.createdBy,
      project.createdDate
    );
    query.select(select);
    query.from(project);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(project.name
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }

    if (filter.getCustomerId() != null) {
      builder.and(project.customerId.eq(filter.getCustomerId()));
    }

    if (filter.getManagerId() != null) {
      builder.and(project.managerId.eq(filter.getManagerId()));
    }

    if (!isEmpty(filter.getCustomerManagerName())) {
      builder.and(project.customerManagerContact.name.likeIgnoreCase(
        queryDslJpaSupport.toLikeKeyword("%", filter.getCustomerManagerName(), "%")));
    }

    if (filter.getItemId() != null) {
      builder.and(
        project.id.in(
          JPAExpressions.select(saleItem.projectId)
            .from(saleItem)
            .where(saleItem.itemId.eq(filter.getItemId()))
        )
      );
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
