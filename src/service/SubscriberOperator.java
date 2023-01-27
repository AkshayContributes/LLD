package service;

import entity.Message;
import entity.Topic;
import entity.TopicSubscriber;

public class SubscriberOperator implements Runnable{

  private final Topic topic;
  private final TopicSubscriber topicSubscriber;


  public SubscriberOperator(Topic topic, TopicSubscriber topicSubscriber) {
    this.topic = topic;
    this.topicSubscriber = topicSubscriber;
  }

  public void wakeUpIfNeeded() {
    synchronized (topicSubscriber){
      topicSubscriber.notify();
    }
  }

  @Override public void run() {
    synchronized (topicSubscriber){
      do{
        int currOffset = topicSubscriber.getOffset().get();
        while(currOffset >= topic.getMessages().size()){
          try {
            topicSubscriber.wait();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }

        Message message = topic.getMessages().get(currOffset);
        topicSubscriber.getSubscriber().consume(message);

        topicSubscriber.getOffset().compareAndSet(currOffset, currOffset+1);
      }while(true);
    }
  }
}
