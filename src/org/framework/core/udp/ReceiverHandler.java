package org.framework.core.udp; 
import java.util.Random;  

import nhjkpt.configmanage.service.meterfuncshowhistory.MeterFuncShowHistoryServiceI;

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
@Component("receiverHandler")  
public class ReceiverHandler extends SimpleChannelHandler  
{  
    private static final Logger logger=Logger.getLogger(ReceiverHandler.class.getName());  
    @Autowired
    private MeterFuncShowHistoryServiceI meterFuncShowHistoryService;
      
    @Override  
    public void messageReceived(ChannelHandlerContext ctx,MessageEvent e) 
    {  
        try{
        	String recMsg=(String)e.getMessage(); 
            recMsg=new String(recMsg.getBytes(),"UTF-8");
            meterFuncShowHistoryService.splitShowStr(recMsg);
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        
         
        
//        Random random=new Random();  
//        int backWord=random.nextInt(10000);  
//        ChannelBuffer responseBuffer=new DynamicChannelBuffer(4);  
//        responseBuffer.readBytes(backWord);  
//        e.getChannel().write(responseBuffer);  
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
