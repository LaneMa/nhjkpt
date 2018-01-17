package nhjkpt.configmanage.service.impl.roomlight;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.entity.roomlight.RoomlightEntity;
import nhjkpt.configmanage.service.roomlight.RoomlightServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.framework.core.util.ResourceUtil;

@Service("roomlightService")
@Transactional
public class RoomlightServiceImpl extends CommonServiceImpl implements RoomlightServiceI {
	public void ReceiveUdp(String msg){
		try{
			System.out.println("ReceiveUdp====:"+msg);
			String[] code=msg.split(";");
			String buildingid=code[0];
			String layerid=code[1];
			String roomid=code[2];
			String controltype=code[3];
			String controldata1=code[4];
			String controldata2=code[5];
			String lightdata=code[6];
			String mannumber=code[7];
			String lightnumber=code[8];
			String lightstate=code[9];
			String mark1=code[10];
			String mark2=code[11];
			
			RoomlightEntity roomlight=new RoomlightEntity();
			List<RoomlightEntity> roomlightList=this.findHql(" from RoomlightEntity where buildingid="+buildingid+" and layerid="+layerid+" and roomid="+roomid);
			if(roomlightList!=null && roomlightList.size()>0){
				roomlight=roomlightList.get(0);
				roomlight.setLightdata(Integer.parseInt(lightdata));
				roomlight.setMannumber(Integer.parseInt(mannumber));
				roomlight.setLightnumber(Integer.parseInt(lightnumber));
				roomlight.setLightstate(Integer.parseInt(lightstate));
				roomlight.setMark1(Integer.parseInt(mark1));
				roomlight.setMark2(Integer.parseInt(mark2));
				this.saveOrUpdate(roomlight);
				
				if(roomlight.getControltype()!=Integer.parseInt(controltype)){
					//发送udp
					SendUdp(roomlight);
					
				}
				
				if(roomlight.getControltype()==2 && roomlight.getControldata1()!=roomlight.getLightstate()){
					//发送udp
					SendUdp(roomlight);
				}
				
				
			}else{
				roomlight.setBuildingid(Integer.parseInt(buildingid));
				roomlight.setBuildingname(buildingid+"号楼");
				roomlight.setLayerid(Integer.parseInt(layerid));
				roomlight.setRoomid(Integer.parseInt(roomid));
				roomlight.setControltype(Integer.parseInt(controltype));
				roomlight.setControldata1(Integer.parseInt(controldata1));
				roomlight.setControldata2(Integer.parseInt(controldata2));
				roomlight.setLightdata(Integer.parseInt(lightdata));
				roomlight.setMannumber(Integer.parseInt(mannumber));
				roomlight.setLightnumber(Integer.parseInt(lightnumber));
				roomlight.setLightstate(Integer.parseInt(lightstate));
				roomlight.setMark1(Integer.parseInt(mark1));
				roomlight.setMark2(Integer.parseInt(mark2));
				
				this.save(roomlight);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public void SendUdp(RoomlightEntity roomlight){
		
		String str=roomlight.getBuildingid()+";"+roomlight.getLayerid()+";"+roomlight.getRoomid()+";"+roomlight.getControltype()+";"+roomlight.getControldata1()+";"+roomlight.getControldata2()+";"+roomlight.getLightdata()+";"+roomlight.getMannumber()+";"+roomlight.getLightnumber()+";"+roomlight.getLightstate()+";"+roomlight.getMark1()+";"+roomlight.getMark2();
		System.out.println("SendUdp====:"+str);
		
		
		
		try {
			
			//byte[] message="testinfo分水利电力111我1111".getBytes();
			
			byte[] message=str.getBytes();
			
			// 初始化一个UDP包。
			// DatagramPacket的构造方法中必须使用InetAddress，而不能是IP地址或者域名

			InetAddress address = InetAddress.getByName(ResourceUtil.getConfigByName("udpLight_client"));
			int port= Integer.parseInt(ResourceUtil.getConfigByName("udpLight_chlient_port"));
			
			
			// 注意：如果在构造DatagramPacket时，不提供IP地址和端口号，
			// 则需要调用DatagramSocket的connect方法，否则无法发送UDP包
			DatagramPacket packet = new DatagramPacket(message, message.length);
			DatagramSocket dsocket = new DatagramSocket();
			dsocket.connect(address, port);
			dsocket.send(packet);
			dsocket.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
		
	}
}