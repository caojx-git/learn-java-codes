package caojx.learn.springboothelloword.valid;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 学生Service实现类
 * @author caojx
 * Created on 2018/3/23 下午上午11:11
 */
@Transactional
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    public void add(Student student) {
        studentDao.save(student);
    }
}
