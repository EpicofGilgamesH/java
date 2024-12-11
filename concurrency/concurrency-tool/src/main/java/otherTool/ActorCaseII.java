package otherTool;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用Actor实现累加器
 */
public class ActorCaseII {

	static class CounterActor extends UntypedAbstractActor {

		private int counter = 0;

		@Override
		public void onReceive(Object message) throws Throwable {
			// 如果接收到的消息是数字类型,执行累加操作
			// 否则打印counter的值
			if (message instanceof Number) {
				counter += ((Number) message).intValue();
			} else {
				System.out.println(counter);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ActorSystem system = ActorSystem.create("CounterSystem");
		ExecutorService es = Executors.newFixedThreadPool(4);
		ActorRef counterActor = system.actorOf(Props.create(CounterActor.class));
		for (int i = 0; i < 4; i++) {
			es.execute(() -> {
				for (int j = 0; j < 100000; j++) {
					counterActor.tell(1,ActorRef.noSender());
				}
			});
		}
		es.shutdown();
		Thread.sleep(1000);
		counterActor.tell("",ActorRef.noSender());
	}
}
