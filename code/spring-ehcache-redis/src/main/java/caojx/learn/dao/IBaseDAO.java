package caojx.learn.dao;

import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * Description: Dao层接口基础接口
 *
 * @author caojx
 *         Created by caojx on 2017年04月10 下午10:52:52
 */
public interface IBaseDAO<T> {

    /**
     * 通过主键获取一条记录
     *
     * @param t
     * @return
     */
    public T get(T t);

    /**
     * 查询多条记录
     *
     * @param t
     * @return
     */
    public List<T> query(@Param("entity") T t);
    
    /**
     *
     */
    public Integer insert(T t) throws SQLException;

    /**
     * 更新一条记录
     *
     * @param t
     */
    public Integer update(T t) throws SQLException;

    /**
     * 删除一条记录
     *
     * @param t
     * @return
     */
    public Integer delete(T t) throws SQLException;


}
