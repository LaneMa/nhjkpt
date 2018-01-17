package nhjkpt.test.service.impl.student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.test.service.student.StudentServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("studentService")
@Transactional
public class StudentServiceImpl extends CommonServiceImpl implements StudentServiceI {
	
}