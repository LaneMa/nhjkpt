package org.framework.core.udp;

import java.net.InetSocketAddress;  
import java.net.SocketAddress;  
import java.util.concurrent.Executors;  
import org.apache.log4j.Logger;  
import org.framework.core.util.ResourceUtil;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;  
import org.jboss.netty.channel.Channel;  
import org.jboss.netty.channel.ChannelFutureListener;  
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.socket.DatagramChannelFactory;  
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;  
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Component;  
@Component("udpServerNetty")  
public class UdpServerNetty {  

    
    @Autowired  
    private ReceiverHandler receiverHandler;  
    
    private Channel channel;  
    private static final Logger logger = Logger.getLogger(UdpServerNetty.class  
            .getName());  
   
    public void start() {  
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
                pipleline.addLast("handler", receiverHandler);  
                return pipleline;  
            }  
  
        });  
        
        int udpPort=Integer.valueOf(ResourceUtil.getConfigByName("udpPort"));
        SocketAddress serverAddress = new InetSocketAddress(udpPort); 
        
        this.channel = bootstrap.bind(serverAddress);  
        logger.info("服务器启动   端口： " + serverAddress);  
        

    }  
   
    public void restart() {  
        this.stop();  
        this.start();  
    }  
   
    public void stop() {  
        if (this.channel != null) {  
            this.channel.close().addListener(ChannelFutureListener.CLOSE);  
        }  
    }  
}  