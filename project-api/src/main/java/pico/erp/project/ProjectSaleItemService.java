package pico.erp.project;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.item.data.ItemId;
import pico.erp.project.ProjectSaleItemRequests.CreateRequest;
import pico.erp.project.ProjectSaleItemRequests.DeleteRequest;
import pico.erp.project.ProjectSaleItemRequests.UpdateRequest;
import pico.erp.project.data.ProjectId;
import pico.erp.project.data.ProjectSaleItemData;
import pico.erp.project.data.ProjectSaleItemId;

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
