package ra.service.users;

import ra.model.Users;
import ra.service.IGenericService;

public interface IUserService extends IGenericService<Users> {
    boolean existUsername(String username);
    boolean existEmail(String email);
    Users checkLogin(String username, String password);


}
