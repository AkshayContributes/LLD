package service;

import entity.Topic;
import entity.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;

public class TopicOperator implements  Runnable{

  private final Topic topic;
  private final Map<String, SubscriberOperator> subscriberOperators;
  public TopicOperator(Topic topic) {
      this.topic = topic;
      this.subscriberOperators = new HashMap<>();
  }



  public void publish() {
    for(TopicSubscriber topicSubscriber : topic.getSubscribers()){
      startSubscriberOperator(topicSubscriber);
    }
  }

  private void startSubscriberOperator(TopicSubscriber topicSubscriber) {
    final String subscriberId = topicSubscriber.getSubscriber().getId();
    if(!subscriberOperators.containsKey(subscriberId)){
      final SubscriberOperator subscriberOperator = new SubscriberOperator(topic, topicSubscriber);
      subscriberOperators.put(subscriberId, subscriberOperator);
      new Thread((Runnable) subscriberOperator).start();
    }

    final SubscriberOperator subscriberOperator = subscriberOperators.get(subscriberId);
    subscriberOperator.wakeUpIfNeeded();
  }

  @Override public void run() {

  }
}
