package com.example.md4.service.role;


import com.example.md4.model.Role;
import com.example.md4.service.IGeneralService;

import java.util.Set;

public interface IRoleService extends IGeneralService<Role> {
    Set<Role> findByName(String name);
}
