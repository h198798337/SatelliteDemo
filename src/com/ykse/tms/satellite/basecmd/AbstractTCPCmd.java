package com.ykse.tms.satellite.basecmd;

import java.io.IOException;
import java.net.UnknownHostException;

//import org.apache.log4j.Logger;

import com.ykse.socket.TcpConnect;

/**
 * TCP命令发送抽象类
 * @author tony
 *
 * @param <T>
 */
public abstract class AbstractTCPCmd<T> extends AbstractSocketCmd<T, TcpConnect> {
//	protected static Logger logger = Logger.getLogger("audio");
	
	/**
	 * 构造函数
	 * @param host 主机名(ip)
	 * @param tcpPort 端口
	 * @param timeout 超时值
	 */
	public AbstractTCPCmd(String host, int tcpPort, int timeout) {
		super(host, tcpPort, timeout);
	}

	@Override
	protected TcpConnect createSocket(String host, int tcpPort, int timeout) throws UnknownHostException, IOException {
		return new TcpConnect(host, tcpPort, timeout);
	}


}
