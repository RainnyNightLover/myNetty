package zxh.netty.ssl.oneway;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import javax.net.ssl.SSLSession;
import javax.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecureChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

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
                    }
                });
    }

}
