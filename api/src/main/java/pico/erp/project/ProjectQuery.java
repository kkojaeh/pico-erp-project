package pico.erp.project;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.shared.data.LabeledValuable;

public interface ProjectQuery {

  List<? extends LabeledValuable> asLabels(@NotNull String keyword, long limit);

  Page<ProjectView> retrieve(@NotNull ProjectView.Filter filter, @NotNull Pageable pageable);

}
