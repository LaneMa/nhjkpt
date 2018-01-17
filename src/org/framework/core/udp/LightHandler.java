package org.framework.core.udp; 
import java.util.Random;  

import nhjkpt.configmanage.service.meterfuncshowhistory.MeterFuncShowHistoryServiceI;
import nhjkpt.configmanage.service.roomlight.RoomlightServiceI;

import org.apache.log4j.Logger;  
import org.jboss.netty.buffer.ChannelBuffer;  
import org.jboss.netty.buffer.DynamicChannelBuffer;  
import org.jboss.netty.channel.ChannelFutureListener;  
import org.jboss.netty.channel.ChannelHandlerContext;  
import org.jboss.netty.channel.ExceptionEvent;  
import org.jboss.netty.channel.MessageEvent;  
import org.jboss.netty.channel.SimpleChannelHandler;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  
@Component("lightHandler")  
public class LightHandler extends SimpleChannelHandler  
{  
    private static final Logger logger=Logger.getLogger(LightHandler.class.getName());  
    @Autowired
    private RoomlightServiceI roomlightService;
      
    @Override  
    public void messageReceived(ChannelHandlerContext ctx,MessageEvent e) 
    {  
        try{
        	String recMsg=(String)e.getMessage(); 
            recMsg=new String(recMsg.getBytes(),"UTF-8");
            roomlightService.ReceiveUdp(recMsg);
            
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
