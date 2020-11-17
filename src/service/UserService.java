package service;

import domain.Message;
import domain.PageBean;
import domain.User;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    public List<Message> findAll();

    User login(User user);

    /**
     * 保存
     * @param user
     */
    void addUser(Message message);

    /**
     * 删除
     * @param id
     */
    void deleteUser(String id);

    /**
     *根据ID查询
     * @param id
     * @return
     */
    Message findUserById(String id);

    /**
     * 修改
     * @param user
     */
    void updateUser(Message message);

    /**
     * 批量删除用户
     * @param ids
     */
    void delSelectedUser(String[] ids);


    /**
     * 分页条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<Message> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
