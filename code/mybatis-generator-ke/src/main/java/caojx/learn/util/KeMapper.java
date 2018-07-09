package caojx.learn.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
* @author halley.qiu
* @description 注意，该接口不能被Mybatis扫描到，否则会出错
* @dateTime 2018/6/26 16:42
*/
public interface KeMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME
}
