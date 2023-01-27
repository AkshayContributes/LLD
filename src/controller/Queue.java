package controller;

import entity.ISubscriber;
import entity.Message;
import entity.Topic;
import entity.TopicSubscriber;
import service.TopicOperator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Queue {
  private final Map<String, TopicOperator> topicOperators;

  public Queue(){
    this.topicOperators = new HashMap<>();
  }

  public Topic createTopic(final String topicName){
    final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
    TopicOperator topicOperator = new TopicOperator(topic);
    topicOperators.put(topic.getTopicId(), topicOperator);
    System.out.println("Created topic: "+ topic.getTopicName());
    return topic;
  }

  public void subscribe(final ISubscriber subscriber, final Topic topic){
      topic.addSubscriber(new TopicSubscriber(subscriber));
    System.out.println("Subscriber subscribed for topic: "+ topic.getTopicName());
  }

  public void publish(final Topic topic, final Message message){
    topic.addMessage(message);
    System.out.println(String.format("%s published to topic %s", message.getMsg(), topic.getTopicName()));
    new Thread(() -> topicOperators.get(topic.getTopicId()).publish()).start();

  }

}
