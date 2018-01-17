package nhjkpt.system.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingitemize.BuildingItemizeEntity;
import nhjkpt.configmanage.entity.buildingsum.BuildingSumEntity;
import nhjkpt.configmanage.entity.buildingunit.BuildingUnitEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentitemize.DepartmentItemizeEntity;
import nhjkpt.configmanage.entity.departmentsum.DepartmentSumEntity;
import nhjkpt.configmanage.entity.departmentunit.DepartmentUnitEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.metershowfunc.MetershowfuncEntity;
import nhjkpt.configmanage.entity.originadata.OriginadataEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.entity.schoolinfo.SchoolinfoEntity;
import nhjkpt.configmanage.entity.schoolitemize.SchoolItemizeEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;
import nhjkpt.configmanage.entity.schoolunit.SchoolUnitEntity;
import nhjkpt.system.pojo.base.TSFunction;
import nhjkpt.system.pojo.base.TSIcon;
import nhjkpt.system.pojo.base.TSLog;
import nhjkpt.system.pojo.base.TSRole;
import nhjkpt.system.pojo.base.TSRoleFunction;
import nhjkpt.system.pojo.base.TSRoleUser;
import nhjkpt.system.pojo.base.TSType;
import nhjkpt.system.pojo.base.TSTypegroup;
import nhjkpt.system.pojo.base.TSUser;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.framework.core.common.hibernate.qbc.CriteriaQuery;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.framework.core.util.BrowserUtils;
import org.framework.core.util.ContextHolderUtils;
import org.framework.core.util.DataUtils;
import org.framework.core.util.ResourceUtil;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	public TSUser checkUserExits(TSUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request=ContextHolderUtils.getRequest();
		String broswer=BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DataUtils.gettimestamp());
		log.setTSUser(ResourceUtil.getSessionUserName());
		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode,String typename,TSTypegroup tsTypegroup)
	{
		TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode",typecode);
		if (actType == null) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			commonDao.save(actType);
		}
		return actType;
		
	}
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode,String typgroupename)
	{
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode",typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;	
	}
	@Override
	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode",typegroupCode);
		return tsTypegroup;
	}

	@Override
	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
			TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
		}
	}
	@Override
	public void refleshTypesCach(TSType type) {
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());
		List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
		TSTypegroup.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
	}
	@Override
	public void refleshTypeGroupCach(){
		TSTypegroup.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
	}
	
	//----------------------------------------------------------------
	//update-start--Author:anchao  Date:20130415 for：按钮权限控制
	//----------------------------------------------------------------
	
	@Override
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId,String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1=new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id",role.getId());
		cq1.eq("TSFunction.id",functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1,false);
		if(null!=rFunctions && rFunctions.size()>0){
			TSRoleFunction tsRoleFunction =  rFunctions.get(0);
			if(null!=tsRoleFunction.getOperation()){
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	@Override
	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId,String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1=new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id",role.getId());
			cq1.eq("TSFunction.id",functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1,false);
			if(null!=rFunctions && rFunctions.size()>0){
				TSRoleFunction tsRoleFunction =  rFunctions.get(0);
				if(null!=tsRoleFunction.getOperation()){
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}
	//----------------------------------------------------------------
	//update-start--Author:anchao  Date:20130415 for：按钮权限控制
	//----------------------------------------------------------------
	@Override
	public void flushRoleFunciton(String id, TSFunction newFunction) {
		TSFunction functionEntity = this.getEntity(TSFunction.class, id);
		if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
			return;
		}
		TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getTSIcon().getId());
			if(!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
				//刷新缓存
				HttpSession session = ContextHolderUtils.getSession();
				TSUser user = ResourceUtil.getSessionUserName();
				List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
				for (TSRoleUser ru : rUsers) {
					TSRole role = ru.getTSRole();
					session.removeAttribute(role.getId());
				} 
			}
	}

	@Override
	public void exportConfigurationFile(OutputStream out) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("原始数据配置");
		Row row = sheet.createRow(0); 
		Cell cell = row.createCell(0);
		cell.setCellValue("数据库");
		cell = row.createCell(1);
		cell.setCellValue("数据库ip");
		cell = row.createCell(2);
		cell.setCellValue("数据库名称");
		cell = row.createCell(3);
		cell.setCellValue("用户名");
		cell = row.createCell(4);
		cell.setCellValue("密码");
		cell = row.createCell(5);
		cell.setCellValue("表名");
		List<OriginadataEntity> listOrigin=commonDao.findHql(" from OriginadataEntity ");
		int i=0;
		if(listOrigin!=null){
			for(OriginadataEntity origin:listOrigin){
				i++;
				row=sheet.createRow(i);
				cell = row.createCell(0);
				cell.setCellValue(origin.getDbtype());
				cell = row.createCell(1);
				cell.setCellValue(origin.getDbip());
				cell = row.createCell(2);
				cell.setCellValue(origin.getDbname());
				cell = row.createCell(3);
				cell.setCellValue(origin.getDbuser());
				cell = row.createCell(4);
				cell.setCellValue(origin.getDbpwd());
				cell = row.createCell(5);
				cell.setCellValue(origin.getTbname());
			}
		}
		sheet = wb.createSheet("学校信息");
		List<SchoolinfoEntity> listSchool=commonDao.findHql(" from SchoolinfoEntity");
		if(listSchool!=null&&listSchool.size()!=0){
			SchoolinfoEntity school=listSchool.get(0);
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("学校id");
			cell = row.createCell(1);
			cell.setCellValue(school.getSchoolid());
			row = sheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue("学校名称");
			cell = row.createCell(1);
			cell.setCellValue(school.getSchoolname());
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellValue("预警时间类型");
			cell = row.createCell(1);
			cell.setCellValue(school.getAlarmTimeType());
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellValue("预警时间");
			cell = row.createCell(1);
			cell.setCellValue(school.getAlarmTime());
			row = sheet.createRow(4);
			cell = row.createCell(0);
			cell.setCellValue("学校简介");
			cell = row.createCell(1);
			cell.setCellValue(school.getSchooltext());
			
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellValue("用水配额");
			cell = row.createCell(1);
			cell.setCellValue(school.getWaterlimit());
			
			row = sheet.createRow(6);
			cell = row.createCell(0);
			cell.setCellValue("用电配额");
			cell = row.createCell(1);
			cell.setCellValue(school.getElectriclimit());
			
			row = sheet.createRow(7);
			cell = row.createCell(0);
			cell.setCellValue("用气配额");
			cell = row.createCell(1);
			cell.setCellValue(school.getGaslimit());
			
			row = sheet.createRow(8);
			cell = row.createCell(0);
			cell.setCellValue("用热配额");
			cell = row.createCell(1);
			cell.setCellValue(school.getHeatlimit());
		}else{
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("学校id");
			row = sheet.createRow(0);
			cell = row.createCell(1);
			cell.setCellValue("学校名称");
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellValue("学校简介");

			
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellValue("用水配额");
			row = sheet.createRow(4);
			cell = row.createCell(0);
			cell.setCellValue("用电配额");
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellValue("用气配额");
			row = sheet.createRow(6);
			cell = row.createCell(0);
			cell.setCellValue("用热配额");
		}
		sheet = wb.createSheet("功能数据表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("功能id");
		cell = row.createCell(1);
		cell.setCellValue("功能名称");
		cell = row.createCell(2);
		cell.setCellValue("是否检查");
		cell = row.createCell(3);
		cell.setCellValue("显示名称");
		List<FuncEntity> listFunc=commonDao.findHql(" from FuncEntity");
		if(listFunc!=null&&listFunc.size()!=0){
			i=0;
			for(FuncEntity func:listFunc){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(func.getFuncid());
				cell = row.createCell(1);
				cell.setCellValue(func.getFuncname());
				cell = row.createCell(2);
				cell.setCellValue(func.getIscheck());
				cell = row.createCell(3);
				cell.setCellValue(func.getShowtext());
			}
		}
		sheet = wb.createSheet("配电房表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("配电房id");
		cell = row.createCell(1);
		cell.setCellValue("配电房名称");
		List<RoomEntity> listRoom=commonDao.findHql(" from RoomEntity");
		if(listRoom!=null&&listRoom.size()!=0){
			i=0;
			for(RoomEntity room:listRoom){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(room.getRoomid());
				cell = row.createCell(1);
				cell.setCellValue(room.getRoomtext());
			}
		}
		sheet = wb.createSheet("配电房内电表及表间关系列表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("表具id");
		cell = row.createCell(1);
		cell.setCellValue("表具名称");
		cell = row.createCell(2);
		cell.setCellValue("配电房id");
		cell = row.createCell(3);
		cell.setCellValue("表具级别");
		cell = row.createCell(4);
		cell.setCellValue("父表具id");
		List<MeterEntity> listMeter=commonDao.findHql(" from MeterEntity");
		if(listMeter!=null&&listMeter.size()!=0){
			i=0;
			for(MeterEntity meter:listMeter){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(meter.getMeterid());
				cell = row.createCell(1);
				cell.setCellValue(meter.getMetertext());
				cell = row.createCell(2);
				cell.setCellValue(meter.getRoomid());
				cell = row.createCell(3);
				cell.setCellValue(meter.getLevel());
				cell = row.createCell(4);
				if(meter.getMeter()!=null){
					cell.setCellValue(meter.getMeter().getMeterid());
				}else{
					cell.setCellValue("-1");
				}
			}
		}
		sheet = wb.createSheet("表具上下限功能");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("表具id");
		cell = row.createCell(1);
		cell.setCellValue("功能id");
		cell = row.createCell(2);
		cell.setCellValue("数据上限");
		cell = row.createCell(3);
		cell.setCellValue("数据下限");
		List<MetershowfuncEntity> listShow=commonDao.findHql(" from MetershowfuncEntity");
		if(listShow!=null&&listShow.size()!=0){
			i=0;
			for(MetershowfuncEntity show:listShow){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(show.getMeterid());
				cell = row.createCell(1);
				cell.setCellValue(show.getFuncid());
				cell = row.createCell(2);
				cell.setCellValue(show.getTop());
				cell = row.createCell(3);
				cell.setCellValue(show.getFloor());
			}
		}
		sheet = wb.createSheet("分类方式表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("分类id");
		cell = row.createCell(1);
		cell.setCellValue("分类名称");
		cell = row.createCell(2);
		cell.setCellValue("分类级别");
		cell = row.createCell(3);
		cell.setCellValue("父分类id");
		List<ItemizeEntity> listItem=commonDao.findHql(" from ItemizeEntity");
		if(listItem!=null&&listItem.size()!=0){
			i=0;
			for(ItemizeEntity item:listItem){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(item.getItemizeid());
				cell = row.createCell(1);
				cell.setCellValue(item.getItemizetext());
				cell = row.createCell(2);
				cell.setCellValue(item.getLevel());
				cell = row.createCell(3);
				if(item.getItemize()!=null){
					cell.setCellValue(item.getItemize().getItemizeid());
				}else{
					cell.setCellValue("-1");
				}
			}
		}
		sheet = wb.createSheet("建筑物信息");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("建筑物id");
		cell = row.createCell(1);
		cell.setCellValue("建筑物名称");
		cell = row.createCell(2);
		cell.setCellValue("建筑物简介");
		
		cell = row.createCell(3);
		cell.setCellValue("用水配额");
		cell = row.createCell(4);
		cell.setCellValue("用电配额");
		cell = row.createCell(5);
		cell.setCellValue("用气配额");
		cell = row.createCell(6);
		cell.setCellValue("用热配额");
		List<BuildinginfoEntity> listBuilding=commonDao.findHql(" from BuildinginfoEntity");
		if(listBuilding!=null&&listBuilding.size()!=0){
			i=0;
			for(BuildinginfoEntity building:listBuilding){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(building.getBuildingid());
				cell = row.createCell(1);
				cell.setCellValue(building.getBuildingname());
				cell = row.createCell(2);
				cell.setCellValue(building.getBuildingtext());
				
				cell = row.createCell(3);
				cell.setCellValue(building.getWaterlimit());
				cell = row.createCell(4);
				cell.setCellValue(building.getElectriclimit());
				cell = row.createCell(5);
				cell.setCellValue(building.getGaslimit());
				cell = row.createCell(6);
				cell.setCellValue(building.getHeatlimit());
			}
		}
		sheet = wb.createSheet("院系信息");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("院系id");
		cell = row.createCell(1);
		cell.setCellValue("院系名称");
		cell = row.createCell(2);
		cell.setCellValue("院系简介");
		
		cell = row.createCell(3);
		cell.setCellValue("用水配额");
		cell = row.createCell(4);
		cell.setCellValue("用电配额");
		cell = row.createCell(5);
		cell.setCellValue("用气配额");
		cell = row.createCell(6);
		cell.setCellValue("用热配额");
		List<DepartmentinfoEntity> listDepartment=commonDao.findHql(" from DepartmentinfoEntity");
		if(listDepartment!=null&&listDepartment.size()!=0){
			i=0;
			for(DepartmentinfoEntity department:listDepartment){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(department.getDepartmentid());
				cell = row.createCell(1);
				cell.setCellValue(department.getDepartmentname());
				cell = row.createCell(2);
				cell.setCellValue(department.getDepartmenttext());
				
				cell = row.createCell(3);
				cell.setCellValue(department.getWaterlimit());
				cell = row.createCell(4);
				cell.setCellValue(department.getElectriclimit());
				cell = row.createCell(5);
				cell.setCellValue(department.getGaslimit());
				cell = row.createCell(6);
				cell.setCellValue(department.getHeatlimit());
			}
		}
		sheet = wb.createSheet("学校水电气总量计算表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("功能id");
		cell = row.createCell(1);
		cell.setCellValue("表具id");
		cell = row.createCell(2);
		cell.setCellValue("拆分系数");
		List<SchoolSumEntity> listSS=commonDao.findHql(" from SchoolSumEntity");
		if(listSS!=null&&listSS.size()!=0){
			i=0;
			for(SchoolSumEntity sum:listSS){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(sum.getFuncid());
				cell = row.createCell(1);
				cell.setCellValue(sum.getMeterid());
				cell = row.createCell(2);
				cell.setCellValue(sum.getFactor());
			}
		}
		sheet = wb.createSheet("大楼水电气总量计算表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("大楼id");
		cell = row.createCell(1);
		cell.setCellValue("功能id");
		cell = row.createCell(2);
		cell.setCellValue("表具id");
		cell = row.createCell(3);
		cell.setCellValue("拆分系数");
		List<BuildingSumEntity> listBS=commonDao.findHql(" from BuildingSumEntity");
		if(listBS!=null&&listBS.size()!=0){
			i=0;
			for(BuildingSumEntity sum:listBS){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(sum.getBuildingid());
				cell = row.createCell(1);
				cell.setCellValue(sum.getFuncid());
				cell = row.createCell(2);
				cell.setCellValue(sum.getMeterid());
				cell = row.createCell(3);
				cell.setCellValue(sum.getFactor());
			}
		}
		sheet = wb.createSheet("院系水电气总量计算表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("院系id");
		cell = row.createCell(1);
		cell.setCellValue("功能id");
		cell = row.createCell(2);
		cell.setCellValue("表具id");
		cell = row.createCell(3);
		cell.setCellValue("拆分系数");
		List<DepartmentSumEntity> listDS=commonDao.findHql(" from DepartmentSumEntity");
		if(listDS!=null&&listDS.size()!=0){
			i=0;
			for(DepartmentSumEntity sum:listDS){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(sum.getDepartmentid());
				cell = row.createCell(1);
				cell.setCellValue(sum.getFuncid());
				cell = row.createCell(2);
				cell.setCellValue(sum.getMeterid());
				cell = row.createCell(3);
				cell.setCellValue(sum.getFactor());
			}
		}
		sheet = wb.createSheet("学校分类用能计算用表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("分类id");
		cell = row.createCell(1);
		cell.setCellValue("功能id");
		cell = row.createCell(2);
		cell.setCellValue("表具id");
		cell = row.createCell(3);
		cell.setCellValue("拆分系数");
		List<SchoolItemizeEntity> listSI=commonDao.findHql(" from SchoolItemizeEntity");
		if(listSI!=null&&listSI.size()!=0){
			i=0;
			for(SchoolItemizeEntity itemize:listSI){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(itemize.getItemizeid());
				cell = row.createCell(1);
				cell.setCellValue(itemize.getFuncid());
				cell = row.createCell(2);
				cell.setCellValue(itemize.getMeterid());
				cell = row.createCell(3);
				cell.setCellValue(itemize.getFactor());
			}
		}
		sheet = wb.createSheet("建筑分类用能计算用表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("大楼id");
		cell = row.createCell(1);
		cell.setCellValue("分类id");
		cell = row.createCell(2);
		cell.setCellValue("功能id");
		cell = row.createCell(3);
		cell.setCellValue("表具id");
		cell = row.createCell(4);
		cell.setCellValue("拆分系数");
		List<BuildingItemizeEntity> listBI=commonDao.findHql(" from BuildingItemizeEntity");
		if(listBI!=null&&listBI.size()!=0){
			i=0;
			for(BuildingItemizeEntity itemize:listBI){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(itemize.getBuildingid());
				cell = row.createCell(1);
				cell.setCellValue(itemize.getItemizeid());
				cell = row.createCell(2);
				cell.setCellValue(itemize.getFuncid());
				cell = row.createCell(3);
				cell.setCellValue(itemize.getMeterid());
				cell = row.createCell(4);
				cell.setCellValue(itemize.getFactor());
			}
		}
		sheet = wb.createSheet("院系分类用能计算用表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("院系id");
		cell = row.createCell(1);
		cell.setCellValue("分类id");
		cell = row.createCell(2);
		cell.setCellValue("功能id");
		cell = row.createCell(3);
		cell.setCellValue("表具id");
		cell = row.createCell(4);
		cell.setCellValue("拆分系数");
		List<DepartmentItemizeEntity> listDI=commonDao.findHql(" from DepartmentItemizeEntity");
		if(listDI!=null&&listDI.size()!=0){
			i=0;
			for(DepartmentItemizeEntity itemize:listDI){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(itemize.getDepartmentid());
				cell = row.createCell(1);
				cell.setCellValue(itemize.getItemizeid());
				cell = row.createCell(2);
				cell.setCellValue(itemize.getFuncid());
				cell = row.createCell(3);
				cell.setCellValue(itemize.getMeterid());
				cell = row.createCell(4);
				cell.setCellValue(itemize.getFactor());
			}
		}
		sheet = wb.createSheet("学校单位用能统计计算表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("单位id");
		cell = row.createCell(1);
		cell.setCellValue("单位名称");
		cell = row.createCell(2);
		cell.setCellValue("单位总数量");
		
		cell = row.createCell(3);
		cell.setCellValue("用水配额");
		cell = row.createCell(4);
		cell.setCellValue("用电配额");
		cell = row.createCell(5);
		cell.setCellValue("用气配额");
		cell = row.createCell(6);
		cell.setCellValue("用热配额");
		List<SchoolUnitEntity> listUnit=commonDao.findHql(" from SchoolUnitEntity");
		if(listUnit!=null&&listUnit.size()!=0){
			i=0;
			for(SchoolUnitEntity unit:listUnit){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(unit.getUnitid());
				cell = row.createCell(1);
				cell.setCellValue(unit.getUnittext());
				cell = row.createCell(2);
				cell.setCellValue(unit.getUnitsum());
				
				cell = row.createCell(3);
				cell.setCellValue(unit.getWaterlimit());
				cell = row.createCell(4);
				cell.setCellValue(unit.getElectriclimit());
				cell = row.createCell(5);
				cell.setCellValue(unit.getGaslimit());
				cell = row.createCell(6);
				cell.setCellValue(unit.getHeatlimit());
			}
		}
		sheet = wb.createSheet("建筑单位用能统计计算表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("建筑物id");
		cell = row.createCell(1);
		cell.setCellValue("单位id");
		cell = row.createCell(2);
		cell.setCellValue("单位名称");
		cell = row.createCell(3);
		cell.setCellValue("单位总数量");
		
		cell = row.createCell(4);
		cell.setCellValue("用水配额");
		cell = row.createCell(5);
		cell.setCellValue("用电配额");
		cell = row.createCell(6);
		cell.setCellValue("用气配额");
		cell = row.createCell(7);
		cell.setCellValue("用热配额");
		List<BuildingUnitEntity> listBUnit=commonDao.findHql(" from BuildingUnitEntity");
		if(listBUnit!=null&&listBUnit.size()!=0){
			i=0;
			for(BuildingUnitEntity unit:listBUnit){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(unit.getBuildingid());
				cell = row.createCell(1);
				cell.setCellValue(unit.getUnitid());
				cell = row.createCell(2);
				cell.setCellValue(unit.getUnittext());
				cell = row.createCell(3);
				cell.setCellValue(unit.getUnitsum());
				
				cell = row.createCell(4);
				cell.setCellValue(unit.getWaterlimit());
				cell = row.createCell(5);
				cell.setCellValue(unit.getElectriclimit());
				cell = row.createCell(6);
				cell.setCellValue(unit.getGaslimit());
				cell = row.createCell(7);
				cell.setCellValue(unit.getHeatlimit());
			}
		}
		sheet = wb.createSheet("院系单位用能统计计算表");
		row = sheet.createRow(0); 
		cell = row.createCell(0);
		cell.setCellValue("院系id");
		cell = row.createCell(1);
		cell.setCellValue("单位id");
		cell = row.createCell(2);
		cell.setCellValue("单位名称");
		cell = row.createCell(3);
		cell.setCellValue("单位总数量");
		
		cell = row.createCell(4);
		cell.setCellValue("用水配额");
		cell = row.createCell(5);
		cell.setCellValue("用电配额");
		cell = row.createCell(6);
		cell.setCellValue("用气配额");
		cell = row.createCell(7);
		cell.setCellValue("用热配额");
		List<DepartmentUnitEntity> listDUnit=commonDao.findHql(" from DepartmentUnitEntity");
		if(listDUnit!=null&&listDUnit.size()!=0){
			i=0;
			for(DepartmentUnitEntity unit:listDUnit){
				i++;
				row = sheet.createRow(i); 
				cell = row.createCell(0);
				cell.setCellValue(unit.getDepartmentid());
				cell = row.createCell(1);
				cell.setCellValue(unit.getUnitid());
				cell = row.createCell(2);
				cell.setCellValue(unit.getUnittext());
				cell = row.createCell(3);
				cell.setCellValue(unit.getUnitsum());
				
				cell = row.createCell(4);
				cell.setCellValue(unit.getWaterlimit());
				cell = row.createCell(5);
				cell.setCellValue(unit.getElectriclimit());
				cell = row.createCell(6);
				cell.setCellValue(unit.getGaslimit());
				cell = row.createCell(7);
				cell.setCellValue(unit.getHeatlimit());
			}
		}
		try {
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importConfiguratinoFile(InputStream in) {
		Workbook wb;
		try {
			wb = new HSSFWorkbook(in);
			int len=wb.getNumberOfSheets();
			Sheet sheet=null;
			Row row = null; 
			Cell cell = null;
			Cell cell1=null;
			Cell cell2=null;
			Cell cell3=null;
			for(int i=0;i<len;i++){
				sheet=wb.getSheetAt(i);
				if(sheet.getSheetName().equals("原始数据配置")){
					commonDao.deleteAllEntitie(commonDao.findHql("from OriginadataEntity"));
					OriginadataEntity origin=null;
					List<OriginadataEntity> listOrigin=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
//						listOrigin=commonDao.findHql(" from OriginadataEntity where dbtype=? and dbip=? and dbname=? and dbuser=? and dbpwd=? and tbname=?", row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue(),row.getCell(2).getStringCellValue(),row.getCell(3).getStringCellValue(),row.getCell(4).getStringCellValue(),row.getCell(5).getStringCellValue());
						
						if(row.getCell(0).getStringCellValue()!=null && row.getCell(0).getStringCellValue().length()>0){
						
							if(CommonUtil.isNull(listOrigin)){
								origin=new OriginadataEntity();
								origin.setDbtype(row.getCell(0).getStringCellValue());
								origin.setDbip(row.getCell(1).getStringCellValue());
								origin.setDbname(row.getCell(2).getStringCellValue());
								origin.setDbuser(row.getCell(3).getStringCellValue());
								origin.setDbpwd(row.getCell(4).getStringCellValue());
								origin.setTbname(row.getCell(5).getStringCellValue());
								commonDao.save(origin);
							}
						}
					}
				}else if(sheet.getSheetName().equals("学校信息")){
					List<SchoolinfoEntity> listSchool=commonDao.findHql(" from SchoolinfoEntity");
					SchoolinfoEntity school=null;
					if(CommonUtil.isNull(listSchool)){
						school=new SchoolinfoEntity();
					}else{
						school=listSchool.get(0);
					}
					row=sheet.getRow(0);
					
					
					
					school.setSchoolid(row.getCell(1).getStringCellValue());
					row=sheet.getRow(1);
					school.setSchoolname(row.getCell(1).getStringCellValue());
					row=sheet.getRow(2);
					cell=row.getCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if(CommonUtil.isNull(cell.getStringCellValue())){
						school.setAlarmTimeType(11);
						school.setAlarmTime(4);
					}else{
						school.setAlarmTimeType(Integer.parseInt(cell.getStringCellValue()));
						row=sheet.getRow(3);
						cell=row.getCell(1);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						school.setAlarmTime(Integer.parseInt(cell.getStringCellValue()));
					}
					row=sheet.getRow(4);
					school.setSchooltext(row.getCell(1).getStringCellValue());
					row=sheet.getRow(5);
					school.setWaterlimit(row.getCell(1).getNumericCellValue());
					row=sheet.getRow(6);
					school.setElectriclimit(row.getCell(1).getNumericCellValue());
					row=sheet.getRow(7);
					school.setGaslimit(row.getCell(1).getNumericCellValue());
					row=sheet.getRow(8);
					school.setHeatlimit(row.getCell(1).getNumericCellValue());

					commonDao.saveOrUpdate(school);
				}else if(sheet.getSheetName().equals("功能数据表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from FuncEntity"));
					List<FuncEntity> listFunc=null;
					FuncEntity func=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//							listFunc=commonDao.findByProperty(FuncEntity.class, "funcid", Integer.parseInt(cell.getStringCellValue()));
							if(CommonUtil.isNull(listFunc)){
								func=new FuncEntity();
							}else{
								func=listFunc.get(0);
							}
					    if(cell!=null && cell.getStringCellValue().length()>0){
							func.setFuncid(Integer.parseInt(cell.getStringCellValue()));
							func.setFuncname(row.getCell(1).getStringCellValue());
							cell=row.getCell(2);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							func.setIscheck(cell.getStringCellValue());
							func.setShowtext(row.getCell(3).getStringCellValue());
							commonDao.saveOrUpdate(func);
						}
						
					}
					
				}else if(sheet.getSheetName().equals("配电房表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from RoomEntity"));
					List<RoomEntity> listRoom=null;
					RoomEntity room=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listRoom=commonDao.findByProperty(RoomEntity.class, "roomid", Integer.parseInt(cell.getStringCellValue()));
						if(cell!=null && cell.getStringCellValue().length()>0){
							if(CommonUtil.isNull(listRoom)){
								room=new RoomEntity();
							}else{
								room=listRoom.get(0);
							}
							room.setRoomid(Integer.parseInt(cell.getStringCellValue()));
							room.setRoomtext(row.getCell(1).getStringCellValue());
							commonDao.saveOrUpdate(room);
						}
					}
				}else if(sheet.getSheetName().equals("配电房内电表及表间关系列表")){
					commonDao.executeSql("delete from meter");
					List<MeterEntity> listMeter=null;
					MeterEntity meter=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listMeter=commonDao.findByProperty(MeterEntity.class, "meterid", cell.getStringCellValue());
						if(cell!=null && cell.getStringCellValue().length()>0){
							listMeter=null;
							if(CommonUtil.isNull(listMeter)){
								meter=new MeterEntity();
							}else{
								meter=listMeter.get(0);
							}
							meter.setMeterid(cell.getStringCellValue());
							meter.setMetertext(row.getCell(1).getStringCellValue());
							cell=row.getCell(2);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							meter.setRoomid(cell.getStringCellValue());
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							meter.setLevel(Integer.parseInt(cell.getStringCellValue()));
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(!CommonUtil.isNull(cell.getStringCellValue())){
								listMeter=commonDao.findByProperty(MeterEntity.class, "meterid", cell.getStringCellValue());
								if(!CommonUtil.isNull(listMeter)){
									meter.setMeter(listMeter.get(0));
								}
							}
							commonDao.saveOrUpdate(meter);
						}
					}
				}else if(sheet.getSheetName().equals("表具上下限功能")){
					commonDao.deleteAllEntitie(commonDao.findHql("from MetershowfuncEntity"));
					List<MetershowfuncEntity> listShow=null;
					MetershowfuncEntity show=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listShow=commonDao.findHql(" from MetershowfuncEntity where meterid=? and funcid=?", cell.getStringCellValue(),cell1.getStringCellValue());
							if(CommonUtil.isNull(listShow)){
								show=new MetershowfuncEntity();
							}else{
								show=listShow.get(0);
							}
							show.setMeterid(cell.getStringCellValue());
							show.setFuncid(cell1.getStringCellValue());
							show.setTop(row.getCell(2).getNumericCellValue());
							show.setFloor(row.getCell(3).getNumericCellValue());
							commonDao.saveOrUpdate(show);
						}
					}
				}else if(sheet.getSheetName().equals("分类方式表")){
					commonDao.executeSql("delete from itemize");
					List<ItemizeEntity> listItem=null;
					ItemizeEntity itemize=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
	//						listItem=commonDao.findByProperty(ItemizeEntity.class, "itemizeid", Integer.parseInt(cell.getStringCellValue()));
							listItem=null;
							if(CommonUtil.isNull(listItem)){
								itemize=new ItemizeEntity();
							}else{
								itemize=listItem.get(0);
							}
							itemize.setItemizeid(Integer.parseInt(cell.getStringCellValue()));
							itemize.setItemizetext(row.getCell(1).getStringCellValue());
							cell=row.getCell(2);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							itemize.setLevel(Integer.parseInt(cell.getStringCellValue()));
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(!CommonUtil.isNull(cell.getStringCellValue())){
								listItem=commonDao.findByProperty(ItemizeEntity.class, "itemizeid", Integer.parseInt(cell.getStringCellValue()));
								if(!CommonUtil.isNull(listItem)){
									itemize.setItemize(listItem.get(0));
								}
							}
							commonDao.saveOrUpdate(itemize);
						}
					}
				}else if(sheet.getSheetName().equals("建筑物信息")){
					commonDao.deleteAllEntitie(commonDao.findHql("from BuildinginfoEntity"));
					List<BuildinginfoEntity> listBuilding=null;
					BuildinginfoEntity building=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
	//						listBuilding=commonDao.findByProperty(BuildinginfoEntity.class, "buildingid", cell.getStringCellValue());
							if(CommonUtil.isNull(listBuilding)){
								building=new BuildinginfoEntity();
							}else{
								building=listBuilding.get(0);
							}
							building.setBuildingid(cell.getStringCellValue());
							building.setBuildingname(row.getCell(1).getStringCellValue());
							cell=row.getCell(2);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							building.setBuildingtext(cell.getStringCellValue());
							
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							building.setWaterlimit(cell.getNumericCellValue());
							
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							building.setElectriclimit(cell.getNumericCellValue());
							
							cell=row.getCell(5);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							building.setGaslimit(cell.getNumericCellValue());
							
							cell=row.getCell(6);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							building.setHeatlimit(cell.getNumericCellValue());
							
							
							
							
							commonDao.saveOrUpdate(building);
						}
					}
				}else if(sheet.getSheetName().equals("院系信息")){
					commonDao.deleteAllEntitie(commonDao.findHql("from DepartmentinfoEntity"));
					List<DepartmentinfoEntity> listDepartment=null;
					DepartmentinfoEntity department=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
	//						listDepartment=commonDao.findByProperty(DepartmentinfoEntity.class, "buildingid", cell.getStringCellValue());
							if(CommonUtil.isNull(listDepartment)){
								department=new DepartmentinfoEntity();
							}else{
								department=listDepartment.get(0);
							}
							department.setDepartmentid(cell.getStringCellValue());
							department.setDepartmentname(row.getCell(1).getStringCellValue());
							department.setDepartmenttext(row.getCell(2).getStringCellValue());
							
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							department.setWaterlimit(cell.getNumericCellValue());
							
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							department.setElectriclimit(cell.getNumericCellValue());
							
							cell=row.getCell(5);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							department.setGaslimit(cell.getNumericCellValue());
							
							cell=row.getCell(6);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							department.setHeatlimit(cell.getNumericCellValue());
							
							
							commonDao.saveOrUpdate(department);
						}
					}
				}else if(sheet.getSheetName().equals("学校水电气总量计算表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from SchoolSumEntity"));
					List<SchoolSumEntity> listSS=null;
					SchoolSumEntity sum=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listSS=commonDao.findHql(" from SchoolSumEntity where funcid=? and meterid=?", cell.getStringCellValue(),cell1.getStringCellValue());
							if(CommonUtil.isNull(listSS)){
								sum=new SchoolSumEntity();
							}else{
								sum=listSS.get(0);
							}
							sum.setFuncid(cell.getStringCellValue());
							sum.setMeterid(cell1.getStringCellValue());
							cell=row.getCell(2);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(CommonUtil.isNull(cell.getStringCellValue())){
								sum.setFactor(1);
							}else{
								sum.setFactor(Integer.parseInt(cell.getStringCellValue()));
							}
							commonDao.saveOrUpdate(sum);
						}
					}
				}else if(sheet.getSheetName().equals("大楼水电气总量计算表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from BuildingSumEntity"));
					List<BuildingSumEntity> listBS=null;
					BuildingSumEntity sum=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell2=row.getCell(2);
							cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listBS=commonDao.findHql(" from BuildingSumEntity where buildingid=? and funcid=? and meterid=?", cell.getStringCellValue(),cell1.getStringCellValue(),cell2.getStringCellValue());
							if(CommonUtil.isNull(listBS)){
								sum=new BuildingSumEntity();
							}else{
								sum=listBS.get(0);
							}
							sum.setBuildingid(row.getCell(0).getStringCellValue());
							sum.setFuncid(cell1.getStringCellValue());
							sum.setMeterid(cell2.getStringCellValue());
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(CommonUtil.isNull(cell.getStringCellValue())){
								sum.setFactor(1);
							}else{
								sum.setFactor(Integer.parseInt(cell.getStringCellValue()));
							}
							commonDao.saveOrUpdate(sum);
						}
					}
				}else if(sheet.getSheetName().equals("院系水电气总量计算表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from DepartmentSumEntity"));
					List<DepartmentSumEntity> listDS=null;
					DepartmentSumEntity sum=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell2=row.getCell(2);
							cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listDS=commonDao.findHql(" from DepartmentSumEntity where departmentid=? and funcid=? and meterid=?", cell.getStringCellValue(),cell1.getStringCellValue(),cell2.getStringCellValue());
							if(CommonUtil.isNull(listDS)){
								sum=new DepartmentSumEntity();
							}else{
								sum=listDS.get(0);
							}
							sum.setDepartmentid(row.getCell(0).getStringCellValue());
							sum.setFuncid(cell1.getStringCellValue());
							sum.setMeterid(cell2.getStringCellValue());
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(CommonUtil.isNull(cell.getStringCellValue())){
								sum.setFactor(1);
							}else{
								sum.setFactor(Integer.parseInt(cell.getStringCellValue()));
							}
							commonDao.saveOrUpdate(sum);
						}
					}
				}else if(sheet.getSheetName().equals("学校分类用能计算用表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from SchoolItemizeEntity"));
					List<SchoolItemizeEntity> listSI=null;
					SchoolItemizeEntity itemize=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell2=row.getCell(2);
							cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listSI=commonDao.findHql(" from SchoolItemizeEntity where itemizeid=? and funcid=? and meterid=?", cell.getStringCellValue(),cell1.getStringCellValue(),cell2.getStringCellValue());
							if(CommonUtil.isNull(listSI)){
								itemize=new SchoolItemizeEntity();
							}else{
								itemize=listSI.get(0);
							}
							itemize.setItemizeid(cell.getStringCellValue());
							itemize.setFuncid(cell1.getStringCellValue());
							itemize.setMeterid(cell2.getStringCellValue());
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(CommonUtil.isNull(cell.getStringCellValue())){
								itemize.setFactor(1);
							}else{
								itemize.setFactor(Integer.parseInt(cell.getStringCellValue()));
							}
							commonDao.saveOrUpdate(itemize);
						}
					}
				}else if(sheet.getSheetName().equals("建筑分类用能计算用表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from BuildingItemizeEntity"));
					List<BuildingItemizeEntity> listBI=null;
					BuildingItemizeEntity itemize=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell2=row.getCell(2);
							cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell3=row.getCell(3);
							cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listBI=commonDao.findHql(" from BuildingItemizeEntity where buildingid=? and itemizeid=? and funcid=? and meterid=?", cell.getStringCellValue(),cell1.getStringCellValue(),cell2.getStringCellValue(),cell3.getStringCellValue());
							if(CommonUtil.isNull(listBI)){
								itemize=new BuildingItemizeEntity();
							}else{
								itemize=listBI.get(0);
							}
							itemize.setBuildingid(cell.getStringCellValue());
							itemize.setItemizeid(cell1.getStringCellValue());
							itemize.setFuncid(cell2.getStringCellValue());
							itemize.setMeterid(cell3.getStringCellValue());
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(CommonUtil.isNull(cell.getStringCellValue())){
								itemize.setFactor(1);
							}else{
								itemize.setFactor(Integer.parseInt(cell.getStringCellValue()));
							}
							commonDao.saveOrUpdate(itemize);
						}
					}
				}else if(sheet.getSheetName().equals("院系分类用能计算用表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from DepartmentItemizeEntity"));
					List<DepartmentItemizeEntity> listDI=null;
					DepartmentItemizeEntity itemize=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell2=row.getCell(2);
							cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell3=row.getCell(3);
							cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listDI=commonDao.findHql(" from DepartmentItemizeEntity where departmentid=? and itemizeid=? and funcid=? and meterid=?", cell.getStringCellValue(),cell1.getStringCellValue(),cell2.getStringCellValue(),cell3.getStringCellValue());
							if(CommonUtil.isNull(listDI)){
								itemize=new DepartmentItemizeEntity();
							}else{
								itemize=listDI.get(0);
							}
							itemize.setDepartmentid(cell.getStringCellValue());
							itemize.setItemizeid(cell1.getStringCellValue());
							itemize.setFuncid(cell2.getStringCellValue());
							itemize.setMeterid(cell3.getStringCellValue());
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(CommonUtil.isNull(cell.getStringCellValue())){
								itemize.setFactor(1);
							}else{
								itemize.setFactor(Integer.parseInt(cell.getStringCellValue()));
							}
							commonDao.saveOrUpdate(itemize);
						}
					}
				}else if(sheet.getSheetName().equals("学校单位用能统计计算表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from SchoolUnitEntity"));
					List<SchoolUnitEntity> listUnit=null;
					SchoolUnitEntity unit=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
	//						listUnit=commonDao.findByProperty(SchoolUnitEntity.class, "unitid", Integer.parseInt(cell.getStringCellValue()));
							if(CommonUtil.isNull(listUnit)){
								unit=new SchoolUnitEntity();
							}else{
								unit=listUnit.get(0);
							}
							unit.setUnitid(Integer.parseInt(cell.getStringCellValue()));
							unit.setUnittext(row.getCell(1).getStringCellValue());
							cell=row.getCell(2);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							unit.setUnitsum(Integer.parseInt(cell.getStringCellValue()));
							
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setWaterlimit(cell.getNumericCellValue());
							
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setElectriclimit(cell.getNumericCellValue());
							
							cell=row.getCell(5);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setGaslimit(cell.getNumericCellValue());
							
							cell=row.getCell(6);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setHeatlimit(cell.getNumericCellValue());
							
							
							
							
							commonDao.saveOrUpdate(unit);
						}
					}
				}else if(sheet.getSheetName().equals("建筑单位用能统计计算表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from BuildingUnitEntity"));
					List<BuildingUnitEntity> listBUnit=null;
					BuildingUnitEntity unit=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listBUnit=commonDao.findHql(" from BuildingUnitEntity where buildingid=? and unitid=?", cell.getStringCellValue(),Integer.parseInt(cell1.getStringCellValue()));
							if(CommonUtil.isNull(listBUnit)){
								unit=new BuildingUnitEntity();
							}else{
								unit=listBUnit.get(0);
							}
							unit.setBuildingid(cell.getStringCellValue());
							unit.setUnitid(Integer.parseInt(cell1.getStringCellValue()));
							unit.setUnittext(row.getCell(2).getStringCellValue());
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							unit.setUnitsum(Integer.parseInt(cell.getStringCellValue()));
							
							
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setWaterlimit(cell.getNumericCellValue());
							
							cell=row.getCell(5);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setElectriclimit(cell.getNumericCellValue());
							
							cell=row.getCell(6);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setGaslimit(cell.getNumericCellValue());
							
							cell=row.getCell(7);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setHeatlimit(cell.getNumericCellValue());
							
							
							commonDao.saveOrUpdate(unit);
						}
					}
				}else if(sheet.getSheetName().equals("院系单位用能统计计算表")){
					commonDao.deleteAllEntitie(commonDao.findHql("from DepartmentUnitEntity"));
					List<DepartmentUnitEntity> listDUnit=null;
					DepartmentUnitEntity unit=null;
					for(int j=1;j<=sheet.getLastRowNum();j++){
						row=sheet.getRow(j);
						
							cell=row.getCell(0);
							if(cell==null) break;
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if(cell!=null && cell.getStringCellValue().length()>0){
							cell1=row.getCell(1);
							cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	//						listDUnit=commonDao.findHql(" from DepartmentUnitEntity where departmentid=? and unitid=?", cell.getStringCellValue(),Integer.parseInt(cell1.getStringCellValue()));
							if(CommonUtil.isNull(listDUnit)){
								unit=new DepartmentUnitEntity();
							}else{
								unit=listDUnit.get(0);
							}
							unit.setDepartmentid(cell.getStringCellValue());
							unit.setUnitid(Integer.parseInt(cell1.getStringCellValue()));
							unit.setUnittext(row.getCell(2).getStringCellValue());
							cell=row.getCell(3);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							unit.setUnitsum(Integer.parseInt(cell.getStringCellValue()));
							
							
							cell=row.getCell(4);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setWaterlimit(cell.getNumericCellValue());
							
							cell=row.getCell(5);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setElectriclimit(cell.getNumericCellValue());
							
							cell=row.getCell(6);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setGaslimit(cell.getNumericCellValue());
							
							cell=row.getCell(7);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							unit.setHeatlimit(cell.getNumericCellValue());
							
							
							commonDao.saveOrUpdate(unit);
						}
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
