package pico.erp.project.sale.item;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.item.ItemId;
import pico.erp.project.ProjectId;


@Repository
interface ProjectSaleItemEntityRepository extends
  CrudRepository<ProjectSaleItemEntity, ProjectSaleItemId> {

  @Query("SELECT CASE WHEN COUNT(psi) > 0 THEN true ELSE false END FROM ProjectSaleItem psi WHERE psi.projectId = :projectId AND psi.itemId = :itemId")
  boolean exists(
    @Param("projectId") ProjectId projectId, @Param("itemId") ItemId itemId);

  @Query("SELECT psi FROM ProjectSaleItem psi WHERE psi.projectId = :projectId")
  Stream<ProjectSaleItemEntity> findAllBy(
    @Param("projectId") ProjectId projectId);

  @Query("SELECT psi FROM ProjectSaleItem psi WHERE psi.projectId = :projectId AND psi.itemId = :itemId")
  Optional<ProjectSaleItemEntity> findBy(
    @Param("projectId") ProjectId projectId, @Param("itemId") ItemId itemId);

}

@Repository
@Transactional
public class ProjectSaleItemRepositoryJpa implements ProjectSaleItemRepository {

  @Autowired
  private ProjectSaleItemEntityRepository repository;

  @Autowired
  private ProjectSaleItemMapper mapper;

  @Override
  public ProjectSaleItem create(ProjectSaleItem projectSaleItem) {
    val entity = mapper.entity(projectSaleItem);
    val created = repository.save(entity);
    return mapper.domain(created);
  }

  @Override
  public void deleteBy(ProjectSaleItemId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(ProjectSaleItemId id) {
    return repository.existsById(id);
  }

  @Override
  public boolean exists(ProjectId projectId, ItemId itemId) {
    return repository.exists(projectId, itemId);
  }

  @Override
  public Stream<ProjectSaleItem> findAllBy(ProjectId projectId) {
    return repository.findAllBy(projectId)
      .map(mapper::domain);
  }

  @Override
  public Optional<ProjectSaleItem> findBy(ProjectId projectId, ItemId itemId) {
    return repository.findBy(projectId, itemId)
      .map(mapper::domain);
  }

  @Override
  public Optional<ProjectSaleItem> findBy(ProjectSaleItemId id) {
    return repository.findById(id)
      .map(mapper::domain);
  }

  @Override
  public void update(ProjectSaleItem projectSaleItem) {
    val entity = repository.findById(projectSaleItem.getId()).get();
    mapper.pass(mapper.entity(projectSaleItem), entity);
    repository.save(entity);
  }
}
