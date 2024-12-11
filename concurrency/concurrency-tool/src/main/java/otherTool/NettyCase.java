package otherTool;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 简单示例
 */
public class NettyCase {

	public void start() {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		// boss线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		// worker线程组
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(serverHandler);
						}
					});
			// bind服务端端口
			ChannelFuture f = b.bind(9090).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	static class EchoServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			ctx.write(msg);
		}

		// 处理读完成事件
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}

		// 处理异常事件
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
		}
	}

	public static void main(String[] args) {
		NettyCase nettyCase=new NettyCase();
		nettyCase.start();
	}
}
