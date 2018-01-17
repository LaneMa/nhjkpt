package org.framework.core.udp; 


import nhjkpt.configmanage.service.recorddata.RecordDataServiceI;

import org.apache.log4j.Logger;  
import org.jboss.netty.channel.ChannelFutureListener;  
import org.jboss.netty.channel.ChannelHandlerContext;  
import org.jboss.netty.channel.ExceptionEvent;  
import org.jboss.netty.channel.MessageEvent;  
import org.jboss.netty.channel.SimpleChannelHandler;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  
@Component("gatewayHandler")  
public class GatewayHandler extends SimpleChannelHandler  
{  
    private static final Logger logger=Logger.getLogger(GatewayHandler.class.getName());  
    
    @Autowired
    private RecordDataServiceI recordDataService;
    @Override  
    public void messageReceived(ChannelHandlerContext ctx,MessageEvent e) 
    {  
        try{
        	String recMsg=(String)e.getMessage(); 
            recMsg=new String(recMsg.getBytes(),"UTF-8");
            recordDataService.splitShowStr(recMsg);
        }catch(Exception ex){
        	ex.printStackTrace();
        }
    }  
      
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx,ExceptionEvent e)  
    {  
        logger.error(e.getCause());  
        if(e.getChannel() !=null)  
        {  
            e.getChannel().close().addListener(ChannelFutureListener.CLOSE);  
        }  
    }  
}   
