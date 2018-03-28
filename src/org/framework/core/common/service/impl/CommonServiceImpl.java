package org.framework.core.common.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.system.pojo.base.TSDepart;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.lang3.StringUtils;
import org.framework.core.common.dao.ICommonDao;
import org.framework.core.common.hibernate.qbc.CriteriaQuery;
import org.framework.core.common.hibernate.qbc.HqlQuery;
import org.framework.core.common.hibernate.qbc.PageList;
import org.framework.core.common.model.common.DBTable;
import org.framework.core.common.model.common.UploadFile;
import org.framework.core.common.model.json.ComboTree;
import org.framework.core.common.model.json.DataGridReturn;
import org.framework.core.common.model.json.ImportFile;
import org.framework.core.common.model.json.TreeGrid;
import org.framework.core.common.service.CommonService;
import org.framework.tag.vo.datatable.DataTableReturn;
import org.framework.tag.vo.easyui.Autocomplete;
import org.framework.tag.vo.easyui.ComboTreeModel;
import org.framework.tag.vo.easyui.TreeGridModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	public ICommonDao commonDao = null;
	/**
	 * 获取所有数据库表
	 * @return
	 */
	public List<DBTable> getAllDbTableName()
	{
		return commonDao.getAllDbTableName();
	}
	public Integer getAllDbTableSize() {
		return commonDao.getAllDbTableSize();
	}
	@Resource
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public <T> void save(T entity) {
		commonDao.save(entity);
	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);

	}

	@Override
	public <T> void delete(T entity) {
		commonDao.delete(entity);

	}

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteAllEntitie(Collection<T> entities) {
		commonDao.deleteAllEntitie(entities);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T get(Class<T> class1, Serializable id) {
		return commonDao.get(class1, id);
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param <T>
	 * @param hql
	 * @param size
	 * @return
	 */
	public <T> List<T> getList(Class clas) {
		return commonDao.loadAll(clas);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T getEntity(Class entityName, Serializable id) {
		return commonDao.getEntity(entityName, id);
	}

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		return commonDao.findUniqueByProperty(entityClass, propertyName, value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {

		return commonDao.findByProperty(entityClass, propertyName, value);
	}

	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> loadAll(final Class<T> entityClass) {
		return commonDao.loadAll(entityClass);
	}
	
	public <T> T singleResult(String hql)
	{
		return commonDao.singleResult(hql);
	}

	/**
	 * 删除实体主键ID删除对象
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteEntityById(entityName, id);
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(T pojo) {
		commonDao.updateEntitie(pojo);

	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql) {
		return commonDao.findByQueryString(hql);
	}

	/**
	 * 根据sql更新
	 * 
	 * @param query
	 * @return
	 */
	public int updateBySqlString(String sql) {
		return commonDao.updateBySqlString(sql);
	}

	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findListbySql(String query) {
		return commonDao.findListbySql(query);
	}

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @param clas
	 * @return
	 */
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc) {
		return commonDao.findByPropertyisOrder(entityClass, propertyName, value, isAsc);
	}

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getPageList(cq, isOffset);
	}

	/**
	 * 返回DataTableReturn模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	/**
	 * 返回easyui datagrid模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getDataGridReturn(cq, isOffset);
	}

	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final HqlQuery hqlQuery, final boolean needParameter) {
		return commonDao.getPageList(hqlQuery, needParameter);
	}

	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageListBySql(final HqlQuery hqlQuery, final boolean isToEntity) {
		return commonDao.getPageListBySql(hqlQuery, isToEntity);
	}

	public Session getSession()

	{
		return commonDao.getSession();
	}
	
	public Session getSession(Interceptor inter)

	{
		return commonDao.getSession(inter);
	}

	public List findByExample(final String entityName, final Object exampleEntity) {
		return commonDao.findByExample(entityName, exampleEntity);
	}

	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,Boolean ispage) {
		return commonDao.getListByCriteriaQuery(cq,ispage);
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile) {
		return commonDao.uploadFile(uploadFile);
	}

	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile)

	{
		return commonDao.viewOrDownloadFile(uploadFile);
	}


	/**
	 * 生成XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 * @return
	 */
	public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 */
	public void parserXml(String fileName) {
		commonDao.parserXml(fileName);
	}

	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		return commonDao.comTree(all, comboTree);
	}

	/**
	 * 根据模型生成JSON
	 * 
	 * @param all
	 *            全部对象
	 * @param in
	 *            已拥有的对象
	 * @param comboBox
	 *            模型
	 * @return
	 */
	public List<ComboTree> ComboTree(List all,ComboTreeModel comboTreeModel,List in) {
		return commonDao.ComboTree(all,comboTreeModel,in);
	}
	/**
	 * 构建树形数据表
	 */
	public List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel) {
		return commonDao.treegrid(all, treeGridModel);
	}

	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getAutoList(Autocomplete autocomplete) {
		// update-begin--Author:tanghong  Date:20130528 for：[ 114 ]自动补全标签优化
		StringBuffer sb = new StringBuffer("");
		for(String searchField:autocomplete.getSearchField().split(",")){
			sb.append("  or "+searchField+" like '%"+autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where 1!=1 " + sb.toString();
		return commonDao.getSession().createQuery(hql).setFirstResult(autocomplete.getCurPage()-1).setMaxResults(autocomplete.getMaxRows()).list();
		// update-end--Author:tanghong  Date:20130528 for：[ 114 ]自动补全标签优化
	}
	
	
	@Override
	public Integer executeSql(String sql, List<Object> param) {
		return commonDao.executeSql(sql, param);
	}
	@Override
	public Integer executeSql(String sql, Object... param) {
		return commonDao.executeSql(sql, param);
	}
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		return commonDao.findForJdbc(sql, page,rows);
	}
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return commonDao.findForJdbc(sql, objs);
	}
	@Override
	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
			int rows, Object... objs) {
		return commonDao.findForJdbcParam(sql, page, rows, objs);
	}
	@Override
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
			Class<T> clazz) {
		return commonDao.findObjForJdbc(sql, page, rows, clazz);
	}
	@Override
	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return commonDao.findOneForJdbc(sql, objs);
	}
	@Override
	public Long getCountForJdbc(String sql) {
		return commonDao.getCountForJdbc(sql);
	}
	@Override
	public Long getCountForJdbcParam(String sql, Object[] objs) {
		return commonDao.getCountForJdbc(sql);
	}
	@Override
	public <T> void batchSave(List<T> entitys) {
		this.commonDao.batchSave(entitys);
	}
	
	
	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findHql(String hql, Object... param) {
		return this.commonDao.findHql(hql, param);
	}
	
	public <T> List<T> queryHighchartData(String hql,String type,String origTable,String dayTable,String hourTable,String startDate, String endDate,Class entity,String linep){
		String descCol = linep;
		if(StringUtils.isBlank(descCol)) {
			descCol = "receivetime";
		}
		List<T> listdata=null;
		SimpleDateFormat sdf=null;
		Calendar calendar=Calendar.getInstance();
		String tables[]=null;
		if(type.equals("year")){
			//默认月份查询一年的数据
			sdf=new SimpleDateFormat("yyyy");
			if(CommonUtil.isNull(endDate)){
				endDate=sdf.format(calendar.getTime());;
			}
			if(CommonUtil.isNull(startDate)){
				calendar.add(Calendar.YEAR, -11);
				startDate=sdf.format(calendar.getTime());
			}
			try{
				if(!CommonUtil.isNull(linep)){
					linep+=",";
				}else{
					linep="";
				}
				hql="select new "+entity.getName()+"("+linep+"receivetime,sum(data) as data) "+hql+" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"' group by date_format(receivetime,'%Y') order by "+descCol;
				listdata=this.findHql(hql);
			}catch(Exception e){
			}
		}else if(type.equals("month")){
			//默认月份查询一年的数据
			sdf=new SimpleDateFormat("yyyy-MM");
			if(CommonUtil.isNull(endDate)){
				endDate=sdf.format(calendar.getTime());;
			}
			if(CommonUtil.isNull(startDate)){
				calendar.add(Calendar.YEAR, -1);
				startDate=sdf.format(calendar.getTime());
			}
			try{
				hql+=" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"' order by "+descCol;
				listdata=this.findHql(hql);
			}catch(Exception e){
			}
		}else if(type.equals("day")){
			try{
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				//默认取本月往前推12天的数据
				if(CommonUtil.isNull(endDate)){
					endDate=sdf.format(calendar.getTime());;
				}
				if(CommonUtil.isNull(startDate)){
					calendar.add(Calendar.DATE, -11);
					startDate=sdf.format(calendar.getTime());
				}
				hql+=" and receivetime<='"+endDate+" 24"+"' and receivetime>='"+startDate+"' order by "+descCol;
				tables=findTable(startDate, endDate, dayTable);
				Interceptor interceptor=new MyInterceptor(origTable,tables);
				Session session=getSession(interceptor);
				Query query=session.createQuery(hql);
//				query.setDate(0, sdf.parse(endDate));
//				query.setDate(1, sdf.parse(startDate));
				listdata=query.list();
				if(session!=null){
					session.clear();
					session.close();
				}
			}catch(Exception e){
				
			}
		}else{
			try{
				sdf=new SimpleDateFormat("yyyy-MM-dd HH");
				//默认取本月往前推12天的数据
				if(CommonUtil.isNull(endDate)){
					endDate=sdf.format(calendar.getTime());;
				}
				if(CommonUtil.isNull(startDate)){
					calendar.add(Calendar.HOUR, -11);
					startDate=sdf.format(calendar.getTime());
				}
				hql+=" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"' order by "+descCol;
				tables=findTable(startDate, endDate, hourTable);
				Interceptor interceptor=new MyInterceptor(origTable,tables);
				Session session=getSession(interceptor);
				Query query=session.createQuery(hql);
//				query.setDate(0, sdf.parse(endDate));
//				query.setDate(1, sdf.parse(startDate));
				listdata=query.list();
				if(session!=null){
					session.clear();
					session.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
		}
		
		//System.out.println("queryHighchartData1 sql====:"+hql);
		return listdata;
	}
	
	public <T> List<T> queryHighchartDataBuild(String hql,String type,String origTable,String dayTable,String hourTable,String startDate, String endDate,Class entity,String linep){
		List<T> listdata=null;
		SimpleDateFormat sdf=null;
		Calendar calendar=Calendar.getInstance();
		String tables[]=null;
		if(type.equals("year")){
			//默认月份查询一年的数据
			sdf=new SimpleDateFormat("yyyy");
			if(CommonUtil.isNull(endDate)){
				endDate=sdf.format(calendar.getTime());;
			}
			if(CommonUtil.isNull(startDate)){
				calendar.add(Calendar.YEAR, -11);
				startDate=sdf.format(calendar.getTime());
			}
			try{
				if(!CommonUtil.isNull(linep)){
					linep+=",";
				}else{
					linep="";
				}
				hql="select new "+entity.getName()+"("+linep+"receivetime,sum(data) as data) "+hql+" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"' group by date_format(receivetime,'%Y') order by su.buildingid";
				listdata=this.findHql(hql);
			}catch(Exception e){
			}
		}else if(type.equals("month")){
			//默认月份查询一年的数据
			sdf=new SimpleDateFormat("yyyy-MM");
			if(CommonUtil.isNull(endDate)){
				endDate=sdf.format(calendar.getTime());;
			}
			if(CommonUtil.isNull(startDate)){
				calendar.add(Calendar.YEAR, -1);
				startDate=sdf.format(calendar.getTime());
			}
			try{
				hql+=" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"' order by receivetime";
				listdata=this.findHql(hql);
			}catch(Exception e){
			}
		}else if(type.equals("day")){
			try{
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				//默认取本月往前推12天的数据
				if(CommonUtil.isNull(endDate)){
					endDate=sdf.format(calendar.getTime());;
				}
				if(CommonUtil.isNull(startDate)){
					calendar.add(Calendar.DATE, -11);
					startDate=sdf.format(calendar.getTime());
				}
				hql+=" and receivetime<='"+endDate+" 24"+"' and receivetime>='"+startDate+"' order by receivetime";
				tables=findTable(startDate, endDate, dayTable);
				Interceptor interceptor=new MyInterceptor(origTable,tables);
				Session session=getSession(interceptor);
				Query query=session.createQuery(hql);
//				query.setDate(0, sdf.parse(endDate));
//				query.setDate(1, sdf.parse(startDate));
				listdata=query.list();
				if(session!=null){
					session.clear();
					session.close();
				}
			}catch(Exception e){
				
			}
		}else{
			try{
				sdf=new SimpleDateFormat("yyyy-MM-dd HH");
				//默认取本月往前推12天的数据
				if(CommonUtil.isNull(endDate)){
					endDate=sdf.format(calendar.getTime());;
				}
				if(CommonUtil.isNull(startDate)){
					calendar.add(Calendar.HOUR, -11);
					startDate=sdf.format(calendar.getTime());
				}
				hql+=" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"' order by receivetime";
				tables=findTable(startDate, endDate, hourTable);
				Interceptor interceptor=new MyInterceptor(origTable,tables);
				Session session=getSession(interceptor);
				Query query=session.createQuery(hql);
//				query.setDate(0, sdf.parse(endDate));
//				query.setDate(1, sdf.parse(startDate));
				listdata=query.list();
				if(session!=null){
					session.clear();
					session.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
		}
		
		//System.out.println("queryHighchartData1 sql====:"+hql);
		return listdata;
	}
	
	
	
	//饼图统计查询语句
	public <T> List<T> queryHighchartData(String hql,String type,String origTable,String dayTable,String hourTable,String startDate, String endDate,Class entity,String linep,String groupby){
		List<T> listdata=null;
		SimpleDateFormat sdf=null;
		Calendar calendar=Calendar.getInstance();
		String tables[]=null;
		if(type.equals("year")){
			//默认月份查询一年的数据
			sdf=new SimpleDateFormat("yyyy");
			if(CommonUtil.isNull(endDate)){
				endDate=sdf.format(calendar.getTime());;
			}
			if(CommonUtil.isNull(startDate)){
				calendar.add(Calendar.YEAR, -11);
				startDate=sdf.format(calendar.getTime());
			}
			try{
				if(!CommonUtil.isNull(linep)){
					linep+=",";
				}else{
					linep="";
				}
				hql=hql+" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"' "+groupby+" ,date_format(receivetime,'%Y') order by receivetime";
				listdata=this.findHql(hql);
			}catch(Exception e){
			}
		}else if(type.equals("month")){
			//默认月份查询一年的数据
			sdf=new SimpleDateFormat("yyyy-MM");
			if(CommonUtil.isNull(endDate)){
				endDate=sdf.format(calendar.getTime());;
			}
			if(CommonUtil.isNull(startDate)){
				calendar.add(Calendar.YEAR, -1);
				startDate=sdf.format(calendar.getTime());
			}
			try{
				hql+=" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"'";
				hql+=groupby+" order by receivetime";
				listdata=this.findHql(hql);
			}catch(Exception e){
			}
		}else if(type.equals("day")){
			try{
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				//默认取本月往前推12天的数据
				if(CommonUtil.isNull(endDate)){
					endDate=sdf.format(calendar.getTime());;
				}
				if(CommonUtil.isNull(startDate)){
					calendar.add(Calendar.DATE, -11);
					startDate=sdf.format(calendar.getTime());
				}
				hql+=" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"'";
				hql+=groupby+"  order by receivetime";
				tables=findTable(startDate, endDate, dayTable);
				Interceptor interceptor=new MyInterceptor(origTable,tables);
				Session session=getSession(interceptor);
				Query query=session.createQuery(hql);
//				query.setDate(0, sdf.parse(endDate));
//				query.setDate(1, sdf.parse(startDate));
				try{
					listdata=query.list();
				}catch(Exception e){
				}
				if(session!=null){
					session.clear();
					session.close();
				}
			}catch(Exception e){
				
			}
		}else{
			try{
				sdf=new SimpleDateFormat("yyyy-MM-dd HH");
				//默认取本月往前推12天的数据
				if(CommonUtil.isNull(endDate)){
					endDate=sdf.format(calendar.getTime());;
				}
				if(CommonUtil.isNull(startDate)){
					calendar.add(Calendar.HOUR, -11);
					startDate=sdf.format(calendar.getTime());
				}
				hql+=" and receivetime<='"+endDate+"' and receivetime>='"+startDate+"'";
				hql+=groupby+" order by receivetime";
				tables=findTable(startDate, endDate, hourTable);
				Interceptor interceptor=new MyInterceptor(origTable,tables);
				Session session=getSession(interceptor);
				Query query=session.createQuery(hql);
//				query.setDate(0, sdf.parse(endDate));
//				query.setDate(1, sdf.parse(startDate));
				try{
					listdata=query.list();
				}catch(Exception e){
				}
				if(session!=null){
					session.clear();
					session.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		
		//System.out.println("queryHighchartData2 sql====:"+hql);
		return listdata;
	}
	public String findName(String type,Date date){
		String year=String.valueOf(date.getYear()+1900);
		String month=String.valueOf(date.getMonth()+1);
		String day=String.valueOf(date.getDate());
		String hour=String.valueOf(date.getHours());
		
		if(type.equals("year")){
			return year+"年";
		}else if(type.equals("month")){
			return year+"-"+month+"月";
		}else if(type.equals("day")){
			return year+"-"+month+"-"+day;
		}else{
			return month+"-"+day+" "+hour+"";
		}
	}
	public String[] findTable(String startDate,String endDate,String tableName){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Calendar calendar=Calendar.getInstance();
		Date startD = null;
		Date endD = null;
		try {
			startD = sdf.parse(startDate);
			endD=sdf.parse(endDate);
		} catch (ParseException e) {
		}
		calendar.setTime(startD);
		List<String> list=new ArrayList<String>();
		while(calendar.getTime().getTime()<=endD.getTime()){
			String sql="SELECT table_name FROM information_schema.TABLES WHERE table_name ='"+tableName+"_"+simpledateformat.format(calendar.getTime())+"'";
			List<Object> listtab=this.findListbySql(sql);
			if(listtab!=null&&listtab.size()!=0){
				list.add(sdf.format(calendar.getTime()));
			}
			calendar.add(Calendar.MONTH, 1);
		}
		String[] tables=new String[list.size()];
		for(int i=0,l=list.size();i<l;i++){
			try {
				tables[i]=tableName+"_"+simpledateformat.format(sdf.parse(list.get(i)));
			} catch (ParseException e) {
			}
		}
		return tables;
	}
}
