package pico.erp.project.sale.item;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.item.ItemId;
import pico.erp.project.ProjectId;
import pico.erp.project.sale.item.ProjectSaleItemRequests.CreateRequest;
import pico.erp.project.sale.item.ProjectSaleItemRequests.DeleteRequest;
import pico.erp.project.sale.item.ProjectSaleItemRequests.UpdateRequest;

public interface ProjectSaleItemService {

  ProjectSaleItemData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull ProjectSaleItemId id);

  boolean exists(@NotNull ProjectId id, @NotNull ItemId itemId);

  ProjectSaleItemData get(@NotNull ProjectSaleItemId id);

  ProjectSaleItemData get(@NotNull ProjectId id, @NotNull ItemId itemId);

  List<ProjectSaleItemData> getAll(ProjectId projectId);

  void update(@Valid UpdateRequest request);

}
