package ra.service.category;

import ra.model.Category;
import ra.service.IGenericService;

public interface ICategoryService extends IGenericService<Category> {
    boolean existCategoryName(String categoryName);
}
