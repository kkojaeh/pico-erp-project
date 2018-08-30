package pico.erp.project.core;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import pico.erp.item.data.ItemId;
import pico.erp.project.data.ProjectId;
import pico.erp.project.data.ProjectSaleItemId;
import pico.erp.project.domain.ProjectSaleItem;

public interface ProjectSaleItemRepository {

  ProjectSaleItem create(@NotNull ProjectSaleItem projectSaleItem);

  void deleteBy(@NotNull ProjectSaleItemId id);

  boolean exists(@NotNull ProjectSaleItemId id);

  boolean exists(@NotNull ProjectId projectId, @NotNull ItemId itemId);

  Stream<ProjectSaleItem> findAllBy(@NotNull ProjectId projectId);

  Optional<ProjectSaleItem> findBy(@NotNull ProjectId projectId, @NotNull ItemId itemId);

  Optional<ProjectSaleItem> findBy(@NotNull ProjectSaleItemId id);

  void update(@NotNull ProjectSaleItem projectSaleItem);

}
