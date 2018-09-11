package pico.erp.project.jpa;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.item.data.ItemId;
import pico.erp.project.data.ProjectId;
import pico.erp.project.sale.item.ProjectSaleItem;
import pico.erp.project.sale.item.ProjectSaleItemRepository;
import pico.erp.project.sale.item.data.ProjectSaleItemId;


@Repository
interface ProjectSaleItemEntityRepository extends
  CrudRepository<ProjectSaleItemEntity, ProjectSaleItemId> {

  @Query("SELECT CASE WHEN COUNT(psi) > 0 THEN true ELSE false END FROM ProjectSaleItem psi JOIN psi.project p  WHERE p.id = :projectId AND psi.itemId = :itemId")
  boolean exists(
    @Param("projectId") ProjectId projectId, @Param("itemId") ItemId itemId);

  @Query("SELECT psi FROM ProjectSaleItem psi JOIN psi.project p  WHERE p.id = :projectId")
  Stream<ProjectSaleItemEntity> findAllBy(
    @Param("projectId") ProjectId projectId);

  @Query("SELECT psi FROM ProjectSaleItem psi JOIN psi.project p  WHERE p.id = :projectId AND psi.itemId = :itemId")
  ProjectSaleItemEntity findOne(
    @Param("projectId") ProjectId projectId, @Param("itemId") ItemId itemId);

}

@Repository
@Transactional
public class ProjectSaleItemRepositoryJpa implements ProjectSaleItemRepository {

  @Autowired
  private ProjectSaleItemEntityRepository repository;

  @Autowired
  private ProjectJpaMapper mapper;

  @Override
  public ProjectSaleItem create(ProjectSaleItem projectSaleItem) {
    val entity = mapper.map(projectSaleItem);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(ProjectSaleItemId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProjectSaleItemId id) {
    return repository.exists(id);
  }

  @Override
  public boolean exists(ProjectId projectId, ItemId itemId) {
    return repository.exists(projectId, itemId);
  }

  @Override
  public Stream<ProjectSaleItem> findAllBy(ProjectId projectId) {
    return repository.findAllBy(projectId)
      .map(mapper::map);
  }

  @Override
  public Optional<ProjectSaleItem> findBy(ProjectId projectId, ItemId itemId) {
    return Optional.ofNullable(repository.findOne(projectId, itemId))
      .map(mapper::map);
  }

  @Override
  public Optional<ProjectSaleItem> findBy(ProjectSaleItemId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public void update(ProjectSaleItem projectSaleItem) {
    val entity = repository.findOne(projectSaleItem.getId());
    mapper.pass(mapper.map(projectSaleItem), entity);
    repository.save(entity);
  }
}
