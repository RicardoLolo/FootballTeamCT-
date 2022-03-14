package com.example.md4.service.role;


import com.example.md4.model.Role;
import com.example.md4.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
