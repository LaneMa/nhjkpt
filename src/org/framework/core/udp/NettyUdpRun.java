package org.framework.core.udp;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.beans.factory.InitializingBean;


@Component
public class NettyUdpRun   implements InitializingBean {  
     
  
   
 

	@Autowired
	private UdpServerNetty udpServerNetty;
	@Autowired
	private UdpServerGateway udpServerGateway;
	@Autowired
	private UdpServerLight udpServerLight;


	public void afterPropertiesSet() throws Exception 
    {  
          
    	
		
		
		
        System.out.println("开始udpserver====================");
        udpServerNetty.start();  
        udpServerGateway.start();
        udpServerLight.start();
    	
    }  
	
	
	public static void main(String[] args)  
    {  
		DatagramChannelFactory udpChannelFactory = new NioDatagramChannelFactory(  
                Executors.newCachedThreadPool());  
        ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(udpChannelFactory);  
        bootstrap.setOption("reuseAddress", false);  
        bootstrap.setOption("child.reuseAddress", false);  
        bootstrap.setOption("readBufferSize", 1024);  
        bootstrap.setOption("writeBufferSize", 1024);
  
        bootstrap.setPipelineFactory(new ChannelPipelineFactory()  
        {  
  
            @Override  
            public ChannelPipeline getPipeline() throws Exception  
            {  
                ChannelPipeline pipleline = new DefaultChannelPipeline(); 
                pipleline.addLast("encode", new StringEncoder());  
                pipleline.addLast("decode", new StringDecoder());  
                pipleline.addLast("handler", new ReceiverHandler());  
                return pipleline;  
            }  
  
        });  
          
        bootstrap.bind(new InetSocketAddress(15000));  
        System.out.println("server start on "+new InetSocketAddress(9000));
    
    }
	
	
	
}  
