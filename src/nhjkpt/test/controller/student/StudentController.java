package nhjkpt.test.controller.student;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.framework.core.common.controller.BaseController;
import org.framework.core.common.hibernate.qbc.CriteriaQuery;
import org.framework.core.common.model.json.AjaxJson;
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.pojo.base.TSDepart;
import nhjkpt.system.service.SystemService;
import org.framework.core.util.MyBeanUtils;

import nhjkpt.test.entity.student.StudentEntity;
import nhjkpt.test.service.student.StudentServiceI;

/**   
 * @Title: Controller
 * @Description: 学生表
 * @author zhangdaihao
 * @date 2013-07-10 11:42:24
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/studentController")
public class StudentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private StudentServiceI studentService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 学生表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "student")
	public ModelAndView student(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/test/student/studentList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(StudentEntity student,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(StudentEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, student);
		this.studentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学生表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(StudentEntity student, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		student = systemService.getEntity(StudentEntity.class, student.getId());
		message = "删除成功";
		studentService.delete(student);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学生表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(StudentEntity student, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(student.getId())) {
			message = "更新成功";
			StudentEntity t = studentService.get(StudentEntity.class, student.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(student, t);
				studentService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			studentService.save(student);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学生表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(StudentEntity student, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(student.getId())) {
			student = studentService.getEntity(StudentEntity.class, student.getId());
			req.setAttribute("studentPage", student);
		}
		return new ModelAndView("nhjkpt/test/student/student");
	}
}
