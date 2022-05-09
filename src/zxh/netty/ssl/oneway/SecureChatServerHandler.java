package zxh.netty.ssl.oneway;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.net.ssl.SSLSession;
import javax.security.cert.X509Certificate;

public class SecureChatServerHandler extends SimpleChannelInboundHandler<String> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Once session is secured, send a greeting and register the channel to
        // the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture()
                .addListener(new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> arg0)
                            throws Exception {

                        if (arg0.isSuccess()) {
                            System.out.println("握手成功");

                            SSLSession ss = ctx.pipeline().get(SslHandler.class).engine().getSession();
                            System.out.println("cipherSuite:" + ss.getCipherSuite());
                            try {
                                X509Certificate cert = ss.getPeerCertificateChain()[0];
                                String info = null;
                                // 获得证书版本
                                info = String.valueOf(cert.getVersion());
                                System.out.println("证书版本:" + info);
                                // 获得证书序列号
                                info = cert.getSerialNumber().toString(16);
                                System.out.println("证书序列号:" + info);
                                // 获得证书有效期
                                Date beforedate = cert.getNotBefore();
                                info = new SimpleDateFormat("yyyy/MM/dd").format(beforedate);
                                System.out.println("证书生效日期:" + info);
                                Date afterdate = (Date) cert.getNotAfter();
                                info = new SimpleDateFormat("yyyy/MM/dd").format(afterdate);
                                System.out.println("证书失效日期:" + info);
                                // 获得证书主体信息
                                info = cert.getSubjectDN().getName();
                                System.out.println("证书拥有者:" + info);
                                // 获得证书颁发者信息
                                info = cert.getIssuerDN().getName();
                                System.out.println("证书颁发者:" + info);
                                // 获得证书签名算法名称
                                info = cert.getSigAlgName();
                                System.out.println("证书签名算法:" + info);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("握手失败");
                        }
                        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n");
                        ctx.writeAndFlush("Your session is protected by " + ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() + " cipher suite.\n");
                        channels.add(ctx.channel());
                    }
                });
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg)
            throws Exception {
        // Send the received message to all channels but the current one.
        for (Channel c : channels) {
            if (c != ctx.channel()) {
                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
            } else {
                c.writeAndFlush("[you] " + msg + '\n');
            }
        }

        // Close the connection if the client has sent 'bye'.
        if ("bye".equals(msg.toLowerCase())) {
            ctx.close();
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
