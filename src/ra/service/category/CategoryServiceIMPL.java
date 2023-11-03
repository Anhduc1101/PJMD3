package ra.service.category;

import ra.config.Config;
import ra.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceIMPL implements ICategoryService {
    static Config<List<Category>> config = new Config<>();
    public static List<Category> categoryList;

    static {
        categoryList = config.readFile(Config.URL_CATEGORY);
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryList;
    }


    @Override
    public void save(Category category) {
        if (findById(category.getCategoryId())==null){
        categoryList.add(category);
        updateData();
        }else {
            categoryList.set(categoryList.indexOf(category),category);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        categoryList.remove(findById(id));
    }

    @Override
    public Category findById(int id) {
        for (Category cat : categoryList) {
            if (cat.getCategoryId() == id) {
                return cat;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Category cat : categoryList) {
            if (cat.getCategoryId() > idMax) {
                idMax = cat.getCategoryId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CATEGORY, categoryList);
    }

    @Override
    public boolean existCategoryName(String categoryName) {
        for (Category cat:categoryList) {
            if (cat.getCategoryName().equalsIgnoreCase(categoryName)){
                return true;
            }
        }
        return false;
    }
}
