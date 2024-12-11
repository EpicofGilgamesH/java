package otherTool;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

/**
 * Actor模型:面向对象原生的并发模型
 */
public class ActorCase {

	static class HelloActor extends UntypedAbstractActor {

		@Override
		public void onReceive(Object message) throws Throwable {
			System.out.println("Hello " + message);
		}
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("HelloSystem");
		ActorRef helloActor = system.actorOf(Props.create(HelloActor.class));
		helloActor.tell("Actor", ActorRef.noSender());
	}
}
