package com.epic;
import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AttachMain {

	public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, InterruptedException {
		List<VirtualMachineDescriptor> listBefore = VirtualMachine.list();
		// agentmain()方法所在jar包
		String jar = "D:\\my-projects\\java\\java-agent\\target\\java-agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar";

		for (VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()) {
			// 针对指定名称的JVM实例
			if (virtualMachineDescriptor.displayName().equals("com.epic.AgentTest")) {
				System.out.println("将对该进程的vm进行增强：org.example.agent.AgentTest的vm进程, pid=" + virtualMachineDescriptor.id());
				// attach到新JVM
				VirtualMachine vm = VirtualMachine.attach(virtualMachineDescriptor);
				// 加载agentmain所在的jar包
				vm.loadAgent(jar);
				// detach
				vm.detach();
				break;
			}
		}

		TimeUnit.MINUTES.sleep(10);
	}
}
