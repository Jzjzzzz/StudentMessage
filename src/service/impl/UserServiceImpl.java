package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.Message;
import domain.PageBean;
import domain.User;
import service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();
    @Override
    public List<Message> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public void addUser(Message message) {
        dao.add(message);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public Message findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(Message message) {
        dao.update(message);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        //遍历数组
        if(ids!=null&&ids.length>0){
            for (String id : ids) {
                //调用dao删除
                dao.delete(Integer.parseInt(id));
            }
        }

    }

    @Override
    public PageBean<Message> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);
        if(currentPage<=0){
            currentPage=1;
        }

        //1.创建空的PageBean对象
        PageBean<Message> pb=new PageBean<>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //3.调用dao查询总记录数
        int totalCount=dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //计算总页码
        int totalPage=totalCount% rows==0? totalCount/rows:(totalCount/rows)+1;
        pb.setTotalPage(totalPage);
        if(currentPage>=totalPage){
            currentPage=totalPage;
        }

        //4.调用dao查询List集合
        int start=(currentPage-1)*rows;
        List<Message> list=dao.findByPage(start,rows,condition);
        pb.setList(list);


        return pb;
    }
}
